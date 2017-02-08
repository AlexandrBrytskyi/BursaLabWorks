package rl.lab2;


import rl.RLOperations;
import rl.lab1.RLChislo;

public class RLKodingKeyValue {

    private RLChislo p;
    private RLChislo q;
    private RLChislo n;

    public RLKodingKeyValue() {
    }

    public RLKodingKeyValue(RLChislo p, RLChislo q, RLChislo n) {
        this.p = p;
        this.q = q;
        this.n = n;
    }

    public RLChislo getP() {
        return p;
    }

    public void setP(RLChislo p) {
        this.p = p;
    }

    public RLChislo getQ() {
        return q;
    }

    public void setQ(RLChislo q) {
        this.q = q;
    }

    public RLChislo getN() {
        return n;
    }

    public void setN(RLChislo n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return "RLKodingKeyValue{" +
                "p=" + p + " = " + RLOperations.to10FromRL(p) + "\n" +
                ", q=" + q + " = " + RLOperations.to10FromRL(q) + "\n" +
                ", n=" + n + " = " + RLOperations.to10FromRL(n) + "\n" +
                '}';
    }
}
