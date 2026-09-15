package edu.eci.dosw.bowling;

public class Frame {
    private FrameType type;
    private int firstShot = -1;
    private int secondShot = -1;

    public Frame(FrameType type) {
        this.type = type;
        
    }

    public int getScore() {
        return this.firstShot + this.secondShot;
    }

    public FrameType getType() {
        return type;
    }

    public void setFirstShot(int firstShot) {
        this.firstShot = firstShot;
    }

    public void setSecondShot(int secondShot) {
        this.secondShot = secondShot;
    }

    public int getFirstShot() {
        return firstShot;
    }

    public int getSecondShot() {
        return secondShot;
    }

    
}
