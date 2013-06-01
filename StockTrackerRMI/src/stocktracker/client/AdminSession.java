/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client;

import java.io.Serializable;

/**
 *
 * @author WallaceJ
 */
public class AdminSession extends Session implements Serializable {
    
    private static AdminSession adminSession = null;

    protected AdminSession() {
        
    }
    
    public static AdminSession getInstance() {

        if (adminSession == null) {

            synchronized (UserSession.class) {

                AdminSession inst = adminSession;

                if (inst == null) {

                    synchronized (AdminSession.class) {
                        adminSession = new AdminSession();
                    }
                }
            }
        }

        return adminSession;
    }
}
