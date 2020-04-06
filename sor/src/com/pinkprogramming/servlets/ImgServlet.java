package com.pinkprogramming.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import com.pingprogramming.beans.ImgBean;
import com.pinkprogramming.utils.RmiConnector;

/**
 * The Img servlet is the servlet for manage the storing of an image.
 * @author PinkProgramming
 * @version 1.0
 */
@WebServlet("/ImgServlet")
@MultipartConfig
public class ImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ImgServlet() {
        super();
        
    }
    
    /**
     * Load the form jsp
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher( "/WEB-INF/imgForm.jsp" ).forward( request, response );
	}
	
	
	/**
	 * Get the name and the img and send it to a RMI server.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ImgBean img = new ImgBean();
		
		//Get the file name
		String name = request.getParameter("name");
		img.setName(name);
		
		//Get the byte Array
		Part filePart = request.getPart("file");
		InputStream is = filePart.getInputStream(); // Get input stream of the file
		byte[] bytes = IOUtils.toByteArray(is);
		img.setImg(bytes);
		
		//Create a RMI connector and send the img
		RmiConnector rc = new RmiConnector();
		boolean res = rc.sendImage(img);
		//Printed response
		if(res == true) {
			request.setAttribute("res", "L'image a correctement été sauvegardée");
		}
		else {
			request.setAttribute("res", "L'image n'a pas pu être sauvegardée");
		}
		//Reload the jsp
		this.getServletContext().getRequestDispatcher("/WEB-INF/imgForm.jsp").forward(request, response);
	}

	
	
}
