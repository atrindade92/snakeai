package snake;

import exceptions.InvalidAgentException;
import snake.snakeAI.nn.HomogeneousSnakeAIAgent;
import snake.snakeAI.nn.OneSnakeAIAgent1;
import snake.snakeAI.nn.OneSnakeAIAgent2;
import snake.snakeAdhoc.SnakeAdhocAgent;
import snake.snakeRandom.SnakeRandomAgent;

import java.awt.*;
import java.security.InvalidParameterException;

public class SnakeAgentFactory {

    private static Environment myEnvironment;

    public static SnakeAgent buildSnakeAgent(String agentType, Environment environment, int agentIndex){
        if(agentType == null)
            throw new InvalidParameterException("Controller cannot be null.");

        if(agentType.isEmpty())
            throw new InvalidAgentException("Agent name must not be empty.");

        final Color agentColor = new Color(0,153,51);
        myEnvironment = environment;

        switch (agentType.toUpperCase()) {
            case "RANDOM":
                return new SnakeRandomAgent(environment.getCellAtRandomLocation(), agentColor);
            case "AD-HOC":
                return new SnakeAdhocAgent(environment.getCellAtRandomLocation(), agentColor);
            case "ONE SNAKE 1":
                return new OneSnakeAIAgent1(environment.getCellAtRandomLocation(), environment.getNumInputs(), environment.getNumHiddens(), environment.getNumOutputs());
            case "ONE SNAKE 2":
                return new OneSnakeAIAgent2(environment.getCellAtRandomLocation(), environment.getNumInputs(), environment.getNumHiddens(), environment.getNumOutputs());
            case "HOMOGENEOUS SNAKE":
                return new HomogeneousSnakeAIAgent(environment.getCellAtRandomLocation(), environment.getNumInputs(), environment.getNumHiddens(), environment.getNumOutputs(), agentIndex);
            default:
                throw new InvalidAgentException("Agent name does not exist in the current context.");
        }
    }
}
