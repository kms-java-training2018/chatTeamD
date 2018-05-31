package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorPageServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// 受け取ったエラーメッセージをページに送る
		String errorMsg = (String)req.getAttribute("errorMsg");
		req.setAttribute("errorMsg", errorMsg);
		req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, res);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// 受け取ったエラーメッセージをページに送る
		String errorMsg = (String)req.getAttribute("errorMsg");
		req.setAttribute("errorMsg", errorMsg);
		req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, res);

	}
}
