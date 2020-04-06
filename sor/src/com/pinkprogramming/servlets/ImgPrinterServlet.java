package com.pinkprogramming.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import com.pingprogramming.beans.ImgBean;
import com.pinkprogramming.utils.RmiConnector;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * Servlet implementation class ImgPrinterServlet
 */
@WebServlet("/ImgPrinterServlet")
public class ImgPrinterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ImgPrinterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher( "/WEB-INF/imgPrinter.jsp" ).forward( request, response );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get the file name
		String name = request.getParameter("name");
		
		RmiConnector rc = new RmiConnector();
		byte[] res = rc.loadImage(name);
		
		if(res != null) {
			ServletOutputStream os = response.getOutputStream();
			os.write(res);
			os.close();
		}
		else{
			request.setAttribute("res", "Erreur dans le chargement de l'image");
			this.getServletContext().getRequestDispatcher("/WEB-INF/imgPrinter.jsp").forward(request, response);
		}
	}

}
