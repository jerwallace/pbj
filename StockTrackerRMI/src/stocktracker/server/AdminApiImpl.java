/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.server;

/**
 *
 * @author WallaceJ
 */
import java.rmi.*;
import stocktracker.api.*;
import stocktracker.client.protocol.CustomException;
import stocktracker.client.protocol.CustomException.ErrorType;

/**
 *
 * @author Bahman
 */
public class AdminApiImpl extends AbstractApiImpl implements AbstractApi
{

    private static final long serialVersionUID = 1L;
    private int counter = 0;

    /**
     *
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
     *
     * @param tickerName
     * @param price
     * <p/>
     * @throws RemoteException
     */
    public void updateStock(String tickerName, double price) throws RemoteException
    {
        if (price < 0)
        {
            throw new CustomException(ErrorType.BAD_PRICE_VALUE);
        }
        else
        {
            StockList.getInstance().updateStock(new Stock(tickerName, price));
        }
    }
}
