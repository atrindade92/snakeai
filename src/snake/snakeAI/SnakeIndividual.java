package snake.snakeAI;

import snake.snakeAI.ga.RealVectorIndividual;
import snake.snakeAI.nn.SnakeAIAgent;

public class SnakeIndividual extends RealVectorIndividual<SnakeProblem, SnakeIndividual> {

    private SnakeAIAgent agent;

    public SnakeIndividual(SnakeProblem problem, int size, SnakeAIAgent agent) {
        super(problem, size);
        this.agent = agent;
        this.agent.setWeights(genome);
    }

    public SnakeIndividual(SnakeIndividual original) {
        super(original);
        this.agent = original.getAgent();
    }

    @Override
    public double computeFitness() {
        final double STEPS_WEIGHT = 0.1, FOOD_CAUGHT_WEIGHT = 1000.0;
        for (int i = 0; i < problem.getMaxIterations(); i++)
            if(agent.act(problem.getEnvironment()))
                break;
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

    public SnakeAIAgent getAgent() {
        return agent;
    }
}
