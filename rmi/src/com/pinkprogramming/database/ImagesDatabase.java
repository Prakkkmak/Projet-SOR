package com.pinkprogramming.database;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ImagesDatabase {
	
	static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static final String DB_URL = "jdbc:mariadb://localhost/sor_project";
    static final String USER = "root";
    static final String PASS = "";
    
    /**
     * Save an array of bytes to the image_img table.
     * @param name The name of the array of bytes.
     * @param bytes The bytes.
     * @return Success of fail
     */
    public boolean save(String name, byte[] bytes) {
    	Connection conn = null;
    	try {
    		Class.forName("org.mariadb.jdbc.Driver");
    		System.out.println("Connect to database in progress ...");
    		conn = DriverManager.getConnection(DB_URL, USER, PASS);
    		System.out.println("Connected database !");
    		String sql = "INSERT INTO image_img (img_name, img_bytes) VALUES (?, ?);";
    		PreparedStatement statement = conn.prepareStatement(sql);
    		statement.setString(1, name);
    		statement.setBytes(2, bytes);
    		statement.executeQuery();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		try {
    			conn.close();
    			return true;
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	return false;
    }
    
    public byte[] load(String name) {
    	Connection conn = null;
    	byte[] bytes = null;
    	try {
    		Class.forName("org.mariadb.jdbc.Driver");
    		System.out.println("Connect to database in progress ...");
    		conn = DriverManager.getConnection(DB_URL, USER, PASS);
    		System.out.println("Connected database !");
    		String sql = "SELECT img_bytes FROM image_img WHERE img_name = ?";
    		PreparedStatement statement = conn.prepareStatement(sql);
    		System.out.println("Tentative de load d'image " + name);
    		statement.setString(1, name);
    		System.out.println(statement.toString());
    		ResultSet rs = statement.executeQuery();
    		
    		//Get only the first result
    		if(rs.next()) {
    			Blob blob = rs.getBlob("img_bytes");
        		bytes = blob.getBytes(1, (int) blob.length());
        		blob.free();
        		System.out.println("Bytes récupérés");
    		}
    		else {
    			System.out.println("Bytes pas récupérés");
    			return null;
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		try {
    			conn.close();
    			return bytes;
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	return null;
    }
    
}
