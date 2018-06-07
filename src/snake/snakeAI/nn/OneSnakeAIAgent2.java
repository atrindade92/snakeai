package snake.snakeAI.nn;

import snake.Action;
import snake.Cell;
import snake.Environment;
import snake.Perception;
import snake.snakeAI.utils.Maths;

public class OneSnakeAIAgent2 extends SnakeAIAgent {

    public OneSnakeAIAgent2(Cell cell, int inputLayerSize, int hiddenLayerSize, int outputLayerSize) {
        super(cell, inputLayerSize, hiddenLayerSize, outputLayerSize);
    }

    @Override
    protected void forwardPropagation() {
        double sum;
        for (int i = 0; i < hiddenLayerSize; i++) {
            sum = 0;
            for (int j = 0; j < inputLayerSize; j++) {
                sum += inputs[j] * w1[j][i];
            }
            hiddenLayerOutput[i] = Maths.sigmoid(sum);
        }

        for (int i = 0; i < outputLayerSize; i++) {
            sum = 0;
            for (int j = 0; j < hiddenLayerSize+1; j++) {
                sum += hiddenLayerOutput[j] * w2[j][i];
            }
            output[i] = (sum <= 0f) ? 0 : 1;
        }
    }

    @Override
    protected Action decide(Perception perception) {
        final int TRUE = 1;
        final int FALSE = 0;

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

        inputs[8]=northCell != null && !northCell.hasTail() ? 0 : 1;
        inputs[9]=eastCell != null && !eastCell.hasTail() ? 0 : 1;
        inputs[10]=southCell != null && !southCell.hasTail() ? 0 : 1;
        inputs[11]=westCell != null && !westCell.hasTail() ? 0 : 1;

        forwardPropagation();

        if(output[0] == FALSE && output[1] == FALSE)
            return Action.NORTH;
        else if(output[0] == FALSE && output[1] == TRUE)
            return Action.EAST;
        else if(output[0] == TRUE && output[1] == FALSE)
            return Action.SOUTH;
        else if(output[0] == TRUE && output[1] == TRUE)
            return Action.WEST;

        return null;
    }

    @Override
    protected void checkLimit(Environment environment) {
        if(this.tail.size() < 5 && movesAfterFoodCaught >= 10){
            this.movesAfterLimit++;
        }else if (this.tail.size() < 10 && movesAfterFoodCaught >= 22) {
            this.movesAfterLimit++;
        }
        super.checkLimit(environment);
    }
}
