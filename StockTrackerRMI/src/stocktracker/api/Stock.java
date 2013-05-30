/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.api;

import java.text.DecimalFormat;

/**
 *
 * @author WallaceJ
 */
public class Stock
{

    private String tickerName;
    private double price;
    private int volume;

    public Stock(String tickerName, double price)
    {
        this.tickerName = tickerName;
        this.price = price;
        this.volume = 1000;
    }

    /**
     * @return the tickerName
     */
    public String getTickerName()
    {
        return tickerName;
    }

    /**
     * @param tickerName the tickerName to set
     */
    public void setTickerName(String tickerName)
    {
        this.tickerName = tickerName;
    }

    /**
     * @return the price
     */
    public double getPrice()
    {
        return price;
    }

 /**
     * @return the price
     */
    public String getPriceString()
    {
        DecimalFormat dec = new DecimalFormat("#.00 USD");
        return dec.format(price);
    }    
    
    /**
     * @param price the price to set
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    @Override
    public String toString()
    {
        return getTickerName() + "\t\t" + getPrice();
    }

    public int getVolume()
    {
        return volume;
    }

    public void setVolume(int volume)
    {
        this.volume = volume;
    }
}
