package snake.snakeAI.ga;

import java.util.Objects;

public abstract class Individual<P extends Problem, I extends Individual> implements Comparable<I>{

    protected double fitness;
    protected P problem;

    public Individual(P problem) {
        this.problem = problem;
    }

    public Individual(Individual<P, I> original) {
        this.problem = original.problem;
        this.fitness = original.fitness;
    }

    public abstract double computeFitness();
    
    public abstract int getNumGenes();
    
    public abstract void swapGenes(I other, int g);    

    public double getFitness() {
        return fitness;
    }

    @Override
    public abstract I clone();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Individual<?, ?> that = (Individual<?, ?>) o;
        return Double.compare(that.fitness, fitness) == 0 &&
                Objects.equals(problem, that.problem);
    }
}
