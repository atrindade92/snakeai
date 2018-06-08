package snake.snakeAI.ga.geneticOperators;

import snake.snakeAI.ga.GeneticAlgorithm;
import snake.snakeAI.ga.RealVectorIndividual;

public class MutationUniform<I extends RealVectorIndividual> extends Mutation<I> {


    public MutationUniform(double probability) {
        super(probability);
    }

    @Override
    public void run(I ind) {

        for (int i = 0; i < ind.getNumGenes(); i++) {

            if (GeneticAlgorithm.random.nextDouble() < probability) {
                ind.setGene(i, GeneticAlgorithm.random.nextDouble() * 2 - 1);
            }
        }
    }

    @Override
    public String toString() {
        return "Uniform distribution mutation (" + probability + ")";
    }
}