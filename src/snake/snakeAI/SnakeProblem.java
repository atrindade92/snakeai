package snake.snakeAI;

import snake.Environment;
import snake.snakeAI.ga.Problem;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SnakeProblem implements Problem<SnakeIndividual> {
    public static final String ONE_SNAKE_2 = "ONE SNAKE 2";
    public static final String HOMOGENOUS_SNAKE = "HOMOGENEOUS SNAKE";
    public static final String HETEROUGENEOUS_SNAKE = "HETEROUGENEOUS SNAKE";
    private static final int NUM_NN_INPUTS_ONE = 13;
    private static final int NUM_NN_INPUTS_HOMOGENOUS = 17;
    private static final int NUM_NN_OUTPUTS = 4;
    private static final int NUM_NN_OUTPUTS_SNAKE_ONE_2 = 2;
    private final int GENOME_SIZE;

    final private int environmentSize;
    final private int maxIterations;
    final private int numInputs;
    final private int numHiddenUnits;
    final public int numOutputs;
    final private int numEnvironmentRuns;
    final private Environment environment;

    public SnakeProblem(
            int environmentSize,
            int maxIterations,
            int numHiddenUnits,
            int numEnvironmentRuns,
            String snakeController) {
        this.environmentSize = environmentSize;
        this.numInputs = isSnakeControllerEquals(snakeController, HOMOGENOUS_SNAKE) || isSnakeControllerEquals(snakeController, HETEROUGENEOUS_SNAKE)
                ? NUM_NN_INPUTS_HOMOGENOUS : NUM_NN_INPUTS_ONE;
        this.numHiddenUnits = numHiddenUnits;
        this.numOutputs = isSnakeControllerEquals(snakeController, ONE_SNAKE_2) ? NUM_NN_OUTPUTS_SNAKE_ONE_2 : NUM_NN_OUTPUTS;
        this.numEnvironmentRuns = numEnvironmentRuns;

        GENOME_SIZE = numInputs * numHiddenUnits + (numHiddenUnits+1)*numOutputs;

        environment = new Environment(
                environmentSize,
                maxIterations, numInputs, numHiddenUnits, numOutputs);

        environment.setAgent(snakeController);

        this.maxIterations = environment.isOneSnakeProblem() ? maxIterations : 300;
    }

    public static SnakeProblem buildDefaultProblem(String controller){
        return new SnakeProblem(10, 500, 0, 10, controller);
    }

    @Override
    public SnakeIndividual getNewIndividual() {
        if(environment.isHomogenousSnakeController())
            return new SnakeHomogeneousIndividual(this, GENOME_SIZE);

        if(environment.isHeterogeneousSnakeController())
            return new SnakeHeterogeneousIndividual(this, GENOME_SIZE);

        return new OneSnakeIndividual(this, GENOME_SIZE);
    }

    private boolean isSnakeControllerEquals(String myController, String controller){
        return myController.toUpperCase().equals(controller);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public int getNumEvironmentSimulations() {
        return numEnvironmentRuns;
    }

    // MODIFY IF YOU DEFINE OTHER PARAMETERS
    public static SnakeProblem buildProblemFromFile(File file, String controller) throws IOException {
        java.util.Scanner f = new java.util.Scanner(file);

        List<String> lines = new LinkedList<>();

        while (f.hasNextLine()) {
            String s = f.nextLine();
            if (!s.equals("") && !s.startsWith("//")) {
                lines.add(s);
            }
        }

        List<String> parametersValues = new LinkedList<>();
        for (String line : lines) {
            String[] tokens = line.split(":");
            parametersValues.add(tokens[1].trim());
        }

        int environmentSize = Integer.parseInt(parametersValues.get(0));
        int maxIterations = Integer.parseInt(parametersValues.get(1));
        int numHiddenUnits = Integer.parseInt(parametersValues.get(2));
        int numEnvironmentRuns = Integer.parseInt(parametersValues.get(3));

        return new SnakeProblem(
                environmentSize,
                maxIterations,
                numHiddenUnits,
                numEnvironmentRuns,
                controller);
    }

    // MODIFY IF YOU DEFINE OTHER PARAMETERS
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Environment size: ");
        sb.append(environmentSize);
        sb.append("\n");
        sb.append("Maximum number of iterations: ");
        sb.append(maxIterations);
        sb.append("\n");
        sb.append("Number of inputs: ");
        sb.append(numInputs);
        sb.append("\n");
        sb.append("Number of hidden units: ");
        sb.append(numHiddenUnits);
        sb.append("\n");
        sb.append("Number of outputs: ");
        sb.append(numOutputs);
        sb.append("\n");
        sb.append("Number of environment simulations: ");
        sb.append(numEnvironmentRuns);
        return sb.toString();
    }

    public int getNumInputs() {
        return numInputs;
    }

    public int getNumHiddenUnits() {
        return numHiddenUnits;
    }

    public int getNumOutputs() {
        return numOutputs;
    }

    public int getMaxIterations() {
        return maxIterations;
    }
}
