package gui;

import snake.Environment;
import snake.EnvironmentListener;
import snake.snakeAI.SnakeProblem;
import snake.snakeAI.nn.SnakeAIAgent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class PanelSimulation extends JPanel implements EnvironmentListener {

    public static final int PANEL_SIZE = 250;
    public static final int CELL_SIZE = 20;
    public static final int GRID_TO_PANEL_GAP = 20;
    MainFrame mainFrame;
    private Environment environment;
    private Image image;
    JPanel environmentPanel = new JPanel();
    final JButton buttonSimulate = new JButton("Simulate");
    private SwingWorker worker;

    public PanelSimulation(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        environmentPanel.setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
        setLayout(new BorderLayout());

        add(environmentPanel, java.awt.BorderLayout.NORTH);
        add(buttonSimulate, java.awt.BorderLayout.SOUTH);
        buttonSimulate.addActionListener(new SimulationPanel_jButtonSimulate_actionAdapter(this));

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(""),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void setJButtonSimulateEnabled(boolean enabled) {
        buttonSimulate.setEnabled(enabled);
    }

    public void jButtonSimulate_actionPerformed(ActionEvent e) {
        if(worker != null)
        {
            worker.cancel(true);
            environment.cleanBoard(); // Se clicar 2x em simulate, não limpa a snake
            environment.removeEnvironmentListener(this);
            worker = null;
            return ;
        }

        SnakeProblem problem = mainFrame.getProblem();

        if(problem == null) {
            problem = SnakeProblem.buildDefaultProblem(mainFrame.getControllerType());
            mainFrame.setProblem(problem);
        }

        environment = problem.getEnvironment();
        environment.addEnvironmentListener(this);

//        if(environment.hasAgent())
//            environment.cleanBoard();
//        else
//            environment.setAgent(mainFrame.getControllerType());

        final String mainFrameSnakeController = mainFrame.getControllerType();
        if(!environment.getController().equals(mainFrameSnakeController))
            environment.setAgent(mainFrameSnakeController);

        if(!environment.hasAgent())
            environment.setAgent(mainFrame.getControllerType());

        buildImage(environment);

        final PanelSimulation simulationPanel = this;

        worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                int environmentSimulations = mainFrame.getProblem().getNumEvironmentSimulations();
                for (int i = 0; i < environmentSimulations; i++) {
                    environment.initialize(i);
                    if(mainFrame.isAIAgent())
                        ((SnakeAIAgent)  environment.getAgent()).setWeights(mainFrame.getBestInRun().getGenome());
                    environmentUpdated();
                    environment.simulate();
                }
                return null;
            }

            @Override
            public void done() {
                environment.removeEnvironmentListener(simulationPanel);
                try {
                    Thread.sleep(400);
                } catch (InterruptedException ignore) {
                }

            }
        };
        worker.execute();
    }

    public void buildImage(Environment environment) {
        image = new BufferedImage(
                environment.getSize() * CELL_SIZE + 1,
                environment.getSize() * CELL_SIZE + 1,
                BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void environmentUpdated() {
        int n = environment.getSize();
        Graphics g = image.getGraphics();

        //Fill the cells color
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                g.setColor(environment.getCellColor(y, x));
                g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        //Draw the grid lines
        g.setColor(Color.BLACK);
        for (int i = 0; i <= n; i++) {
            g.drawLine(0, i * CELL_SIZE, n * CELL_SIZE, i * CELL_SIZE);
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, n * CELL_SIZE);
        }

        g = environmentPanel.getGraphics();
        g.drawImage(image, GRID_TO_PANEL_GAP, GRID_TO_PANEL_GAP, null);

        try {
            Thread.sleep(100);
        } catch (InterruptedException ignore) {
        }
    }
}

//--------------------
class SimulationPanel_jButtonSimulate_actionAdapter implements ActionListener {

    final private PanelSimulation adaptee;

    SimulationPanel_jButtonSimulate_actionAdapter(PanelSimulation adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.jButtonSimulate_actionPerformed(e);
    }
}
