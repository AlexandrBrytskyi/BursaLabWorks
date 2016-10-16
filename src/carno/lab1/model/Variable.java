package carno.lab1.model;

/**
 * Created by alexandr on 02.10.16.
 */
public class Variable {

    private char name;
    private boolean isInverted;

    public Variable(char name, boolean isInverted) {
        this.name = name;
        this.isInverted = isInverted;
    }


    public void setName(char name) {
        this.name = name;
    }

    public boolean isInverted() {
        return isInverted;
    }

    public void setInverted(boolean inverted) {
        isInverted = inverted;
    }

    @Override
    public String toString() {
        return isInverted ? "!" + name : "" + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variable variable = (Variable) o;

        if (name != variable.name) return false;
        return isInverted == variable.isInverted;

    }

    public char getName() {
        return name;
    }

    @Override
    public int hashCode() {
        int result = (int) name;
        result = 31 * result + (isInverted ? 1 : 0);
        return result;
    }
}
