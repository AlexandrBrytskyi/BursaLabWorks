package carno.lab2.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by alexandr on 02.10.16.
 */
public abstract class Function {

    private List<Minterm> minterms;

    public Function(List<Minterm> minterms) {
        this.minterms = new LinkedList<Minterm>();
        if (minterms != null && minterms.size() > 0) {
            for (Minterm minterm : minterms) {
                this.minterms.add(minterm);
            }
        }
    }

    public List<Minterm> getMinterms() {
        return minterms;
    }


}
