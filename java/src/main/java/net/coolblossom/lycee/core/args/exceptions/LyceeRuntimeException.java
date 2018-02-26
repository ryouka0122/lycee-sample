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
	
	protected String message;

	public LyceeRuntimeException(Throwable t) {
		super(t);
	}

	public LyceeRuntimeException(String message) {
		super(message);
		this.message = message;
	}
	
	public LyceeRuntimeException(String message, Throwable t) {
		super(message, t);
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
	
}
