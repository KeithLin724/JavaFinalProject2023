package Game.gameBase;

import java.awt.Point;

/**
 * This is a class to pass 2 of thing
 */

public class GameUnitPair extends Point {

    public GameUnitPair() {
        super();
    }

    public GameUnitPair(int arg1, int arg2) {
        super(arg1, arg2);
    }

    public static GameUnitPair buildGameUnitPair(int arg1, int arg2) {
        return new GameUnitPair(arg1, arg2);
    }

    public static GameUnitPair buildGameUnitPair(Point point) {
        return new GameUnitPair(point);
    }

    public GameUnitPair(Point point) {
        super(point);
    }

    public GameUnitPair(GameUnitPair obj) {
        this.copy(obj);
    }

    public Point getPoint() {
        return new Point(this);
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

    public GameUnitPair add(int scale) {
        return new GameUnitPair(this.x + scale, this.y + scale);
    }

    public GameUnitPair sub(int scale) {
        return new GameUnitPair(this.x - scale, this.y - scale);
    }

    public GameUnitPair mul(int scale) {
        return new GameUnitPair(this.x * scale, this.y * scale);
    }

    public GameUnitPair div(int scale) {
        if (scale == 0) {
            throw new ArithmeticException("Cannot divide by zero.");
        }
        return new GameUnitPair(this.x / scale, this.y / scale);
    }

}
