package snake.snakeAI.nn;

import snake.Action;
import snake.Cell;
import snake.Perception;

public class HomogeneousSnakeAIAgent extends SnakeAIAgent {

    private int opponentIndex;

    public HomogeneousSnakeAIAgent(Cell cell, int inputLayerSize, int hiddenLayerSize, int outputLayerSize, int agentIndex) {
        super(cell, inputLayerSize, hiddenLayerSize, outputLayerSize);
        this.opponentIndex = agentIndex == 0 ? 1 : 0 ;
    }

    public int getOpponentIndex() {
        return opponentIndex;
    }

    @Override
    protected Action decide(Perception perception) {
        final int TRUE = 1;

        final Cell northCell = perception.getN(), eastCell = perception.getE(), southCell = perception.getS(),
                westCell = perception.getW(), foodCell = perception.getF();

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

        inputs[12]=northCell != null && !northCell.hasAgent()   ? 0 : 1;
        inputs[13]=eastCell != null && !eastCell.hasAgent()     ? 0 : 1;
        inputs[14]=southCell != null && !southCell.hasAgent()   ? 0 : 1;
        inputs[15]=westCell != null && !westCell.hasAgent()     ? 0 : 1;

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
}
