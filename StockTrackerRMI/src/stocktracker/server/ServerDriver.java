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
        private static final int USER_PORT = 1099,
                                 ADMIN_PORT = 1100;
        
        private static Registry userRegistry, adminRegistry;

        public static void startRegistry() throws RemoteException {
            // create in server registry
            userRegistry = java.rmi.registry.LocateRegistry.createRegistry(USER_PORT);
            adminRegistry = java.rmi.registry.LocateRegistry.createRegistry(ADMIN_PORT);
        }

        public static void registerUserObject(String name, Remote remoteUserObj)
            throws RemoteException, AlreadyBoundException {
            userRegistry.bind(name, remoteUserObj);
            System.out.println("Registered: " + name + " -> " +
                remoteUserObj.getClass().getName() + "[" + remoteUserObj + "]");
        }
        
        public static void registerAdminObject(String name, Remote remoteAdminObj)
            throws RemoteException, AlreadyBoundException {
            adminRegistry.bind(name, remoteAdminObj);
            System.out.println("Registered: " + name + " -> " +
                remoteAdminObj.getClass().getName() + "[" + remoteAdminObj + "]");
        }
        
        public static void main(String[] args) throws Exception {
            startRegistry();
            registerUserObject(StockTrackerUserApi.class.getSimpleName(), new StockTrackerUserApiImpl());
            registerAdminObject(StockTrackerApi.class.getSimpleName(), new StockTrackerAdminApiImpl());
            //Thread stockTrackerThread = new Thread(new StockTracker(myStockList));
            Thread.sleep(5 * 60 * 1000);
        }
        
}
