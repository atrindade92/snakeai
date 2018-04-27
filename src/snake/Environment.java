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

    public Environment(
            int size,
            int maxIterations) {

        this.maxIterations = maxIterations;

        this.grid = new Cell[size][size];
        initializeBoard();

        this.random = new Random();
        this.food = new Food(new Cell(0,0));
    }

    private void initializeBoard(){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
    }

    public void initialize(int seed) {
        random.setSeed(seed);

        initializeBoard();
        placeFood();
    }

    public void placeAgent(String controller){
        this.agent = SnakeAgentFactory.buildSnakeAgent(controller, grid.length, grid.length);
    }

    public void placeFood() {
        random = new Random();
        int l;
        int c;

        Cell foodCell = food.getCell();
        int oldLine = foodCell.getLine();
        int oldColumn = foodCell.getColumn();

        //Só pode criar uma food se a celula estiver vazia
        do{
            l = random.nextInt(getNumLines());
            c = random.nextInt(getNumColumns());
        }while(grid[l][c].getColor() != Cell.COLOR);

        food.setCell(new Cell(l, c));
        grid[l][c].setFood(food);

        //Remove old food
        grid[oldLine][oldColumn].setFood(null);
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

    public Cell setTailPiece(Cell cell){
        int line = cell.getLine();
        int column = cell.getColumn();
        grid[line][column].setTail(new Tail());
        return grid[line][column];
    }

    public void removeTailPiece(Cell cell){
        grid[cell.getLine()][cell.getColumn()].setTail(null);
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
