package snake.snakeAI.nn;

import snake.*;
import snake.snakeAI.utils.*;
import java.awt.Color;

public class SnakeAIAgent extends SnakeAgent {
   
    final private int inputLayerSize;
    final private int hiddenLayerSize;
    final private int outputLayerSize;

    /**
     * Network inputs array.
     */
    final private int[] inputs;
    /**
     * Hiddden layer weights.
     */
    final private double[][] w1;
    /**
     * Output layer weights.
     */
    final private double[][] w2;
    /**
     * Hidden layer activation values.
     */
    final private double[] hiddenLayerOutput;
    /**
     * Output layer activation values.
     */
    final private int[] output;

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
    private void forwardPropagation() {
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

    @Override
    protected Action decide(Perception perception) {
        final int TRUE = 1;

        final Cell northCell = perception.getN(), eastCell = perception.getE(), southCell = perception.getS(),
                westCell = perception.getW(), foodCell = perception.getF();

        inputs[0]=northCell != null && !northCell.hasTail() ? 0 : 1;
        inputs[1]=eastCell != null && !eastCell.hasTail() ? 0 : 1;
        inputs[2]=southCell != null && !southCell.hasTail() ? 0 : 1;
        inputs[3]=westCell != null && !westCell.hasTail() ? 0 : 1;

        inputs[4]=foodOnN(foodCell) ? 0 : 1;
        inputs[5]=foodOnE(foodCell) ? 0 : 1;
        inputs[6]=foodOnS(foodCell) ? 0 : 1;
        inputs[7]=foodOnW(foodCell) ? 0 : 1;

        forwardPropagation();

        if(output[0] == TRUE)
            return Action.NORTH;
        else if(output[1] == TRUE)
            return Action.EAST;
        else if(output[2] == TRUE)
            return Action.SOUTH;
        else if(output[3] == TRUE)
            return Action.WEST;

        return null;
    }
}
