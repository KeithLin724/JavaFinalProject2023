package Game.PLUG;

/**
 * This code is defining a functional interface named `GameCalculatorLambda`. A
 * functional interface is
 * an interface that has only one abstract method. In this case, the abstract
 * method is `calculate`
 * which takes an integer input and returns an integer output. This interface
 * can be used to define
 * lambda expressions that implement the `calculate` method.
 */

@FunctionalInterface
public interface GameCalculatorLambda {
    public int calculate(int inputNumber);
}
