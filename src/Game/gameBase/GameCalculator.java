package Game.gameBase;

import Game.PLUG.GameCalculatorLambda;

public enum GameCalculator {
    ADD,
    SUB,
    MUL,
    DIV;

    /**
     * The function takes in two integers, a GameCalculator mode, and a scale, and
     * performs arithmetic
     * operations based on the mode and scale.
     * 
     * @param arg1  An integer value representing the first argument for the
     *              calculation.
     * @param arg2  The second integer argument passed to the method.
     * @param mode  The mode parameter is an enum type GameCalculator, which
     *              specifies the arithmetic
     *              operation to be performed on the two integer arguments arg1 and
     *              arg2. The possible values of the
     *              enum are ADD, SUB, MUL, and DIV, corresponding to addition,
     *              subtraction, multiplication, and
     *              division respectively.
     * @param scale The scale parameter is an integer value used to determine the
     *              amount of change to be
     *              applied to the arguments based on the selected mode of
     *              calculation. It is used to multiply, add,
     *              subtract or divide the arguments depending on the selected mode.
     * @return The method returns a GameUnitPair object.
     */
    public static GameUnitPair calculate(int arg1, int arg2, GameCalculator mode, int scale) {
        switch (mode) {
            case ADD -> {
                return new GameUnitPair(arg1 + scale, arg2 + scale);
            }
            case SUB -> {
                return new GameUnitPair(arg1 - scale, arg2 - scale);
            }
            case MUL -> {
                return new GameUnitPair(arg1 * scale, arg2 * scale);
            }
            case DIV -> {
                if (scale == 0) {
                    throw new ArithmeticException("Cannot divide by zero.");
                }
                return new GameUnitPair(arg1 / scale, arg2 / scale);
            }
        }
        return null;
    }

    /**
     * The function takes two integer arguments and a lambda function, and returns a
     * GameUnitPair object
     * with the results of applying the lambda function to each argument.
     * 
     * @param arg1           An integer value that will be passed as an argument to
     *                       the lambda function for
     *                       calculation.
     * @param arg2           arg2 is an integer parameter passed to the method
     *                       `calculate()`. It is the second
     *                       argument used in the lambda function
     *                       `lambdaFunction.calculate()`.
     * @param lambdaFunction lambdaFunction is a functional interface that takes an
     *                       integer input and
     *                       returns an integer output. It is used as a parameter in
     *                       the calculate method to perform a
     *                       calculation on the input arguments arg1 and arg2. The
     *                       lambdaFunction can be any implementation of
     *                       the functional interface, allowing for different
     *                       calculations to be performed
     * @return A GameUnitPair object is being returned.
     */
    public static GameUnitPair calculate(int arg1, int arg2, GameCalculatorLambda lambdaFunction) {
        return new GameUnitPair(lambdaFunction.calculate(arg1), lambdaFunction.calculate(arg2));
    }

}
