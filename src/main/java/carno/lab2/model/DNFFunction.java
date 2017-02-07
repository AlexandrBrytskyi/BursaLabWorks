package carno.lab2.model;

import java.util.List;

/**
 * Created by alexandr on 03.10.16.
 */
public class DNFFunction extends Function  {

    public DNFFunction(List<Minterm> minterms) {
        super(minterms);
    }


    @Override
    public String toString() {
        String res = "";
        for (Minterm minterm : super.getMinterms()) {
            /*logicheskoe ili*/
            res += minterm + " V ";
        }
        res = res.substring(0, res.length() - 2);
        return "СДНФ Fun = " + res;
    }
}
