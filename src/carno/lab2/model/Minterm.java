package carno.lab2.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by alexandr on 02.10.16.
 */
public class Minterm implements Comparable<Minterm> {

    private int num;
    private boolean functionValue;
    private List<Variable> variables;

    public Minterm(int num, boolean functionValue, List<Variable> variables) {
        this.num = num;
        this.functionValue = functionValue;
        this.variables = new LinkedList<Variable>();
        this.variables = variables;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public boolean isFunctionValue() {
        return functionValue;
    }

    public void setFunctionValue(boolean functionValue) {
        this.functionValue = functionValue;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    @Override
    public String toString() {
        String res = "";
        for (Variable variable : variables) {
            /*logicheskoe i*/
            res += variable;
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Minterm minterm = (Minterm) o;

        if (num != minterm.num) return false;
        if (functionValue != minterm.functionValue) return false;
        return variables != null ? variables.equals(minterm.variables) : minterm.variables == null;

    }

    @Override
    public int hashCode() {
        int result = num;
        result = 31 * result + (functionValue ? 1 : 0);
        result = 31 * result + (variables != null ? variables.hashCode() : 0);
        return result;
    }

    public String knfString() {
        String res = "(";
        for (Variable variable : variables) {
            /*logicheskoe ili*/
            res += variable + "+";
        }
        res += ")";
        return res;
    }

    @Override
    public int compareTo(Minterm o) {
        for (int i = 0; i < o.getVariables().size(); i++) {
            if (variables.size() - o.getVariables().size() != 0) return variables.size() - o.getVariables().size();
            if (this.variables.get(i).isInverted() && !o.getVariables().get(i).isInverted()) return -1;
            if (!this.variables.get(i).isInverted() && o.getVariables().get(i).isInverted()) return 1;
        }
        return 0;
    }
}
