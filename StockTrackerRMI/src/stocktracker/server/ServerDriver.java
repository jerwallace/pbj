package stocktracker.server;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import stocktracker.api.*;

/**
 *
 * @author Bahman
 */
public class ServerDriver
{ 
        private static final int PORT = 1099;
        
        private static Registry registry;

        public static void startRegistry() throws RemoteException {
            // create in server registry
            registry = java.rmi.registry.LocateRegistry.createRegistry(PORT);
        }

        public static void registerObject(String name, Remote remoteUserObj)
            throws RemoteException, AlreadyBoundException {
            registry.bind(name, remoteUserObj);
            System.out.println("Registered: " + name + " -> " +
                remoteUserObj.getClass().getName() + "[" + remoteUserObj + "]");
        }
   
        public static void main(String[] args) throws Exception {
            
            startRegistry();
            registerObject(UserApi.class.getSimpleName(), new UserApiImpl());
            registerObject(AbstractApi.class.getSimpleName(), new AdminApiImpl());
            Thread stockTrackerThread = new Thread(new StockTracker());
            stockTrackerThread.start();
            stockTrackerThread.join();
            
        }
        
}
