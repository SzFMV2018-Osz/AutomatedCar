package hu.oe.nik.szfmv.visualization;

import java.io.Console;

public class Index {
    public enum Direction {left, right, none, warningsign}

    ;
    public Direction actIndex;


    public Index() {
        Direction actIndex = Direction.none;
    }


    public void TurnLeft() {
        actIndex=Direction.left;
    }
    public void TurnRight(){
        actIndex=Direction.right;
    }
    public void Warning(){
        actIndex=Direction.warningsign;

    }

    public void SwitchBack() {
        actIndex = Direction.none;
    }

}

