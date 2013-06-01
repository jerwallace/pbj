/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client;

import java.io.Serializable;
import stocktracker.client.protocol.UserProtocol.Stock_Action;

/**
 *
 * @author WallaceJ
 */
public class UserSession extends Session implements Serializable {
    
    private Stock_Action currentAction = Stock_Action.QUERY_STOCK;
    
    private static UserSession userSession = null;
    
    protected UserSession() {
        
    }
    
    public static UserSession getInstance() {

        if (userSession == null) {

            synchronized (UserSession.class) {

                UserSession inst = userSession;

                if (inst == null) {

                    synchronized (UserSession.class) {
                        userSession = new UserSession();
                    }
                }
            }
        }

        return userSession;
    }
    
    public Stock_Action getCurrentAction() {
        return this.currentAction;
    }

    /**
     * @param currentAction the currentAction to set
     */
    public void setCurrentAction(Stock_Action currentAction) {
        this.currentAction = currentAction;
    }
    
}
