package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MakeGroupBean;
import bean.SessionBean;
import bean.UserListBean;
import model.GetUserListModel;
import model.MakeGroupModel;

public class MakeGroupServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// グループ作成ページに移動する処理
		// 初期化
		GetUserListModel model = new GetUserListModel();
		ArrayList<UserListBean> userListBeanList = new ArrayList<>();
		String direction = "/WEB-INF/jsp/makeGroup.jsp";
		/**
		 * 1)パラメータチェック
		 */
		HttpSession session = req.getSession();
		SessionBean sesBean = (SessionBean) session.getAttribute("session");

		if (session == null || session.getAttribute("userId") == null) {
			// セッション情報なし
			// 行き先をエラーページに
			direction = "/errorPage";
			req.setAttribute("errorMsg", "セッション情報が無効です");
		} else {
			/**
			 * 1)会員一覧取得処理
			 */
			String sesUserNo = sesBean.getUserNo();
			try {
				userListBeanList = model.getUserList(sesUserNo);
			} catch (Exception e) {
				// 諸々のエラーはここに来る
				e.printStackTrace();
				// エラーはいてるのでuserListBeanList初期化してエラー情報入れる
				userListBeanList.clear();
				// エラー情報入れたbeanだけセット
				UserListBean ulBean = new UserListBean();
				ulBean.setErrorFlag(1);
				userListBeanList.add(ulBean);
			}
			if (userListBeanList.isEmpty() || userListBeanList.get(0).getErrorFlag() == 1) {
				// 途中でエラーはいている場合
				// エラーメッセージ送りつつ行き先をエラーページに
				direction = "/errorPage";
				req.setAttribute("errorMsg", "DB接続に失敗しました");
			} else {
				// 正常に一覧取得できた場合
				// リクエストに送る
				req.setAttribute("bean", userListBeanList);
				// GMタブから遷移したという情報をsessionに加える
				session.setAttribute("from", "GMからきた");
			}
		}
		// 出力
		req.getRequestDispatcher(direction).forward(req, res);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// グループ作成を行いメインページに移動する処理
		// 初期化
		MakeGroupBean bean = new MakeGroupBean();
		MakeGroupModel model = new MakeGroupModel();
		String direction = "/WEB-INF/jsp/makeGroup.jsp";
		/**
		 * 1)パラメータチェック
		 */
		req.setCharacterEncoding("UTF-8");
		// セッション情報確認
		HttpSession session = req.getSession();
		// 入力値変数に渡す
		String groupName = req.getParameter("groupName");
		String[] groupMemberNo = req.getParameterValues("userNo");
		if (session == null || session.getAttribute("userId") == null) {
			// セッション情報なし
			// 行き先をエラーページに
			direction = "/errorPage";
			req.setAttribute("errorMsg", "セッション情報が無効です");
		} else {
			// それ以外の場合処理続ける
			// ログインユーザー情報をセッションから取得
			SessionBean sesBean = (SessionBean) session.getAttribute("session");
			String sesUserNo = sesBean.getUserNo();

			try {
				/**
				 * 2)グループ登録処理
				 * 3)グループ会員登録処理
				 */
				bean = model.makeGroup(groupName, groupMemberNo, sesUserNo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (bean.getErrorFlag() == 1) {
				// SQLでエラーを吐いていた場合
				req.setAttribute("errorMsg", bean.getErrorMsg());
				// 行き先をエラーページに
				direction = "/errorPage";
				req.setAttribute("bean", bean);
			} else if (bean.getErrorFlag() == 2) {
				// グループ名が不正だった場合
				// 再度グループ作成ページ表示
				/**
				 * 会員一覧取得処理
				 */
				GetUserListModel ulModel = new GetUserListModel();
				ArrayList<UserListBean> userListBeanList = new ArrayList<>();
				try {
					userListBeanList = ulModel.getUserList(sesUserNo);
				} catch (Exception e) {
					// 諸々のエラーはここに来る
					e.printStackTrace();
					// エラーはいてるのでuserListBeanList初期化してエラー情報入れる
					userListBeanList.clear();
					// エラー情報入れたbeanだけセット
					UserListBean ulBean = new UserListBean();
					ulBean.setErrorFlag(1);
					userListBeanList.add(ulBean);
				}
				if (userListBeanList.isEmpty() || userListBeanList.get(0).getErrorFlag() == 1) {
					// 途中でエラーはいている場合
					// エラーメッセージ送りつつ行き先をエラーページに
					direction = "/errorPage";
					req.setAttribute("errorMsg", "DB接続に失敗しました");
				} else {
					// 正常に一覧取得できた場合
					// リクエストに送る
					req.setAttribute("bean", userListBeanList);
					// 行き先をグループ作成ページに
					direction = "/WEB-INF/jsp/makeGroup.jsp";
				}

			} else {
				// 正常にグループ作成できた場合
				// 行き先をメインページに
				direction = "/main";
				req.setAttribute("bean", bean);
				req.setAttribute("errorMsg", bean.getErrorMsg());
				// リロード防止用にリダイレクトで移動
				res.sendRedirect("/chat"+direction);
				return;
			}

		}
		// エラーなどリロードされても問題ない場合
		// 出力
		req.getRequestDispatcher(direction).forward(req, res);
	}
}
