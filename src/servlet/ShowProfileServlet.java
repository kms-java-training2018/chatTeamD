package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.SessionBean;
import bean.ShowProfileBean;
import model.ShowProfileModel;

public class ShowProfileServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		String errorMsg = "aiueo";
		// 初期化
		ShowProfileBean bean = new ShowProfileBean();
		ShowProfileModel model = new ShowProfileModel();

		String direction = "/WEB-INF/jsp/showProfile.jsp";
		bean.setUserNo(req.getParameter("otherUserNo"));
		String userNo = "13";
		bean.setUserNo(userNo);

		// Sessionの取得
		HttpSession session = req.getSession();
		SessionBean sesBean = (SessionBean) session.getAttribute("session");
		String sesUserNo = sesBean.getUserNo();

		// Sessionにユーザ情報がなければ、エラーページへ遷移
		if (sesUserNo == null) {
			errorMsg = "セッションが切れました";
			req.setAttribute("errorMsg", errorMsg);
			req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, res);
			return;
		}

		try {
			bean = model.output(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String test = bean.getErrorMessage();
		String test2 = "aiueo";

		req.setAttribute("test", test);
		req.setAttribute("test2", test2);
		if (bean.getErrorMessage() == null) {
			req.setAttribute("showMyPageText", bean.getMyPageText());
			req.setAttribute("showName", bean.getUserName());
		} else {
			req.setAttribute("errorMsg", bean.getErrorMessage());
			req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, res);
			return;
		}

		req.getRequestDispatcher(direction).forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		req.getRequestDispatcher("/main").forward(req, res);

	}
}
