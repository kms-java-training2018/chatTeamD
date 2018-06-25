package servlet;

import java.io.IOException;
import java.util.ArrayList;

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
		ArrayList<GroupMessageBean> list = new ArrayList<>();
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

		// -------------------------------------------------------------

		String sesUserNo = sesBean.getUserNo();
		bean.setMyNo(sesUserNo);

		if (req.getParameter("groupNo").equals("")) {
			direction = "/errorPage";
			req.setAttribute("errorMsg", "無効なグループです");
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}

		// -------------------------------------------------------------
		// beanへセット
		int groupNo = Integer.parseInt(req.getParameter("groupNo"));
		bean.setGroupNo(groupNo);
		req.setAttribute("groupBean", bean);
		bean.setUserNo(sesUserNo);
		bean.setOutFlagMessage("送信者不明");
		// -------------------------------------------------------------

		// グループナンバーが取得できない場合エラー
		if (groupNo == 0) {
			req.getRequestDispatcher("/errorPage").forward(req, res);
			return;
		}

		// -------------------------------------------------------------
		// SQL実行
		// 所属ユーザ取得
		try {
			bean = model.confirmation(bean);
		} catch (Exception e) {
			errorMsg = "DB接続に失敗しました ";
			req.setAttribute("errorMsg", errorMsg);
			direction = "/errorPage";
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}
		// -------------------------------------------------------------

		if (bean.getConMessage() != null) {
			// セッション情報なし
			// 行き先をエラーページに
			direction = "/errorPage";
			req.setAttribute("errorMsg", "グループに入っていません");
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}

		// -------------------------------------------------------------
		// SQL実行
		try {
			list = model.output(groupNo);
		} catch (Exception e) {
			errorMsg = "DB接続に失敗しました ";
			req.setAttribute("errorMsg", errorMsg);
			direction = "/errorPage";
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// SQL実行
		// メッセージ表示
		try {
			bean = model.groupName(bean);
		} catch (Exception e) {
			errorMsg = "DB接続に失敗しました";
			req.setAttribute("errorMsg", errorMsg);
			direction = "/errorPage";
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}
		// -------------------------------------------------------------

		String errorMsg2 = req.getParameter("errorMsg");

		//	    if(req.getParameter("errorMsg") != null) {
		//
		//	    }

		session.setAttribute("from", "GMからきた");
		req.setAttribute("errorMsg", errorMsg2);
		req.setAttribute("bean", bean);
		req.setAttribute("groupBean", bean);
		req.setAttribute("list", list);
		req.getRequestDispatcher(direction).forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		// -------------------------------------------------------------
		// 初期化
		GroupMessageBean bean = new GroupMessageBean();
		GroupMessageModel model = new GroupMessageModel();
		String direction = "/WEB-INF/jsp/groupMessage.jsp";
		String errorMsg = "";
		int deleteCheck = 0;
		ArrayList<GroupMessageBean> list = new ArrayList<>();
		bean.setOutFlagMessage("送信者不明");
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

		String sesUserNo = sesBean.getUserNo();
		bean.setMyNo(sesUserNo);
		String message = req.getParameter("message");
		int groupNo = Integer.parseInt(req.getParameter("groupNo"));
		bean.setMessage(message);
		bean.setUserNo(sesUserNo);
		bean.setGroupNo(groupNo);


		int testnum = 0;

		// 削除処理
		if (req.getParameter("delete") != null) {
			testnum = Integer.parseInt(req.getParameter("deleteNo"));
			bean.setMessageNo(testnum);
			try {
				bean = model.delete(bean);
			} catch (Exception e) {
				errorMsg = "DB接続に失敗しました ";
				req.setAttribute("errorMsg", errorMsg);
				direction = "/errorPage";
				req.getRequestDispatcher(direction).forward(req, res);
				return;
			}

		}else {


		// -------------------------------------------------------------
		// SQL実行
		try {
			list = model.output(groupNo);
		} catch (Exception e) {
			errorMsg = "DB接続に失敗しました ";
			req.setAttribute("errorMsg", errorMsg);
			direction = "/errorPage";
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// SQL実行
		// メッセージ表示
		try {
			bean = model.groupName(bean);
		} catch (Exception e) {
			errorMsg = "DB接続に失敗しました";
			req.setAttribute("errorMsg", errorMsg);
			direction = "/errorPage";
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}
		// -------------------------------------------------------------

		// 脱退処理
		if (req.getParameter("exit") != null) {

			int exitGroupNo = Integer.parseInt(req.getParameter("exit"));
			bean.setExitGroupNo(exitGroupNo);
			try {
				bean = model.author(bean);
			} catch (Exception e) {
				errorMsg = "DB接続に失敗しました ";
				req.setAttribute("errorMsg", errorMsg);
				direction = "/errorPage";
				req.getRequestDispatcher(direction).forward(req, res);
				return;
			}
			if (bean.getRegistUserNo() == Integer.parseInt(sesUserNo)) {
				errorMsg = "作成者は抜けれません";
				groupNo = Integer.parseInt(req.getParameter("exit"));
				bean.setUserNo(sesUserNo);
				bean.setGroupNo(groupNo);
				req.setAttribute("errorMsg", errorMsg);
				req.setAttribute("bean", bean);
				req.setAttribute("groupBean", bean);
				req.setAttribute("list", list);
				req.getRequestDispatcher(direction).forward(req, res);
				return;
			}
			bean.setUserNo(sesUserNo);
			try {
				bean = model.exit(bean);
			} catch (Exception e) {
				errorMsg = "DB接続に失敗しました ";
				req.setAttribute("errorMsg", errorMsg);
				direction = "/errorPage";
				req.getRequestDispatcher(direction).forward(req, res);
				return;
			}
			req.getRequestDispatcher("/main").forward(req, res);
			return;
		}

		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// SQL実行
		// グループ名、グループ作成者表示
		try {
			bean = model.groupName(bean);
		} catch (Exception e) {
			errorMsg = "DB接続に失敗しました ";
			req.setAttribute("errorMsg", errorMsg);
			direction = "/errorPage";
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}
		// -------------------------------------------------------------

		// 入力できる文字は100桁まで
		if (!message.equals("")) {
			if (message.equals(bean.getGroupName() + "へのメッセージ　　")) {
				errorMsg = "メッセージを入力してください";
				req.setAttribute("errorMsg", errorMsg);
				req.setAttribute("bean", bean);
				req.setAttribute("groupBean", bean);
				req.setAttribute("list", list);
				req.getRequestDispatcher(direction).forward(req, res);
				return;
			}
			byte[] byteCheck = message.getBytes();
			int messageBytes = byteCheck.length;
			if (messageBytes > 100) {
				errorMsg = "文字数が多すぎます";
				req.setAttribute("errorMsg", errorMsg);
				req.setAttribute("bean", bean);
				req.setAttribute("groupBean", bean);
				req.setAttribute("list", list);
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
				errorMsg = "DB接続に失敗しました ";
				req.setAttribute("errorMsg", errorMsg);
				direction = "/errorPage";
				req.getRequestDispatcher(direction).forward(req, res);
				return;
			}
		} else {
			errorMsg = "メッセージを入力してください。";

			req.setAttribute("errorMsg", errorMsg);
			req.setAttribute("bean", bean);
			req.setAttribute("groupBean", bean);
			req.setAttribute("list", list);
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// SQL実行
		// 所属ユーザ取得
		try {
			bean = model.confirmation(bean);
		} catch (Exception e) {
			errorMsg = "DB接続に失敗しました";
			req.setAttribute("errorMsg", errorMsg);
			direction = "/errorPage";
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}
		// -------------------------------------------------------------

		if (bean.getConMessage() != null) {
			// セッション情報なし
			// 行き先をエラーページに
			direction = "/errorPage";
			req.setAttribute("errorMsg", "グループに所属していません");
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}

		}
		// -------------------------------------------------------------
		// SQL実行
		try {
			list = model.output(groupNo);
		} catch (Exception e) {
			errorMsg = "DB接続に失敗しました ";
			req.setAttribute("errorMsg", errorMsg);
			direction = "/errorPage";
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// SQL実行
		// メッセージ表示
		try {
			bean = model.groupName(bean);
		} catch (Exception e) {
			errorMsg = "DB接続に失敗しました";
			req.setAttribute("errorMsg", errorMsg);
			direction = "/errorPage";
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}
		// -------------------------------------------------------------

		req.setAttribute("errorMsg", errorMsg);
		req.setAttribute("bean", bean);
		req.setAttribute("groupBean", bean);
		req.setAttribute("list", list);
		req.getRequestDispatcher(direction).forward(req, res);
	}

}