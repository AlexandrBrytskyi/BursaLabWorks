package carno.lab4.model;

import org.apache.log4j.Logger;


public class Test {


    private static final Logger LOGGER = Logger.getLogger(Test.class);

    public static void main(String[] args) {


        /*AbstractOperation[] operations = new Operation[]{
                new Operation(new Operation(new Variable(null, "X1"), new Variable(OperationType.not, "X2"), OperationType.mnoj),
                        new Operation(new Variable(OperationType.not, "X1"), new Variable(null, "X2"), OperationType.mnoj), OperationType.or),
                new Operation(new Variable(null,"X1"), new Variable(null, "X2"), OperationType.or),
                new Operation(new Variable(null, "X1"), new Variable(null, "X2"), OperationType.mnoj)
        };*/
        Utils Utils = new Utils();

        AbstractOperation[] operations = new Operation[6];
        operations[0] = new Operation(Utils.getSistolicheskiyProcessorOperations6()[4], new Variable(null, "X6"), OperationType.or);
        operations[1] = new Operation(Utils.getSistolicheskiyProcessorOperations6()[12], Utils.getSistolicheskiyProcessorOperations6()[9], OperationType.or);
        operations[2] = new Operation(Utils.getSistolicheskiyProcessorOperations6()[18], Utils.getSistolicheskiyProcessorOperations6()[16], OperationType.or);
        operations[3] = new Operation(Utils.getSistolicheskiyProcessorOperations6()[23], Utils.getSistolicheskiyProcessorOperations6()[21], OperationType.or);
        operations[4] = new Operation(Utils.getSistolicheskiyProcessorOperations6()[22], Utils.getSistolicheskiyProcessorOperations6()[24], OperationType.or);
        operations[5] = Utils.getSistolicheskiyProcessorOperations6()[25];


        printOperations(operations, "Логическая функция ");

        ArithmeticFunc[] arithmeticPolinoms = findPolinoms(operations, Utils);

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

    private static ArithmeticFunc[] findPolinoms(AbstractOperation[] operations, Utils Utils) {
        ArithmeticFunc[] res = new ArithmeticFunc[operations.length];
        for (int i = 0; i < operations.length; i++) {
            LOGGER.info("finding " + (i + 1) + " polinom");
            LOGGER.info(operations[i]);

            ArithmeticFunc arithmeticFunc = Utils.getResult6(i + 1);
            LOGGER.info("Arithmetic polinom is " + arithmeticFunc);

            res[i] = arithmeticFunc;
        }
        return res;
    }

    private static void printOperations(Object[] operations, String message) {
        for (int i = 0; i < operations.length; i++) {
            System.out.println(message + i + " " + operations[i]);
        }
    }
}
