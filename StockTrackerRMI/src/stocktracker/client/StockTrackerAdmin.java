/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client;

import stocktracker.api.StockTrackerApi;

/**
 *
 * @author WallaceJ
 */
public class StockTrackerAdmin extends StockTrackerClient {
    
    public static void main(String[] args) throws Exception  {
        loadRegistry();
        remoteApi = (StockTrackerApi) registry.lookup(StockTrackerApi.class.getSimpleName());
        run();
        
    }
}
