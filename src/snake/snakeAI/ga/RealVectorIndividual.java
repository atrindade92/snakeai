package snake.snakeAI.ga;

public abstract class RealVectorIndividual <P extends Problem, I extends RealVectorIndividual> extends Individual<P, I>{

    protected double genome[];

    public RealVectorIndividual(P problem, int size) {
        super(problem);
        genome = new double[size];
        for (int i = 0; i <size ; i++) {
            genome[i]=GeneticAlgorithm.random.nextDouble()*2-1;

        }
    }

    public RealVectorIndividual(RealVectorIndividual<P, I> original) {
        super(original);
        genome = new double[original.getNumGenes()];
        System.arraycopy(original.genome, 0, this.genome, 0, this.genome.length);
    }
    
    @Override
    public int getNumGenes() {
        return genome.length;
    }
    
    public double getGene(int index) {
        return genome[index];
    }
    
    public void setGene(int index, double newValue) {
        genome[index] = newValue;
    }

    @Override
    public void swapGenes(RealVectorIndividual other, int index) {
        double buffer = other.getGene(index);
        other.setGene(index, this.getGene(index));
        this.setGene(index, buffer);
    }
}
