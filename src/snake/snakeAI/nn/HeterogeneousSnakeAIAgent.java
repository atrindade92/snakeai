package snake.snakeAI.nn;

import snake.*;

public class HeterogeneousSnakeAIAgent extends SnakeAIAgent{

    private int opponentIndex;

    public HeterogeneousSnakeAIAgent(Cell cell, int inputLayerSize, int hiddenLayerSize, int outputLayerSize, int agentIndex) {
        super(cell, inputLayerSize, hiddenLayerSize, outputLayerSize);
        this.opponentIndex = agentIndex == 0 ? 1 : 0 ;
    }

    public int getOpponentIndex() {
        return opponentIndex;
    }

    @Override
    protected Perception buildPerception(Environment environment) {
        return new PerceptionWithOpponent(
                environment.getNorthCell(cell),
                environment.getSouthCell(cell),
                environment.getEastCell(cell),
                environment.getWestCell(cell),
                environment.getFoodCell(),
                (environment.getAgent(this.opponentIndex) == null ? null : environment.getAgent(this.opponentIndex).getCell()));
    }

    @Override
    protected Action decide(Perception perception) {
        final Cell  northCell = perception.getN(),
                    eastCell = perception.getE(),
                    southCell = perception.getS(),
                    westCell = perception.getW(),
                    foodCell = perception.getF(),
                    opponentCell = ((PerceptionWithOpponent) perception).getOpponent();

        inputs[0]=northCell != null ? 0 : 1;
        inputs[1]=eastCell != null ? 0 : 1;
        inputs[2]=southCell != null ? 0 : 1;
        inputs[3]=westCell != null ? 0 : 1;

        inputs[4]=foodOnN(foodCell) ? 0 : 1;
        inputs[5]=foodOnE(foodCell) ? 0 : 1;
        inputs[6]=foodOnS(foodCell) ? 0 : 1;
        inputs[7]=foodOnW(foodCell) ? 0 : 1;

        inputs[8]=northCell != null && !northCell.hasTail()     ? 0 : 1;
        inputs[9]=eastCell != null && !eastCell.hasTail()       ? 0 : 1;
        inputs[10]=southCell != null && !southCell.hasTail()    ? 0 : 1;
        inputs[11]=westCell != null && !westCell.hasTail()      ? 0 : 1;

        inputs[12]=opponentCell != null && opponentOnN(opponentCell) ? 0 : 1;
        inputs[13]=opponentCell != null && opponentOnE(opponentCell) ? 0 : 1;
        inputs[14]=opponentCell != null && opponentOnS(opponentCell) ? 0 : 1;
        inputs[15]=opponentCell != null && opponentOnW(opponentCell) ? 0 : 1;

        forwardPropagation();

        if(output[0] == TRUE)
            return Action.NORTH;
        else if(output[1] == TRUE)
            return Action.EAST;
        else if(output[2] == TRUE)
            return Action.SOUTH;
        else if(output[3] == TRUE)
            return Action.WEST;

        return null;
    }

    @Override
    protected void checkLimit(Environment environment) {
        if (this.tail.size() < 5 && movesAfterFoodCaught >= 30) {
            this.movesAfterLimit++;
        } else if (this.tail.size() < 10 && movesAfterFoodCaught >= 40) {
            this.movesAfterLimit++;
        /*}else if (this.tail.size() < 15 && movesAfterFoodCaught >= 30){
            this.movesAfterLimit++;
/*        }else if (this.tail.size() < 20 && movesAfterFoodCaught >= 30){
            this.movesAfterLimit++;*/
        } else {
            /*if(movesAfterFoodCaught >= 55){
                this.movesAfterLimit++;
            }*/
        }
        super.checkLimit(environment);
    }
}
