package bean;

public class GroupMessageBean {
	/**
	 * フィールド
	 */
	/** 相手の会員番号を格納する変数を宣言 */
	private String userNo;

	/**ログインユーザーあての会話情報を格納する変数を宣言 */
	private String message;

	/**	相手ユーザーの名前を格納する変数を宣言 */
	private String userName;


	/**	エラーページ用の変数を宣言 */
	private String errorMsg ;

	private String userId;

	private int groupNo;

	private int messageNo;

	private int registUserNo;

	private int exitGroupNo;

	private String outFlagMessage;

	private String outFlagUserName;

	private String outFlagUserNo;

	/** グループ名 */
	private String groupName;

	/** グループ作成者 */
	private String authorName;

	/**
	 * メソッド
	 */
	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}

	public int getMessageNo() {
		return messageNo;
	}

	public void setMessageNo(int messageNo) {
		this.messageNo = messageNo;
	}

	public int getRegistUserNo() {
		return registUserNo;
	}

	public void setRegistUserNo(int registUserNo) {
		this.registUserNo = registUserNo;
	}

	public int getExitGroupNo() {
		return exitGroupNo;
	}

	public void setExitGroupNo(int exitGroupNo) {
		this.exitGroupNo = exitGroupNo;
	}

	public String getOutFlagMessage() {
		return outFlagMessage;
	}

	public void setOutFlagMessage(String outFlagMessage) {
		this.outFlagMessage = outFlagMessage;
	}

	public String getOutFlagUserNo() {
		return outFlagUserNo;
	}

	public void setOutFlagUserNo(String outFlagUserNo) {
		this.outFlagUserNo = outFlagUserNo;
	}

	public String getOutFlagUserName() {
		return outFlagUserName;
	}

	public void setOutFlagUserName(String outFlagUserName) {
		this.outFlagUserName = outFlagUserName;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}



}
