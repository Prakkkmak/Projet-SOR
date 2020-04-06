package com.pinkprogramming.implementations;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.pinkprogramming.database.ImagesDatabase;
import com.pinkprogramming.interfaces.IServer;

public class ServerImpl implements IServer {
	
	ImagesDatabase db;
	
	public ServerImpl() {
		db = new ImagesDatabase();
	}
	
	public static void main(String [] args) {
		int port = 10001;
		Registry registry = null;
		
		// création registry
		try {
			LocateRegistry.createRegistry(port);
			registry = LocateRegistry.getRegistry(port);
		} catch (RemoteException e) {
			System.out.println("Error RMI createRegistry "+e.getMessage());
		}
		
		ServerImpl srmii = new ServerImpl();
		IServer srmi = null;
		
		// création objet distant
		try {
			srmi = (IServer) UnicastRemoteObject.exportObject(srmii,0);
		} catch (RemoteException e) {
			System.out.println("Erreur RMI exportObject "+e.getMessage());
		}
		
		// enregistrement objet distant
		try {
			registry.rebind("serverRMI", srmi);
		} catch (Exception e) {
			System.out.println("Erreur RMI rebind "+
			e.getMessage());
		}
		
		System.out.println("Serveur RMI lancé");
	}

	@Override
	public boolean saveImg(String name, byte[] bytes) throws RemoteException {
		System.out.println(name + " Imgsize : " + bytes.length);
		return db.save(name, bytes);
	}

	@Override
	public byte[] loadImg(String name) throws RemoteException {
		System.out.println(name + " Imgsize : KSCSDC ");
		return db.load(name);
	}
}
