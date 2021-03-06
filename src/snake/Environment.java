package snake;

import snake.snakeAI.SnakeProblem;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Environment {

    private Random random;
    private final Cell[][] grid;
    private SnakeAgent agent[];
    private Food food;
    private final int maxIterations;
    private String controller;
    private int numInputs;
    private int numHiddens;
    private int numOutputs;
    private int numMoves = 0;

    public Environment(int size, int maxIterations, int numInputs,
                       int numHiddens, int numOutputs){
        this(size, maxIterations);
        this.numInputs = numInputs;
        this.numHiddens = numHiddens;
        this.numOutputs = numOutputs;
        this.agent = new SnakeAgent[2];
    }

    public Environment(
            int size,
            int maxIterations) {

        this.agent = new SnakeAgent[2];
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

    public void cleanBoard(){
        numMoves = 0;
        if(agent[0] != null) {
            agent[0].getCell().setAgent(null);
            agent[0].cleanTail();
            agent[0].resetFoodCaught();
            agent[0].resetPenaltyValues();

            if(!isOneSnakeProblem()){
                agent[1].getCell().setAgent(null);
                agent[1].cleanTail();
                agent[1].resetFoodCaught();
                agent[1].resetPenaltyValues();
            }
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
        agent[0].setCell(getCellAtRandomLocation());

        if(!isOneSnakeProblem()){
            agent[1].setCell(getCellAtRandomLocation());
        }
    }

    public void createAgent(){
        this.agent[0] = SnakeAgentFactory.buildSnakeAgent(this.controller, this, 0);

        if(!isOneSnakeProblem()){
            this.agent[1] = SnakeAgentFactory.buildSnakeAgent(this.controller, this, 1);
        }
    }

    public void setAgent(String controller){
        this.controller = controller;
        createAgent();
    }

    public boolean hasAgent(){
        return agent[0] != null;
    }

    public boolean hasTwoAgents(){
        return agent[0] != null && agent[1] != null;
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

    public void setAgentAt(SnakeAgent agent, int index) {
        this.agent[index] = agent;
    }

    public void simulate() {
        boolean isAgentStucked = false;
        int i;

        for (i = 0; i < maxIterations; i++) {
            if(isAgentStucked)
                break;

            if(!isOneSnakeProblem()){
                if(i%2 == 0){
                    isAgentStucked = agent[0].act(this);
                }else{
                    isAgentStucked = agent[1].act(this);
                }
            }else{
                isAgentStucked = agent[0].act(this);
            }

            fireUpdatedEnvironment();
        }
       numMoves=i;
    }

    public boolean isHomogenousSnakeController(){
        return controller.toUpperCase().equals(SnakeProblem.HOMOGENOUS_SNAKE);
    }

    public boolean isHeterogeneousSnakeController(){
        return controller.toUpperCase().equals(SnakeProblem.HETEROUGENEOUS_SNAKE);
    }

    public boolean isOneSnakeProblem(){
        return !(isHomogenousSnakeController() || isHeterogeneousSnakeController());
    }

    public int getNumInputs() {
        return numInputs;
    }

    public int getNumHiddens() {
        return numHiddens;
    }

    public int getNumOutputs() {
        return numOutputs;
    }

    public int getSize() {
        return grid.length;
    }

    public int getNumMoves(){
        return numMoves;
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

    public Random getRandom() {
        return random;
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

    public SnakeAgent getAgent(int index) {
        return agent[index];
    }

    public Cell getCellAtRandomLocation(){
        return new Cell(random.nextInt(grid.length), random.nextInt(grid[0].length));
    }

    public String getController() {
        return controller;
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
