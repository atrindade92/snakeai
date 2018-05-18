package snake;

import exceptions.InvalidAgentException;
import snake.snakeAI.SnakeProblem;
import snake.snakeAI.nn.SnakeAIAgent;
import snake.snakeAdhoc.SnakeAdhocAgent;
import snake.snakeRandom.SnakeRandomAgent;

import java.awt.*;
import java.security.InvalidParameterException;
import java.util.Random;

public class SnakeAgentFactory {

    private static int thisMaxGridXSize;
    private static int thisMaxGridYSize;

    public static SnakeAgent buildSnakeAgent(String agentType, int maxGridXSize, int maxGridYSize, SnakeProblem problem){
        if(agentType == null)
            throw new InvalidParameterException("Controller cannot be null.");

        if(agentType.isEmpty())
            throw new InvalidAgentException("Agent name must not be empty.");

        final Color agentColor = new Color(0,153,51);
        thisMaxGridXSize = maxGridXSize;
        thisMaxGridYSize = maxGridYSize;

        switch (agentType.toUpperCase()) {
            case "RANDOM":
                return new SnakeRandomAgent(getCellAtRandomLocation(), agentColor);
            case "AD-HOC":
                return new SnakeAdhocAgent(getCellAtRandomLocation(), agentColor);
            case "ONE SNAKE":
                return new SnakeAIAgent(getCellAtRandomLocation(), problem.getNumInputs(), problem.getNumHiddenUnits(),
                        problem.getNumOutputs());
            default:
                throw new InvalidAgentException("Agent name does not exist in the current context.");
        }
    }

    private static Cell getCellAtRandomLocation(){
        Random random = new Random();
        return new Cell(random.nextInt(thisMaxGridXSize), random.nextInt(thisMaxGridYSize));
    }

}
