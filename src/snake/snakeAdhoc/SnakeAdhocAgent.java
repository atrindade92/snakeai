package snake.snakeAdhoc;

import snake.*;

import java.awt.*;


public class SnakeAdhocAgent extends SnakeAgent {

    public SnakeAdhocAgent(Cell cell, Color color) {
        super(cell, color);
    }

    @Override
    protected Action decide(Perception perception) {
        //Food N
        if(foodOnN(perception.getF()) && !perception.getN().hasTail()){
            return Action.NORTH;
        }

        //Food E
        if(foodOnE(perception.getF()) && !perception.getE().hasTail()){
            return Action.EAST;
        }

        //Food S
        if(foodOnS(perception.getF()) && !perception.getS().hasTail()){
            return Action.SOUTH;
        }

        //Food W
        if(foodOnW(perception.getF()) && !perception.getW().hasTail()){
            return Action.WEST;
        }

        // Se não poder ir na direção da comida escolhe uma celula vazia

        //N
        if (perception.getN() != null && !perception.getN().hasTail()) {
            return Action.NORTH;
        }
        //E
        if (perception.getE() != null && !perception.getE().hasTail()) {
            return Action.EAST;
        }
        //S
        if (perception.getS() != null && !perception.getS().hasTail()) {
            return Action.SOUTH;
        }
        //W
        if (perception.getW() != null && !perception.getW().hasTail()) {
            return Action.WEST;
        }

        return null;
    }

    private boolean foodOnN(Cell foodCell){
        return (foodCell.getLine() < this.cell.getLine());
    }

    private boolean foodOnE(Cell foodCell){
        return (foodCell.getColumn() > this.cell.getColumn());
    }

    private boolean foodOnS(Cell foodCell){
        return (foodCell.getLine() > this.cell.getLine());
    }

    private boolean foodOnW(Cell foodCell){
        return (foodCell.getColumn() < this.cell.getColumn());
    }
}
