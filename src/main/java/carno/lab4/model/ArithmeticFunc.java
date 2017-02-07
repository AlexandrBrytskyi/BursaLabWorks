package carno.lab4.model;


import java.util.*;

public class ArithmeticFunc {

    private List<Variable> variables;

    public ArithmeticFunc() {
        variables = new ArrayList<>();
    }

    public void addVariable(Variable variable) {
        if (variables.contains(variable)) {
                /*plus or minus*/
            Variable inList = variables.get(variables.indexOf(variable));
            inList.setKoef(inList.getKoef() + variable.getKoef());
            if (inList.getKoef() == 0) variables.remove(inList);
        } else variables.add(variable);
    }

    private int defineKoef(Variable variable) {
        return variable.getKoef();
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public void umnojit(int mnojnik) {
        for (Variable variable : variables) {
            variable.setKoef(defineKoef(variable) * mnojnik);
        }
    }

    public ArithmeticFunc doLogicOperation(OperationType operationType, ArithmeticFunc arithmeticFunc) {
        /*a and b = a*b
        * a or b = a+b - a*b*/

        if (operationType.equals(OperationType.mnoj)) {
            ArithmeticFunc res = new ArithmeticFunc();
            for (Variable var : arithmeticFunc.variables) {
                for (Variable thisVar : variables) {
                    res.addVariable(umnojitVariables(var, thisVar));
                }
            }
            return res;
        }
        if (operationType.equals(OperationType.or)) {
            ArithmeticFunc res = new ArithmeticFunc();
            for (Variable variable : variables) {
                res.addVariable(variable);
            }
            for (Variable variable : arithmeticFunc.variables) {
                res.addVariable(variable);
            }
            for (Variable var : arithmeticFunc.variables) {
                for (Variable thisVar : variables) {
                    Variable umnojennie = umnojitVariables(var, thisVar);
                    umnojennie.setOperationType(OperationType.minus);
                    res.addVariable(umnojennie);
                }
            }
            return res;
        }
        return null;
    }

    private Variable umnojitVariables(Variable var1, Variable var2) {
        Set<Integer> integers = new HashSet<>();
        for (String integer : var1.getName().split("X")) {
            if (!integer.isEmpty()) integers.add(Integer.valueOf(integer));
        }
        for (String integer : var2.getName().split("X")) {
            if (!integer.isEmpty()) integers.add(Integer.valueOf(integer));
        }
        Integer[] arr = new Integer[integers.size()];
        arr = integers.toArray(arr);
        Arrays.sort(arr);
        StringBuilder res = new StringBuilder();
        for (Integer integer : arr) {
            res.append("X" + integer);
        }
        return new Variable(
                (var1.operationType.equals(OperationType.minus) ||
                        var2.operationType.equals(OperationType.minus) ?
                        OperationType.minus : OperationType.plus)
                , res.toString()
                , var1.getKoef() * var2.getKoef());
    }

    @Override
    public String toString() {
        Collections.sort(variables);
        return getStringVars();
    }

    private String getStringVars() {
        StringBuilder b = new StringBuilder();
        for (Variable variable : variables) {
            b.append(variable.toString() + " ");
        }
        return b.toString();
    }
}
