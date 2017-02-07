package carno.lab4.model;


public abstract class AbstractOperation {

    protected OperationType operationType;

    public AbstractOperation(OperationType operationType) {
        this.operationType = operationType;
    }

    public abstract AbstractOperation doJob();

    public abstract AbstractOperation umnojit();

    public abstract AbstractOperation uprostit();

    @Override
    public String toString() {
        return "AbstractOperation{" +
                "operationType=" + operationType +
                '}';
    }

    public abstract void setOperationType(OperationType operationType);

    public abstract ArithmeticFunc getArithmFunc();

    public abstract AbstractOperation copy();
}
