package snake.snakeAI;

import snake.snakeAI.ga.RealVectorIndividual;

public abstract class SnakeIndividual extends RealVectorIndividual<SnakeProblem, SnakeIndividual> {

    protected double averageFoodCaught = 0f;
    protected double averageMoves = 0f;
    protected double averageMovesWithPenalty = 0f;

    protected final double STEPS_WEIGHT = 1, FOOD_CAUGHT_WEIGHT = 1000.0, PENALTY_WEIGHT = 15.0;

    public SnakeIndividual(SnakeProblem problem, int size) {
        super(problem, size);
    }

    public SnakeIndividual(SnakeIndividual original) {
        super(original);
        this.fitness = original.fitness;
        this.averageMoves = original.averageMoves;
        this.averageFoodCaught = original.averageFoodCaught;
        this.averageMovesWithPenalty = original.averageMovesWithPenalty;
    }

    @Override
    public abstract double computeFitness();

    public double[] getGenome(){
        return genome;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nfitness: ").append(fitness).append(System.lineSeparator())
            .append("Average Moves: ").append(averageMoves).append(System.lineSeparator())
            .append("Average Food Caught: ").append(averageFoodCaught).append(System.lineSeparator())
            .append("Average Moves With Penalty: ").append(averageMovesWithPenalty).append(System.lineSeparator())
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
    public abstract SnakeIndividual clone();
}
