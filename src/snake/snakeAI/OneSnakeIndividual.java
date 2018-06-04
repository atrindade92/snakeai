package snake.snakeAI;

import snake.Environment;
import snake.snakeAI.nn.SnakeAIAgent;

public class OneSnakeIndividual extends SnakeIndividual {

    public OneSnakeIndividual(SnakeProblem problem, int size) {
        super(problem, size);
    }

    public OneSnakeIndividual(OneSnakeIndividual original) {
        super(original);
        this.fitness = original.fitness;
        this.averageMoves = original.averageMoves;
        this.averageFoodCaught = original.averageFoodCaught;
        this.averageMovesWithPenalty = original.averageMovesWithPenalty;
    }

    @Override
    public double computeFitness() {
        final double STEPS_WEIGHT = 1, FOOD_CAUGHT_WEIGHT = 1000.0, PENALTY_WEIGHT = 15.0;
        int totalFoods = 0;
        int totalMoves = 0;
        int totalMovesWithPenalty = 0;
        final int numEnvironmentSimulations = problem.getNumEvironmentSimulations();
        Environment environment = problem.getEnvironment();
        SnakeAIAgent agent = null;
        for (int i = 0; i < numEnvironmentSimulations; i++) {
            environment.initialize(i);
            agent = (SnakeAIAgent) environment.getAgent(0);
            agent.setWeights(genome);

            environment.simulate();
            totalMovesWithPenalty += agent.getNumOfMovesWithPenalty();
            totalFoods += agent.getNumFoodCaught();
            totalMoves += environment.getNumMoves();
        }
        averageFoodCaught = (double) totalFoods/numEnvironmentSimulations;
        averageMoves = (double) totalMoves/numEnvironmentSimulations;
        averageMovesWithPenalty = (double) totalMovesWithPenalty/numEnvironmentSimulations;

        fitness = averageMoves * STEPS_WEIGHT + averageFoodCaught * FOOD_CAUGHT_WEIGHT - averageMovesWithPenalty * PENALTY_WEIGHT;
        return fitness;
    }

    @Override
    public SnakeIndividual clone() {
        return new OneSnakeIndividual(this);
    }
}
