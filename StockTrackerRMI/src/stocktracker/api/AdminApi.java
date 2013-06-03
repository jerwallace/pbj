package stocktracker.api;

import java.rmi.RemoteException;
import stocktracker.client.protocol.CustomException;

/**
 * Interface class for the Administrator type client containing the methods
 * remotely invoked by the Admin clients
 */
public interface AdminApi extends AbstractApi
{

    /**
     * Public method that is invoked by Admin client to update the Price of a
     * Stock Object using the tickerName, and new Price Value parameters
     * <p/>
     * @param tickerName
     * @param newPrice   <p/>
     * @throws RemoteException
     */
    public void updateStock(String tickerName, double price) throws CustomException, RemoteException;
}
