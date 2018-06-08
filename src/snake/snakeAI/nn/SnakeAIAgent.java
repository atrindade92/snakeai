package snake.snakeAI.nn;

import snake.*;
import snake.snakeAI.utils.*;
import java.awt.Color;

public abstract class SnakeAIAgent extends SnakeAgent {

    final protected int TRUE = 1;
    final protected int FALSE = 0;

    final protected int inputLayerSize;
    final protected int hiddenLayerSize;
    final protected int outputLayerSize;

    /**
     * Network inputs array.
     */
    protected final int[] inputs;
    /**
     * Hiddden layer weights.
     */
    final protected double[][] w1;
    /**
     * Output layer weights.
     */
    final protected double[][] w2;
    /**
     * Hidden layer activation values.
     */
    final protected double[] hiddenLayerOutput;
    /**
     * Output layer activation values.
     */
    protected final int[] output;

    public SnakeAIAgent(
            Cell cell,
            int inputLayerSize,
            int hiddenLayerSize,
            int outputLayerSize) {
        super(cell, Color.BLUE);
        this.inputLayerSize = inputLayerSize;
        this.hiddenLayerSize = hiddenLayerSize;
        this.outputLayerSize = outputLayerSize;
        inputs = new int[inputLayerSize];
        inputs[inputs.length - 1] = -1; //bias entry
        w1 = new double[inputLayerSize][hiddenLayerSize]; // the bias entry for the hidden layer neurons is already counted in inputLayerSize variable
        w2 = new double[hiddenLayerSize + 1][outputLayerSize]; // + 1 due to the bias entry for the output neurons
        hiddenLayerOutput = new double[hiddenLayerSize + 1];
        hiddenLayerOutput[hiddenLayerSize] = -1; // the bias entry for the output neurons
        output = new int[outputLayerSize];
    }

    /**
     * Initializes the network's weights
     * 
     * @param weights vector of weights comming from the individual.
     */
    public void setWeights(double[] weights) {
        int weightsIterator = initializeWeightsArray(w1, weights, 0);
        initializeWeightsArray(w2, weights, weightsIterator);
    }

    /**
     *
     * @param w w1 or w2 in this use case, being w1 the set of weights of the hidden layer
     *          and w2 the set of weights of the  output layer
     * @param newWeights array of new weights coming from the individual
     * @param newWeightsIterator the iterator of newWeights array
     * @return newWeightsIterator
     */
    private int initializeWeightsArray(double w[][], double[] newWeights, int newWeightsIterator){
        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j < w[0].length; j++) {
                w[i][j] = newWeights[newWeightsIterator++];
            }
        }
        return newWeightsIterator;
    }
    
    /**
     * Computes the output of the network for the inputs saved in the class
     * vector "inputs".
     *
     */
    protected void forwardPropagation() {
        double sum;
        for (int i = 0; i < hiddenLayerSize; i++) {
            sum = 0;
            for (int j = 0; j < inputLayerSize; j++) {
                sum += inputs[j] * w1[j][i];
            }
            hiddenLayerOutput[i] = Maths.sigmoid(sum);
        }

        int index = 0;
        double bestOutput = Double.MIN_VALUE;

        for (int i = 0; i < outputLayerSize; i++) {
            sum = 0;
            for (int j = 0; j < hiddenLayerSize+1; j++) {
                sum += hiddenLayerOutput[j] * w2[j][i];
            }
            if(sum > bestOutput) {
                bestOutput = sum;
                index = i;
            }
            output[i] = 0;

        }
        output[index] = 1;
    }

    protected abstract Action decide(Perception perception);
}
