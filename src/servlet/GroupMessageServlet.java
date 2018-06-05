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

		// -------------------------------------------------------------
		// beanへセット
		int groupNo = Integer.parseInt(req.getParameter("groupNo"));
		bean.setGroupNo(groupNo);
		req.setAttribute("groupBean", bean);
		bean.setUserNo(sesUserNo);
		bean.setOutFlag1("送信者不明");
		// -------------------------------------------------------------

		// グループナンバーが取得できない場合エラー
		if (groupNo == 0) {
			req.getRequestDispatcher("/errorPage").forward(req, res);
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

		// -------------------------------------------------------------
		// メッセージ送信者が、グループ脱退者かの判定
		int sizeMsg = bean.getListMessage().size();
		int sizeOutFlag = bean.getListOutFlagUN().size();
		int sizeOutFlagUser = bean.getListOutFlagUNum().size();
		String setUN = "";
		String setUserNo = "";
		int check = 1;

		for (int i = 0; i < sizeOutFlagUser; i++) {
			setUserNo = bean.getListOutFlagUNum().get(i);
			if (setUserNo.equals(sesUserNo)) {
				if (bean.getListOutFlag().get(i).equals("0")) {
					check = 0;
				}
			}
		}

		if (check == 1) {
			direction = "/errorPage";
			errorMsg = "グループに入っていません";
			req.setAttribute("errorMsg", errorMsg);
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}

		for (int i = 0; i < sizeMsg; i++) {
			setUN = bean.getListUserName().get(i);
			for (int j = 0; j < sizeOutFlag; j++) {
				if (setUN.equals(bean.getListOutFlagUN().get(j))) {
					if (bean.getListOutFlag().get(j).equals("1")) {
						bean.getListUserName().set(i, "送信者不明");
					}
				}
			}
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
			direction = "/errorPage";
			req.getRequestDispatcher(direction).forward(req, res);
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

		if (req.getParameter("exit") != null) {

			int exitGroupNo = Integer.parseInt(req.getParameter("exit"));
			bean.setExitGroupNo(exitGroupNo);
			try {
				bean = model.author(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (bean.getRegistUserNo() == Integer.parseInt(sesUserNo)) {
				direction = "/errorPage";
				req.getRequestDispatcher(direction).forward(req, res);
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

		// -------------------------------------------------------------
		// 入力エラーチェック
		// 入力できる文字は100桁まで
		if (message != null) {
			int messageLen = message.length();
			if (messageLen > 100) {
				errorMsg = "文字数が多すぎます";

				// SQL実行
				// メッセージ表示
				try {
					bean = model.output(bean);
				} catch (Exception e) {
					e.printStackTrace();
				}
				req.setAttribute("errorMsg", errorMsg);
				req.setAttribute("groupBean", bean);
				req.setAttribute("userName", bean.getListUserName());
				req.setAttribute("message", bean.getListMessage());
				req.setAttribute("userNo", bean.getListUserNo());
				req.setAttribute("bean", bean);
				req.getRequestDispatcher(direction).forward(req, res);
				return;
			}
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// SQL実行
		// メッセージ送信
			try {
				bean = model.send(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// SQL実行
		// メッセージ表示
		try {
			bean = model.output(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// メッセージ送信者が、グループ脱退者かの判定
		int sizeMsg = bean.getListMessage().size();
		int sizeOutFlag = bean.getListOutFlagUN().size();
		String setUN = "";
		for (int i = 0; i < sizeMsg; i++) {
			setUN = bean.getListUserName().get(i);
			for (int j = 0; j < sizeOutFlag; j++) {
				if (setUN.equals(bean.getListOutFlagUN().get(j))) {
					if (bean.getListOutFlag().get(j).equals("1")) {
						bean.getListUserName().set(i, "送信者不明");
					}
				}
			}
		}
		bean.setOutFlag1("送信者不明");
		// -------------------------------------------------------------

		req.setAttribute("groupBean", bean);
		req.setAttribute("userName", bean.getListUserName());
		req.setAttribute("message", bean.getListMessage());
		req.setAttribute("userNo", bean.getListUserNo());
		req.setAttribute("bean", bean);
		req.getRequestDispatcher(direction).forward(req, res);
	}

}
