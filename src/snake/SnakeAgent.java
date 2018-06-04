package snake;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public abstract class SnakeAgent {

    protected Cell cell;
    protected Color color;
    protected List<Cell> tail = new ArrayList<>();
    protected int foodCaught = 0;
    protected int numOfMovesWithPenalty = 0;
    protected int movesAfterLimit = 0;
    protected int movesAfterFoodCaught = 0;

    public SnakeAgent(Cell cell, Color color) {
        this.cell = cell;
        if (cell != null) {
            this.cell.setAgent(this);
        }
        this.color = color;
    }

    public boolean act(Environment environment) {
        Perception perception = buildPerception(environment);
        Action action = decide(perception);
        return execute(action, environment);
    }

    protected Perception buildPerception(Environment environment) {
        return new Perception(
                environment.getNorthCell(cell),
                environment.getSouthCell(cell),
                environment.getEastCell(cell),
                environment.getWestCell(cell),
                environment.getFoodCell());
    }

    protected boolean execute(Action action, Environment environment) {
        Cell nextCell = null;

        if (action == Action.NORTH && cell.getLine() != 0) {
            nextCell = environment.getNorthCell(cell);
        } else if (action == Action.SOUTH && cell.getLine() != environment.getNumLines() - 1) {
            nextCell = environment.getSouthCell(cell);
        } else if (action == Action.WEST && cell.getColumn() != 0) {
            nextCell = environment.getWestCell(cell);
        } else if (action == Action.EAST && cell.getColumn() != environment.getNumColumns() - 1) {
            nextCell = environment.getEastCell(cell);
        }

        if (nextCell != null && !nextCell.hasAgent() && !nextCell.hasTail()) {
            Cell lastCell = this.cell;
            setCell(nextCell);

            //SE apanhou comida
            if (nextCell.hasFood()) {
                lastCell.setTail(new Tail());
                this.tail.add(0, lastCell);

                this.numOfMovesWithPenalty += this.movesAfterLimit;
                this.movesAfterLimit = 0;
                this.movesAfterFoodCaught = 0;
                this.foodCaught++;
                environment.placeFood();
                nextCell.setFood(null);
            //SE NAO apanhou comida
            } else {
                if (!this.tail.isEmpty()) {
                    lastCell.setTail(new Tail());
                    this.tail.add(0, lastCell);
                    this.tail.get(this.tail.size() - 1).setTail(null);
                    this.tail.remove(this.tail.size() - 1);
                }
                checkLimit(environment);
            }
            return false;
        } else
            return true;
    }

    protected abstract Action decide(Perception perception);

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell newCell) {
        if (this.cell != null) {
            this.cell.setAgent(null);
        }
        this.cell = newCell;
        if (newCell != null) {
            newCell.setAgent(this);
        }
    }

    public Color getColor() {
        return color;
    }

    public void cleanTail() {
        for (Cell cell : tail) {
            cell.setTail(null);
        }
        tail.clear();
    }

    public void resetFoodCaught(){
        this.foodCaught = 0;
    }

    public void resetPenaltyValues(){
        this.numOfMovesWithPenalty = 0;
        this.movesAfterLimit = 0;
        this.movesAfterFoodCaught = 0;
    }

    public int getNumFoodCaught() {
        return foodCaught;
    }

    public int getNumOfMovesWithPenalty() {
        return numOfMovesWithPenalty;
    }

    private void checkLimit(Environment environment){
        if(environment.getController().equals("Homogeneous Snake")){
            if(this.tail.size() < 5 && movesAfterFoodCaught >= 30){
                this.movesAfterLimit++;
            }else if (this.tail.size() < 10 && movesAfterFoodCaught >= 50){
                this.movesAfterLimit++;
            /*}else if (this.tail.size() < 15 && movesAfterFoodCaught >= 30){
                this.movesAfterLimit++;
    /*        }else if (this.tail.size() < 20 && movesAfterFoodCaught >= 30){
                this.movesAfterLimit++;*/
            }else{
                /*if(movesAfterFoodCaught >= 55){
                    this.movesAfterLimit++;
                }*/
            }
        }else{
            if(this.tail.size() < 5 && movesAfterFoodCaught >= 10){
                this.movesAfterLimit++;
            }else if (this.tail.size() < 10 && movesAfterFoodCaught >= 22){
                this.movesAfterLimit++;
            /*}else if (this.tail.size() < 15 && movesAfterFoodCaught >= 30){
                this.movesAfterLimit++;
    /*        }else if (this.tail.size() < 20 && movesAfterFoodCaught >= 30){
                this.movesAfterLimit++;*/
            }else{
                /*if(movesAfterFoodCaught >= 55){
                    this.movesAfterLimit++;
                }*/
            }
        }


        this.movesAfterFoodCaught++;
    }

    protected boolean foodOnN(Cell foodCell){
        return (foodCell.getLine() < this.cell.getLine());
    }

    protected boolean foodOnE(Cell foodCell){
        return (foodCell.getColumn() > this.cell.getColumn());
    }

    protected boolean foodOnS(Cell foodCell){
        return (foodCell.getLine() > this.cell.getLine());
    }

    protected boolean foodOnW(Cell foodCell){
        return (foodCell.getColumn() < this.cell.getColumn());
    }

    public String foodAndMovesInfo(Environment environment){
        StringBuilder sb = new StringBuilder();
        sb.append("\nFood Caught: ").append(this.foodCaught).append(System.lineSeparator())
                .append("Moves: ").append(environment.getNumMoves()).append(System.lineSeparator());

        return sb.toString();
    }

}
