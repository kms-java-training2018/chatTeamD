package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.SessionBean;
import bean.WithdrawalBean;
import model.WithdrawalModel;

public class WithdrawalServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// -------------------------------------------------------------
		// 初期化
		WithdrawalBean bean = new WithdrawalBean();
		WithdrawalModel model = new WithdrawalModel();
		String direction = "/WEB-INF/jsp/groupMessage.jsp";
		ArrayList<WithdrawalBean> list = new ArrayList<>();
		String errorMsg = "";
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// Sessionの取得
		HttpSession session = req.getSession();
		SessionBean sesBean = (SessionBean) session.getAttribute("session");

		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// Sessionにユーザ情報がなければ、エラーページへ遷移
		if (session == null || session.getAttribute("userId") == null) {
			// セッション情報なし
			// 行き先をエラーページに
			direction = "/errorPage";
			req.setAttribute("errorMsg", "セッション情報が無効です");
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}

		int groupNo = Integer.parseInt(req.getParameter("groupNo"));
		bean.setGroupNo(groupNo);

		// グループ所属ユーザー取得
		try {
			list = model.withdrawal(groupNo);
		}catch(Exception e) {

		}

		// -------------------------------------------------------------


		req.setAttribute("bean", bean);
		req.setAttribute("list", list);
		direction = "/WEB-INF/jsp/withdrawal.jsp";
		req.getRequestDispatcher(direction).forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

}
