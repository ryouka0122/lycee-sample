package net.coolblossom.lycee.core.args.exceptions;

/**
 * <b>Lycee用ランタイム例外</b>
 *
 * @category Exception
 * @author ryouka
 *
 */
public class LyceeRuntimeException extends RuntimeException {
	/** Default Serial Version UID */
	private static final long serialVersionUID = 1L;

	/** エラーメッセージ */
	protected String errorMessage;

	/**
	 * コンストラクタ
	 * @param t 引継ぎする例外
	 */
	public LyceeRuntimeException(final Throwable t) {
		super(t);
	}

	/**
	 * コンストラクタ
	 * @param message エラーメッセージ
	 */
	public LyceeRuntimeException(final String message) {
		super(message);
		errorMessage = message;
	}

	/**
	 * コンストラクタ
	 * @param message エラーメッセージ
	 * @param t 引継ぎする例外
	 */
	public LyceeRuntimeException(final String message, final Throwable t) {
		super(message, t);
		errorMessage = message;
	}

	@Override
	public String getMessage() {
		return errorMessage;
	}

}
