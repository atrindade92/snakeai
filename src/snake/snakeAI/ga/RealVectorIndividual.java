package snake.snakeAI.ga;

public abstract class RealVectorIndividual <P extends Problem, I extends RealVectorIndividual> extends Individual<P, I>{

    protected double genome[];

    public RealVectorIndividual(P problem, int size) {
        super(problem);
        genome = new double[size];
    }

    public RealVectorIndividual(RealVectorIndividual<P, I> original) {
        super(original);
        genome = new double[original.getNumGenes()];
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
