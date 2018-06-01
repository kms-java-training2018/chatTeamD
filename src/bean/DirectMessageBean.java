package bean;

public class DirectMessageBean {
	/**
	 * フィールド
	 */
	/** 相手の会員番号を格納する変数を宣言 */
	private int userNo;

	/** ログインユーザーの会員番号を格納する変数を宣言 */
	private int myNo;

	/**ログインユーザーあての会話情報を格納する変数を宣言 */
	private String message;

	/**	相手ユーザーの名前を格納する変数を宣言 */
	private String username;

	/**	会話番号の最大値を格納する変数を宣言 */
	private int n ;

	/**	エラーページ用の変数を宣言 */
	private String errorMsg ;



	/**
	 * メソッド
	 */
	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public int getMyNo() {
		return myNo;
	}

	public void setMyNo(int myNo) {
		this.myNo = myNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
