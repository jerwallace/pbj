package stocktracker.api;

import java.rmi.*;
import stocktracker.client.protocol.CustomException;

/**
 * Interface class for the User(Trader) type client containing the methods
 * remotely invoked by the User clients
 */
public interface UserApi extends AbstractApi
{

    /**
     * Public method that is invoked by User client that is identified by
     * particular userName, to purchase certain number of a Stock Object using
     * the tickerName, and numStocks parameters
     * <p/>
     * @param tickerName
     * @param username
     * @param numStocks  <p/>
     * @throws RemoteException
     * @return The new balance of the user.
     */
    public double buyStock(String tickerName, String username, int numStocks) throws CustomException, RemoteException;

    /**
     * Public method that is invoked by User client that is identified by
     * particular userName, to sell certain number of a Stock Object using the
     * tickerName, and numStocks parameters
     * <p/>
     * @param tickerName
     * @param username
     * @param numStocks  <p/>
     * @throws RemoteException
     * @return The new balance of the user.
     */
    public double sellStock(String tickerName, String username, int numStocks) throws CustomException, RemoteException;
}
