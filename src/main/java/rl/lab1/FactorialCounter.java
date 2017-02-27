package rl.lab1;


import org.apache.log4j.Logger;
import rl.RLOperations;

import java.math.BigDecimal;
import java.math.MathContext;

public class FactorialCounter {

    private static final Logger logger = Logger.getLogger(FactorialCounter.class);

    public BigDecimal countFact10(BigDecimal num) {
        if (num.equals(BigDecimal.ONE)) return BigDecimal.ONE;
        return num.multiply(countFact10(num.subtract(BigDecimal.ONE)), MathContext.DECIMAL128);
    }

    public Double countFact10(Integer num) {
        if (num.equals(1)) return 1d;
        return num * countFact10(--num);
    }

    public RLChislo countFactorial(RLChislo num) {
        RLChislo one = RLOperations.rlFrom10(BigDecimal.ONE);
        if (num.equals(one)) return one;
        return RLOperations.multiply(num, countFactorial(RLOperations.decrement(num)));
    }

    public static void main(String[] args) {
        countFactorialBigDecimal();

//        countFactorialDouble();
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

    private static void countFactorialDouble() {
        FactorialCounter fc = new FactorialCounter();
        for (int i = 15; i < 25; i++) {
            Double res_10 = fc.countFact10(i);
            logger.info("Факторіал звичайним методом " + i + " = " + res_10);

            RLChislo factorial10 = fc.countFactorial(RLOperations.rlFrom10(BigDecimal.valueOf(i)));
            BigDecimal rlFloat = RLOperations.to10FromRL(factorial10);
            logger.info("Факторіал РЛ числа " + i + " = " + rlFloat.toEngineeringString() + " , " + " \nРЛ запис: " + factorial10);

            logger.info("Різниця " + (rlFloat.subtract(BigDecimal.valueOf(res_10))) + "\n\n");
        }
        System.out.println(Double.MAX_VALUE);
//        System.out.println(Long.MAX_VALUE);
    }

}

//2.092279e+13
//-4.056482E31