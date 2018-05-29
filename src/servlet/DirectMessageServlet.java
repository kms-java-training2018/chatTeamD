package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class DirectMessageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

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

		//パラメータチェック(セッションの存在チェック)
		//sessionスコープを使う下準備
		HttpSession session = req.getSession();

		//LoginServletでsessionスコープに入れた値が正しいか(入っているか)判断
        //まずはsessionスコープに入っている値を取得
        String userId = (String)session.getAttribute("userId");
        String password = (String)session.getAttribute("password");

     	//sessionスコープに正しい値が入っていない場合はログインページに戻す



		req.getRequestDispatcher("/WEB-INF/jsp/directMessage.jsp").forward(req, res);



	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		req.getRequestDispatcher("/WEB-INF/jsp/directMessage.jsp").forward(req, res);

	}
}
