package rl.lab1;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RLOperations {

    /**
     * returns RL form of number
     *
     * @param number value to be parsed
     */
    public static RLChislo rlFrom10(BigDecimal number) {
        List<Long> znachOd = new ArrayList<>();

        long stepen = (long) (Math.log(number.floatValue()) / Math.log(2));

        return new RLChislo(number.compareTo(BigDecimal.ZERO) > 0 ? false : true, findZnachOd(number.abs(), znachOd, stepen));
    }

    private static List<Long> findZnachOd(BigDecimal number, List<Long> znachOd, long stepen) {

        BigDecimal dif = number.subtract(new BigDecimal(2).pow((int) stepen, MathContext.DECIMAL128));
        if (dif.equals(BigDecimal.ZERO)) {
            znachOd.add(stepen);
            return znachOd;
        } else if (dif.compareTo(BigDecimal.ZERO) < 0) {
            stepen--;
            return findZnachOd(number, znachOd, stepen);
        } else {
            znachOd.add(stepen);
            stepen--;
            return findZnachOd(dif, znachOd, stepen);
        }
    }


    public static BigDecimal to10FromRL(RLChislo rl) {
        BigDecimal res = new BigDecimal(0);
        for (Long rozrid : rl.getRozriads()) {
            res = res.add(new BigDecimal(2).pow(rozrid.intValue(), MathContext.DECIMAL128));
        }
        return rl.isZnakQ() ? res.multiply(new BigDecimal(-1)) : res;
    }

    public static RLChislo sum(final RLChislo rl1, final RLChislo rl2) {
        if (rl1 == null) return rl2;
        if (rl2 == null) return rl1;
        if (rl1.isZnakQ() || rl2.isZnakQ()) return minus(rl1, rl2);
        ArrayList<Long> rozriads = new ArrayList<Long>() {{
            for (Long aLong : rl1.getRozriads()) {
                add(aLong);
            }
            for (Long bLong : rl2.getRozriads()) {
                add(bLong);
            }
        }};
        sortOdinici(rozriads);
        zvestiPodibni(rozriads);
        return new RLChislo(false, rozriads);
    }

    public static RLChislo umnojitSimple(RLChislo rl1, RLChislo rl2) {
        List<Long> odinici = new ArrayList<>();
        for (Long rl1Odinicia : rl1.getRozriads()) {
            for (Long rl2Odinicia : rl2.getRozriads()) {
                odinici.add(rl1Odinicia + rl2Odinicia);
            }
        }
        sortOdinici(odinici);
        zvestiPodibni(odinici);
        return new RLChislo(rl1.isZnakQ() || rl2.isZnakQ(), odinici);
    }

    private static void sortOdinici(List<Long> odinizi) {
        Collections.sort(odinizi, new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                return o2.compareTo(o1);
            }
        });
    }

    private static void zvestiPodibni(List<Long> odinizi) {
        for (int i = 1; i < odinizi.size(); i++) {
            Long one = odinizi.get(i - 1);
            Long next = odinizi.get(i);
            if (one.equals(next)) {
                odinizi.remove(next);
                odinizi.set(i - 1, ++one);
                if (i > 1) i -= 2;
            }
        }
    }

    public static RLChislo increment(RLChislo rl) {
        return sum(rl, rlFrom10(BigDecimal.ONE));
    }

    public static RLChislo decrement(RLChislo num) {
        RLChislo operand = null;
        try {
            operand = (RLChislo) num.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return minus(operand, rlFrom10(BigDecimal.ONE));
    }

    private static RLChislo minus(RLChislo rl1, RLChislo rl2) {
        RLChislo zmenshuvane = null;
        RLChislo vidyemnik = null;
        boolean setup = false;
        int currRozr = 0;
        while (!setup) {
            List<Long> rozriadsRL1 = rl1.getRozriads();
            List<Long> rozriadsRL2 = rl2.getRozriads();
            if (rozriadsRL1.size() > currRozr && rozriadsRL2.size() > currRozr) {
                Long aLong = rozriadsRL1.get(currRozr);
                Long bLong = rozriadsRL2.get(currRozr);
                if (aLong > bLong) {
                    zmenshuvane = rl1;
                    vidyemnik = rl2;
                    setup = true;
                } else if (aLong < bLong) {
                    zmenshuvane = rl2;
                    vidyemnik = rl1;
                    setup = true;
                } else {
                    currRozr++;
                }
            } else if (rozriadsRL1.size() < currRozr && rozriadsRL2.size() > currRozr) {
                zmenshuvane = rl2;
                vidyemnik = rl1;
                setup = true;
            } else {
                zmenshuvane = rl1;
                vidyemnik = rl2;
                setup = true;
            }
        }


        while (vidyemnik.getRozriads().size() != 0) {
            rozlojitkRozriadu(zmenshuvane, vidyemnik.getRozriads().get(vidyemnik.getRozriads().size() - 1));
            removeSameRozriads(zmenshuvane, vidyemnik);
            sortOdinici(zmenshuvane.getRozriads());
            zvestiPodibni(zmenshuvane.getRozriads());
        }
        return zmenshuvane;
    }

    private static void rozlojitkRozriadu(RLChislo zmenshuvane, Long neededRozriad) {
        List<Long> zmenshuvaneRozriads = zmenshuvane.getRozriads();
        Long max = zmenshuvaneRozriads.get(0);
        zmenshuvaneRozriads.remove(0);
        for (Long i = max - 1; i >= neededRozriad; i--) {
            zmenshuvaneRozriads.add(i);
        }
        zmenshuvaneRozriads.add(neededRozriad);
    }

    private static void removeSameRozriads(RLChislo zmenshuvane, final RLChislo vidyemnik) {
        if (zmenshuvane.getRozriads().containsAll(vidyemnik.getRozriads()))
//            if (zmenshuvane.getRozriads().removeAll(vidyemnik.getRozriads())) vidyemnik.getRozriads().clear();
            zmenshuvane.setRozriads(zmenshuvane.getRozriads().stream().filter(r -> {
                if (vidyemnik.getRozriads().contains(r)) {
                    vidyemnik.getRozriads().remove(r);
                    return false;
                }
                return true;
            }).collect(Collectors.toList()));
    }

    public static void main(String[] args) {
        RLChislo rl1 = new RLChislo(false, new ArrayList<Long>() {{
            add(8l);
            add(4l);
            add(2l);
            add(1l);
            add(0l);
            add(-1l);
            add(-2l);
        }});
        BigDecimal rl1_10 = to10FromRL(rl1);
        System.out.println(rl1 + " = " + rl1_10);

        RLChislo rl2 = new RLChislo(false, new ArrayList<Long>() {{
            add(5l);
            add(3l);
            add(1l);
            add(-5l);
        }});
        BigDecimal rl2_10 = to10FromRL(rl2);
        System.out.println(rl2 + " = " + rl2_10);

        RLChislo rizn = minus(rl1, rl2);

        System.out.println(rl1 + " = " + rl1_10 + " minus " + rl2 + " = " + rl2_10 + " = " + rizn + " = " + to10FromRL(rizn));

        System.out.println("Umnoj simple10 " + (rl1_10.multiply(rl2_10)));
        RLChislo umnojRes = umnojitSimple(rl1, rl2);
        System.out.println("Umnoj rl " + umnojRes + "  =  " + to10FromRL(umnojRes));

    }


}
