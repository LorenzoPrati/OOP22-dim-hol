package it.unibo.dimhol;

public class StateInfo {
    private final int frames;
    private final int num;

    /*TODO : remove numbers */
    public StateInfo(int[] info){
        this.frames = info[0];
        this.num = info[1];
    }

    public int getFrames() {
        return this.frames;
    }

    public int getNum() {
        return this.num;
    }
}
