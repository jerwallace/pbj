package stocktracker.server;

import java.rmi.*;
import stocktracker.api.*;
import stocktracker.client.protocol.CustomException;
import stocktracker.client.protocol.CustomException.ErrorType;

/**
 * Extends the AbstractApiImpl by adding Server side remotely invoked methods
 * that are used by Admin type Client
 */
public class AdminApiImpl extends AbstractApiImpl implements AdminApi
{

    private static final long serialVersionUID = 1L;
    private int counter = 0;

    /**
     * * Public default constructor overriding the AbstractApiImpl superclass
     * <p/>
     * @throws RemoteException
     */
    public AdminApiImpl() throws RemoteException
    {
        super();
        UserList.getInstance().addUser("bahman");
        UserList.getInstance().addUser("jeremy");
        UserList.getInstance().addUser("peter");
    }

    /**
     * Public method that updates the price of a stock given it's tickerName and
     * a new price value
     * <p/>
     * @param tickerName
     * @param price      <p/>
     * @throws RemoteException
     */
    public synchronized void updateStock(String tickerName, double price) throws CustomException, RemoteException
    {
        //If new price value is not a positive value, throw an error
        if (price < 0)
        {
            throw new CustomException(ErrorType.BAD_PRICE_VALUE);
        }
        //Else update the price of the Stock inside the StockList Singleton object
        else
        {
            StockList.getInstance().updateStock(new Stock(tickerName, price));
        }
    }
}
