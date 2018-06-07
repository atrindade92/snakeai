package snake;

public class PerceptionWithOpponent extends Perception {

    private Cell opponent;

    public PerceptionWithOpponent(Cell N, Cell S, Cell E, Cell O, Cell f, Cell opponent) {
        super(N, S, E, O, f);
    }

    public Cell getOpponent() {
        return opponent;
    }
}
