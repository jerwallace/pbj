/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import stocktracker.api.AbstractApi;
import stocktracker.client.protocol.AbstractProtocol.State;

/**
 *
 * @author WallaceJ
 */
public abstract class Session {
    
    private String username = "";
    private String selectedStockName = "";
    private State currentState = State.LOGIN;
    
    private static AbstractApi remoteApi;
    
    private static String host = "localhost";
    private static int port = 1099;
    protected static Registry registry;
    
         /**
     * @return the currentState
     */
    public State getCurrentState() {
        return currentState;
    }

    /**
     * @param currentState the currentState to set
     */
    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }   

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the selectedStockName
     */
    public String getSelectedStockName() {
        return selectedStockName;
    }

    /**
     * @param selectedStockName the selectedStockName to set
     */
    public void setSelectedStockName(String selectedStockName) {
        this.selectedStockName = selectedStockName;
    }
    
    /**
     * @return the remoteApi
     */
    public static AbstractApi getRemoteApi() {
        return remoteApi;
    }
    
    /**
     * @param aRemoteApi the remoteApi to set
     */
    public static void setRemoteApi(AbstractApi aRemoteApi) {
        remoteApi = aRemoteApi;
    }
    
        public static void loadRegistry() throws RemoteException {
            registry = LocateRegistry.getRegistry(getHost(), getPort());
       }
        
        /**
     * @return the host
     */
    public static String getHost() {
        return host;
    }

    /**
     * @param aHost the host to set
     */
    public static void setHost(String aHost) {
        host = aHost;
    }

    /**
     * @return the port
     */
    public static int getPort() {
        return port;
    }

    /**
     * @param aPort the port to set
     */
    public static void setPort(int aPort) {
        port = aPort;
    }
}
