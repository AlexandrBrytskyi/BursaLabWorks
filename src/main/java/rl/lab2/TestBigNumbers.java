package rl.lab2;


import org.apache.log4j.Logger;
import rl.NumberIsNotSimpleException;
import rl.RLOperations;
import rl.lab1.RLChislo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestBigNumbers {

    private static final Logger logger = Logger.getLogger(TestBigNumbers.class);

    public static final String N = "968696137546220614771409222543558829057599911245743198746951209308162982251457083569314766228839896280133391990551829945157815154";
    //968696137546220614771409222543558829057599911245743198746951209308162982251457083569314766228839896280133391990551829945157815154 =
    //114381625757888867669235779976146612010218296721242362562561842935706935245733897830597123563958705058989075147599290026879543541
//101100101110000100000111000110000010101001110010001111010111111110101001011111010011010100001101000001011000010100101110010100000110001101100100111000011100011100100110011101011001111101101000111111111100011111000010111001110011111111010010001000111110001111000010010010101010100000010101011000010000001111001001100011001000000110000010111101111001100000111001100100100101011100111000000110101101000010001101001100000101101110010

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
                new BigInteger("2423883715664737311141302515477790208486959394200386442244903090",10),
                RLOperations.to10FromRL(sqrtN).toBigInteger());

        //968696137546220614771409222543558829057599911245743198746951209308162982251457083569314766228839896280133391990551829945157815154
        //31123883715664737311141302515477790208486959394200386442244903090 sqrt
        //? p
        //? q



        //114381625757888867669235779976146612010218296721242362562561842935706935245733897830597123563958705058989075147599290026879543541
        //10694934584086471525314207693308900296322993593605128511616736585 sqrt
        //32769132993266709549961988190834461413177642967992942539798288533 p
        //3490529510847650949147849619903898133417764638493387843990820577 q

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
