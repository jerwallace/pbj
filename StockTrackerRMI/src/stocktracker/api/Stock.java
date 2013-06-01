package stocktracker.api;

import java.text.DecimalFormat;

/**
 * Class describing a Stock object
 */
public class Stock
{

    private String tickerName;
    private double price;
    private int volume;

    /**
     * Default public constructor for the Stock class
     * <p/>
     * @param tickerName - String tickerName
     * @param price      - double Price
     */
    public Stock(String tickerName, double price)
    {
        this.tickerName = tickerName;
        this.price = price;

        //Maximum number of available initial stocks
        this.volume = 1000;
    }

    /**
     * Public method returns the tickerName of this Stock
     * <p/>
     * @return the tickerName
     */
    public String getTickerName()
    {
        return this.tickerName;
    }

    /**
     * Public method sets the tickerName for this Stock
     * <p/>
     * @param tickerName
     */
    public void setTickerName(String tickerName)
    {
        this.tickerName = tickerName;
    }

    /**
     * Public method that returns the Price for this Stock
     * <p/>
     * @return the price
     */
    public double getPrice()
    {
        return this.price;
    }

    /**
     * Public method that returns the Price of this stock in String format with
     * two decimals
     * <p/>
     * @return the Price in string formatted version
     */
    public String getPriceString()
    {
        DecimalFormat dec = new DecimalFormat("#.00 USD");
        return dec.format(price);
    }

    /**
     * Public method that sets the Price for this Stock
     * <p/>
     * @param price
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /**
     * Overriding the toString() method so that it returns a String containing
     * both the tickerName and the stock Price when it is called
     */
    @Override
    public String toString()
    {
        return this.getTickerName() + "\t\t" + this.getPrice();
    }

    /**
     * Public method returns the Volume for this Stock
     * <p/>
     * @return - Integer Stock Volume
     */
    public int getVolume()
    {
        return this.volume;
    }

    /**
     * Public method that sets the Volume for this Stock
     * <p/>
     * @param volume
     */
    public void setVolume(int volume)
    {
        this.volume = volume;
    }
    
    /**
     * Public method decreases the volume for this Stock
     * @param decreaseValue The amount of stocks to decrease.
     */
    public void decreaseVolume(int decreaseValue) {
        this.volume = this.volume - decreaseValue;
    }
    
    /**
     * Public method increases the volume for this Stock
     * @param increaseValue The amount of stocks to increase.
     */
    public void increaseVolume(int increaseValue) {
        this.volume = this.volume + increaseValue;
    }
}
