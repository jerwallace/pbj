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
public class UserApiImpl extends AbstractApiImpl implements UserApi
{

    private static final long serialVersionUID = 1L;

    /**
     *
     * @throws RemoteException
     */
    public UserApiImpl() throws RemoteException
    {
        super();

    }

    /**
     *
     * @param tickerName
     * @param username
     * @param numStocks
     * <p/>
     * @throws RemoteException
     */
    @Override
    public synchronized double buyStock(String tickerName, String username, int numStocks) throws RemoteException
    {
        Stock currentStock = StockList.getInstance().getStockByTickerName(tickerName);
        User currentUser = UserList.getInstance().getUser(username);

        double totalCost = numStocks * currentStock.getPrice();
        int numStocksOwned = currentUser.numStocksOwned(tickerName);

        if (currentUser.getBalance() < totalCost)
        {
            throw new CustomException(ErrorType.INSUFFICIENT_FUNDS);
        }
        else if (currentStock.getVolume() < numStocks)
        {
            throw new CustomException(ErrorType.UNAVAILABLE_STOCK_VOLUME);
        }
        else if (numStocks < 0) {
            throw new CustomException(ErrorType.NEGATIVE_VOLUME);
        } else {
            UserList.getInstance().getUser(username).setBalance((currentUser.getBalance() - totalCost));
            UserList.getInstance().getUser(username).updateUserStock(currentStock.getTickerName(), numStocksOwned + numStocks);
            StockList.getInstance().getStockByTickerName(currentStock.getTickerName()).decreaseVolume(numStocks);
            return (UserList.getInstance().getUser(username).getBalance());
        }
    }

    /**
     *
     * @param tickerName
     * @param username
     * @param numStocks
     * <p/>
     * @throws RemoteException
     */
    @Override
    public synchronized double sellStock(String tickerName, String username, int numStocks) throws RemoteException
    {
        Stock currentStock = StockList.getInstance().getStockByTickerName(tickerName);
        User currentUser = UserList.getInstance().getUser(username);

        double totalSalePrice = numStocks * currentStock.getPrice();
        int numStocksOwned = currentUser.getStocksOwned().get(currentStock.getTickerName());

        if (numStocksOwned < numStocks)
        {
            throw new CustomException(ErrorType.STOCKS_NOT_AVAILABLE);
        } if (numStocks < 0) {
            throw new CustomException(ErrorType.NEGATIVE_VOLUME);
        } else
        {
            
            StockList.getInstance().getStockByTickerName(currentStock.getTickerName()).increaseVolume(numStocks);
            UserList.getInstance().getUser(username).setBalance((currentUser.getBalance() + totalSalePrice));

            if (numStocksOwned == numStocks)
            {
                UserList.getInstance().getUser(username).removeUserStock(currentStock.getTickerName());
            }
            else 
            {
                // Overwrite the current stock.
                UserList.getInstance().getUser(username).updateUserStock(currentStock.getTickerName(), (numStocksOwned - numStocks));
            }
            return (UserList.getInstance().getUser(username).getBalance());
        }
    }
}
