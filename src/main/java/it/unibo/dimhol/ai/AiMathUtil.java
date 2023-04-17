package it.unibo.dimhol.ai;

public class AiMathUtil {

    private double playerX;
    private double playerY;
    private double enemyX;
    private double enemyY;

    public AiMathUtil(final double pX, final double pY, final double eX, final double eY) {
        this.playerX = pX;
        this.playerY = pY;
        this.enemyX = eX;
        this.enemyY = eY;
    }

    public int getAngle() {
        double m = (playerY - enemyY) / (playerX - enemyX);
        return (int) (Math.atan(m) * 180/Math.PI);
    }
}
