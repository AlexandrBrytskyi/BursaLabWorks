package carno.lab4.model;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by alexandr on 13.11.16.
 */
public class Operation extends AbstractOperation {

    private static final Logger LOGGER = Logger.getLogger(Operation.class);

    private AbstractOperation variable1;
    private AbstractOperation variable2;

    public Operation(AbstractOperation variable1, AbstractOperation variable2, OperationType type) {
        super(type);
        this.variable1 = variable1;
        this.variable2 = variable2;
    }

    @Override
    public AbstractOperation doJob() {
        variable1 = variable1.doJob();
        variable2 = variable2.doJob();
        Operation toRet = null;
        if (operationType.equals(OperationType.or)) {
            toRet = new Operation(
                    new Operation(variable1, variable2, OperationType.plus),
                    new Operation(variable1, variable2, OperationType.mnoj),
                    OperationType.minus);
        } else if (operationType.equals(OperationType.mnoj)) {
            toRet = this;
        }
//        LOGGER.info("preveli " + toRet);
        return toRet;
    }

    @Override
    public AbstractOperation umnojit() {
//        LOGGER.info("umnojaem " + variable1 + ":" + variable2);
        return umnojitRec(variable1, variable2, operationType);

    }

    public AbstractOperation umnojitRec(AbstractOperation variable1, AbstractOperation variable2, OperationType operation) {
        if (operationType.equals(OperationType.mnoj)) {
//            LOGGER.info("mnojim " + variable1 + "----" + operation + "----" + variable2);
            if (variable1 instanceof Variable && variable2 instanceof Variable) {
                return twoVariblesMnoj(variable1, variable2);
            } else if (variable1 instanceof Variable && !(variable2 instanceof Variable)) {
                AbstractOperation oap = variable2.umnojit();
                if (oap instanceof Variable) {
                    return twoVariblesMnoj(variable1, oap);
                } else {
                    Operation var2 = (Operation) oap;
                    variable1 = umnojitRec(variable1, var2.variable1, OperationType.mnoj);
                    variable2 = umnojitRec(variable1, var2.variable2, OperationType.mnoj);
                    Operation toRet = new Operation(
                            variable1, variable2,
                            var2.operationType);
//                    System.out.println("return new Operation 0-1 " + toRet);
                    return toRet;
                }
            } else if (!(variable1 instanceof Variable) && variable2 instanceof Variable) {
                AbstractOperation oap = variable1.umnojit();
                if (oap instanceof Variable) {
                    return twoVariblesMnoj(oap, variable2);
                } else {
                    Operation var1 = (Operation) oap;
                    variable1 = umnojitRec(var1.variable1, variable2, OperationType.mnoj);
                    variable2 = umnojitRec(var1.variable2, variable2, OperationType.mnoj);

                    Operation toRet = new Operation(
                            variable1, variable2,
                            var1.operationType);
//                    System.out.println("return new Operation 1-0 " + toRet);
                    return toRet;
                }
            } else {
//                    System.out.println(variable1 + ":" + variable2);
                variable1 = variable1.umnojit();
                variable2 = variable2.umnojit();
                if (variable1 instanceof Operation && variable2 instanceof Operation) {
                    Operation var1 = (Operation) variable1;
                    Operation var2 = (Operation) variable2;
                    Operation toRet = new Operation(
                            new Operation(
                                    umnojitRec(var1.variable1, var2.variable1, OperationType.mnoj),
                                    umnojitRec(var1.variable2, var2.variable1, OperationType.mnoj), var1.operationType),
                            new Operation(
                                    umnojitRec(var1.variable1, var2.variable2, OperationType.mnoj),
                                    umnojitRec(var1.variable2, var2.variable2, OperationType.mnoj), var2.operationType), OperationType.plus);
//                    System.out.println("return new Operation 1-1 " + toRet);
                    return toRet;
                } else {
                    return umnojitRec(variable1, variable2, operation);
                }
                   /* return new Operation(
                            var1, var2, OperationType.mnoj
                    ).umnojit();
*/
                   /* return new Operation(
                            new Operation(
                                    new Operation(var1.variable1, var2.variable1, OperationType.mnoj).umnojit(),
                                    new Operation(var1.variable2, var2.variable1, OperationType.mnoj).umnojit(), var1.operationType),
                            new Operation(
                                    new Operation(var1.variable1, var2.variable2, OperationType.mnoj).umnojit(),
                                    new Operation(var1.variable2, var2.variable2, OperationType.mnoj).umnojit(), var2.operationType), OperationType.plus).umnojit();
*/
            }
        } else {
            variable1 = variable1.umnojit();
            variable2 = variable2.umnojit();
            return new Operation(variable1, variable2, operation);
        }
    }

    private AbstractOperation twoVariblesMnoj(AbstractOperation variable1, AbstractOperation variable2) {

        Variable variable11 = (Variable) variable1;
        Variable variable21 = (Variable) variable2;
        if (variable11.getName().contains(variable21.getName())) {
            return new Variable(null, variable11.getName());
        } else if (variable21.getName().contains(variable11.getName())) {
            return new Variable(null, variable21.getName());
        } else return new Variable(null, defineNewName(variable11.getName(), variable21.getName()));
    }

    private String defineNewName(String name1, String name2) {
        Set<Integer> integers = new HashSet<>();
        for (String integer : name1.split("X")) {
            if (!integer.isEmpty()) integers.add(Integer.valueOf(integer));
        }
        for (String integer : name2.split("X")) {
            if (!integer.isEmpty()) integers.add(Integer.valueOf(integer));
        }
        Integer[] arr = new Integer[integers.size()];
        arr = integers.toArray(arr);
        Arrays.sort(arr);
        StringBuilder res = new StringBuilder();
        for (Integer integer : arr) {
            res.append("X" + integer);
        }
        return res.toString();
    }

    public AbstractOperation uprostit() {
        try {
            if (operationType.equals(OperationType.mnoj)) {
//                LOGGER.info("uproshch " + variable1 + ":" + operationType + ":" + variable2);
                String[] varsArr = new String[]{((Variable) variable1.uprostit()).getName(), ((Variable) variable2.uprostit()).getName()};
                Arrays.sort(varsArr);
                if (varsArr[0].contains(varsArr[1])) {
                    return new Variable(null, varsArr[0]);
                } else if (varsArr[1].contains(varsArr[0])) {
                    return new Variable(null, varsArr[1]);
                } else return new Variable(null, (varsArr[0].equals("1") ? "" : varsArr[0]) + varsArr[1]);
            }
            variable1 = variable1.uprostit();
            variable2 = variable2.uprostit();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return this;
    }


    public ArithmeticFunc getArithmFunc() {
        ArithmeticFunc res = new ArithmeticFunc();
        putChildren(res, this, OperationType.plus);
        return res;


    }

    private void putChildren(ArithmeticFunc res, Operation operation, OperationType type) {
        OperationType currentOpType = operation.operationType;
        if (operation.variable1 instanceof Variable) {
            Variable var = (Variable) operation.variable1;
            var.setOperationType(defineOperation( type, var.operationType));
            res.addVariable(var);
        } else
            putChildren(res, (Operation) operation.variable1, type);
        if (operation.variable2 instanceof Variable) {
            Variable var = (Variable) operation.variable2;
            var.setOperationType(currentOpType);
            res.addVariable(var);
        } else putChildren(res, (Operation) operation.variable2, defineOperation(type, currentOpType));
    }

    private OperationType defineOperation(OperationType typeParent, OperationType operationType) {
        if (typeParent.equals(OperationType.minus)) {
            if (operationType == null || operationType.equals(OperationType.plus)) return OperationType.minus;
            if (operationType.equals(OperationType.minus)) return OperationType.plus;
        } else {
            if (operationType == null || operationType.equals(OperationType.plus)) return OperationType.plus;
            if (operationType.equals(OperationType.minus)) return OperationType.minus;
        }
        return null;
    }
//((((((X1 + X2) - X1X2) + X3) - ((X1X3 + X2X3) - X1X2X3)) + X4) - ((((X1X4 + X2X4) - X1X2X4) + X3X4) - ((X1X3X4 + X2X3X4) - X1X2X3X4)))

    @Override
    public String toString() {
        return "(" + variable1 + " " + (operationType.equals(OperationType.plus) ? "+" :
                operationType.equals(OperationType.minus) ? "-" :
                        operationType.equals(OperationType.or) ? "V" :
                                operationType.equals(OperationType.not) ? "!" : "") + " " +
                variable2 + ")";
    }

    @Override
    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Operation copy() {
        return new Operation(variable1.copy(), variable2.copy(), operationType);
    }
}
