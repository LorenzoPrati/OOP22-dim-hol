package it.unibo.dimhol;

public class StateInfo {
    private final int frames;
    private final int num;

    public StateInfo(int frames, int num){
        this.frames = frames;
        this.num = num;
    }

    public int getFrames() {
        return this.frames;
    }

    public int getNum() {
        return this.num;
    }
}
