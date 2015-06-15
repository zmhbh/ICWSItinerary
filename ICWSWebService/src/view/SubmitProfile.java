package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Consumer;
import model.Location;
import model.Retailer;
import model.event.Profile;

import com.google.gson.JsonObject;

import controller.Create;
import controller.GeoParser;

/**
 * Servlet implementation class SubmitProfile
 */
@WebServlet("/SubmitProfile")
public class SubmitProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SubmitProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");

		// get user info and populate user
		boolean successStatus = false;
		Create create = new Create();

		String fullname = request.getParameter("fullname").trim();
		String title = request.getParameter("title").trim();
		String college = request.getParameter("college").trim();
		String email = request.getParameter("email").trim();
		
		System.out.println("fullname: "+fullname);
		
	
		Profile profile= new Profile(fullname,title,college,email);
		// adding location
		
			successStatus = create.createProfile(profile);
		

		// if success
		if (successStatus) {
			JsonObject myObj = new JsonObject();
			myObj.addProperty("success", true);
			out.println(myObj.toString());
		}
		// if failure
		else {
			JsonObject myObj = new JsonObject();
			myObj.addProperty("success", false);
			out.println(myObj.toString());
		}
		out.close();
	}

}
