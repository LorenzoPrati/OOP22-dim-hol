package model.common;

public class IDUtil {
    
    int counter = 0;

    public int generateID() {
        counter++;
        return counter;
    }

    public void reset() {
        this.counter = 0;
    }
}
