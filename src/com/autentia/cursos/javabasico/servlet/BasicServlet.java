package com.autentia.cursos.javabasico.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class BasicServlet extends GenericServlet {

	private static String sampleInitParam;
	private static BufferedReader configReader;
	/**
	 * 
	 */
	private static final long serialVersionUID = -1289907736259029596L;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		sampleInitParam = config.getInitParameter("sample");
		
		System.out.println("Init param 'sample' : " + sampleInitParam);
		System.out.println("All servlet param: " + config.getServletContext().getInitParameter("allServletParam"));
		
		InputStream is = config.getServletContext().getResourceAsStream(config.getServletContext().getInitParameter("configFile"));
		
		configReader = new BufferedReader(new InputStreamReader(is));
		
		super.init(config);
	}
	
	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		
		final StringBuilder htmlStrBldr = new StringBuilder();
		
		htmlStrBldr.append("<html><head/><body style=\"border-width:1;border:solid\">");
		htmlStrBldr.append("<p>Aplicación: ").append(configReader.readLine()).append("<br/>");
		htmlStrBldr.append("Versión: ").append(configReader.readLine()).append("</p>");
		htmlStrBldr.append("<p>Hola, mundo!!</p>");
		htmlStrBldr.append("</body></html>");
		
		arg1.getWriter().write(htmlStrBldr.toString());
		arg1.getWriter().close();
	}
	
	@Override
	public void destroy() {
		
		try {
			configReader.close();
		} catch (IOException e) {
			System.err.println("Erorr cerrando el stream de lectura del fichero de configuración");
		}
		
		System.out.println("Goodbye' : " + sampleInitParam);
		super.destroy();
	}
}
