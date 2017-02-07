package carno.lab1.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by alexandr on 02.10.16.
 */
public class Function {

    private List<Minterm> minterms;

    public Function(Minterm... minterms) {
        this.minterms = new LinkedList<Minterm>();
        if (minterms != null && minterms.length > 0) {
            for (Minterm minterm : minterms) {
                this.minterms.add(minterm);
            }
        }
    }

    public List<Minterm> getMinterms() {
        return minterms;
    }

    @Override
    public String toString() {
        String res = "";
        for (Minterm minterm : minterms) {
            /*logicheskoe ili*/
            res += minterm + "V";
        }
        res = res.substring(0, res.length() - 1);
        return "Fun = " + res;
    }
}
