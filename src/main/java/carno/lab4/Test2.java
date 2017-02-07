package carno.lab4;

import carno.lab4.model.*;
import org.apache.log4j.Logger;


public class Test2 {

    private static final Logger LOGGER = Logger.getLogger(Test2.class);

    public static void main(String[] args) {


        /*AbstractOperation[] operations = new Operation[]{
                new Operation(new Operation(new Variable(null, "X1"), new Variable(OperationType.not, "X2"), OperationType.mnoj),
                        new Operation(new Variable(OperationType.not, "X1"), new Variable(null, "X2"), OperationType.mnoj), OperationType.or),
                new Operation(new Variable(null,"X1"), new Variable(null, "X2"), OperationType.or),
                new Operation(new Variable(null, "X1"), new Variable(null, "X2"), OperationType.mnoj)
        };*/
        Utils Utils = new Utils();

        AbstractOperation[] operations = new Operation[25];

        for (int i = 0; i < 25; i++) {
            operations[i] = Utils.getSistolicheskiyProcessorOperations6()[i + 1];
        }


        printOperations(operations, "Логическая функция ");

        ArithmeticFunc[] arithmeticPolinoms = findPolinoms(operations);

        printOperations(arithmeticPolinoms, "Арифметический полином ");

        dobavitPositionVes(arithmeticPolinoms);

        printOperations(arithmeticPolinoms, "Арифметический полином c позиционным весом ");

        ArithmeticFunc obobshch = makeObobshchenniyPolinom(arithmeticPolinoms);

        System.out.println("Oбобщенный арифметический полином " + obobshch);

    }

    private static ArithmeticFunc makeObobshchenniyPolinom(ArithmeticFunc[] arithmeticPolinoms) {
        ArithmeticFunc func = new ArithmeticFunc();
        for (ArithmeticFunc arithmeticPolinom : arithmeticPolinoms) {
            for (Variable variable : arithmeticPolinom.getVariables()) {
                func.addVariable(variable);
            }
        }
        return func;
    }

    private static void dobavitPositionVes(ArithmeticFunc[] arithmeticPolinoms) {
        for (int i = 0; i < arithmeticPolinoms.length; i++) {
            arithmeticPolinoms[i].umnojit((int) Math.pow(2, i));
        }
    }

    private static ArithmeticFunc[] findPolinoms(final AbstractOperation[] operations) {
        final ArithmeticFunc[] res = new ArithmeticFunc[operations.length];
        for (int i = 0; i < operations.length; i++) {
            final int finalI = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    LOGGER.info("finding " + finalI + " polinom");
                    AbstractOperation operation = operations[finalI].doJob();
                    LOGGER.info("make job " + operation);
                    operation = operation.umnojit();
                    LOGGER.info("Umnojeno " + operation);
                    operation = operation.uprostit();
                    LOGGER.info("Uproshcheno " + operation);
                    ArithmeticFunc resF = operation.getArithmFunc();
                    LOGGER.info("Poluchili function " + resF);
                    res[finalI] = resF;
                    System.out.println(res[finalI]);
                }
            });
            thread.start();
        }
        return res;
    }

    private static void printOperations(Object[] operations, String message) {
        for (int i = 0; i < operations.length; i++) {
            System.out.println(message + i + " " + operations[i]);
        }
    }
}
