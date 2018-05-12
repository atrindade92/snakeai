package snake.snakeAI.ga.geneticOperators;

import snake.snakeAI.ga.GeneticAlgorithm;
import snake.snakeAI.ga.RealVectorIndividual;

public class MutationUniform<I extends RealVectorIndividual> extends Mutation<I> {

   
    public MutationUniform(double probability /*TODO?*/) {
        super(probability);
        // TODO
    }

    @Override
    public void run(I ind) {
        int geneIndex = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        ind.setGene(geneIndex, GeneticAlgorithm.random.nextDouble()*2-1);
    }
    
    @Override
    public String toString(){
        return "Uniform distribution mutation (" + probability /* + TODO?*/ + ")";
    }
}