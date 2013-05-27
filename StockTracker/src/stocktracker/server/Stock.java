/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.server;

/**
 *
 * @author Bahman
 */
public class Stock
{

    private String tickerName;
    private Double price;

    public Stock(String stockName, Double stockPrice)
    {
        this.tickerName = stockName;
        this.price = stockPrice;
    }

    public String getTickerName()
    {
        return this.tickerName;
    }

    public Double getPrice()
    {
        return this.price;
    }

    public void setTickerName(String tickerName)
    {
        this.tickerName = tickerName;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }
}
