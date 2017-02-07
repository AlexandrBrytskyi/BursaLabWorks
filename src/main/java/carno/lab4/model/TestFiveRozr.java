package carno.lab4.model;

import org.apache.log4j.Logger;


public class TestFiveRozr {

    private static final Logger LOGGER = Logger.getLogger(TestFiveRozr.class);

    public static void main(String[] args) {


        /*AbstractOperation[] operations = new Operation[]{
                new Operation(new Operation(new Variable(null, "X1"), new Variable(OperationType.not, "X2"), OperationType.mnoj),
                        new Operation(new Variable(OperationType.not, "X1"), new Variable(null, "X2"), OperationType.mnoj), OperationType.or),
                new Operation(new Variable(null,"X1"), new Variable(null, "X2"), OperationType.or),
                new Operation(new Variable(null, "X1"), new Variable(null, "X2"), OperationType.mnoj)
        };*/

        Utils Utils = new Utils();
        AbstractOperation[] operations = new Operation[5];
        operations[0] = new Operation(Utils.getSistolicheskiyProcessorOperations5()[3], new Variable(null, "X5"), OperationType.or);
        operations[1] = new Operation(Utils.getSistolicheskiyProcessorOperations5()[10], Utils.getSistolicheskiyProcessorOperations5()[7], OperationType.or);
        operations[2] = new Operation(Utils.getSistolicheskiyProcessorOperations5()[14], Utils.getSistolicheskiyProcessorOperations5()[12], OperationType.or);
        operations[3] = new Operation(Utils.getSistolicheskiyProcessorOperations5()[13], Utils.getSistolicheskiyProcessorOperations5()[15], OperationType.or);
        operations[4] = Utils.getSistolicheskiyProcessorOperations5()[16];


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

    private static ArithmeticFunc[] findPolinoms(AbstractOperation[] operations) {
        ArithmeticFunc[] res = new ArithmeticFunc[operations.length];
        for (int i = 0; i < operations.length; i++) {
            LOGGER.info("finding " + i + " polinom");
            AbstractOperation operation = operations[i].doJob();
            LOGGER.info("make job " + operation);
            operation = operation.umnojit();
            LOGGER.info("Umnojeno " + operation);
            operation = operation.uprostit();
            LOGGER.info("Uproshcheno " + operation);
            ArithmeticFunc resF = operation.getArithmFunc();
            LOGGER.info("Poluchili function " + resF);
            res[i] = resF;
            System.out.println(res[i]);
        }
        return res;
    }

    private static void printOperations(Object[] operations, String message) {
        for (int i = 0; i < operations.length; i++) {
            System.out.println(message + i + " " + operations[i]);
        }
    }
}
