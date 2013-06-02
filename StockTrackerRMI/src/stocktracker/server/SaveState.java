/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.tools.JavaFileManager.Location;
import stocktracker.api.Stock;
import stocktracker.api.StockList;
import stocktracker.api.UserList;

/**
 *
 * @author WallaceJ
 */
public class SaveState {

    String tmpDir = System.getProperty("user.dir");
    ArrayList<Map> mapsToSave = new ArrayList<>();
    
    public void saveState() {
                
                mapsToSave.add(UserList.getInstance().exportUserList());
                mapsToSave.add(StockList.getInstance().exportStocksTable());
        
               try
               {
                  FileOutputStream fileOut =
                  new FileOutputStream(this.tmpDir+"pbjdata.ser");
                  ObjectOutputStream out =
                                     new ObjectOutputStream(fileOut);
                  out.writeObject(mapsToSave);
                  out.close();
                  fileOut.close();
               }catch(IOException i)
               {
                   i.printStackTrace();
               }
    }

        public void loadState() {
            
            File f = new File(this.tmpDir+"pbjdata.ser");
 
	  if(f.exists()){
            try{
                  //use buffering
                  InputStream file = new FileInputStream(this.tmpDir+"pbjdata.ser" );
                  InputStream buffer = new BufferedInputStream( file );
                  ObjectInput input = new ObjectInputStream ( buffer );
                  try{
                    //deserialize the List
                    mapsToSave = (ArrayList<Map>)input.readObject();
                    
                    UserList.getInstance().importUserList(mapsToSave.get(0));
                    StockList.getInstance().importStocksTable(mapsToSave.get(1));
                    //display its data
                    System.out.println("Stocklist Imported:");
                    System.out.println(StockList.getInstance());
                    System.out.println("User lit Imported:");
                    System.out.println(UserList.getInstance());
                  }
                  finally{
                    input.close();
                  }
                }
                catch(ClassNotFoundException ex){
                  System.err.println("Cannot perform input. Class not found.");
                }
                catch(IOException ex){
                  System.err.println("Cannot perform input.");
                }
        } else {
            System.err.println("No saved state could be found. Creating new instance.");
        }
}
 }
   
    
