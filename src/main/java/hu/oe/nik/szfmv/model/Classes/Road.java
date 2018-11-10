package hu.oe.nik.szfmv.model.Classes;

import java.awt.*;
import java.util.HashMap;

public class Road extends Static {
    public static HashMap<String, Polygon> roadPolyMap = new HashMap<>();

    private static final Polygon ROAD_2LANE_STRAIGHT = new Polygon(new int[]{0,350,350,0}, new int[]{0,0,350,350}, 4);

    //525x525s
    private static final Polygon ROAD_2LANE_90_LEFT = new Polygon(
            new int[]{0, 143, 227, 307, 358, 408, 461, 502, 525, 175, 162, 140, 97, 48, 0},
            new int[]{0, 21, 51, 96, 138, 197, 272, 364, 525, 525, 461, 422, 380, 356, 350},
            15);

    //525x525
    private static final Polygon ROAD_2LANE_90_RIGHT = new Polygon(
            new int[]{525, 382, 297, 212, 177, 117, 64, 23, 0, 350, 363, 385, 428, 477, 525},
            new int[]{0, 21, 51, 96, 138, 197, 272, 364, 525, 525, 461, 422, 380, 356, 350},
            15);

    //400x370
    private static final Polygon ROAD_2LANE_45_LEFT = new Polygon(
            new int[]{0, 248, 325, 358, 382, 398, 400, 50, 48, 43, 36, 27, 17},
            new int[]{248, 0, 97, 162, 230, 310, 370, 370, 342, 321, 300, 285, 267},
            13);

    //400x370
    private static final Polygon ROAD_2LANE_45_RIGHT = new Polygon(
            new int[]{400, 152, 75, 42, 18, 2, 0, 350, 352, 357, 364, 373, 382},
            new int[]{248, 0, 97, 162, 230, 310, 370, 370, 342, 321, 300, 285, 267},
            13);

    //366x366
    private static final Polygon ROAD_2LANE_6_LEFT = new Polygon(
            new int[]{0, 347, 355, 361, 364, 366, 17, 13, 8},
            new int[]{37, 0, 71, 153, 226, 366, 366, 211, 129},
            9);

    //366x366
    private static final Polygon ROAD_2LANE_6_RIGHT = new Polygon(
            new int[]{366, 19, 11, 5, 2, 0, 349, 253, 358},
            new int[]{37, 0, 71, 153, 226, 366, 366, 211, 129},
            9);

    //874*1399
    private static final Polygon ROAD_2LANE_ROTARY = new Polygon(
            new int[]{0, 0, 350, 450, 525, 525, 875, 875, 950, 1050, 1399, 1399, 1050, 950, 875, 875, 525, 525, 450, 350},
            new int[]{875, 525, 525, 450, 350, 0, 0, 350, 450, 525, 525, 875, 875, 950, 1050, 1399, 1399, 1050, 950, 875},
            20);

    //1399x965
    private static final Polygon ROAD_2LANE_TJUNCTIONLEFT = new Polygon(
            new int[]{0, 0, 350, 450, 525, 525, 875, 875, 525, 525, 450, 350},
            new int[]{875, 525, 525, 450, 350, 0, 0, 1399, 1399, 1050, 950, 875},
            12);

    //1399x965
    private static final Polygon ROAD_2LANE_TJUNCTIONRIGHT = new Polygon(
            new int[]{875, 875, 525, 425, 350, 350, 0, 0, 350, 350, 425, 525},
            new int[]{875, 525, 525, 450, 350, 0, 0, 1399, 1399, 1050, 950, 875},
            12);

    /**
     *
     * @param x coordinate
     * @param y coordinate
     * @param imageFileName the name of the imagefile used for this type
     */
    public Road(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
        hashMapInit();
    }

    /**
     *
     * @param x coordinate
     * @param y coordinate
     * @param imageFileName the name of the imagefile used for this type
     * @param m11 transformation
     * @param m12 transformation
     * @param m21 transformation
     * @param m22 transformation
     */
    public Road(int x, int y, String imageFileName, double m11, double m12, double m21, double m22) {
        super(x, y, imageFileName, m11, m12, m21, m22);
        hashMapInit();
    }

    private void hashMapInit() {
        roadPolyMap.put("road_2lane_90left.png", ROAD_2LANE_90_LEFT);
        roadPolyMap.put("road_2lane_straight.png", ROAD_2LANE_STRAIGHT);
        roadPolyMap.put("road_2lane_90right.png", ROAD_2LANE_90_RIGHT);
        roadPolyMap.put("road_2lane_45left.png", ROAD_2LANE_45_LEFT);
        roadPolyMap.put("road_2lane_45right.png", ROAD_2LANE_45_RIGHT);
        roadPolyMap.put("road_2lane_6left.png", ROAD_2LANE_6_LEFT);
        roadPolyMap.put("road_2lane_6right.png", ROAD_2LANE_6_RIGHT);
        roadPolyMap.put("road_2lane_rotary.png", ROAD_2LANE_ROTARY);
        roadPolyMap.put("road_2lane_tjunctionleft.png", ROAD_2LANE_TJUNCTIONLEFT);
        roadPolyMap.put("road_2lane_tjunctionright.png", ROAD_2LANE_TJUNCTIONRIGHT);
    }
}
