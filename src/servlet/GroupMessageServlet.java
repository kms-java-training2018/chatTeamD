package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.GroupMessageBean;
import bean.SessionBean;
import model.GroupMessageModel;

public class GroupMessageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		// -------------------------------------------------------------
		// 初期化
		GroupMessageBean bean = new GroupMessageBean();
		GroupMessageModel model = new GroupMessageModel();
		String direction = "/WEB-INF/jsp/groupMessage.jsp";
		String errorMsg = "";
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// Sessionの取得
		HttpSession session = req.getSession();
		SessionBean sesBean = (SessionBean) session.getAttribute("session");
		String sesUserNo = sesBean.getUserNo();
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// Sessionにユーザ情報がなければ、エラーページへ遷移
		if (sesUserNo == null) {
			errorMsg = "セッションが切れました";
			req.setAttribute("errorMsg", errorMsg);
			req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, res);
			return;
		}

		// -------------------------------------------------------------

		int groupNo = Integer.parseInt(req.getParameter("groupNo"));
		bean.setGroupNo(groupNo);
		req.setAttribute("groupBean", bean);
		bean.setUserNo(sesUserNo);
		//session.setAttribute("groupNo", groupNo);

		// グループナンバーが取得できない場合エラー
		if (groupNo == 0) {
			req.getRequestDispatcher("/error").forward(req, res);
			return;
		}

		// -------------------------------------------------------------
		// SQL実行
		try {
			bean = model.output(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// -------------------------------------------------------------

		req.setAttribute("userName", bean.getListUserName());
		req.setAttribute("message", bean.getListMessage());
		req.setAttribute("userNo", bean.getListUserNo());
		req.setAttribute("bean", bean);
		req.getRequestDispatcher(direction).forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		// -------------------------------------------------------------
		// 初期化
		GroupMessageBean bean = new GroupMessageBean();
		GroupMessageModel model = new GroupMessageModel();
		String direction = "/WEB-INF/jsp/groupMessage.jsp";
		String errorMsg = "";
		// -------------------------------------------------------------

		req.setCharacterEncoding("UTF-8");

		// -------------------------------------------------------------
		// Sessionの取得
		HttpSession session = req.getSession();
		SessionBean sesBean = (SessionBean) session.getAttribute("session");
		String sesUserNo = sesBean.getUserNo();
		String delete = "";
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// Sessionにユーザ情報がなければ、エラーページへ遷移
		if (sesUserNo == null) {
			errorMsg = "セッションが切れました";
			req.setAttribute("errorMsg", errorMsg);
			req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, res);
			return;
		}

		int testnum = 0;

		if (req.getParameter("delete") != null) {
			testnum = Integer.parseInt(req.getParameter("delete"));
			bean.setMessageNo(testnum);
			try {
				bean = model.delete(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (req.getParameter("groupexit") != null) {

			int exitGroupNo = Integer.parseInt(req.getParameter("exit"));
			bean.setExitGroupNo(exitGroupNo);
			try {
				bean = model.author(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (bean.getRegistUserNo() == Integer.parseInt(sesUserNo)) {
				req.getRequestDispatcher("/error").forward(req, res);
				return;
			}
			bean.setUserNo(sesUserNo);
			try {
				bean = model.exit(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
			req.getRequestDispatcher("/main").forward(req, res);
			return;
		}

		// -------------------------------------------------------------

		String message = req.getParameter("message");
		int groupNo = Integer.parseInt(req.getParameter("groupNo"));

		bean.setMessage(message);
		bean.setUserNo(sesUserNo);
		bean.setGroupNo(groupNo);

		if (message != null) {
			int messageLen = message.length();
			if (messageLen > 100) {
				req.getRequestDispatcher("/error").forward(req, res);
				return;
			}
			// -------------------------------------------------------------
			// SQL実行
			// メッセージ送信
			try {
				bean = model.send(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// -------------------------------------------------------------
		}

		// -------------------------------------------------------------
		// SQL実行
		// メッセージ表示
		try {
			bean = model.output(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// -------------------------------------------------------------

		req.setAttribute("groupBean", bean);
		req.setAttribute("userName", bean.getListUserName());
		req.setAttribute("message", bean.getListMessage());
		req.setAttribute("userNo", bean.getListUserNo());
		req.setAttribute("bean", bean);
		req.getRequestDispatcher(direction).forward(req, res);
	}

}
