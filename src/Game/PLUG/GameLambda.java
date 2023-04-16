package Game.PLUG;

/**
 * This code is defining a generic interface called `GameLambda` with a type
 * parameter `T`. The
 * interface has a single method called `function` that takes two parameters of
 * type `T` and returns a
 * value of type `T`. This interface can be used to define lambda expressions
 * that take two objects of
 * type `T` and return a result of type `T`.
 */

public interface GameLambda<T> {
    public T function(T objectA, T objectB);
}
