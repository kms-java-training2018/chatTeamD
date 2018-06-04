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
		String sesUserId = sesBean.getUserId();
		String sesUserNo = sesBean.getUserNo();
		String myPageText = (String) req.getAttribute("myPageText");
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

		String groupNo = req.getParameter("groupNo");
		bean.setGroupNo(groupNo);
		session.setAttribute("groupNo", groupNo);

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

		// TODO 削除機能の実装
		// groupNoをdoGetでセッションに持たせて、Post側ではセッションを取得

		// -------------------------------------------------------------
		// 初期化
		GroupMessageBean bean = new GroupMessageBean();
		GroupMessageModel model = new GroupMessageModel();
		String direction = "/WEB-INF/jsp/groupMessage.jsp";
		String errorMsg = "";
		// -------------------------------------------------------------

		req.setCharacterEncoding("UTF-8");
		String groupNo = req.getParameter("groupNo");

		// -------------------------------------------------------------
		// Sessionの取得
		HttpSession session = req.getSession();
		SessionBean sesBean = (SessionBean) session.getAttribute("session");
		String sesUserId = sesBean.getUserId();
		String sesUserNo = sesBean.getUserNo();
		String myPageText = (String) req.getAttribute("myPageText");
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

		groupNo = (String) req.getAttribute("groupNo");
		String message = req.getParameter("message");

		bean.setMessage(message);
		bean.setUserNo(sesUserNo);
		bean.setGroupNo(groupNo);

		// -------------------------------------------------------------
		// SQL実行
		try {
			bean = model.send(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// -------------------------------------------------------------
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
		req.getRequestDispatcher("WEB-INF/jsp/groupMessage.jsp").forward(req, res);
	}

}
