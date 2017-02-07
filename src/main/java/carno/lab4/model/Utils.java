package carno.lab4.model;

import org.apache.log4j.Logger;


public class Utils {

    private static final Logger LOGGER = Logger.getLogger(Utils.class);
    private ArithmeticFunc[] arithmeticFuncs6;
    private ArithmeticFunc[] results6;

    public Utils() {
        init();
    }

    private void init() {
        arithmeticFuncs6 = new ArithmeticFunc[26];
        arithmeticFuncs6[0] = null;
        arithmeticFuncs6[1] = createArithmeticFunction6(1);
        arithmeticFuncs6[2] = createArithmeticFunction6(2);
        arithmeticFuncs6[3] = createArithmeticFunction6(3);
        arithmeticFuncs6[4] = createArithmeticFunction6(4);
        arithmeticFuncs6[5] = createArithmeticFunction6(5);
        arithmeticFuncs6[6] = createArithmeticFunction6(6);
        arithmeticFuncs6[7] = createArithmeticFunction6(7);
        arithmeticFuncs6[8] = createArithmeticFunction6(8);
        arithmeticFuncs6[9] = createArithmeticFunction6(9);

        arithmeticFuncs6[10] = createArithmeticFunction6(10, 5, 6, OperationType.or, false);
        arithmeticFuncs6[11] = createArithmeticFunction6(11, 10, 7, OperationType.or, false);
        arithmeticFuncs6[12] = createArithmeticFunction6(12, 11, 8, OperationType.or, false);
        arithmeticFuncs6[13] = createArithmeticFunction6(13, 5, 6, OperationType.mnoj, false);
        arithmeticFuncs6[14] = createArithmeticFunction6(14, 10, 7, OperationType.mnoj, false);
        arithmeticFuncs6[15] = createArithmeticFunction6(15, 11, 8, OperationType.mnoj, false);
        arithmeticFuncs6[16] = createArithmeticFunction6(16, 12, 9, OperationType.mnoj, false);
        arithmeticFuncs6[17] = createArithmeticFunction6(17, 13, 14, OperationType.or, false);
        arithmeticFuncs6[18] = createArithmeticFunction6(18, 17, 15, OperationType.or, false);
        arithmeticFuncs6[19] = createArithmeticFunction6(19, 13, 14, OperationType.mnoj, false);
        arithmeticFuncs6[20] = createArithmeticFunction6(20, 17, 15, OperationType.mnoj, false);
        arithmeticFuncs6[21] = createArithmeticFunction6(21, 18, 16, OperationType.mnoj, false);
        arithmeticFuncs6[22] = createArithmeticFunction6(22, 19, 20, OperationType.mnoj, false);
        arithmeticFuncs6[23] = createArithmeticFunction6(23, 19, 20, OperationType.or, false);
        arithmeticFuncs6[24] = createArithmeticFunction6(24, 23, 21, OperationType.mnoj, false);

        arithmeticFuncs6[25] = createArithmeticFunction6(25, 22, 24, OperationType.mnoj, false);

        results6 = new ArithmeticFunc[7];
        results6[1] = createArithmeticFunction6(26);
        results6[2] = createArithmeticFunction6(2, 12, 9, OperationType.or, true);
        results6[3] = createArithmeticFunction6(3, 18, 16, OperationType.or, true);
        results6[4] = createArithmeticFunction6(4, 23, 21, OperationType.or, true);
        results6[5] = createArithmeticFunction6(5, 22, 24, OperationType.or, true);
        results6[6] = createArithmeticFunction6(6, 22, 24, OperationType.mnoj, true);
    }


    public Operation[] getSistolicheskiyProcessorOperations6() {
        Operation[] res = new Operation[27];
        res[0] = null;
        res[1] = new Operation(new Variable(null, "X1"), new Variable(null, "X2"), OperationType.or);
        res[2] = new Operation(res[1], new Variable(null, "X3"), OperationType.or);
        res[3] = new Operation(res[2], new Variable(null, "X4"), OperationType.or);
        res[4] = new Operation(res[3], new Variable(null, "X5"), OperationType.or);
        res[5] = new Operation(new Variable(null, "X1"), new Variable(null, "X2"), OperationType.mnoj).copy();
        res[6] = new Operation(res[1], new Variable(null, "X3"), OperationType.mnoj).copy();
        res[7] = new Operation(res[2], new Variable(null, "X4"), OperationType.mnoj).copy();
        res[8] = new Operation(res[3], new Variable(null, "X5"), OperationType.mnoj).copy();
        res[9] = new Operation(res[4], new Variable(null, "X6"), OperationType.mnoj).copy();
        res[10] = new Operation(res[5], res[6], OperationType.or).copy();
        res[11] = new Operation(res[10], res[7], OperationType.or).copy();
        res[12] = new Operation(res[11], res[8], OperationType.or).copy();
        res[13] = new Operation(res[5], res[6], OperationType.mnoj).copy();
        res[14] = new Operation(res[10], res[7], OperationType.mnoj).copy();
        res[15] = new Operation(res[11], res[8], OperationType.mnoj).copy();
        res[16] = new Operation(res[12], res[9], OperationType.mnoj).copy();
        res[17] = new Operation(res[13], res[14], OperationType.or).copy();
        res[18] = new Operation(res[17], res[15], OperationType.or).copy();
        res[19] = new Operation(res[13], res[14], OperationType.mnoj).copy();
        res[20] = new Operation(res[17], res[15], OperationType.mnoj).copy();
        res[21] = new Operation(res[18], res[16], OperationType.mnoj).copy();
        res[22] = new Operation(res[19], res[20], OperationType.mnoj).copy();
        res[23] = new Operation(res[19], res[20], OperationType.or).copy();
        res[24] = new Operation(res[23], res[21], OperationType.mnoj).copy();
        res[25] = new Operation(res[22], res[24], OperationType.mnoj).copy();
        res[26] = new Operation(res[4], new Variable(null, "X6"), OperationType.or).copy();
        return res;
    }

    public ArithmeticFunc getArithmeticOperation6(int num) {
        return arithmeticFuncs6[num];
    }

    public ArithmeticFunc getResult6(int num) {
        return results6[num];
    }

    private ArithmeticFunc createArithmeticFunction6(int numOfOperation) {
        LOGGER.info("Trying to count arithmetic function of z=" + numOfOperation);
        AbstractOperation operation = getSistolicheskiyProcessorOperations6()[numOfOperation];
        LOGGER.info("Logic function is " + operation);
        operation = operation.doJob();
        LOGGER.info("After job is done " + operation);
        operation = operation.umnojit();
        LOGGER.info("After umnojenie " + operation);
        operation = operation.uprostit();
        LOGGER.info("After sproshchennia " + operation);
        ArithmeticFunc func = operation.getArithmFunc();
        LOGGER.info("So have arithmetic function " + func);
        return func;
    }


    private ArithmeticFunc createArithmeticFunction6(int zNum, int num1, int num2, OperationType opType, boolean isY) {
        LOGGER.info("Trying to count arithmetic function of " + (isY ? "Y = " : "z =") + zNum);
        ArithmeticFunc arithmeticFunc1 = getArithmeticOperation6(num1);
        ArithmeticFunc arithmeticFunc2 = getArithmeticOperation6(num2);
        LOGGER.info("First arithmetic function is " + arithmeticFunc1);
        LOGGER.info("Second arithmetic function is " + arithmeticFunc2);
        LOGGER.info("Operation is " + opType);
        ArithmeticFunc res = arithmeticFunc1.doLogicOperation(opType, arithmeticFunc2);
        LOGGER.info("Result is " + res);
        return res;
    }


    public Operation[] getSistolicheskiyProcessorOperations5() {
        Operation[] res = new Operation[17];
        res[0] = null;
        res[1] = new Operation(new Variable(null, "X1"), new Variable(null, "X2"), OperationType.or);
        res[2] = new Operation(res[1], new Variable(null, "X3"), OperationType.or);
        res[3] = new Operation(res[2], new Variable(null, "X4"), OperationType.or);
        res[4] = new Operation(new Variable(null, "X1"), new Variable(null, "X2"), OperationType.mnoj).copy();
        res[5] = new Operation(res[1], new Variable(null, "X3"), OperationType.mnoj).copy();
        res[6] = new Operation(res[2], new Variable(null, "X4"), OperationType.mnoj).copy();
        res[7] = new Operation(res[3], new Variable(null, "X5"), OperationType.mnoj).copy();
        res[8] = new Operation(res[4], res[5], OperationType.mnoj).copy();
        res[9] = new Operation(res[4], res[5], OperationType.or).copy();
        res[10] = new Operation(res[9], res[6], OperationType.or).copy();
        res[11] = new Operation(res[9], res[6], OperationType.mnoj).copy();
        res[12] = new Operation(res[10], res[7], OperationType.mnoj).copy();
        res[13] = new Operation(res[8], res[11], OperationType.mnoj).copy();
        res[14] = new Operation(res[8], res[11], OperationType.or).copy();
        res[15] = new Operation(res[14], res[12], OperationType.mnoj).copy();
        res[16] = new Operation(res[13], res[15], OperationType.mnoj).copy();
        return res;
    }
}
