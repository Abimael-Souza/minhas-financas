package br.com.dev.minhasfinancas.exception;

public class AuthenticationError extends RuntimeException{

	
	private static final long serialVersionUID = 1L;

	public AuthenticationError(String msg) {
		super(msg);
	}
}
