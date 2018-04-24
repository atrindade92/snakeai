package snake.snakeAI.ga;

public abstract class RealVectorIndividual <P extends Problem, I extends RealVectorIndividual> extends Individual<P, I>{
    // TODO
    
    public RealVectorIndividual(P problem, int size) {
        super(problem);
        // TODO
    }

    public RealVectorIndividual(RealVectorIndividual<P, I> original) {
        super(original);
        // TODO
    }
    
    @Override
    public int getNumGenes() {
        // TODO
        return 1;
    }
    
    public double getGene(int index) {
        // TODO
        return 0;
    }
    
    public void setGene(int index, double newValue) {
        // TODO
    }

    @Override
    public void swapGenes(RealVectorIndividual other, int index) {
        // TODO
    }
}
