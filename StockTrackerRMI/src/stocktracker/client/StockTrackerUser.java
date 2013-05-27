/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client;

import stocktracker.api.StockTrackerUserApi;

/**
 *
 * @author WallaceJ
 */
public class StockTrackerUser extends StockTrackerClient {
    
    public static void main(String[] args) throws Exception  {
        loadRegistry();
        remoteApi = (StockTrackerUserApi) registry.lookup(StockTrackerUserApi.class.getSimpleName());
        run();
    }
}
