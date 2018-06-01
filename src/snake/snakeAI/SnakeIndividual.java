package snake.snakeAI;

import snake.Environment;
import snake.snakeAI.ga.RealVectorIndividual;
import snake.snakeAI.nn.SnakeAIAgent;

public class SnakeIndividual extends RealVectorIndividual<SnakeProblem, SnakeIndividual> {

    private double averageFoodCaught = 0f;
    private double averageMoves = 0f;

    public SnakeIndividual(SnakeProblem problem, int size) {
        super(problem, size);
    }

    public SnakeIndividual(SnakeIndividual original) {
        super(original);
        this.fitness = original.fitness;
        this.averageMoves = original.averageMoves;
        this.averageFoodCaught = original.averageFoodCaught;
    }

    @Override
    public double computeFitness() {
        final double STEPS_WEIGHT = 1, FOOD_CAUGHT_WEIGHT = 1000.0;
        int totalFoods = 0;
        int totalMoves = 0;
        final int numEnvironmentSimulations = problem.getNumEvironmentSimulations();
        Environment environment = problem.getEnvironment();
        SnakeAIAgent agent = null;
        for (int i = 0; i < numEnvironmentSimulations; i++) {
            environment.initialize(i);
            agent = (SnakeAIAgent) environment.getAgent();
            agent.setWeights(genome);
            environment.simulate();
            totalFoods+= agent.getNumFoodCaught();
            totalMoves+= environment.getNumMoves();
        }
        averageFoodCaught = (double) totalFoods/numEnvironmentSimulations;
        averageMoves = (double) totalMoves/numEnvironmentSimulations;
        fitness = averageMoves * STEPS_WEIGHT + averageFoodCaught * FOOD_CAUGHT_WEIGHT;
        return fitness;
    }

    public double[] getGenome(){
        return genome;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nfitness: ").append(fitness).append(System.lineSeparator())
            .append("Average Moves: ").append(averageMoves).append(System.lineSeparator())
            .append("Average Food Caught: ").append(averageFoodCaught).append(System.lineSeparator())
            .append("genome:");
        for (double gene : genome) {
            sb.append(gene).append(" ");
        }
        return sb.toString();
    }

    /**
     *
     * @param i
     * @return 1 if this object is BETTER than i, -1 if it is WORST than I and
     * 0, otherwise.
     */
    @Override
    public int compareTo(SnakeIndividual i) {
        return fitness > i.fitness ? 1 : (fitness < i.fitness ? -1 : 0);
    }

    @Override
    public SnakeIndividual clone() {
        return new SnakeIndividual(this);
    }
}
