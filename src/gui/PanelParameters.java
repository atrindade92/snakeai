package gui;

import snake.snakeAI.ga.geneticOperators.*;
import snake.snakeAI.ga.selectionMethods.RouletteWheel;
import snake.snakeAI.ga.selectionMethods.SelectionMethod;
import snake.snakeAI.ga.selectionMethods.Tournament;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import snake.snakeAI.SnakeIndividual;
import snake.snakeAI.SnakeProblem;

public class PanelParameters extends PanelAtributesValue {

    public static final int TEXT_FIELD_LENGHT = 7;

    public static final String SEED = "1";
    public static final String POPULATION_SIZE = "50";
    public static final String GENERATIONS = "1000";
    public static final String TOURNAMENT_SIZE = "10";
    public static final String PROB_RECOMBINATION = "0.85";
    public static final String PROB_MUTATION = "0.01";

    private MainFrame mainFrame;

    JTextField textFieldSeed = new JTextField(SEED, TEXT_FIELD_LENGHT);
    JTextField textFieldN = new JTextField(POPULATION_SIZE, TEXT_FIELD_LENGHT);
    JTextField textFieldGenerations = new JTextField(GENERATIONS, TEXT_FIELD_LENGHT);
    String[] selectionMethods = {"Tournament", "Roulette"};
    JComboBox comboBoxSelectionMethods = new JComboBox(selectionMethods);
    JTextField textFieldTournamentSize = new JTextField(TOURNAMENT_SIZE, TEXT_FIELD_LENGHT);
    String[] recombinationMethods = {"One cut", "Two cuts", "Uniform", "Arithmetical"};
    JComboBox comboBoxRecombinationMethods = new JComboBox(recombinationMethods);
    JTextField textFieldProbRecombination = new JTextField(PROB_RECOMBINATION, TEXT_FIELD_LENGHT);
    String[] mutationMethods = {"Uniform", "Swap"};
    JComboBox comboBoxMutationMethods = new JComboBox(mutationMethods);
    JTextField textFieldProbMutation = new JTextField(PROB_MUTATION, TEXT_FIELD_LENGHT);
    String[] controllerTypes = {"One snake 1", "One snake 2", "Homogeneous Snake", "Heterougeneous Snake", "Random", "Ad-Hoc"};
    JComboBox comboBoxControllerTypes = new JComboBox(controllerTypes);

    public PanelParameters(MainFrame mainFrame) {
        title = "Genetic algorithm parameters";

        this.mainFrame = mainFrame;

        labels.add(new JLabel("Controller: "));
        valueComponents.add(comboBoxControllerTypes);
        comboBoxControllerTypes.addActionListener(new JComboBoxController_ActionAdapter(this));

        labels.add(new JLabel("Seed: "));
        valueComponents.add(textFieldSeed);
        textFieldSeed.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("Population size: "));
        valueComponents.add(textFieldN);
        textFieldN.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("# of generations: "));
        valueComponents.add(textFieldGenerations);
        textFieldGenerations.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("Selection method: "));
        valueComponents.add(comboBoxSelectionMethods);
        comboBoxSelectionMethods.addActionListener(new JComboBoxSelectionMethods_ActionAdapter(this));

        labels.add(new JLabel("Tournament size: "));
        valueComponents.add(textFieldTournamentSize);
        textFieldTournamentSize.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("Recombination method: "));
        valueComponents.add(comboBoxRecombinationMethods);

        labels.add(new JLabel("Recombination prob.: "));
        valueComponents.add(textFieldProbRecombination);

        labels.add(new JLabel("Mutation method: "));
        valueComponents.add(comboBoxMutationMethods);

        labels.add(new JLabel("Mutation prob.: "));
        valueComponents.add(textFieldProbMutation);

        configure();
    }

    public void actionPerformedSelectionMethods(ActionEvent e) {
        textFieldTournamentSize.setEnabled(comboBoxSelectionMethods.getSelectedIndex() == 0);
    }

    public void actionPerformedControllerSelection(ActionEvent e){
        final int selectedIndex = comboBoxControllerTypes.getSelectedIndex();
        final boolean isControllerAdhocOrRandom = !(selectedIndex == 4 || selectedIndex == 5);

        //TODO: limpar ao mudar de controlador

        toggleInputs(isControllerAdhocOrRandom);
        mainFrame.toggleDataSetButton(isControllerAdhocOrRandom);
    }

    public boolean isAIController(){
        final int selectedIndex = comboBoxControllerTypes.getSelectedIndex();
        return selectedIndex == 0 || selectedIndex == 1 || selectedIndex == 2 || selectedIndex == 3;
    }

    public boolean hasTwoAgents(){
        final int selectedIndex = comboBoxControllerTypes.getSelectedIndex();
        return selectedIndex == 2 || selectedIndex == 3;
    }

    private void toggleInputs(boolean value) {
        textFieldSeed.setEnabled(value);
        textFieldN.setEnabled(value);
        textFieldGenerations.setEnabled(value);
        comboBoxSelectionMethods.setEnabled(value);
        textFieldTournamentSize.setEnabled(value);
        comboBoxRecombinationMethods.setEnabled(value);
        textFieldProbRecombination.setEnabled(value);
        textFieldProbMutation.setEnabled(value);
    }

    public SelectionMethod<SnakeIndividual, SnakeProblem> getSelectionMethod() {
        switch (comboBoxSelectionMethods.getSelectedIndex()) {
            case 0:
                return new Tournament<>(
                        Integer.parseInt(textFieldN.getText()),
                        Integer.parseInt(textFieldTournamentSize.getText()));
            case 1:
                return new RouletteWheel<>(
                        Integer.parseInt(textFieldN.getText()));

        }
        return null;
    }

    public Recombination<SnakeIndividual> getRecombinationMethod() {

        double recombinationProb = Double.parseDouble(textFieldProbRecombination.getText());

        switch (comboBoxRecombinationMethods.getSelectedIndex()) {
            case 0:
                return new RecombinationOneCut<>(recombinationProb);
            case 1:
                return new RecombinationTwoCuts<>(recombinationProb);
            case 2:
                return new RecombinationUniform<>(recombinationProb);
            case 3:
                return new RecombinationArithmetical<>(recombinationProb);
        }
        return null;
    }

    public Mutation<SnakeIndividual> getMutationMethod() {
        double mutationProbability = Double.parseDouble(textFieldProbMutation.getText());
        switch (comboBoxMutationMethods.getSelectedIndex()) {
            case 0:
                return new MutationUniform<>(mutationProbability);
            case 1:
                return new MutationSwap<>(mutationProbability);
        }
        return null;
    }

    public String getControllerType() {
        return controllerTypes[comboBoxControllerTypes.getSelectedIndex()];
    }
}

class JComboBoxController_ActionAdapter implements ActionListener{
    final private PanelParameters adaptee;

    public JComboBoxController_ActionAdapter(PanelParameters adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.actionPerformedControllerSelection(e);
    }
}

class JComboBoxSelectionMethods_ActionAdapter implements ActionListener {

    final private PanelParameters adaptee;

    JComboBoxSelectionMethods_ActionAdapter(PanelParameters adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.actionPerformedSelectionMethods(e);
    }
}

class IntegerTextField_KeyAdapter implements KeyListener {

    final private MainFrame adaptee;

    IntegerTextField_KeyAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
            e.consume();
        }
    }
}
