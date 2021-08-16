package br.sergio.math;

/**
 * Classe que representa uma função polinomial do terceiro grau.
 * @author Sergio Luis
 *
 */
public class CubicFunction extends Polynomial {
	
	private static final long serialVersionUID = 8020826963395681621L;
	private double a, b, c, d;
	private Complex realRoot;
	private Complex plusRoot;
	private Complex minusRoot;
	
	/**
	 * Construtor. Faz uma função cúbica do tipo ax³ + bx² + cx + d.
	 * @param a o coeficiente de x³.
	 * @param b o coeficiente de x².
	 * @param c o coeficiente de x.
	 * @param d o termo independente.
	 * @throws MathException se a = 0.
	 */
	public CubicFunction(double a, double b, double c, double d) {
		super(a, b, c, d);
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		double p = c / a - AdvancedMath.pow(b, 2) / (3 * AdvancedMath.pow(a, 2));
		double q = d / a - b * c / (3 * AdvancedMath.pow(a, 2)) + 2 * AdvancedMath.pow(b, 3) / (27 * AdvancedMath.pow(a, 3));
		double delta = AdvancedMath.pow(q, 2) / 4 + AdvancedMath.pow(p, 3) / 27;
		Complex constant = new Complex(-b / (3 * a));
		Complex rootDelta = new Complex(delta).sqrt();
		Complex factor1 = new Complex(-0.5, AdvancedMath.sqrt(3) / 2);
		Complex factor2 = factor1.conjugate();
		Complex base1 = new Complex(-q / 2).add(rootDelta).cbrt();
		Complex base2 = new Complex(-q / 2).subtract(rootDelta).cbrt();
		realRoot = constant.add(base1).add(base2);
		plusRoot = constant.add(factor1.multiply(base1)).add(factor2.multiply(base2));
		minusRoot = constant.add(factor2.multiply(base1)).add(factor1.multiply(base2));
	}
	
	/**
	 * @return o coeficiente de x³.
	 */
	public double getA() {
		return a;
	}
	
	/**
	 * @return o coeficiente de x².
	 */
	public double getB() {
		return b;
	}
	
	/**
	 * @return o coeficiente de x.
	 */
	public double getC() {
		return c;
	}
	
	/**
	 * @return o termo independente.
	 */
	public double getD() {
		return d;
	}
	
	/**
	 * Funções cúbicas sempre têm ao menos 1 rais real; as
	 * demais podem ser ou não complexas. Este método retorna
	 * a raiz real.
	 * @return a raiz real.
	 */
	public Complex getRealRoot() {
		return realRoot;
	}
	
	/**
	 * Funções cúbicas sempre têm ao menos 1 raiz real; as
	 * demais podem ser ou não complexas. Se forem complexas,
	 * uma é conjugada da outra. Este método retorna a raiz no
	 * que tem o sinal positivo do imaginário se ela não for real.
	 * @return a raiz que tem o sinal positivo do imaginário
	 * se ela não for real.
	 */
	public Complex getPlusRoot() {
		return plusRoot;
	}
	
	/**
	 * Funções cúbicas sempre têm ao menos 1 raiz real; as
	 * demais podem ser ou não complexas. Se forem complexas,
	 * uma é conjugada da outra. Este método retorna a raiz no
	 * que tem o sinal negativo do imaginário se ela não for real.
	 * @return a raiz que tem o sinal negativo do imaginário
	 * se ela não for real.
	 */
	public Complex getMinusRoot() {
		return minusRoot;
	}
}
