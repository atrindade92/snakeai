package snake;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Environment {

    public Random random;
    private final Cell[][] grid;
    private SnakeAgent agent;
    private Food food;
    private final int maxIterations;
    private String controller;

    public Environment(
            int size,
            int maxIterations) {

        this.maxIterations = maxIterations;

        this.grid = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.grid[i][j] = new Cell(i, j);
            }
        }

        this.random = new Random();
        //this.food = new Food(new Cell(0,0));
    }

    private void cleanBoard(){
        if(agent != null) {
            agent.getCell().setAgent(null);
            agent.cleanTail();
            agent = null;
        }

        if(food != null) {
            food.getCell().setFood(null);
            food = null;
        }
    }

    public void initialize(int seed) {
        random.setSeed(seed);
        cleanBoard();
        placeFood();
        placeAgent();
    }

    public void placeAgent(){
        this.agent = SnakeAgentFactory.buildSnakeAgent(this.controller, grid.length, grid.length);
    }

    public void setAgent(String controller){
        this.controller = controller;
        this.agent = SnakeAgentFactory.buildSnakeAgent(this.controller, grid.length, grid.length);
    }

    public void placeFood() {
        int l;
        int c;


        //Só pode criar uma food se a celula estiver vazia
        do{
            l = random.nextInt(getNumLines());
            c = random.nextInt(getNumColumns());
        }while(grid[l][c].getColor() != Cell.COLOR);

        food = new Food (grid[l][c]);
    }

    public void setAgent(SnakeAgent agent) {
        this.agent = agent;
    }

    public void simulate() {
        boolean isAgentStucked = false;
        for (int i = 0; i < maxIterations; i++) {
            if(isAgentStucked)
                break;
            isAgentStucked = agent.act(this);
            fireUpdatedEnvironment();
        }
    }

    public int getSize() {
        return grid.length;
    }

        public Cell getNorthCell(Cell cell) {
        if (cell.getLine() > 0) {
            return grid[cell.getLine() - 1][cell.getColumn()];
        }
        return null;
    }

    public Cell getSouthCell(Cell cell) {
        if (cell.getLine() < grid.length - 1) {
            return grid[cell.getLine() + 1][cell.getColumn()];
        }
        return null;
    }

    public Cell getEastCell(Cell cell) {
        if (cell.getColumn() < grid[0].length - 1) {
            return grid[cell.getLine()][cell.getColumn() + 1];
        }
        return null;
    }

    public Cell getWestCell(Cell cell) {
        if (cell.getColumn() > 0) {
            return grid[cell.getLine()][cell.getColumn() - 1];
        }
        return null;
    }

    public Cell getFoodCell(){
        return food.getCell();
    }


    public int getNumLines() {
        return grid.length;
    }

    public int getNumColumns() {
        return grid[0].length;
    }

    public final Cell getCell(int linha, int coluna) {
        return grid[linha][coluna];
    }

    public Color getCellColor(int linha, int coluna) {
        return grid[linha][coluna].getColor();
    }

    //listeners
    private final ArrayList<EnvironmentListener> listeners = new ArrayList<>();

    public synchronized void addEnvironmentListener(EnvironmentListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public synchronized void removeEnvironmentListener(EnvironmentListener l) {
        listeners.remove(l);
    }

    public void fireUpdatedEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.environmentUpdated();
        }
    }
}
