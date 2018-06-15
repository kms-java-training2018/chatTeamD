package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {
		public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, res);
		}

		public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

			String direction = "/WEB-INF/jsp/login.jsp";
			HttpSession session = req.getSession();

			if(session != null) {
				session.invalidate();
			}

			req.getRequestDispatcher(direction).forward(req, res);

}
}
