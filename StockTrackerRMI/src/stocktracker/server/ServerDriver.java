package stocktracker.server;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.util.Scanner;
import stocktracker.api.*;

/**
 *
 */
public class ServerDriver
{

    private static final int PORT = 1099;
    private static Registry registry;
    private SaveState stateTools;

    public ServerDriver() {
        stateTools = new SaveState();
        stateTools.loadState();
    }
    
    /**
     *
     * @throws RemoteException
     */
    public static void startRegistry() throws RemoteException
    {
        // create in server registry
        registry = java.rmi.registry.LocateRegistry.createRegistry(PORT);
    }

    /**
     *
     * @param name
     * @param remoteUserObj <p/>
     * @throws RemoteException
     * @throws AlreadyBoundException
     */
    public static void registerObject(String name, Remote remoteUserObj)
            throws RemoteException, AlreadyBoundException
    {
        registry.bind(name, remoteUserObj);
        System.out.println("Registered: " + name + " -> "
                + remoteUserObj.getClass().getName() + "[" + remoteUserObj + "]");

    }

    /**
     *
     * @param args <p/>
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        ServerDriver serverInstance = new ServerDriver();
        serverInstance.attachShutDownHook();
        Scanner input = new Scanner(System.in);
        
        try {
        startRegistry();
        registerObject(UserApi.class.getSimpleName(), new UserApiImpl());
        registerObject(AdminApi.class.getSimpleName(), new AdminApiImpl());
        } catch (ExportException epex)
            {
                System.err.println("Error starting server. Please check the port or if another instance is already running.");
                System.exit(0);
            }
        
        Thread stockTrackerThread = new Thread(new StockTracker());
        stockTrackerThread.start();
        
        String inputString = "";
        while (true) {
        System.out.print("PBJ StEx Server > ");
        inputString = input.nextLine();
            if (inputString.equalsIgnoreCase("exit")) {
                  System.exit(0);
            }
        }   
        
    }
    
    public void attachShutDownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread() {
        
        @Override
        public void run() {
            stateTools.saveState();
            System.out.println("Inside Add Shutdown Hook");
        }
        });
        System.out.println("Shut Down Hook Attached.");
   }
}
