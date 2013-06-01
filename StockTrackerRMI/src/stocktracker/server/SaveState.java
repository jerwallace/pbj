/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.tools.JavaFileManager.Location;

/**
 *
 * @author WallaceJ
 */
public class SaveState {
    public File savedHashMaps = new File("SavedHashMaps.list");
public Map<Location, Long> map = new HashMap<Location, Long>();

public void saveMaps() {
    try {
        ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream(savedHashMaps));
        oos.write(map);
        oos.close();
    } catch (Exception e) {
        // Catch exceptions
    }
}

public void loadMaps() {
    try {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(savedHashMaps));
        Object readMap = ois.readObject();
        if(readMap != null && readMap instanceof HashMap) {
            map.putAll((HashMap) readMap);
        }
        ois.close();
    } catch (Exception e) {
        // Catch exceptions
    }
}
}
