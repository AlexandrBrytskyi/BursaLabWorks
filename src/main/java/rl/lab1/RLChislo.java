package rl.lab1;


import rl.RLOperations;

import java.util.ArrayList;
import java.util.List;

public class RLChislo implements Cloneable {

    /**
     * if value = false => Q = +
     */
    private boolean znakQ = false;

    /**
     * znachushchi odinici
     */
    private List<Long> rozriads;

    public RLChislo() {
    }

    public RLChislo(boolean znakQ, List<Long> rozriads) {
        this.znakQ = znakQ;
        this.rozriads = rozriads;
    }

    public boolean isZnakQ() {
        return znakQ;
    }

    public void setZnakQ(boolean znakQ) {
        this.znakQ = znakQ;
    }

    public List<Long> getRozriads() {
        return rozriads;
    }

    public void setRozriads(List<Long> rozriads) {
        this.rozriads = rozriads;
    }

    @Override
    public String toString() {
        return "{" + (znakQ ? 1 : 0) +
                "." + rozriads.size() +
                printRozriads(rozriads) + " = " + RLOperations.to10FromRL(this) + "}";
    }

    private String printRozriads(List<Long> rozriads) {
        StringBuilder builder = new StringBuilder();
        for (Long rozriad : rozriads) {
            builder.append(".").append(rozriad);
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RLChislo rlChislo = (RLChislo) o;

        if (znakQ != rlChislo.znakQ) return false;
        return rozriads != null ? rozriads.equals(rlChislo.rozriads) : rlChislo.rozriads == null;

    }

    @Override
    public int hashCode() {
        int result = (znakQ ? 1 : 0);
        result = 31 * result + (rozriads != null ? rozriads.hashCode() : 0);
        return result;
    }

    @Override
    public RLChislo clone() throws CloneNotSupportedException {
        return new RLChislo(new Boolean(znakQ), new ArrayList<>(rozriads));
    }
}
