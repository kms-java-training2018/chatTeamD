package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.DirectMessageBean;

public class DirectMessageServlet extends HttpServlet {


	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		/*
		* 画面表示処理
		*/
		//データベースと接続
		Connection conn = null;
		String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
		String user = "DEV_TEAM_D";
		String dbPassword = "D_DEV_TEAM";

		DirectMessageBean bean = new DirectMessageBean();

		try {
			//JDBCドライバーのロード
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			//データベースへの接続作成
			conn = DriverManager.getConnection(url, user, dbPassword);
			Statement stmt = conn.createStatement();

			//(1)-1セッション確認
			//sessionスコープを使う下準備
			HttpSession session = req.getSession();

			//LoginServletでsessionスコープに入れた値が正しいか(入っているか)判断
			//まずはsessionスコープに入っている値を取得
			String userId = (String) session.getAttribute("userId");
			try {
				//SQLのSELECT文を準備
				String sql = "SELECT USER_NO FROM M_USER WHERE USER_ID='" + userId + "'";
				//SQLをDBに届けるPreparedStatementのインスタンスを取得
				PreparedStatement pStmt = conn.prepareStatement(sql);
				//ResultSetインスタンスにSELECT文の結果を格納する
				ResultSet result = pStmt.executeQuery();

				while (result.next()) {
					bean.setMyNo(result.getInt("USER_NO"));
				}

				//sessionスコープに正しい値が入っていない場合はログインページに戻す
			} catch (SQLException e) {
				bean.setErrorMsg("セッションがありません");
				session.invalidate();
				req.setAttribute("errorMsg", bean.getErrorMsg());
				req.getRequestDispatcher("/errorPage").forward(req, res);
			}
			//(1)-2パラメータチェック
			try {
				//パラメータのチェック
				//mainPage.jspで指定されたuserNoというパラメータを受け取り、変数に格納(データの降り口)
				bean.setUserNo(req.getParameter("userNo"));
				String sqlGetuserNo = "SELECT USER_NO FROM M_USER WHERE USER_NO = '" + bean.getUserNo() + "'";
				PreparedStatement pStmtGetuserNo = conn.prepareStatement(sqlGetuserNo);
				//(1)-3		チェックでエラーが発生した場合の処理
			} catch (SQLException e) {
				bean.setErrorMsg("セッションがありません。");
				session.invalidate();
				req.setAttribute("errorMsg", bean.getErrorMsg());
				req.getRequestDispatcher("/errorPage").forward(req, res);
			}

			//(2)会話情報取得処理
			//(2)-1会話情報取得
			//SQLのSELECT文を準備
			try {
				String sqlMes = "SELECT MESSAGE, USER_NAME FROM T_MESSAGE_INFO INNER JOIN M_USER ON T_MESSAGE_INFO.SEND_USER_NO = M_USER.USER_NO WHERE SEND_USER_NO = '" + bean.getUserNo() + "' AND TO_SEND_USER_NO = '" + bean.getMyNo() + "'";

				//SQLをDBに届けるPreparedStatementのインスタンスを取得
				PreparedStatement pStmtMes = conn.prepareStatement(sqlMes);
				//ResultSetインスタンスにSELECT文の結果を格納する
				ResultSet resultMes = pStmtMes.executeQuery();
				while (resultMes.next()) {
					bean.setMessage(resultMes.getString("MESSAGE"));
					bean.setUsername(resultMes.getString("USER_NAME"));

				}

			} catch (SQLException e) {
				System.out.println("会話情報取得できません。");
				session.invalidate();
				req.setAttribute("errorMsg", bean.getErrorMsg());
				req.getRequestDispatcher("/errorPage").forward(req, res);
			}

			session.setAttribute("message", bean.getMessage());
			session.setAttribute("username", bean.getUsername());


			req.getRequestDispatcher("/WEB-INF/jsp/directMessage.jsp").forward(req, res);









			/*
			* メッセージ送信処理
			*/
			//(1)パラメータチェック
			//directMessage.jspで指定されたsendMessageというパラメータを受け取り、変数に格納(データの降り口)
			String sendMessage = req.getParameter("sendMessage");
			//(1)-1入力値のチェック
			if (sendMessage.equals(null) || sendMessage.length() > 100) {
				System.out.println("パラメーターが不正");
				//エラーメッセージを表示し、メッセージ画面に遷移
				System.out.println("100字以内のメッセージを入力してください。");
				req.getRequestDispatcher("/directMessage.jsp").forward(req, res);
			}

			//会話情報登録処理

			//会話番号の自動採番処理
			//会話番号の最大値を持ってくるSQL文を送信する
			try {
			String sqlGetMax = "SELECT MAX(MESSAGE_NO) FROM  T_MESSAGE_INFO";
			//SQLをDBに届けるPreparedStatementのインスタンスを取得
			PreparedStatement pStmtGetMax = conn.prepareStatement(sqlGetMax);
			//ResultSetインスタンスにSELECT文の結果を格納する
			ResultSet resultMax = pStmtGetMax.executeQuery();
			while (resultMax.next()) {
				bean.setN(resultMax.getInt("MESSAGE_NO"));
			}
			}catch (SQLException e) {
				bean.setErrorMsg("会話情報の自動採番できません。");
				session.invalidate();
				req.setAttribute("errorMsg", bean.getErrorMsg());
				req.getRequestDispatcher("/errorPage").forward(req, res);
			}

			//会話番号の最大値+1を入れる変数を宣言
			int newMesNo = bean.getMyNo() + 1;

			try {
				//SQLのSELECT文を準備
				String sqlSendMes = "INSERT INTO T_MESSAGE_INFO(MESSAGE_NO, SEND_USER_NO, MESSAGE, TO_SEND_USER_NO,DELETE_FLAG, REGIST_DATE)VALUES('"
						+ newMesNo + "','" + bean.getMyNo() + "','" + sendMessage + "','" + bean.getUserNo() + "', 0, SYSDATE)";
				//SQLをDBに届けるPreparedStatementのインスタンスを取得
				PreparedStatement pStmtSendMes = conn.prepareStatement(sqlSendMes);

				//内容を登録できなかった場合、エラー画面に遷移する
			} catch (SQLException e) {
				bean.setErrorMsg("会話内容が登録できません。");
				e.printStackTrace();
				session.invalidate();
				req.setAttribute("errorMsg", bean.getErrorMsg());
				req.getRequestDispatcher("/errorPage").forward(req, res);
			}

			/*
			* メッセージ削除処理
			*/
			//確認ダイアログ表示処理

			//会話情報論理削除処理
			//SQLのSELECT文を準備

			//SQLをDBに届けるPreparedStatementのインスタンスを取得

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//SQLの接続は絶対に切断
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	//	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
	//
	//		req.getRequestDispatcher("/WEB-INF/jsp/directMessage.jsp").forward(req, res);
	//
	//	}
	//branch 'feature-chat-teamD' of https://github.com/kms-java-training2018/chatteamD
}
