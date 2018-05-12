package snake.snakeAI.utils;

import java.util.ArrayList;

public class Maths {

    public static double average(double[] array) {
        double sum = 0;
        for (double value : array) {
            sum += value;
        }
        return sum / array.length;
    }

    public static double standardDeviation(double[] array, double mean) {
        double sum = 0;
        for (double value : array) {
            sum += Math.pow(value - mean, 2);
        }
        return Math.sqrt(1 / (double) array.length * sum);
    }

    public static double standardDeviation(ArrayList<Double> array, double mean) {
        double sum = 0;
        for (Double value : array) {
            sum += Math.pow(value.doubleValue() - mean, 2);
        }
        return Math.sqrt(1 / (double) array.size() * sum);
    }

    public static double sigmoid(double x){
        return 1/(1 + Math.pow(Math.E, -x));
    }

    public static int roundedSigmoid(double x){
        return (int) Math.round(sigmoid(x));
    }

    public static int sign(double x){
        return x > 0 ? 1 : (x == 0 ? 0 : -1);
    }
}
