package classes;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kohsuke.stapler.StaplerProxy;

public class Test extends HttpServlet implements StaplerProxy{

	@Override
	public Object getTarget() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter writer = response.getWriter();
		writer.print("hello world");
	}

}
