package rl.lab2;


import org.apache.log4j.Logger;
import rl.NumberIsNotSimpleException;
import rl.RLOperations;
import rl.lab1.RLChislo;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TestBigNumbers {

    private static final Logger logger = Logger.getLogger(TestBigNumbers.class);

    // просте число пишеш тут
    public static final String N = "7238417";


    public static void main(String[] args) {
        BigInteger bi = new BigInteger(N, 10);
        String bi_binary = bi.toString(2);

        logger.info("Число N у 10й системі числення має вигляд " + N + "\n" + "тоді як у двійквій " + bi_binary);

        RLChislo nRL = RLOperations.toRLFromBinary(bi_binary);

        BigDecimal bigDecimal = RLOperations.to10FromRL(nRL);

        logger.info("Тоді у РЛ формі має вигляд " + nRL);
        logger.info("Для перевірки правильності переводу, переведемо знову у десяткову систему та зрівняємо результати\n" +
                "Вхідне N = " + N + "\n" +
                "Із  РЛ   = " + bigDecimal.toString());

        logger.info("Починаэмо пошук ключів ");
        RLChislo sqrtN;
        try {
            sqrtN = RLOperations.sqrt(nRL, Integer.MAX_VALUE, true);
        } catch (NumberIsNotSimpleException e) {
            sqrtN = e.getSimpleRL();
        }
        System.out.println(RLOperations.to10FromRL(sqrtN));

        RLOperations.encodePQ(nRL, true,
//                тут змінюєш роміжок на якому шукаємо (можливо від 2 до корня) бо на 0 не ділиться,
// якшо ділимо на 1, то це буде результатом,
// а це нас не влащтовує
                new BigInteger("2",10),
                RLOperations.to10FromRL(sqrtN).toBigInteger());

    }


    public static BigInteger bigIntSqRootFloor(BigInteger x)
            throws IllegalArgumentException {
        if (x.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("Negative argument.");
        }
        // square roots of 0 and 1 are trivial and
        // y == 0 will cause a divide-by-zero exception
        if (x.equals(BigInteger.ZERO) || x.equals(BigInteger.ONE)) {
            return x;
        } // end if
        BigInteger two = BigInteger.valueOf(2L);
        BigInteger y;
        // starting with y = x / 2 avoids magnitude issues with x squared
        for (y = x.divide(two);
             y.compareTo(x.divide(y)) > 0;
             y = ((x.divide(y)).add(y)).divide(two));
        return y;
    } // end bigIntSqRootFloor



}
