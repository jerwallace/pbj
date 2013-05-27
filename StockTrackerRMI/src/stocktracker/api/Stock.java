/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.api;

/**
 *
 * @author WallaceJ
 */
public class Stock {
    
    private String tickerName;
    private double price;

    public Stock(String tickerName, double price) {
        this.tickerName = tickerName;
        this.price = price;
    }
    
    /**
     * @return the tickerName
     */
    public String getTickerName() {
        return tickerName;
    }

    /**
     * @param tickerName the tickerName to set
     */
    public void setTickerName(String tickerName) {
        this.tickerName = tickerName;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    @Override
    public String toString() {
        return getTickerName()+"\t\t"+getPrice();
    }
    
}