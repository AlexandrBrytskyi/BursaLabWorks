package carno.lab4.model;


public class Variable extends AbstractOperation implements Comparable<Variable> {

    private String name;
    private int koef = 1;

    public Variable(OperationType operationType, String name) {
        super(operationType);
        this.name = name;
    }

    public Variable(OperationType operationType, String name, int koef) {
        super(operationType);
        this.name = name;
        this.koef = koef;
    }

    @Override
    public AbstractOperation doJob() {
        if (operationType == null) return this;
        if (operationType.equals(OperationType.not)) return new Operation(
                new Variable(null, String.valueOf(1)),
                new Variable(null, String.valueOf(name)),
                OperationType.minus);
        return null;
    }

    @Override
    public AbstractOperation umnojit() {
        return this;
    }

    @Override
    public AbstractOperation uprostit() {
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return (operationType == null ? "" : " " + (operationType.equals(OperationType.plus) ? "+" :
                operationType.equals(OperationType.or) ? "V" :
                        operationType.equals(OperationType.not) ? "!" : "")) +
                (koef == 1 ? "" : koef) + name;
    }

    @Override
    public void setOperationType(OperationType operationType) {
        if (operationType.equals(OperationType.plus)) {
            koef = Math.abs(koef);
        }
        if (operationType.equals(OperationType.minus)) {
            koef = -koef;
        }
        this.operationType = operationType;
    }

    @Override
    public ArithmeticFunc getArithmFunc() {
        ArithmeticFunc arithmeticFunc = new ArithmeticFunc();
        this.operationType = OperationType.plus;
        arithmeticFunc.addVariable(this);
        return arithmeticFunc;
    }

    @Override
    public AbstractOperation copy() {
        return new Variable(operationType, new String(name));
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variable variable = (Variable) o;

        return name != null ? name.equals(variable.name) : variable.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public int getKoef() {
        return koef;
    }

    public void setKoef(int koef) {
        this.koef = koef;
    }

    @Override
    public int compareTo(Variable o) {
        return name.compareTo(o.name);
    }
}
