package com.pinkprogramming.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface IServer extends Remote {
	public boolean saveImg(String name, byte[] bytes) throws RemoteException ;
	public byte[] loadImg(String name) throws RemoteException ;
}
