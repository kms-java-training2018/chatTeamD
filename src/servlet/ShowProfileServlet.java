package servlet;

/**
 * 05_他ユーザプロフィール確認機能
 * @author hanawa-tomonori
 */


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
		
		// -------------------------------------------------------------
		// 初期化
		ShowProfileBean bean = new ShowProfileBean();
		ShowProfileModel model = new ShowProfileModel();
		String errorMsg = "";
		String direction = "/WEB-INF/jsp/showProfile.jsp";
		
		// userNoを取得し、beanへセット
		bean.setUserNo(req.getParameter("otherUserNo"));
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// ユーザ情報確認処理
		// ユーザ情報が確認できなければ、エラーページへ遷移
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
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// SQL実行
		try {
			bean = model.output(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// --------------------------------------------------------------

		// --------------------------------------------------------------
		// 他ユーザ情報が取得できなければ、エラーページへ遷移
		if (bean.getErrorMessage() == null) {
			req.setAttribute("showMyPageText", bean.getMyPageText());
			req.setAttribute("showName", bean.getUserName());
		} else {
			req.setAttribute("errorMsg", bean.getErrorMessage());
			req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, res);
			return;
		}
		// --------------------------------------------------------------

		// directionへ遷移
		req.getRequestDispatcher(direction).forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		req.getRequestDispatcher("/main").forward(req, res);

	}
}
