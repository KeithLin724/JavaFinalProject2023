package Game.gameBase;

import java.awt.Point;

/**
 * This is a class to pass 2 of thing
 */

public class GameUnitPair extends Point {
    public int width;
    public int height;

    private void setPoint(int arg1, int arg2) {
        super.x = arg1;
        super.y = arg2;
    }

    private void settingAll(int arg1, int arg2) {
        this.width = arg1;
        this.height = arg2;
    }

    public GameUnitPair() {
        super();
    }

    public GameUnitPair(int arg1, int arg2) {
        super(arg1, arg2);
        this.settingAll(arg1, arg2);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void getHeight(int height) {
        this.height = height;
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

    /**
     * The "copy" function sets the point and settings of a GameUnitPair object to
     * those of another
     * GameUnitPair object.
     * 
     * @param obj obj is an object of the class GameUnitPair that is being passed as
     *            a parameter to the
     *            copy method.
     */
    public void copy(GameUnitPair obj) {
        this.setPoint(obj.width, obj.height);
        this.settingAll(obj.width, obj.height);
    }

    /**
     * The function copies the coordinates of a Point object to another Point
     * object.
     * 
     * @param obj The parameter "obj" is an object of the class "Point". It is being
     *            passed as an argument
     *            to the "copy" method.
     */
    public void copy(Point obj) {
        this.setPoint(y, x);
        this.settingAll(obj.x, obj.y);
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
