package rl.lab1;


import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.BigInteger;

public class FactorialCounter {

    private static final Logger logger = Logger.getLogger(FactorialCounter.class);

    public BigDecimal countFact10(BigDecimal num) {
        if (num.equals(BigDecimal.ONE)) return BigDecimal.ONE.ONE;
        return new BigDecimal(num.toString()).multiply(countFact10(num.subtract(BigDecimal.ONE)));
    }

    public Float countFact10(Float num) {
        if (num.equals(1f)) return 1f;
        return num * countFact10(--num);
    }

    public RLChislo countFactorial(RLChislo num) {
        RLChislo one = RLOperations.rlFrom10(BigDecimal.ONE);
        if (num.equals(one)) return one;
        return RLOperations.umnojitSimple(num, countFactorial(RLOperations.decrement(num)));
    }

    public static void main(String[] args) {
//        countFactorialBigDecimal();

        countFactorialFloat();
    }

    private static void countFactorialBigDecimal() {
        FactorialCounter fc = new FactorialCounter();
        for (int i = 25; i < 40; i++) {
            BigDecimal res_10 = fc.countFact10(BigDecimal.valueOf(i));
            logger.info("Факторіал звичайним методом " + i + " = " + res_10);

            RLChislo factorial10 = fc.countFactorial(RLOperations.rlFrom10(BigDecimal.valueOf(i)));
            BigDecimal rlFloat = RLOperations.to10FromRL(factorial10);
            logger.info("Факторіал РЛ числа " + i + " = " + rlFloat.toEngineeringString() + " , " + " \nРЛ запис: " + factorial10);

            logger.info("Різниця " + (rlFloat.subtract(res_10)) + "\n\n");
        }
//        System.out.println(Long.MAX_VALUE);
    }

    private static void countFactorialFloat() {
        FactorialCounter fc = new FactorialCounter();
        for (int i = 10; i < 20; i++) {
            Float res_10 = fc.countFact10((float) i);
            logger.info("Факторіал звичайним методом " + i + " = " + res_10);

            RLChislo factorial10 = fc.countFactorial(RLOperations.rlFrom10(BigDecimal.valueOf(i)));
            BigDecimal rlFloat = RLOperations.to10FromRL(factorial10);
            logger.info("Факторіал РЛ числа " + i + " = " + rlFloat.toEngineeringString() + " , " + " \nРЛ запис: " + factorial10);

            logger.info("Різниця " + (rlFloat.subtract(BigDecimal.valueOf(res_10))) + "\n\n");
        }
        System.out.println(Float.MAX_VALUE);
//        System.out.println(Long.MAX_VALUE);
    }

}

//2.092279e+13
//-4.056482E31