package hu.oe.nik.szfmv.common;


public class Turning {

    private Car car;
    private int turningCircle;

    public Turning() {
        car = new Car();
        turningCircle = 0;
    }

    public Car getCar() {
        return car;
    }

    public int getTurningCircle() {
        return turningCircle;
    }

    public void CalculateTurningCircle(int turningDegree){
        turningCircle = (int)(car.getWheelbase()/Math.tan(turningDegree) + car.getWidth());
    }
}
