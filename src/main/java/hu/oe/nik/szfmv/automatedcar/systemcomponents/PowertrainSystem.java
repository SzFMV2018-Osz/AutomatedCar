package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

/**
 * Powertrain system is responsible for the movement of the car.
 */
public class PowertrainSystem extends SystemComponent {
    private double speed;
    private DummyGearEnum gearState;
    private int brake;

    /**
     * Creates a powertrain system that connects the Virtual Function Bus
     *
     * @param virtualFunctionBus {@link VirtualFunctionBus} used to connect {@link SystemComponent}s
     */
    public PowertrainSystem(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
    }

    @Override
    public void loop() {
        this.gearState = DummyGearEnum.D;
        int gasPedal = virtualFunctionBus.samplePacket.getGaspedalPosition();
        this.brake = 1;
        speed = gasPedal * 0.8;
        //TODO write this
    }

    public double getSpeed() {
        return this.speed;
    }

    private void doPowertrain() {
        double acceleration = 0.5; // Todo - calculate acceleration

        switch (gearState) {
            case N:
                if (brake > 0) {
                    if (Math.abs(speed) > 0) {
                        modifySpeed(acceleration);
                    }
                    if (speed < 0) {
                        speed = 0;
                        // TODO set speed in VFC
                    }
                }
                break;

            case R:
                if (brake == 0) {
                    if (acceleration < 0 && (speed > 10)) {
                        modifySpeed(acceleration);
                    }
                    if (acceleration > 0 && speed < 10) {
                        modifySpeed(acceleration);
                    }

                } else {
                    if (speed < 0) {
                        modifySpeed(acceleration);
                    }
                    if (speed > 0) {
                        speed = 0;
                        // TODO set speed in VFC
                    }
                }
                break;

            case D:
                // TODO - calculate gear shift

                if (brake == 0) {
                    if (acceleration > 0 && speed < 10) {
                        modifySpeed(acceleration);
                    }

                    if (acceleration < 0 && speed > 10) {
                        modifySpeed(acceleration);
                    }
                } else {
                    if (speed > 0) {
                        modifySpeed(acceleration);
                    }
                    if (speed < 0) {
                        speed = 0;
                        // TODO set speed in VFC
                    }
                }
                break;

            default:
                break;
        }
    }

    private void modifySpeed(double acceleration) {
        this.speed += acceleration;
    }
}

