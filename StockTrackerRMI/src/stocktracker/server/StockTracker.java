package stocktracker.server;

import stocktracker.api.StockList;

/**
 *
 * @author Bahman
 */
public class StockTracker implements Runnable
{

    private StockList myStockList;

    public StockTracker(StockList stocklist)
    {
        this.myStockList = stocklist;
    }

    @Override
    public void run()
    {
    }
}
