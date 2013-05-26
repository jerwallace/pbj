
public class Stock
{

    private String tickerName;
    private double price;

    public Stock(String stockName, Integer stockPrice)
    {
        this.tickerName = stockName;
        this.price = stockPrice;
    }

    public String getTickerName()
    {
        return tickerName;
    }

    public void setTickerName(String tickerName)
    {
        this.tickerName = tickerName;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public String toString()
    {
        String stockString = "";
        stockString = this.getTickerName() + "/t/t" + String.valueOf(this.getPrice());
        return stockString;
    }
}
