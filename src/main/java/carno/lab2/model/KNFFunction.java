package carno.lab2.model;

import java.util.List;

/**
 * Created by alexandr on 03.10.16.
 */
public class KNFFunction extends Function  {

    public KNFFunction(List<Minterm> minterms) {
        super(minterms);
    }


    @Override
    public String toString() {
        String res = "";
        for (Minterm minterm : super.getMinterms()) {
            /*logicheskoe ili*/
            res += minterm.knfString() + " ∧ ";
        }
        res = res.substring(0, res.length() - 2);
        return "СКНФ Fun = " + res;
    }
}
