// package hu.oe.nik.szfmv.automatedcar.systemcomponents;

// import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
// import java.awt.*;
// import java.awt.geom.Point2D;

// /**
//  * Steering system is responsible for the turning of the car.
//  */
// public class SteeringSystem extends SystemComponent {
//     private static final double MAX_WHEEL_ABS = 100;
//     private static final double MAX_ANGLE_ABS = 10;
//     private int turningAngle;
//     private int steeringWheel;
//     private Point adjustedPosition;
//     private int adjustedRotation;


//     /**
//      * Creates a steering system that connects the Virtual Function Bus
//      *
//      * @param virtualFunctionBus {@link VirtualFunctionBus} used to connect {@link SystemComponent}s
//      */
//     public SteeringSystem(VirtualFunctionBus virtualFunctionBus) {
//         super(virtualFunctionBus);

//         turningAngle = 0;
//         adjustedPosition = new Point(0, 0);
//         adjustedRotation = 0;
//     }

//     @Override
//     public void loop() {
//         this.steeringWheel = this.virtualFunctionBus.samplePacket.getWheelPosition();

//         calculateTurningAngle(this.steeringWheel);
//         adjustPositionAndRotation();
//     }


//     /**
//      * @param wheelPosition Degree of the steering wheel
//      */
//     public void calculateTurningAngle(int wheelPosition) {
//         this.turningAngle = (int) Math.round(((double) wheelPosition / MAX_WHEEL_ABS) * MAX_ANGLE_ABS);
//     }

//     public void adjustPositionAndRotation() {
//         // Basically the car rotates around its top-left corner so the position needs to be adjusted to make
//         // it look like it turns around its center.
//         int x = this.virtualFunctionBus.carPacket.getxPosition();
//         int y = this.virtualFunctionBus.carPacket.getyPosition();
//         int width = this.virtualFunctionBus.carPacket.getCarWidth();
//         int height = this.virtualFunctionBus.carPacket.getCarHeigth();
//         float rotation = this.virtualFunctionBus.carPacket.getRotation();

        
//         final int threeQuarterCircle = 270;
//         double carHeading = Math.toRadians(threeQuarterCircle) - rotation;
//         double halfWidth = (double) width / 2;
//         double halfHeight = (double) height / 2;

//         Point2D carPosition = new Point2D.Double(x, y);
//         Object[] carPositionAndHeading = getCarPositionAndCarHead(carPosition, carHeading, virtualFunctionBus.powertrainPacket.getSpeed(),
//                 this.turningAngle, new int[]{width, height});
//         if (carPositionAndHeading[0].getClass() == Point2D.Double.class) {
//             carPosition = new Point2D.Double(((Point2D) carPositionAndHeading[0]).getX(),
//                     ((Point2D) carPositionAndHeading[0]).getY());
//         }

//         if (carPositionAndHeading[1].getClass() == Double.class) {
//             carHeading = (Double) carPositionAndHeading[1];
//         }

//         adjustedPosition = new Point((int)Math.round(carPosition.getX() - halfWidth), (int)Math.round(carPosition.getY() - halfHeight));

//         adjustedRotation = (int)Math.round(Math.toRadians(threeQuarterCircle) - carHeading);
//     }

//     public int getTurningAngle() {
//         return this.turningAngle;
//     }

//     public int getAdjustedRotation() {
//         return this.adjustedRotation;
//     }

//     public Point getAdjustedPosition() {
//         return this.adjustedPosition;
//     }
// }
