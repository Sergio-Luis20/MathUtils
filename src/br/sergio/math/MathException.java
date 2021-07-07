package br.sergio.math;

/**
 * Classe que formaliza todas (ou quase todas) as exceptions
 * do pacote br.sergio.math. Esta classe estende java.lang.RuntimeException.
 * @author Sergio Luis
 *
 */
public class MathException extends RuntimeException {
	
	private static final long serialVersionUID = -2210557336141235679L;
	
	/**
	 * Construtor padrão.
	 */
	public MathException() {
		super();
	}
	
	/**
	 * Construtor com mensagem.
	 * @param message a mensage.
	 */
	public MathException(String message) {
		super(message);
	}
	
	/**
	 * Construtor com causa.
	 * @param cause a causa.
	 */
	public MathException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Construtor com mensagem e causa.
	 * @param message a mensagem.
	 * @param cause a causa.
	 */
	public MathException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public static void minusOrEqualsToZero(double element, String name) {
		if(element <= 0) {
			throw new MathException(name + " não pode ser menor ou igual a 0.");
		}
	}
}
