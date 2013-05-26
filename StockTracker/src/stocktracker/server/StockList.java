import java.sql.Time;
import java.util.Map;

public class StockList {

	private Map <String, Stock>stocksTable;
	private Time timeStamp;
	
	public Time getTimeStamp() {
		return timeStamp;
	}
	public void updateTimeStamp(Time timeStamp) {
		this.timeStamp = timeStamp;
	}
	public void addNewStock(Stock thisStock)
	{
		this.stocksTable.put(thisStock.getTickerName(), thisStock);
	}
	public void updateStockPrice(Stock thisStock)
	{
		this.stocksTable.put(thisStock.getTickerName(), thisStock);
	}
	public String toString()
	{
		String mapString = "";
		mapString = "Stock Name" + "/t" + "Stock Value";
		for (Map.Entry<String, Stock> entry : this.stocksTable.entrySet()) 
		{
		    mapString += entry.getValue();
		}
		return mapString;
	}
	public void resetStockList()
	{
		this.stocksTable.clear();
	}
	public int getNumDiffStocks()
	{
		return this.stocksTable.size();
	}
}
