/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client;

import stocktracker.api.UserApi;

/**
 *
 * @author WallaceJ
 */
public class UserDriver extends AbstractClient {
    
    public static void main(String[] args) throws Exception  {
        loadRegistry();
        remoteApi = (UserApi) registry.lookup(UserApi.class.getSimpleName());

        run();
    }
}
