package net.hualisheng.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(value="/user.do")
public class UserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4474017985173785871L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doGet(req, resp);
		resp.getWriter().print("user servlet");
	}

}
