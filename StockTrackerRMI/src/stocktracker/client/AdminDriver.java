/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client;

import stocktracker.api.AdminApi;

/**
 *
 * @author WallaceJ
 */
public class AdminDriver extends AbstractClient
{

    public static void main(String[] args) throws Exception
    {
        AdminSession.loadRegistry();
        AdminSession.setRemoteApi((AdminApi) AdminSession.registry.lookup(AdminApi.class.getSimpleName()));
        run();
    }
    
}
