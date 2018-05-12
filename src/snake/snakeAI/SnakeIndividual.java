package snake.snakeAI;

import snake.SnakeAgent;
import snake.snakeAI.ga.RealVectorIndividual;

public class SnakeIndividual extends RealVectorIndividual<SnakeProblem, SnakeIndividual> {

    public SnakeIndividual(SnakeProblem problem, int size /*TODO?*/) {
        super(problem, size);
        //TODO?
    }

    public SnakeIndividual(SnakeIndividual original) {
        super(original);
        //TODO
    }

    @Override
    public double computeFitness() {
        final double STEPS_WEIGHT = 0.1, FOOD_CAUGHT_WEIGHT = 1000.0;
        SnakeAgent agent = problem.getEnvironment().getAgent();
        fitness = agent.getNumIterationsSurvived() * STEPS_WEIGHT + agent.getNumFoodCaught() * FOOD_CAUGHT_WEIGHT;
        return fitness;
    }

    public double[] getGenome(){
        return genome.clone();
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
        double individualFitness = i.computeFitness();
        return fitness > individualFitness ? 1 : (fitness < individualFitness ? -1 : 0);
    }

    @Override
    public SnakeIndividual clone() {
        return new SnakeIndividual(this);
    }
}
