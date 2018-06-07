package snake.snakeAI.ga.geneticOperators;

import snake.snakeAI.ga.GeneticAlgorithm;
import snake.snakeAI.ga.Individual;
import snake.snakeAI.ga.RealVectorIndividual;

public class RecombinationArithmetical<I extends Individual> extends Recombination<I> {

    public RecombinationArithmetical(double probability) {
        super(probability);
    }

    /**
     *
     * @param ind1 A parent who must be a RealVectorIndividual
     * @param ind2 A parent who must be a RealVectorIndividual
     */
    @Override
    public void run(I ind1, I ind2) {
        double a = GeneticAlgorithm.random.nextDouble();
        for(int i = 0; i < ind1.getNumGenes(); i++){
            double individualOneGene = ((RealVectorIndividual) ind1).getGene(i);
            double individualTwoGene = ((RealVectorIndividual) ind2).getGene(i);
            ((RealVectorIndividual)ind1).setGene(i, a * individualOneGene + (1-a)* individualTwoGene);
            ((RealVectorIndividual)ind2).setGene(i, (1-a)*individualOneGene + a * individualTwoGene);
        }
    }
    
    @Override
    public String toString(){
        return "Arithmetical recombination (" + probability + ")";
    }    
}