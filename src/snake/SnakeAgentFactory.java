package snake;

import exceptions.InvalidAgentException;
import snake.snakeAI.nn.SnakeAIAgent;
import snake.snakeAdhoc.SnakeAdhocAgent;
import snake.snakeRandom.SnakeRandomAgent;

import java.awt.*;
import java.security.InvalidParameterException;

public class SnakeAgentFactory {

    private static Environment myEnvironment;

    public static SnakeAgent buildSnakeAgent(String agentType, Environment environment){
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
            case "ONE SNAKE":
                return new SnakeAIAgent(environment.getCellAtRandomLocation(), environment.getNumInputs(), environment.getNumHiddens(), environment.getNumOutputs());
            default:
                throw new InvalidAgentException("Agent name does not exist in the current context.");
        }
    }
}
