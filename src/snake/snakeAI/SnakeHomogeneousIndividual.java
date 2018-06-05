package snake.snakeAI;

import snake.Environment;
import snake.snakeAI.ga.RealVectorIndividual;
import snake.snakeAI.nn.SnakeAIAgent;

public class SnakeHomogeneousIndividual extends SnakeIndividual {

    public SnakeHomogeneousIndividual(SnakeProblem problem, int size) {
        super(problem, size);
    }

    public SnakeHomogeneousIndividual(SnakeHomogeneousIndividual original) {
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
        SnakeAIAgent snakeOne = null;
        SnakeAIAgent snakeTwo = null;
        for (int i = 0; i < numEnvironmentSimulations; i++) {
            environment.initialize(i);
            snakeOne = (SnakeAIAgent) environment.getAgent(0);
            snakeTwo = (SnakeAIAgent) environment.getAgent(1);

            snakeOne.setWeights(genome);
            snakeTwo.setWeights(genome);

            environment.simulate();
            totalMovesWithPenalty += (snakeOne.getNumOfMovesWithPenalty() + snakeTwo.getNumOfMovesWithPenalty());
            totalFoods += (snakeOne.getNumFoodCaught() + snakeTwo.getNumFoodCaught());
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
        return new SnakeHomogeneousIndividual(this);
    }
}
