package carno.lab1.view;

import carno.lab1.controller.IController;

/**
 * Created by alexandr on 02.10.16.
 */
public interface ControllerObserver {


    void notifyAmountChanged(IController controller, int newAmount);
}
