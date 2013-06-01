/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.tools.JavaFileManager.Location;
import stocktracker.api.StockList;

/**
 *
 * @author WallaceJ
 */
public class SaveState {

    public void saveState() {
               try
               {
                  FileOutputStream fileOut =
                  new FileOutputStream("stocklist.ser");
                  ObjectOutputStream out =
                                     new ObjectOutputStream(fileOut);
                  out.writeObject(StockList.getInstance());
                  out.close();
                  fileOut.close();
               }catch(IOException i)
               {
                   i.printStackTrace();
               }
    }

    public void loadState() {

      }
    }
    
