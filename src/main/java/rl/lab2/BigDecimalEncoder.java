package rl.lab2;


import org.apache.log4j.Logger;
import rl.NumberIsNotSimpleException;
import rl.RLOperations;
import rl.lab1.RLChislo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class BigDecimalEncoder {

    private static final Logger logger = Logger.getLogger(BigDecimalEncoder.class);

    public static RLKodingKeyValue encodePQ(BigDecimal n, boolean useStartEnd, BigInteger start, BigInteger end) {

        logger.info("Шукаємо квадратний корінь з числа " + n);
        BigDecimal sqrt = new BigDecimal(TestBigNumbers.bigIntSqRootFloor(n.toBigInteger()));

        logger.info("Нажаль, корінь виявився не цілим числом, беремо лише його цілу частину " + sqrt);

        BigDecimal iterationsOnThread;

        if (useStartEnd) {
            if (!(end.compareTo(sqrt.toBigInteger()) <= 0) || end.compareTo(start) < 0) {
                throw new IllegalArgumentException("start must be < end and end must be <= sqrt");
            }
        } else {
            start = BigInteger.valueOf(2);
            end = sqrt.toBigInteger();
        }
        logger.info("Починаємо ітерації в 4 потоки \nшукаэмо на проміжку " + start + " <+> " + end);

        BigDecimal allIterations = new BigDecimal(end.subtract(start));
        iterationsOnThread = new BigDecimal(allIterations.toBigIntegerExact().divide(BigInteger.valueOf(4)));
        logger.info("На кожен поток по " + iterationsOnThread + " варіантів ");
        ExecutorService service = Executors.newFixedThreadPool(4);

        List<Future<RLKodingKeyValue>> futures = new ArrayList<>();

        for (long i = 0; i < 4; i++) {
            BigDecimal iterations = iterationsOnThread.multiply(BigDecimal.valueOf(i)).add(new BigDecimal(start));
            if (i < 3) futures.add(service.submit(
                    new PQFounder(
                            iterations.toBigInteger(),
                            iterations.add(iterationsOnThread).toBigInteger(),
                            n)));
            else {
                futures.add(service.submit(
                        new PQFounder(
                                iterations.toBigInteger(),
                                iterations.add(new BigDecimal(end).subtract(iterations)).toBigInteger(),
                                n)));
            }
        }

        Future<RLKodingKeyValue> resultFuture = service.submit(new Watcher(futures));

        RLKodingKeyValue res = null;
        try {
            service.shutdown();
            res = resultFuture.get();
            service.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return res;
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
                        RLKodingKeyValue res = future.get();
                        if (future.isDone() && (res != null)) {
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

    private static class PQFounder implements Callable<RLKodingKeyValue> {

        private BigDecimal start;
        private BigDecimal end;
        private BigDecimal n;

        public PQFounder(BigInteger start, BigInteger end, BigDecimal n) {
            logger.info("starting new thread, counting from " + start + " to " + end);
            this.start = new BigDecimal(start);
            this.end = new BigDecimal(end);
            this.n = n;
        }

        @Override
        public RLKodingKeyValue call() throws Exception {
            BigDecimal p = null;
            BigDecimal q = null;
            boolean print = true;
            int counter = 0;
            while (start.compareTo(end) <= 0 && !Thread.currentThread().isInterrupted()) {
//                System.out.println("I live" + Thread.currentThread() + "\n" + "start=" + start + " end = " + end);
//                RLChislo currentI = subtract(sqrt, toRLFromBinary(start.toString(2)));
                BigDecimal currentI = start;
                String s = start.toString();
                char lastSymb = s.charAt(s.length() - 1);
                if (lastSymb == '1' ||
                        lastSymb == '3' ||
                        lastSymb == '7' ||
                        lastSymb == '9') {
                    p = n.divide(currentI, new MathContext(128));
//                System.out.println(p.remainder(BigDecimal.ONE)+ " rem");
                    if (p.remainder(BigDecimal.ONE).equals(BigDecimal.ZERO)) {
                        logger.info("Знайдено ціле число!!! р = " + p);
                        q = n.divide(p, MathContext.DECIMAL128);
                        RLKodingKeyValue res = new RLKodingKeyValue(RLOperations.toRLFromBinary(p.toBigIntegerExact().toString(2)),
                                RLOperations.toRLFromBinary(q.toBigIntegerExact().toString(2)),
                                RLOperations.toRLFromBinary(n.toBigIntegerExact().toString(2)));
                        logger.info("Результат: \n" + res);
                        return res;
                    } else {
                        start = start.add(BigDecimal.ONE);
                        if (print) {
                            logger.info(Thread.currentThread().getName() + ": ==> " + "p" + currentI + " не є цілим числом " + p.toPlainString());
                            print = false;
                        }
                    }
                }
                if (++counter == 1000000) {
                    print = true;
                    counter = 0;
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {

        encodePQ(new BigDecimal("114381625757888867669235779976146612010218296721242362562561842935706935245733897830597123563958705058989075147599290026879543541"),
                true,
                new BigInteger("3490529510847650949147849619903898133417760638493387843990820577", 10),
                new BigInteger("10694934584086471525314207693308900296322993593605128511616736585", 10));
    }
}


