package com.pinkprogramming.utils;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.pingprogramming.beans.ImgBean;
import com.pinkprogramming.interfaces.IServer;

public class RmiConnector {

	/**
	 * Send an image to the rmi server
	 * @param img the img to send
	 * @return success or fail
	 */
	public boolean sendImage(ImgBean img) {
		int port = 10001;
		try {
			Registry registry = LocateRegistry.getRegistry(port);
			IServer srmi = (IServer)registry.lookup("serverRMI");
			boolean res = srmi.saveImg(img.getName(), img.getImg());
			return true;
		} catch (Exception e) {
			System.out.println("Error client RMI "+e.getMessage());
			return false;
		}
	}
	/**
	 * Load an image from the rmi server
	 * @param name the name of the image
	 * @return the image to byte array
	 */
	public byte[] loadImage(String name) {
		int port = 10001;
		try{
			Registry registry = LocateRegistry.getRegistry(port);
			IServer srmi = (IServer) registry.lookup("serverRMI");
			byte[] res = srmi.loadImg(name);
			return res;
		}
		catch (Exception e) {
			System.out.println("Error client RMI "+e.getMessage());
			return null;
		}
	}
}
