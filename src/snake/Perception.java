package snake;

public class Perception {
    private Cell n, s, e, w, f;

    public Perception(Cell N, Cell S, Cell E, Cell O, Cell f) {
        this.n = N;
        this.s = S;
        this.e = E;
        this.w = O;
        this.f = f;
    }

    public Cell getE() {
        return e;
    }

    public Cell getN() {
        return n;
    }

    public Cell getW() {
        return w;
    }

    public Cell getS() {
        return s;
    }

    public Cell getF() {
        return f;
    }
}
