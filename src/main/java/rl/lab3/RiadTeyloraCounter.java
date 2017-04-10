package rl.lab3;


import org.apache.log4j.Logger;
import rl.NumberIsNotSimpleException;
import rl.RLOperations;
import rl.lab1.RLChislo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RiadTeyloraCounter {
    private static final Logger logger = Logger.getLogger(RiadTeyloraCounter.class);
    public static final RLChislo RL_CHISLO_0 = new RLChislo(false, new ArrayList<Long>());
    public static final int N = 10;


    public List<RLChislo> generateConstants(int N) {
        logger.info("generating Constants A between numbers {-1000, 1000}");
        List<RLChislo> res = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            Integer generated = Math.toIntExact(Math.round(Math.random() * 2000 - 1000));
            RLChislo rl = RLOperations.toRLFromBinary(generated.toBinaryString(Math.abs(generated)), (generated < 0));
            res.add(rl);
        }
        logger.info("generated constants " + res);
        return res;
    }

    public RiadTeylora createRiadInstance(RLChislo x, int n, List<RLChislo> constants) {
        RiadTeylora riadTeylora = new RiadTeylora(constants, n, x);
        riadTeylora.printFormula(false);
        List<RLChislo> xses = new ArrayList<>();
        logger.info("Start to count x values for x=" + x);
        for (int i = 0; i < n; i++) {
            xses.add(RLOperations.powIntStep(x, i));
        }
        riadTeylora.setXses(xses);
        logger.info("Riad mae vigliad " + riadTeylora.printFormula(false));

        logger.info("Start finding sum");
        RLChislo sum = RL_CHISLO_0;
        int curr = 0;
        for (RLChislo constant : riadTeylora.getConstants()) {
            RLChislo multiply = RLOperations.multiply(constant, riadTeylora.getXses().get(curr++));
//            System.out.println("mult " + multiply);
            sum = RLOperations.sum(sum, multiply);
//            System.out.println(sum);
        }
        logger.info("counted sum is " + sum);
        riadTeylora.setSum(sum);
        logger.info(riadTeylora.printFormula(true));
        return riadTeylora;
    }


    public static void main(String[] args) {
        RiadTeyloraCounter riadTeyloraCounter = new RiadTeyloraCounter();
        int n = N;
        List<RLChislo> constants = riadTeyloraCounter.generateConstants(n);
        RiadTeylora riadTeylora = riadTeyloraCounter.createRiadInstance(RLOperations.rlFrom10(new BigDecimal(15)), n, constants);

        countQ(riadTeyloraCounter, n, riadTeylora);
    }

    private static void countQ(RiadTeyloraCounter riadTeyloraCounter, int n, RiadTeylora riadTeylora) {
        for (int i = 0; i < n; i++) {
            logger.info("virajaemo konstantu #" + i + " :");
            logger.info(riadTeylora.getConstants().get(i) + " = " + printSumWithout(i, riadTeylora));
            int Q = riadTeylora.getSum().getRozriads().size();
            logger.info("berem Q summi = " + Q);
            boolean got = false;
            RLChislo constant = riadTeylora.getConstants().get(i);
            int steps = 1;
            while (!got) {
                RLChislo constantFounded = riadTeyloraCounter.findConstant(Q, riadTeylora, i);
                logger.info("Q = " + Q + ", constant = " + constantFounded + ", need " + constant);
                if (constant.equals(constantFounded)) {
                    got = true;
                    logger.info("Good Q is " + Q + "\n\n");
                }
                steps += 1;
                Q += steps;
            }
        }
    }

    private RLChislo findConstant(int Q, RiadTeylora riadTeylora, int constantNum) {
        RLChislo partSum = RL_CHISLO_0;
        for (int i = 0; i < riadTeylora.getConstants().size(); i++) {
            if (i != constantNum)
                partSum = RLOperations.sum(RLOperations.multiply(riadTeylora.getConstants().get(i), riadTeylora.getXses().get(i), Q), partSum, Q);
        }
        RLChislo fullSum = riadTeylora.getSum();
//        System.out.println("povna " + fullSum);
//        System.out.println("nempovna " + partSum);
        partSum = RLOperations.subtract(fullSum, partSum, false);
        try {
//            System.out.println("before divide " + partSum + " "+riadTeylora.getXses().get(constantNum));
            return RLOperations.divide(partSum, riadTeylora.getXses().get(constantNum), Q, false);
        } catch (NumberIsNotSimpleException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String printSumWithout(int constantNum, RiadTeylora riadTeylora) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append(riadTeylora.getSum()).append(" - ").append("{");
        for (int i = 0; i < riadTeylora.getConstants().size(); i++) {
            if (i != constantNum) {
                builder.append(riadTeylora.getConstants().get(i)).append("*").append(riadTeylora.getXses().get(i));
                if (i != riadTeylora.getXses().size() - 1) builder.append("+");
            }
        }
        builder.append("} }/").append(riadTeylora.getXses().get(constantNum));
        return builder.toString();
    }

}
