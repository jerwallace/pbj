/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.server;

import java.rmi.*;
import java.rmi.server.*;
import stocktracker.api.*;
import stocktracker.client.protocol.CustomException;
import stocktracker.client.protocol.CustomException.ErrorType;

/**
 *
 * @author WallaceJ
 */
public abstract class AbstractApiImpl extends UnicastRemoteObject implements AbstractApi
{


    public AbstractApiImpl() throws RemoteException
    {
        super();
    }

    @Override
    public boolean userExists(String username) throws RemoteException
    {
        User currentUser = UserList.getInstance().getUser(username);

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
    
    @Override
    public User getUser(String username) throws RemoteException {
        System.out.println("test");
        return UserList.getInstance().getUser(username);
    }
    
    @Override
    public String selectStock(String tickerName) throws RemoteException {
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
}
