/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiexample.api;

/**
 *
 * @author WallaceJ
 */
import java.rmi.*;

public interface Api extends Remote {

public Data incrementCounter(Data value) throws RemoteException;

}