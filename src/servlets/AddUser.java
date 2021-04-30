package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AddUser")
public class AddUser extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=null;
		try {
			session=request.getSession();
			String email=request.getParameter("email");
			String name=request.getParameter("name");
			String phone=request.getParameter("phone");
			String gender=request.getParameter("gender");
			String pass=request.getParameter("password");
			String cpass=request.getParameter("cpassword");
			
			if(pass.equals(cpass)) {
			java.util.HashMap userDetails = new java.util.HashMap();
	        userDetails.put("name", name);
	        userDetails.put("email", email);
	        userDetails.put("phone", phone);
	        userDetails.put("gender", gender);
	        userDetails.put("password", pass);
			
	        db.DbConnect db=new db.DbConnect();
		    String result=db.addUser(userDetails);
		    db.dbDisconnect();
		    if (result.equalsIgnoreCase("success")) {
		    	
		    	userDetails.remove("password");
		    	session.setAttribute("userDetails",userDetails);
		    	
		    	response.sendRedirect("profile.jsp");
		    	
		    }else if(result.equalsIgnoreCase("already")) {
		    	session.setAttribute("msg","Email ID Already Exist!");
		        response.sendRedirect("home.jsp");
		    }
			}else {
				session.setAttribute("msg","Password and Confirm-Password not matched!");
		        response.sendRedirect("home.jsp");
			}
		}catch(Exception e) {
			session.setAttribute("exception",e);
	        response.sendRedirect("exceptionPage2.jsp");
		}
	}

}
