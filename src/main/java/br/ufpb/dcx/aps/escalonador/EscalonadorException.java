package br.ufpb.dcx.aps.escalonador;

public class EscalonadorException extends RuntimeException {

	private static final long serialVersionUID = -8704641854988675841L;

	public EscalonadorException() {
		super();
	}

	public EscalonadorException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EscalonadorException(String message, Throwable cause) {
		super(message, cause);
	}

	public EscalonadorException(String message) {
		super(message);
	}

	public EscalonadorException(Throwable cause) {
		super(cause);
	}

	
}
