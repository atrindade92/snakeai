package snake.snakeAI.ga.geneticOperators;

import snake.snakeAI.ga.GeneticAlgorithm;
import snake.snakeAI.ga.RealVectorIndividual;

public class MutationSwap<I extends RealVectorIndividual> extends Mutation<I> {

    public MutationSwap(double probability) {
        super(probability);
    }

    @Override
    public void run(I ind) {
        int index1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        int index2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());

        double aux = ind.getGene(index1);
        ind.setGene(index1, ind.getGene(index2));
        ind.setGene(index2, aux);
    }

    @Override
    public String toString() {
        return "Swap mutation (" + probability + ")";
    }
}