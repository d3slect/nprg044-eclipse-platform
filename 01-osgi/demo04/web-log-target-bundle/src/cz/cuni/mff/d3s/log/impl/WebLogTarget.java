package cz.cuni.mff.d3s.log.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import cz.cuni.mff.dsrg.log.api.ILogTarget;

public class WebLogTarget implements ILogTarget {
	
	private static final String LOG_CONTEXT = "/log";
	
	private HttpService httpService;
	
	private List<String> messages = new LinkedList<String>();	
	
	@Override
	public void write(String message) {
		messages.add(message);
	}	

	@Override
	public String getName() {		
		return "Web Log Target";
	}
	
	private void activate() {
		System.out.println("WebLogTarget.activate()");
	}
	
	private void deactivate() {
		System.out.println("WebLogTarget.deactivate()");
	}
	
	private void bindHttpService(HttpService httpService) {
		this.httpService = httpService;
		
		registerServlet();		
	}
	
	private void unbindHttpService(HttpService httpService) {
		unregisterServlet();
		
		this.httpService = null;
	}

	private void unregisterServlet() {
		httpService.unregister(LOG_CONTEXT);
	}

	private void registerServlet() {
		try {
			httpService.registerServlet(LOG_CONTEXT, new LogTargetServlet(), null, null);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (NamespaceException e) {
			e.printStackTrace();
		}		
	}
	
	private class LogTargetServlet extends HttpServlet {
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			resp.setContentType("text/plain");
			PrintWriter pw = resp.getWriter();
			for(String msg : messages) {
				pw.println(msg);
			}
			
			pw.close();			
		}
	}
}
