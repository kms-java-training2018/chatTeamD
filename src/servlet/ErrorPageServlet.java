package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorPageServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		req.getAttribute("errorMsg");

		req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, res);

	}
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {


		req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, res);

	}
}
