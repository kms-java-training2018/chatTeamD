package bean;

public class MakeGroupBean {
	/**
	 * フィールド
	 */
	/** エラーメッセージ */
	private String errorMsg="";
	/** エラーフラグ */
	private int errorFlag = 0;
	/**
	 * メソッド
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public int getErrorFlag() {
		return errorFlag;
	}
	public void setErrorFlag(int errorFlag) {
		this.errorFlag = errorFlag;
	}
}
