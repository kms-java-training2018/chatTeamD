package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class DirectMessageServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
/*
* 画面表示処理
*/
		//データベースと接続
		Connection conn = null;
		try {
			//JDBCドライバーのロード
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			//データベースへの接続作成
			conn = DriverManager.getConnection("192.168.51.67","DEV_TEAM_D" ,"D_DEV_TEAM");

			//セッションの存在チェック
			//sessionスコープを使う下準備
			HttpSession session = req.getSession();

			//LoginServletでsessionスコープに入れた値が正しいか(入っているか)判断
	        //まずはsessionスコープに入っている値を取得
	        String userId = (String)session.getAttribute("userId");
	        String password = (String)session.getAttribute("password");

	     	//sessionスコープに正しい値が入っていない場合はログインページに戻す

	        	//1.まず、ID、Passwordの照合のために、正しいID、PasswordをDBから取得する
	        	//SQLのSELECT文を準備
	        	String sql = "SELECT USER_ID,PASSWORD,USER_NO FROM M_USER";
	        	//SQLをDBに届けるPreparedStatementのインスタンスを取得
	        	PreparedStatement pStmt = conn.prepareStatement(sql);
	        	//ResultSetインスタンスにSELECT文の結果を格納する
	        	ResultSet result = pStmt.executeQuery();
	        	//DBから出してきたID、Passwordを格納する変数を設定
	        	String db_userID = result.getString("USER_ID");
	        	String db_password = result.getString("PASSWORD");
	        	String db_userNo = result.getString("USER_NO");

	        	//2.Sessionスコープの中身とDBのID、Passwordが合っているか確認
	        	if(!db_userID.equals(userId) || !db_password.equals(password)) {
	                System.out.println("セッションがありません。");
	                /*
	                 * 下記はエラー画面への遷移ですが、エラー画面未作成のため、仮にforwardの先を/error.jspとしています。
	                 */
	                session.invalidate();
	                req.getRequestDispatcher("/error.jsp").forward(req, res);
	            }

	        	//パラメータのチェック
	        	//mainPage.jspで指定されたuserNoというパラメータを受け取り、変数に格納(データの降り口)
	            String userNo = req.getParameter("userNo");

	          //2.mainPage.jspから送られてきたuserNoとDBのuserNOを照合
	        	if(!db_userNo.equals(userNo)) {
	                System.out.println("パラメーターが不正");
	                /*
	                 * 下記はエラー画面への遷移ですが、エラー画面未作成のため、仮にforwardの先を/error.jspとしています。
	                 */
	                session.invalidate();
	                req.getRequestDispatcher("/error.jsp").forward(req, res);
	            }

	        	//会話情報取得処理
	        	//SQLのSELECT文を準備
	        	String sqlMes = "SELECT MESSAGE FROM T_MESSAGE_INFO WHERE USER_NO = " + userNo + "";
	        	//SQLをDBに届けるPreparedStatementのインスタンスを取得
	        	PreparedStatement pStmtMes = conn.prepareStatement(sqlMes);
	        	//ResultSetインスタンスにSELECT文の結果を格納する
	        	ResultSet resultMes = pStmtMes.executeQuery();
	        	String message = resultMes.getString("MESSAGE");

	        	//会話情報のレコードが取得できなかった場合
	        	if(message != null) {

	        		//変数に格納したデータをsessionスコープで保存
		            //messageという名前をそれぞれつける
		            session.setAttribute("message",resultMes);

		            req.getRequestDispatcher("/WEB-INF/jsp/directMessage.jsp").forward(req, res);

	            }else {
	            System.out.println("会話情報のレコードが取得できません");
                /*
                 * 下記はエラー画面への遷移ですが、エラー画面未作成のため、仮にforwardの先を/error.jspとしています。
                 */
	            session.invalidate();
                req.getRequestDispatcher("/error.jsp").forward(req, res);
	            }

		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			//SQLの接続は絶対に切断
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

/*
* メッセージ送信処理
*/
		//パラメータチェック
		//directMessage.jspで指定されたsendMessageというパラメータを受け取り、変数に格納(データの降り口)
        String sendMessage = req.getParameter("sendMessage");
        //入力値のチェック






	}


//	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
//
//		req.getRequestDispatcher("/WEB-INF/jsp/directMessage.jsp").forward(req, res);
//
//	}
 //branch 'feature-chat-teamD' of https://github.com/kms-java-training2018/chatteamD
}

