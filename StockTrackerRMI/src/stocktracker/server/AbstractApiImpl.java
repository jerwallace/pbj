package stocktracker.server;

import java.rmi.*;
import java.rmi.server.*;
import stocktracker.api.*;
import stocktracker.client.protocol.CustomException;
import stocktracker.client.protocol.CustomException.ErrorType;

/**
 * Implementation of the Abstract API class for the Server that extends the
 * UnicastRemoteObject class which is a sublass of the RMI RemoteServer class,
 * implements the AbstractApi class defined in the API package and is further
 * extended in the UserAiImpl & AdminApiImpl
 */
public abstract class AbstractApiImpl extends UnicastRemoteObject implements AbstractApi
{

    /**
     * Public default constructor overriding the UnicastRemoteObject superclass
     * <p/>
     * @throws RemoteException
     */
    public AbstractApiImpl() throws RemoteException
    {
        super();
    }

    /**
     * Public method that returns true if the User already exists in UserList
     * and false if User is not created already
     * <p/>
     * @param username <p/>
     * @return true/user exists - false/user does not exist
     * <p/>
     * @throws RemoteException
     */
    @Override
    public boolean userExists(String username) throws RemoteException
    {
        User currentUser = UserList.getInstance().getUser(username);

        //Creates a new User object if a User with the given userName
        // does not already exist in the UserList
        if (currentUser == null)
        {
            UserList.getInstance().addUser(username);
            return false;
        }
        else
        {
            return true;
        }

    }

    /**
     * Public method that returns the User object referred to by String userName
     * <p/>
     * @param username <p/>
     * @return - User Object
     * <p/>
     * @throws RemoteException
     */
    @Override
    public User getUser(String username) throws RemoteException
    {
        return UserList.getInstance().getUser(username);
    }

    /**
     * Public method that returns a String displaying the currentStock selected
     * by client and it's most recently updated Price
     * <p/>
     * @param tickerName <p/>
     * @return - String containing tickerName and Price
     * <p/>
     * @throws RemoteException
     */
    @Override
    public String selectStock(String tickerName) throws CustomException, RemoteException
    {
        Stock currentStock = StockList.getInstance().getStockByTickerName(tickerName);

        if (currentStock == null)
        {
            throw new CustomException(ErrorType.NO_STOCK_FOUND);
        }
        else
        {
            return tickerName + " Stock price is currently @: $" + currentStock.getPriceString();
        }
    }
    
    /**
     * Returns the entire stock list.
     * @return A string containing the entire stock list.
     * @throws CustomException
     * @throws RemoteException 
     */
    @Override
    public String printStockList() throws CustomException, RemoteException {
        return StockList.getInstance().toString();
    }
}
