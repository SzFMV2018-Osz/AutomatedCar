package hu.oe.nik.szfmv.visualization;

import java.util.HashMap;
import java.util.Map;

public class AutoTransmission {

    public enum Gear {
        R(1), D(4), N(3), P(2);
        private int gearNum;

        Gear(int gearNum) {
            this.gearNum = gearNum;
        }

        private static Map map = new HashMap<>();

        static {
            for (Gear gear : Gear.values()) {
                map.put(gear.gearNum, gear);
            }
        }

        public static Gear valueOf(int gear) {
            return (Gear) map.get(gear);
        }

        public int getValue() {
            return gearNum;
        }

    }

    public Gear actGear;

    public void ShiftUp() {
        int i = actGear.getValue() + 1;
        if (i < 5) {
            actGear = Gear.valueOf(i);
        }
    }

    public void ShiftDown() {
        int i = actGear.getValue() - 1;
        if (i > 0) {
            actGear = Gear.valueOf(i);
        }
    }

    public AutoTransmission() {
        actGear = Gear.P;
    }

}
