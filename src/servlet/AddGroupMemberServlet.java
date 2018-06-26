package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.AddGroupMemberBean;
import bean.SessionBean;
import model.AddGroupMemberModel;

public class AddGroupMemberServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		// -------------------------------------------------------------
		// 初期化
		AddGroupMemberBean bean = new AddGroupMemberBean();
		AddGroupMemberModel model = new AddGroupMemberModel();
		String direction = "/WEB-INF/jsp/groupMessage.jsp";
		String errorMsg = "";
		ArrayList<AddGroupMemberBean> list = new ArrayList<>();
		int groupNo = 0;
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

		groupNo = Integer.parseInt(req.getParameter("groupNo"));

		try {
			list = model.userList(groupNo);
		} catch (Exception e) {
			// DB接続できてません
		}

		bean.setGroupNo(groupNo);
		req.setAttribute("aa", "aa");
		req.setAttribute("bean", bean);
		req.setAttribute("list", list);
		direction = "/WEB-INF/jsp/addGroupMember.jsp";
		req.getRequestDispatcher(direction).forward(req, res);

	}
	// -------------------------------------------------------------

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		// -------------------------------------------------------------
		// 初期化
		AddGroupMemberBean bean = new AddGroupMemberBean();
		AddGroupMemberModel model = new AddGroupMemberModel();
		String direction = "/WEB-INF/jsp/groupMessage.jsp";
		String errorMsg = "";
		int deleteCheck = 0;
		String message = "";
		// -------------------------------------------------------------

		req.setCharacterEncoding("UTF-8");

		// -------------------------------------------------------------
		// Sessionの取得
		HttpSession session = req.getSession();
		SessionBean sesBean = (SessionBean) session.getAttribute("session");

		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// Sessionにユーザ情報がなければ、エラーページへ遷移
		if (sesBean == null || session.getAttribute("userId") == null) {
			// セッション情報なし
			// 行き先をエラーページに
			direction = "/errorPage";
			req.setAttribute("errorMsg", "セッション情報が無効です");
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}
		// -------------------------------------------------------------
		bean.setGroupNo(Integer.parseInt(req.getParameter("groupNo")));

		String[] groupMemberNo = req.getParameterValues("userNo");

		if(groupMemberNo == null) {
			errorMsg = "エラーです。グループページに戻ってください";
			req.setAttribute("errorMsg", errorMsg);
			direction = "/WEB-INF/jsp/addGroupMember.jsp";
			req.setAttribute("groupNo", bean.getGroupNo());
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}

		for (int i = 0; i < groupMemberNo.length; i++) {
			bean.setUserNo(groupMemberNo[i]);
			try {
				bean = model.check(bean);
			} catch (Exception e) {
				// DB接続できませんでした
			}

			if (bean.getResult() != null) {
				try {
					// update
					bean = model.update(bean);
				} catch (Exception e) {

				}
			} else {
				try {
					bean = model.insert(bean);
				} catch (Exception e) {

				}
			}
		}

		message = "メンバーを追加しました";
		req.setAttribute("message", message);
		direction = "/WEB-INF/jsp/addGroupMember.jsp";
		req.getRequestDispatcher(direction).forward(req, res);
		// -------------------------------------------------------------

	}
}
