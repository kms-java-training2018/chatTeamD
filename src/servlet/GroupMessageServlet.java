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
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// Sessionの取得
		HttpSession session = req.getSession();
		SessionBean sesBean = (SessionBean) session.getAttribute("session");

		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// Sessionにユーザ情報がなければ、エラーページへ遷移
        if (session==null || session.getAttribute("userId")==null) {
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
			e.printStackTrace();
		}
		// -------------------------------------------------------------

		if(bean.getConMessage()!=null) {
            // セッション情報なし
            // 行き先をエラーページに
            direction = "/errorPage";
            req.setAttribute("errorMsg", "セッション情報が無効です");
            req.getRequestDispatcher(direction).forward(req, res);
            return;
		}


		// -------------------------------------------------------------
		// SQL実行
		try {
			list = model.output(groupNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// SQL実行
		// メッセージ表示
		try {
			bean = model.groupName(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// -------------------------------------------------------------
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
		ArrayList<GroupMessageBean> list = new ArrayList<>();
		// -------------------------------------------------------------

		req.setCharacterEncoding("UTF-8");

		// -------------------------------------------------------------
		// Sessionの取得
		HttpSession session = req.getSession();
		SessionBean sesBean = (SessionBean) session.getAttribute("session");

		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// Sessionにユーザ情報がなければ、エラーページへ遷移
        if (sesBean==null || session.getAttribute("userId")==null) {
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

		int testnum = 0;

		if (req.getParameter("delete") != null) {
			testnum = Integer.parseInt(req.getParameter("deleteNo"));
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
				errorMsg = "作成者は抜けれません";
				int groupNo = Integer.parseInt(req.getParameter("exit"));
				bean.setUserNo(sesUserNo);
				bean.setGroupNo(groupNo);
				// -------------------------------------------------------------
				// SQL実行
				// メッセージ表示
				try {
					list = model.output(groupNo);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// -------------------------------------------------------------
				req.setAttribute("errorMsg", errorMsg);
				req.setAttribute("groupBean", bean);
				req.setAttribute("list", list);
				req.setAttribute("bean", bean);
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
					list = model.output(groupNo);
				} catch (Exception e) {
					e.printStackTrace();
				}

				direction = "/erroPage";
				req.setAttribute("errorMsg", errorMsg);
				req.setAttribute("groupBean", bean);
				req.setAttribute("list", list);
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
		// 所属ユーザ取得
		try {
			bean = model.confirmation(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// -------------------------------------------------------------

		if(bean.getConMessage()!=null) {
            // セッション情報なし
            // 行き先をエラーページに
            direction = "/errorPage";
            req.setAttribute("errorMsg", "ここのえらー？");
            req.getRequestDispatcher(direction).forward(req, res);
            return;
		}

		// -------------------------------------------------------------
		// SQL実行
		// メッセージ、送信者名表示
		try {
			list = model.output(groupNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// SQL実行
		// グループ名、グループ作成者表示
		try {
			bean = model.groupName(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// 背景色指定
		int num = Integer.parseInt(req.getParameter("myColorNum"));
		bean.setMyColor(num);
		String cssMe = "";
		String cssOther = "";

		switch(num) {
		case 1:
			cssMe = "red";
			break;

		case 2:
			cssMe = "aquamarine";
			break;

		case 3:
			cssMe = "lightsteelblue";
			break;

		case 4:
			cssMe = "khaki";
			break;

		case 5:
			cssMe = "springgreen";
			break;

		case 6:
			cssMe = "orange";
			break;

		default:
			cssMe = "white";
			break;
		}

		num = Integer.parseInt(req.getParameter("otherColorNum"));
		bean.setOtherColor(num);

		switch(num) {
		case 1:
			cssOther = "red";
			break;

		case 2:
			cssOther = "aquamarine";
			break;

		case 3:
			cssOther = "lightsteelblue";
			break;

		case 4:
			cssOther = "khaki";
			break;

		case 5:
			cssOther = "springgreen";
			break;

		case 6:
			cssOther = "orange";
			break;

		default:
			cssOther = "white";
			break;
		}
		// -------------------------------------------------------------

		// -------------------------------------------------------------


		req.setAttribute("cssMe", cssMe);
		req.setAttribute("cssOther", cssOther);
		req.setAttribute("groupBean", bean);
		req.setAttribute("list", list);
		req.setAttribute("bean", bean);
		req.getRequestDispatcher(direction).forward(req, res);
	}

	// TODO 送信ボタン/削除ボタン押した後データ引継ぎ




}