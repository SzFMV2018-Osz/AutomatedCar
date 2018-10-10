package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.powertrain.PowertrainPacket;

/**
 * Powertrain system is responsible for the movement of the car.
 */
public class PowertrainSystem extends SystemComponent {
    private static final int PERCENTAGE_DIVISOR = 100;

    public static final int MAX_RPM = 6000;
    public static final int EXPECTED_RPM = 750;

    private PowertrainPacket powertrainPacket;

    private double speed;
    private int expectedRPM;
    private int actualRPM;

    /**
     * Creates a powertrain system that connects the Virtual Function Bus
     *
     * @param virtualFunctionBus {@link VirtualFunctionBus} used to connect {@link SystemComponent}s
     */
    public PowertrainSystem(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);

        this.powertrainPacket = new PowertrainPacket();

        this.expectedRPM = EXPECTED_RPM;
        this.actualRPM = this.expectedRPM;
    }

    @Override
    public void loop() {
        int gasPedal = virtualFunctionBus.samplePacket.getGaspedalPosition();
        speed = gasPedal * 0.8;
        //TODO write this
    }

    public double getSpeed() {
        return this.speed;
    }

    /**
     * Calculate the actual rpm of the engine
     *
     * @param gaspedalPos position of the gaspedal
     * @return the actual rpm
     */
    public int calculateActualRpm(int gaspedalPos) {
        if (gaspedalPos == 0) {
            int actual = this.expectedRPM;
            this.powertrainPacket.setRpm(actual);
            return actual;
        } else {
            double multiplier = ((double) (MAX_RPM - this.expectedRPM) / PERCENTAGE_DIVISOR);
            int actual = (int) ((gaspedalPos * multiplier) + this.expectedRPM);
            this.powertrainPacket.setRpm(actual);
            return actual;
        }
    }
}

