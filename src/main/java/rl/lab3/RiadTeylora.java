package rl.lab3;


import rl.RLOperations;
import rl.lab1.RLChislo;

import java.util.List;


/* sum = Ai * x^i */
public class RiadTeylora {

    private List<RLChislo> constants;

    private int N;

    private List<RLChislo> xses;

    private RLChislo sum;

    public RiadTeylora(List<RLChislo> constants, int n, RLChislo x) {
        this.constants = constants;
        N = n;
        this.xses = xses;
        this.sum = sum;
    }

    public List<RLChislo> getConstants() {
        return constants;
    }

    public void setConstants(List<RLChislo> constants) {
        this.constants = constants;
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public List<RLChislo> getXses() {
        return xses;
    }

    public void setXses(List<RLChislo> xses) {
        this.xses = xses;
    }

    public RLChislo getSum() {
        return sum;
    }

    public void setSum(RLChislo sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "RiadTeylora{" +
                "constants=" + getConstantsString() +
                ", N=" + N +
                ", xses=" + xses +
                ", sum=" + sum +
                '}';
    }

    public String printFormula(boolean withX) {
        StringBuilder builder = new StringBuilder();
        if (sum == null) builder.append("Sum = ");
        else builder.append(sum + " = ");
        if (!withX) for (int i = 0; i < constants.size(); i++) {
            builder.append(constants.get(i)).append("*").append("x^").append(i).append(" + ");
        }
        else for (int i = 0; i < constants.size(); i++) {
            builder.append(constants.get(i)).append("*").append(xses.get(i)).append(" + ");
        }
        return builder.toString();
    }

    private String getConstantsString() {
        StringBuilder builder = new StringBuilder("{");

        int current = 0;
        for (RLChislo constant : constants) {
            builder.append("A" + current++ + " = " + RLOperations.to10FromRL(constant) + ", ");
        }
        builder.append("}");
        return builder.toString();
    }
}
