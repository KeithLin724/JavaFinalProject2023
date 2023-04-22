package Game.gameBase;

import java.awt.Point;

public class GamePoint extends GameUnitPair {

    public GamePoint() {
        super();
    }

    public GamePoint(int x, int y) {
        super(x, y);
    }

    public GamePoint(float x, float y) {
        super(x, y);
    }

    public GamePoint(GameUnitPair obj) {
        super(obj);
    }

    public GamePoint(Point obj) {
        super(obj);
    }

    public void addToX(float xDis) {
        this.x += xDis;
    }

    public void subToX(float xDis) {
        this.x -= xDis;
    }

    public void mulToX(float xDis) {
        this.x *= xDis;
    }

    public void divToX(float xDis) {
        if (xDis == 0) {
            throw new ArithmeticException("Cannot divide by zero.");
        }
        this.x /= xDis;
    }

    public void addToY(float yDis) {
        this.y += yDis;
    }

    public void subToY(float yDis) {
        this.y -= yDis;
    }

    public void mulToY(float yDis) {
        this.y *= yDis;
    }

    public void divToY(float yDis) {
        if (yDis == 0) {
            throw new ArithmeticException("Cannot divide by zero.");
        }
        this.y /= yDis;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GamePoint) {
            var pt = (GamePoint) obj;
            return (x == pt.x) && (y == pt.y);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + "]";
    }

    // math function

    /**
     * The function calculates the distance between two game units using their x and
     * y coordinates.
     * 
     * @param obj1 GameUnitPair object representing the first game unit, with
     *             properties x and y
     *             representing its coordinates on a 2D plane.
     * @param obj2 obj2 is one of the two GameUnitPair objects that are being used
     *             to calculate the
     *             distance between them. It contains the x and y coordinates of the
     *             second object.
     * @return The method is returning the distance between two GameUnitPairs,
     *         calculated using the
     *         distance formula (square root of the sum of the squares of the
     *         differences in x and y coordinates).
     *         The return type is a double.
     */
    public static double distance(GameUnitPair obj1, GameUnitPair obj2) {
        return Math.sqrt(Math.pow(obj1.x - obj2.x, 2) + Math.pow(obj1.y - obj2.y, 2));
    }

    /**
     * The function takes two GameUnitPair objects and returns a new GamePoint
     * object with the sum of their
     * x and y values.
     * 
     * @param obj1 GameUnitPair object containing x and y coordinates
     * @param obj2 The second GameUnitPair object that is being added to the first
     *             object (obj1) to create
     *             a new GamePoint object.
     * @return The method is returning a new instance of the `GamePoint` class,
     *         which is created by adding
     *         the `x` and `y` values of `obj1` and `obj2`.
     */
    public static GamePoint add(GameUnitPair obj1, GameUnitPair obj2) {
        return new GamePoint(obj1.x + obj2.x, obj1.y + obj2.y);
    }

    /**
     * The function "sub" subtracts the x and y coordinates of two GameUnitPair
     * objects and returns a new
     * GamePoint object.
     * 
     * @param obj1 GameUnitPair object representing the first point with x and y
     *             coordinates.
     * @param obj2 The obj2 parameter is a GameUnitPair object that represents the
     *             second point in the
     *             subtraction operation.
     * @return A new instance of the `GamePoint` class with the `x` and `y` values
     *         calculated by
     *         subtracting the `x` and `y` values of `obj2` from the `x` and `y`
     *         values of `obj1`.
     */
    public static GamePoint sub(GameUnitPair obj1, GameUnitPair obj2) {
        return new GamePoint(obj1.x - obj2.x, obj1.y - obj2.y);
    }

    /**
     * The function takes two GameUnitPair objects and returns a new GamePoint
     * object with the product of
     * their x and y values.
     * 
     * @param obj1 GameUnitPair object containing x and y values for the first
     *             point.
     * @param obj2 The second GameUnitPair object that is being multiplied with the
     *             first object (obj1) in
     *             the mul() method.
     * @return A new instance of the `GamePoint` class, which is the result of
     *         multiplying the `x` and `y`
     *         values of two `GameUnitPair` objects (`obj1` and `obj2`).
     */
    public static GamePoint mul(GameUnitPair obj1, GameUnitPair obj2) {
        return new GamePoint(obj1.x * obj2.x, obj1.y * obj2.y);
    }

    /**
     * The function calculates the dot product of two GameUnitPair objects.
     * 
     * @param obj1 GameUnitPair object containing x and y coordinates of the first
     *             game unit.
     * @param obj2 obj2 is a GameUnitPair object, which likely contains two double
     *             values representing the
     *             x and y coordinates of a game unit.
     * @return The method is returning the dot product of two GameUnitPair objects,
     *         which is a double
     *         value.
     */
    public static double dot(GameUnitPair obj1, GameUnitPair obj2) {
        return obj1.x * obj2.x + obj1.y * obj2.y;
    }
}
