package Game.gameBase;

import java.awt.Point;

public class GamePoint extends GameUnitPair {

    public GamePoint() {
        super();
    }

    public GamePoint(int x, int y) {
        super(x, y);
    }

    public GamePoint(GameUnitPair obj) {
        super(obj);
    }

    public GamePoint(Point obj) {
        super(obj);
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
     * The function calculates the distance between two points in a two-dimensional
     * space.
     * 
     * @param obj1 The first parameter is an object of the class Point, which
     *             represents a point in a
     *             two-dimensional coordinate system. It has two properties, x and
     *             y, that represent the coordinates of
     *             the point on the x-axis and y-axis, respectively.
     * @param obj2 The obj2 parameter is an object of the Point class representing
     *             the second point in the
     *             calculation of the distance between two points. It has properties
     *             x and y representing the
     *             coordinates of the point in a two-dimensional plane.
     * @return The method is returning the distance between two points represented
     *         by the objects obj1 and
     *         obj2.
     */
    public static double distance(Point obj1, Point obj2) {
        return Math.sqrt(Math.pow(obj1.x - obj2.x, 2) + Math.pow(obj1.y - obj2.y, 2));
    }

    /**
     * The function takes two Point objects and returns a new GamePoint object with
     * the sum of their x and
     * y coordinates.
     * 
     * @param obj1 A Point object representing the coordinates of a point in a 2D
     *             space.
     * @param obj2 The obj2 parameter is an object of the Point class, which
     *             represents a point in a
     *             two-dimensional coordinate system with x and y coordinates. It is
     *             being used to add its x and y
     *             values to the x and y values of another Point object (obj1) in
     *             order to create a new Game
     * @return A new instance of the GamePoint class with the x and y values of obj1
     *         and obj2 added
     *         together.
     */
    public static GamePoint add(Point obj1, Point obj2) {
        return new GamePoint(obj1.x + obj2.x, obj1.y + obj2.y);
    }

    /**
     * The function takes two Point objects and returns a new GamePoint object with
     * the difference of their
     * x and y coordinates.
     * 
     * @param obj1 A Point object representing the first point.
     * @param obj2 The second object of type Point that is being subtracted from the
     *             first object (obj1) in
     *             order to calculate the difference between their x and y
     *             coordinates.
     * @return A new instance of the `GamePoint` class with the `x` and `y` values
     *         calculated by
     *         subtracting the corresponding values of `obj2` from `obj1`.
     */
    public static GamePoint sub(Point obj1, Point obj2) {
        return new GamePoint(obj1.x - obj2.x, obj1.y - obj2.y);
    }

    /**
     * The function takes two Point objects and returns a new GamePoint object with
     * the product of their x
     * and y values.
     * 
     * @param obj1 A Point object representing the first point to be multiplied.
     * @param obj2 The obj2 parameter is an instance of the Point class, which
     *             represents a point in a
     *             two-dimensional coordinate system with x and y coordinates. It is
     *             being used to multiply the x and y
     *             coordinates of another Point object (obj1) to create a new
     *             GamePoint object.
     * @return A new instance of the `GamePoint` class with the `x` and `y` values
     *         multiplied from the
     *         `Point` objects `obj1` and `obj2`.
     */
    public static GamePoint mul(Point obj1, Point obj2) {
        return new GamePoint(obj1.x * obj2.x, obj1.y * obj2.y);
    }

    /**
     * The function calculates the dot product of two points in Java.
     * 
     * @param obj1 The first parameter "obj1" is a Point object representing a point
     *             in 2D space. It has
     *             two properties, "x" and "y", which represent the coordinates of
     *             the point on the x-axis and y-axis
     *             respectively.
     * @param obj2 obj2 is an object of the class Point, which presumably has two
     *             instance variables x and
     *             y representing the coordinates of a point in a two-dimensional
     *             space. The dot method takes two Point
     *             objects (obj1 and obj2) as parameters and calculates the dot
     *             product of their coordinates.
     * @return The method `dot` is returning the dot product of two points `obj1`
     *         and `obj2` as a `long`
     *         value.
     */
    public static long dot(Point obj1, Point obj2) {
        return obj1.x * obj2.x + obj1.y * obj2.y;
    }
}
