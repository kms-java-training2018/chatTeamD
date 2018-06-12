package bean;

public class GroupListBean {
	/**
	 * フィールド
	 */
	/**	表示するグループNo */
	private int groupNo;

	/**	表示するグループ名 */
	private String groupName;

	/**	グループの最新メッセージ */
	private String groupMessage;

	/** グループに参加していない場合の文言 */
	private String groupNullMes = "";

	/** エラーフラグ */
	private int errorFlag;

	public int getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupMessage() {
		return groupMessage;
	}

	public void setGroupMessage(String groupMessage) {
		this.groupMessage = groupMessage;
	}

	/**
	 * メソッド
	 */

	public String getGroupNullMes() {
		return groupNullMes;
	}

	public void setGroupNullMes(String groupNullMes) {
		this.groupNullMes = groupNullMes;
	}

	public int getErrorFlag() {
		return errorFlag;
	}

	public void setErrorFlag(int errorFlag) {
		this.errorFlag = errorFlag;
	}

}
