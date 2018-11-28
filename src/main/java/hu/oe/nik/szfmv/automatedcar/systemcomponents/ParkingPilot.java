package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.visualization.Gui;

public class ParkingPilot extends SystemComponent {

    Gui gui = new Gui();

    public void setGui(Gui gui)
    {
        this.gui = gui;
    }

    public ParkingPilot(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
    }


    @Override
    public void loop() {
        if (isParkingTable())
        {
            gui.getDashboard().setPPButtonVisibility();

        }
    }

    private boolean SearchEnoughSpaceRight() {
        return true;
    }

    private boolean SearchEnoughSpaceLeft() {
        return true;
    }

    private boolean isParkingTable() {
        return false;
    }

    private void automaticalParking() {
        
    }
}
