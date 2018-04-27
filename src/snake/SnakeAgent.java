package snake;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public abstract class SnakeAgent {

    protected Cell cell;
    protected Color color;
    protected List<Cell> tail = new ArrayList<>();
    protected int foodCaught = 0;
    // TODO: isDead??

    public SnakeAgent(Cell cell, Color color) {
        this.cell = cell;
        if(cell != null){this.cell.setAgent(this);}
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

    protected boolean execute(Action action, Environment environment)
    {
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

        if (nextCell != null && !nextCell.hasAgent()) {
            setCell(nextCell);
        }
        else
            return true;

        int headLine = this.cell.getLine();
        int headColumn = this.cell.getColumn();

        Cell newTailPiece = environment.setTailPiece(new Cell(headLine,headColumn));
        this.tail.add(0,newTailPiece);


        //SE apanhou comida
        if(nextCell.hasFood()){
            this.foodCaught++;
            environment.placeFood();


            //SE NAO apanhou comida
        }else{
            int lastTailPieceLine = this.tail.get(this.tail.size()-1).getLine();
            int lastTailPieceColumn = this.tail.get(this.tail.size()-1).getColumn();

            if(this.tail.size() > 1) {
                environment.removeTailPiece(new Cell(lastTailPieceLine, lastTailPieceColumn));
                this.tail.remove(this.tail.size() - 1);
            }
        }
        return false;
    }

    protected abstract Action decide(Perception perception);

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell newCell) {
        if(this.cell != null){this.cell.setAgent(null);}
        this.cell = newCell;
        if(newCell != null){newCell.setAgent(this);}
    }    
    
    public Color getColor() {
        return color;
    }
}
