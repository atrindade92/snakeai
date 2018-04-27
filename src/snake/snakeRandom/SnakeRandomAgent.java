package snake.snakeRandom;

import snake.*;

import java.awt.*;
import java.util.Random;

public class SnakeRandomAgent extends SnakeAgent {

    private Random random = new Random(System.currentTimeMillis());

    public SnakeRandomAgent(Cell cell, Color color) {
        super(cell, color);
    }


    @Override
    protected Action decide(Perception perception) {
        int[] visitedDirections = new int[] {0, 0, 0, 0};
        //Fill array with 0s

        do {
            //Generate value between 0 and 3 (0 = N, 1 = E, 2 = S, 3 = W)
            int direction = random.nextInt(4);

            switch(direction){
                case 0:
                    if(perception.getN() != null && !perception.getN().hasTail()){
                        return Action.NORTH;
                    }
                    visitedDirections[0] = 1;
                    break;
                case 1:
                    //E
                    if(perception.getE() != null && !perception.getE().hasTail()){
                        return Action.EAST;
                    }
                    visitedDirections[1] = 1;
                    break;
                case 2:
                    //S
                    if(perception.getS() != null && !perception.getS().hasTail()){
                        return Action.SOUTH;
                    }
                    visitedDirections[2] = 1;
                    break;
                case 3:
                    //W
                    if(perception.getW() != null && !perception.getW().hasTail()){
                        return Action.WEST;
                    }
                    visitedDirections[3] = 1;
                    break;
                default:
                    return null;
            }
        }while(!allDirectionsVisited(visitedDirections));

        return null;
    }

    private boolean allDirectionsVisited(int[] visitedDirections){
        return (visitedDirections[0] == 1 && visitedDirections[1] == 1 && visitedDirections[2] == 1 && visitedDirections[3] == 1);
    }
}
