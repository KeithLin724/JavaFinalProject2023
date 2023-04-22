package Game.gameBase;

import java.awt.Point;

/**
 * This is a class to pass 2 of thing
 */

public class GameUnitPair {

    public float x, y;

    public GameUnitPair() {
        this.x = 0;
        this.y = 0;
    }

    public GameUnitPair(int arg1, int arg2) {
        this.x = arg1;
        this.y = arg2;
    }

    public GameUnitPair(float arg1, float arg2) {
        this.x = arg1;
        this.y = arg2;
    }

    public GameUnitPair(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public GameUnitPair(GameUnitPair obj) {
        this.copy(obj);
    }

    public static GameUnitPair buildGameUnitPair(int arg1, int arg2) {
        return new GameUnitPair(arg1, arg2);
    }

    public static GameUnitPair buildGameUnitPair(float arg1, float arg2) {
        return new GameUnitPair(arg1, arg2);
    }

    public static GameUnitPair buildGameUnitPair(Point point) {
        return new GameUnitPair(point);
    }

    public GameUnitPair getPoint() {
        return new GameUnitPair(this);
    }

    public Point toIntPoint() {
        return new Point((int) this.x, (int) this.y);
    }

    public void setAll(float arg1, float arg2) {
        this.x = arg1;
        this.y = arg2;
    }

    public GameUnitPair getSizePoint() {
        return new GameUnitPair(this);
    }

    public void copy(GameUnitPair obj) {
        this.x = obj.x;
        this.y = obj.y;
    }

    public void copy(Point obj) {
        this.x = obj.x;
        this.y = obj.y;
    }

    public GameUnitPair add(float scale) {
        return new GameUnitPair(this.x + scale, this.y + scale);
    }

    public GameUnitPair sub(float scale) {
        return new GameUnitPair(this.x - scale, this.y - scale);
    }

    public GameUnitPair mul(float scale) {
        return new GameUnitPair(this.x * scale, this.y * scale);
    }

    public GameUnitPair div(float scale) {
        if (scale == 0) {
            throw new ArithmeticException("Cannot divide by zero.");
        }
        return new GameUnitPair(this.x / scale, this.y / scale);
    }

}
