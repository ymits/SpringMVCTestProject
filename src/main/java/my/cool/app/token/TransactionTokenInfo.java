package my.cool.app.token;

/**
 * トランザクショントークンの設定情報
 * 
 * @author mitsui0273
 *
 */
public class TransactionTokenInfo {
	/** トークン名称 */
	private String tokenName;
	/** トークン区分 */
	private TransactionTokenType tokenType;

	public TransactionTokenInfo(String tokenName, TransactionTokenType tokenType) {
		super();
		this.tokenName = tokenName;
		this.tokenType = tokenType;
	}

	public String getTokenName() {
		return tokenName;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	public TransactionTokenType getTokenType() {
		return tokenType;
	}

	public void setTokenType(TransactionTokenType tokenType) {
		this.tokenType = tokenType;
	}

	@Override
	public String toString() {
		return "TransactionTokenInfo [tokenName=" + tokenName
				+ ", transitionType=" + tokenType + "]";
	}
}
