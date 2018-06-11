package bean;


public class UserListBean {
	/**
	 * フィールド
	 */
	/** 表示する他ユーザーのユーザーNo */
	private int userNo;


	/** 表示する他ユーザーのユーザーID */
	private String userID;

	/** 表示する他ユーザーのユーザー名 */
	private String userName;

	/**	個人間の最新メッセージ */
	private String directMessage;

	/** エラーフラグ */
	private int errorFlag = 0;

	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDirectMessage() {
		return directMessage;
	}
	public void setDirectMessage(String directMessage) {
		this.directMessage = directMessage;
	}
	/**
	 * メソッド
	 */

	public int getErrorFlag() {
		return errorFlag;
	}
	public void setErrorFlag(int errorFlag) {
		this.errorFlag = errorFlag;
	}

}
