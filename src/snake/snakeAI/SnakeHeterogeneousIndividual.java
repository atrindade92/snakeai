package snake.snakeAI;

import snake.Environment;
import snake.snakeAI.nn.SnakeAIAgent;

public class SnakeHeterogeneousIndividual extends SnakeIndividual {

    protected double averageFoodsWithPenalty = 0f;

    public SnakeHeterogeneousIndividual(SnakeProblem problem, int size) {
        super(problem, size);
    }

    public SnakeHeterogeneousIndividual(SnakeHeterogeneousIndividual original) {
        super(original);
        this.fitness = original.fitness;
        this.averageMoves = original.averageMoves;
        this.averageFoodCaught = original.averageFoodCaught;
        this.averageMovesWithPenalty = original.averageMovesWithPenalty;
        this.averageFoodsWithPenalty = original.averageFoodsWithPenalty;
    }

    @Override
    public double computeFitness() {
        final double STEPS_WEIGHT = 1, FOOD_CAUGHT_WEIGHT = 1000.0, PENALTY_WEIGHT = 10.0, FOOD_DIFFERENCE = 3, FOOD_PENALTY = 500;
        int totalFoods = 0;
        int totalMoves = 0;
        int totalMovesWithPenalty = 0;
        int totalFoodsWithPenalty = 0;
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

            int foodDiff = Math.abs(snakeOne.getNumFoodCaught() - snakeTwo.getNumFoodCaught());
            totalFoodsWithPenalty += foodDiff >= FOOD_DIFFERENCE ? foodDiff : totalMovesWithPenalty;
        }
        averageFoodCaught = (double) totalFoods/numEnvironmentSimulations;
        averageMoves = (double) totalMoves/numEnvironmentSimulations;
        averageMovesWithPenalty = (double) totalMovesWithPenalty/numEnvironmentSimulations;
        averageFoodsWithPenalty = (double) totalFoodsWithPenalty/numEnvironmentSimulations;
        fitness = averageMoves * STEPS_WEIGHT + averageFoodCaught * FOOD_CAUGHT_WEIGHT -
                averageMovesWithPenalty * PENALTY_WEIGHT - averageFoodsWithPenalty * FOOD_PENALTY;

        return fitness;
    }

    @Override
    public SnakeIndividual clone() {
        return new SnakeHeterogeneousIndividual(this);
    }
}
