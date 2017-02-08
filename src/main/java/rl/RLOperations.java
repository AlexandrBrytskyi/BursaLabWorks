package rl;

import org.apache.log4j.Logger;
import rl.lab1.RLChislo;
import rl.lab2.RLKodingKeyValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class RLOperations {

    private static final Logger logger = Logger.getLogger(RLOperations.class);

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

        BigDecimal dif = number.subtract(new BigDecimal(2).pow((int) stepen, MathContext.UNLIMITED));
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
        for (Long rozriad : rl.getRozriads()) {
            try {
                res = res.add(new BigDecimal(2).pow(rozriad.intValue(), MathContext.UNLIMITED));
            } catch (ArithmeticException e) {
                res = res.add(new BigDecimal(2).pow(rozriad.intValue(), MathContext.DECIMAL128));
            }
        }
        return rl.isZnakQ() ? res.multiply(new BigDecimal(-1)) : res;
    }

    public static RLChislo sum(final RLChislo rl1, final RLChislo rl2) {
        if (rl1 == null) return rl2;
        if (rl2 == null) return rl1;
        if (rl1.isZnakQ() || rl2.isZnakQ()) return subtract(rl1, rl2);
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

    public static RLChislo multiply(RLChislo rl1, RLChislo rl2) {
        List<Long> odinici = new ArrayList<>();
        for (Long rl1Odinicia : rl1.getRozriads()) {
            for (Long rl2Odinicia : rl2.getRozriads()) {
                odinici.add(rl1Odinicia + rl2Odinicia);
            }
        }
        sortOdinici(odinici);
        zvestiPodibni(odinici);
        return new RLChislo(rl1.isZnakQ() ^ rl2.isZnakQ(), odinici);
    }

    public static void sortOdinici(List<Long> odinizi) {
        Collections.sort(odinizi, new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                return o2.compareTo(o1);
            }
        });
    }

    public static void zvestiPodibni(List<Long> odinizi) {
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
        return subtract(operand, rlFrom10(BigDecimal.ONE));
    }

    private static RLChislo subtract(RLChislo rl1, RLChislo rl2) {

        RLChislo zmenshuvane = null;
        RLChislo vidyemnik = null;

        try {
            RLChislo biggerOne = findBiggerOne(rl1, rl2);
            if (rl1 == biggerOne) {
                zmenshuvane = (RLChislo) rl1.clone();
                vidyemnik = (RLChislo) rl2.clone();
            } else {
                zmenshuvane = (RLChislo) rl2.clone();
                vidyemnik = (RLChislo) rl1.clone();
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        boolean removedSomething = true;
        while (vidyemnik.getRozriads().size() != 0) {
            if (removedSomething) removeSameRozriads(zmenshuvane, vidyemnik);
            if (zmenshuvane.getRozriads().size() == 0 || vidyemnik.getRozriads().size() == 0) break;
            rozlojitkRozriadu(zmenshuvane, vidyemnik.getRozriads().get(vidyemnik.getRozriads().size() - 1));
            removedSomething = removeSameRozriads(zmenshuvane, vidyemnik);
            sortOdinici(zmenshuvane.getRozriads());
            zvestiPodibni(zmenshuvane.getRozriads());
        }
        return zmenshuvane;
    }

    private static RLChislo findBiggerOne(RLChislo rl1, RLChislo rl2) {
        int currRozr = 0;
        while (true) {
            List<Long> rozriadsRL1 = rl1.getRozriads();
            List<Long> rozriadsRL2 = rl2.getRozriads();
            if (rozriadsRL1.size() > currRozr && rozriadsRL2.size() > currRozr) {
                Long aLong = rozriadsRL1.get(currRozr);
                Long bLong = rozriadsRL2.get(currRozr);
                if (aLong > bLong) {
                    return rl1;
                } else if (aLong < bLong) {
                    return rl2;
                } else {
                    currRozr++;
                }
            } else if (rozriadsRL1.size() <= currRozr && rozriadsRL2.size() > currRozr) {
                return rl2;
            } else {
                return rl1;
            }
        }
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

    private static boolean removeSameRozriads(RLChislo zmenshuvane, final RLChislo vidyemnik) {
        final boolean[] removed = {false};
//        if (zmenshuvane.getRozriads().containsAll(vidyemnik.getRozriads()))
//            if (zmenshuvane.getRozriads().removeAll(vidyemnik.getRozriads())) vidyemnik.getRozriads().clear();
        zmenshuvane.setRozriads(zmenshuvane.getRozriads().stream().filter(r -> {
            if (vidyemnik.getRozriads().contains(r)) {
                vidyemnik.getRozriads().remove(r);
                removed[0] = true;
                return false;
            }
            return true;
        }).collect(Collectors.toList()));
        return removed[0];
    }

    public static RLChislo divide(RLChislo dilene, RLChislo dilnik, long rozriadnost, boolean needSimple) throws NumberIsNotSimpleException {
        if (dilene.getRozriads().size() == 0 || dilnik.getRozriads().size() == 0)
            throw new IllegalArgumentException("З нулями не працюємо!");
        try {
            dilene = (RLChislo) dilene.clone();
            dilnik = (RLChislo) dilnik.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        List<Long> dileneRozriads = dilene.getRozriads();
        List<Long> dilnikRozriads = dilnik.getRozriads();
        boolean isCounting = true;
        int iteration = 1;
        List<Long> chastka = new ArrayList<>();
        RLChislo o = dilene;
        while (isCounting && iteration != rozriadnost) {
            Long r = dileneRozriads.get(0) - dilnikRozriads.get(0);
            if (needSimple && r < 0)
                throw new NumberIsNotSimpleException("Число не має цілого кореня", new RLChislo(false, chastka));
            RLChislo multiplied = multiplyOnRozriad(dilnik, r);
            while (findBiggerOne(o, multiplied) != o) {
                multiplied = multiplyOnRozriad(dilnik, --r);
                if (needSimple && r < 0)
                    throw new NumberIsNotSimpleException("Число не має цілого кореня", new RLChislo(false, chastka));
            }
            o = subtract(o, multiplied);
            chastka.add(r);
            dileneRozriads = o.getRozriads();
            if (dileneRozriads.size() == 0) isCounting = false;
            iteration++;
        }
        return new RLChislo(dilene.isZnakQ() ^ dilnik.isZnakQ(), chastka);
    }

    private static RLChislo multiplyOnRozriad(RLChislo dilnik, Long r) {
        try {
            dilnik = (RLChislo) dilnik.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        dilnik.setRozriads(dilnik.getRozriads().stream().map(l -> l += r).collect(Collectors.toList()));
        return dilnik;
    }

    public static RLChislo toRLFromBinary(String binary) {
        List<Long> rozriads = new ArrayList<>();
        binary = new StringBuilder(binary).reverse().toString();
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') rozriads.add(Long.valueOf(i));
        }
        RLChislo nRL = new RLChislo(false, rozriads);
        RLOperations.sortOdinici(rozriads);
        RLOperations.zvestiPodibni(rozriads);
        return nRL;
    }

    public static RLChislo sqrt(RLChislo chislo, int rozriads, boolean needSimple) throws NumberIsNotSimpleException {
//      yi = N1(Oi-1) -1 -y1
        if (chislo.getRozriads().size() == 0) return null;
        int k = 2;
        RLChislo operand = null;
        try {
            operand = (RLChislo) chislo.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        RLChislo toExclude = new RLChislo(false, new ArrayList<>());
        RLChislo o = operand;
        List<Long> res = new ArrayList<>();
        Long n = operand.getRozriads().get(0);
        Long y1 = n / 2;
        res.add(y1);
        long rozriad = y1 * k;
        o = subtract(o, new RLChislo(false, Arrays.asList(new Long[]{rozriad})));
        toExclude.getRozriads().add(rozriad);
        Long y = 0l;

        int operations = 1;
        while (o.getRozriads().size() != 0 && operations != rozriads) {
            y = o.getRozriads().get(0) - 1 - y1;
            if (needSimple && y < 0)
                throw new NumberIsNotSimpleException("Число не має цілого кореня", new RLChislo(false, res));
            res.add(y);
            RLChislo rlRes = new RLChislo(false, res);
            RLChislo stepen = subtract(multiply(rlRes, rlRes), toExclude);
            while (findBiggerOne(o, stepen) != o) {
                res.remove(y--);
                if (needSimple && y < 0)
                    throw new NumberIsNotSimpleException("Число не має цілого кореня", new RLChislo(false, res));
                res.add(y);
                rlRes = new RLChislo(false, res);
                stepen = subtract(multiply(rlRes, rlRes), toExclude);
            }
            o = subtract(o, stepen);
            toExclude.getRozriads().addAll(stepen.getRozriads());
            sortOdinici(toExclude.getRozriads());
            zvestiPodibni(toExclude.getRozriads());
            operations++;
        }

        return new RLChislo(false, res);
    }

    public static RLKodingKeyValue encodePQ(RLChislo n, BigInteger start, BigInteger end) {
        RLChislo sqrt;
        try {
            logger.info("Шукаємо квадратний корінь з числа " + n);
            sqrt = sqrt(n, 4096, true);
            logger.info("Знайшли корінь " + sqrt + ", який являється простим числом, тому \np = q = " + sqrt);
            return new RLKodingKeyValue(sqrt, sqrt, n);
        } catch (NumberIsNotSimpleException e) {
            sqrt = e.getSimpleRL();
            logger.info("Нажаль, корінь виявився не цілим числом, беремо лише його цілу частину " + sqrt + "\n= " + to10FromRL(sqrt));
        }


        logger.info("Починаємо ітерації в 4 потоки");
        BigDecimal iterationsOnThread;
        BigDecimal allIterations = new BigDecimal(end.subtract(start));
        iterationsOnThread = new BigDecimal(allIterations.toBigIntegerExact().divide(BigInteger.valueOf(4)));
        logger.info("На кожен поток по " + iterationsOnThread + " варіантів ");
        ExecutorService service = Executors.newFixedThreadPool(4);

        List<Future<RLKodingKeyValue>> futures = new ArrayList<>();

        System.out.println(to10FromRL(sqrt));

        for (long i = 0; i < 4; i++) {
            BigDecimal iterations = iterationsOnThread.multiply(BigDecimal.valueOf(i)).add(new BigDecimal(start));
            if (i < 3) futures.add(service.submit(
                    new Founder(
                            iterations.toBigInteger(),
                            iterations.add(iterationsOnThread).toBigInteger(),
                            n, sqrt)));
            else {
                futures.add(service.submit(
                        new Founder(
                                iterations.toBigInteger(),
                                iterations.add(new BigDecimal(end).subtract(iterations)).toBigInteger(),
                                n, sqrt)));
            }
        }

        Future<RLKodingKeyValue> resultFuture = service.submit(new Watcher(futures));

        service.shutdown();

        try {
            boolean worked = service.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("here");

        try {
            return resultFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class Watcher implements Callable<RLKodingKeyValue> {

        private List<Future<RLKodingKeyValue>> futures;

        public Watcher(List<Future<RLKodingKeyValue>> futures) {
            this.futures = futures;
        }

        @Override
        public RLKodingKeyValue call() throws Exception {
            while (true) {
                try {
                    for (Future<RLKodingKeyValue> future : futures) {
                        if (future.isDone()) {
                            RLKodingKeyValue res = future.get();
                            for (Future<RLKodingKeyValue> future2 : futures) {
                                future2.cancel(true);
                            }
                            return res;
                        }
                    }
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Founder implements Callable<RLKodingKeyValue> {

        private BigInteger start;
        private BigInteger end;
        private RLChislo n;
        private RLChislo sqrt;

        public Founder(BigInteger start, BigInteger end, RLChislo n, RLChislo sqrt) {
            logger.info("starting new thread, counting from " + start + " to " + end);
            this.start = start;
            this.end = end;
            this.n = n;
            this.sqrt = sqrt;
        }

        @Override
        public RLKodingKeyValue call() throws Exception {
            RLChislo p = null;
            RLChislo q = null;
            boolean print = true;
            int counter = 0;
            while (start.compareTo(end) <= 0) {
//                System.out.println("I live" + Thread.currentThread() + "\n" + "start=" + start + " end = " + end);
                RLChislo currentI = subtract(sqrt, toRLFromBinary(start.toString(2)));
                try {
                    p = divide(n, currentI, Integer.MAX_VALUE, true);
                    logger.info("Знайдено ціле число!!! р = " + p);
                    q = divide(n, p, Long.MAX_VALUE, true);
                    RLKodingKeyValue res = new RLKodingKeyValue(p, q, n);
                    logger.info("Результат: \n" + res);
                    return res;
                } catch (NumberIsNotSimpleException e) {
                    start = start.add(BigInteger.ONE);
                    if (print) {
                        logger.info("p" + to10FromRL(currentI) + " не є цілим числом, ціла частина: " + e.getSimpleRL());
                        print = false;
                    }
                    if (++counter == 1000) {
                        print = true;
                        counter = 0;
                    }
                }
            }
            return null;
        }
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
//        System.out.println(multiplyOnRozriad(rl2, 1l));
        RLChislo rizn = subtract(rl1, rl2);

        System.out.println(rl1 + " = " + rl1_10 + " subtract " + rl2 + " = " + rl2_10 + " = " + rizn + " = " + to10FromRL(rizn));

        System.out.println("Umnoj simple10 " + (rl1_10.multiply(rl2_10)));
        RLChislo umnojRes = multiply(rl1, rl2);
        System.out.println("Umnoj rl " + umnojRes + "  =  " + to10FromRL(umnojRes));
        System.out.println();
        RLChislo divide = null;
        try {
            divide = divide(umnojRes, rl2, Integer.MAX_VALUE, false);
        } catch (NumberIsNotSimpleException e) {
            e.printStackTrace();
        }
        System.out.println("Dividing " + umnojRes + " = " + to10FromRL(umnojRes) + "\n on " + rl2 + " = " + to10FromRL(rl2) + "\n" +
                "result is " + divide + " = " + to10FromRL(divide));
        System.out.println();
        System.out.println(multiply(divide, rl2));

        RLChislo _81 = rlFrom10(new BigDecimal(80));

        RLChislo _sqrt81 = null;
        try {
            _sqrt81 = sqrt(_81, 32, false);
        } catch (NumberIsNotSimpleException e) {
            e.printStackTrace();
        }

        System.out.println("Fing sqrt of " + _81 + " = " + to10FromRL(_81) + "\nresult is " + _sqrt81 + " = " + to10FromRL(_sqrt81));


        System.out.println();
        System.out.println();


        RLChislo _n = rlFrom10(new BigDecimal(527));
        encodePQ(_n, BigInteger.ONE, BigInteger.valueOf(527));
        System.out.println();
    }


}
