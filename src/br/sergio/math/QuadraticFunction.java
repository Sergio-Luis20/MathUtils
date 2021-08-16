package br.sergio.math;

/**
 * Classe que representa uma função polinomial do segundo grau.
 * @author Sergio Luis
 *
 */
public class QuadraticFunction extends Polynomial {
	
	private static final long serialVersionUID = 1721688693867376600L;
	private double a, b, c;
	private double delta;
	private Point vertex;
	private Complex rootPlus, rootMinus;
	
	/**
	 * Construtor. Faz uma função quadrática do tipo ax² + bx + c.
	 * @param a o coeficiente do x².
	 * @param b o coeficiente do x.
	 * @param c o termo independente.
	 * @throws MathException se a = 0.
	 */
	public QuadraticFunction(double a, double b, double c) {
		super(a, b, c);
		this.a = a;
		this.b = b;
		this.c = c;
		delta = AdvancedMath.pow(b, 2) - 4 * a * c;
		vertex = new Point(-b / (2 * a), -delta / (4 * a));
		Complex minusB = new Complex(-b);
		Complex rootDelta = new Complex(delta).sqrt();
		Complex twoA = new Complex(2 * a);
		rootPlus = minusB.add(rootDelta).divide(twoA);
		rootMinus = minusB.subtract(rootDelta).divide(twoA);
	}
	
	/**
	 * @return o coeficiente do x².
	 */
	public double getA() {
		return a;
	}
	
	/**
	 * @return o coeficiente do x.
	 */
	public double getB() {
		return b;
	}
	
	/**
	 * @return o termo independente.
	 */
	public double getC() {
		return c;
	}
	
	/**
	 * @return o valor de delta. É sempre real e equivale
	 * a b² - 4ac.
	 */
	public double getDelta() {
		return delta;
	}
	
	/**
	 * @return o vértice da parábola. Ponto de mínimo se
	 * a &lt; 0 (concavidade voltada para cima); ponto de máximo
	 * se a &gt; 0 (concavidade voltada para baixo).
	 */
	public Point getVertex() {
		return vertex;
	}
	
	/**
	 * As raízes de uma função quadrática são dadas pela fórmula
	 * [-b +- sqrt(delta)] / 2a. Se delta &lt; 0, tem-se duas raízes
	 * reais e distintas. Se delta = 0, tem-se duas raízes reais e iguais.
	 * Se delta &gt; 0, tem-se duas raízes complexas (não reais) em que uma
	 * é o conjugado da outra.
	 * @return a raiz em que o sinal resultante da quebra do +- é o +.
	 */
	public Complex getRootPlus() {
		return rootPlus;
	}
	
	/**
	 * As raízes de uma função quadrática são dadas pela fórmula
	 * [-b +- sqrt(delta)] / 2a. Se delta &lt; 0, tem-se duas raízes
	 * reais e distintas. Se delta = 0, tem-se duas raízes reais e iguais.
	 * Se delta &gt; 0, tem-se duas raízes complexas (não reais) em que uma
	 * é o conjugado da outra.
	 * @return a raiz em que o sinal resultante da quebra do +- é o -.
	 */
	public Complex getRootMinus() {
		return rootMinus;
	}
}
