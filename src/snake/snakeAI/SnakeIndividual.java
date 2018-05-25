package snake.snakeAI;

import snake.snakeAI.ga.RealVectorIndividual;
import snake.snakeAI.nn.SnakeAIAgent;

public class SnakeIndividual extends RealVectorIndividual<SnakeProblem, SnakeIndividual> {



    public SnakeIndividual(SnakeProblem problem, int size) {
        super(problem, size);

    }

    public SnakeIndividual(SnakeIndividual original) {
        super(original);

        this.fitness = original.fitness;

    }

    @Override
    public double computeFitness() {
        final double STEPS_WEIGHT = 0.1, FOOD_CAUGHT_WEIGHT = 1000.0;
        numf

        for (int i = 0; i < problem.getNumEvironmentSimulations(); i++) {
            problem.getEnvironment().initialize(i);
            ((SnakeAIAgent) problem.getEnvironment().getAgent()).setWeights(genome);
            problem.getEnvironment().simulate();
            numfoods+=problem.getEnvironment().getAgent().getNumFoodCaught();
            numMoves

        }
        fitn0ess = numMoves* STEPS_WEIGHT + numFoods * FOOD_CAUGHT_WEIGHT;
        return fitness;
    }

    public double[] getGenome(){
        return genome;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nfitness: ");
        sb.append(fitness);
        sb.append("\ngenome:");
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

    public SnakeAIAgent getAgent() {
        return agent;
    }
}
