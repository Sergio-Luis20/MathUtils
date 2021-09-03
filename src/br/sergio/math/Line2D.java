package br.sergio.math;

import java.io.Serializable;

/**
 * Classe que representa uma reta no plano cartesiano.
 * @author Sergio Luis
 *
 */
public class Line2D implements Serializable {
	
	private static final long serialVersionUID = -7775489504409777491L;
	private double a, b, c, m, angle;
	
	/**
	 * Construtor por equação geral.
	 * @param a o coeficiente que acompanha x.
	 * @param b o coeficiente que acompanha y.
	 * @param c o termo independente.
	 */
	public Line2D(double a, double b, double c) {
		subConstructor(a, b, c);
	}
	
	/**
	 * Construtor por pontos.
	 * @param p1 o primeiro ponto.
	 * @param p2 o segundo ponto.
	 */
	public Line2D(Point p1, Point p2) {
		this(p1.getY() - p2.getY(), p2.getX() - p1.getX(), p1.getX() * p2.getY() - p2.getX() * p1.getY());
	}
	
	/**
	 * Retorna uma linha a partir de um ponto e de um dado coeficiente angular.
	 * @param p o ponto.
	 * @param m o coeficiente angular.
	 * @return a reta;
	 */
	public Line2D(Point p, double m) {
		if(Double.isInfinite(m)) {
			subConstructor(1, 0, -p.getX());
		} else {
			subConstructor(-m, 1, p.getX() * m - p.getY());
		}
	}
	
	private void subConstructor(double a, double b, double c) {
		if(a == 0 && b == 0) {
			throw new MathException("A reta não pode ter os coeficientes valendo 0.");
		}
		this.a = a;
		this.b = b;
		this.c = c;
		if(b == 0) {
			m = Double.POSITIVE_INFINITY;
		} else {
			m = -a / b;
		}
		angle = AdvancedMath.arctan(m, true);
	}
	
	/**
	 * Função f(x). Retorna o y para o x dado.
	 * @param x o x.
	 * @return o y.
	 */
	public double fx(double x) {
		if(Double.isInfinite(m)) {
			return -c / a;
		} else {
			return -(a * x + c) / b;
		}
	}
	
	/**
	 * Função f(y). Retorna o x para o y dado.
	 * @param y o y.
	 * @return o x.
	 */
	public double fy(double y) {
		if(a == 0) {
			return -c / b;
		} else {
			return -(b * y + c) / a;
		}
	}
	
	/**
	 * Retorna a menor distância entre esta reta e um determinado ponto.
	 * @param p o ponto com o qual este terá sua distância calculada.
	 * @return a distância.
	 */
	public double distance(Point p) {
		return AdvancedMath.abs(a * p.getX() + b * p.getY() + c) / AdvancedMath.hypotenuse(a, b);
	}
	
	/**
	 * Retorna a distância desta reta a uma outra.
	 * @param line a outra reta.
	 * @return a distância entre as duas retas. 0 se os coeficientes
	 * angulares forem diferentes, ou seja, não forem paralelas.
	 */
	public double distance(Line2D line) {
		if(m != line.m) {
			return 0;
		} else {
			return AdvancedMath.abs(c - line.c) / AdvancedMath.hypotenuse(a, b);
		}
	}
	
	/**
	 * @return o coeficiente do x.
	 */
	public double getA() {
		return a;
	}
	
	/**
	 * @return o coeficiente do y.
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
	 * Retorna o coeficiente angular desta reta. O coeficiente angular é a tangente do
	 * ângulo que a reta forma com a horizontal. Na equação reduzida y = ax + b, a é o
	 * coeficiente angular, enquanto que na equação geral ax + by + c = 0, o coeficiente
	 * angular é dado por -a / b, portanto, para retas completamente horizontais, é retornado
	 * 0, e para retas completamente verticais, é retornado Double.POSITIVE_INFINITY, ideia
	 * essa que só é válida dentro do conceito de limites, uma vez que a tangente não está
	 * definida para múltiplos ímpares de pi / 2.
	 * @return o coeficiente angular. 0 se a reta for horizontal; Double.POSITIVE_INFINITY
	 * se for vertical.
	 */
	public double getSlope() {
		return m;
	}
	
	/**
	 * Retorna o ângulo (em radianos) que esta reta forma com a horizontal. É sempre no intervalo
	 * (-pi / 2, pi / 2]. Este ângulo é nada mais, nada menos, que o arco tangente do coeficiente
	 * angular. Por esse motivo, uma vez que a tangente não está definida em múltiplos ímpares de
	 * pi / 2, este método só retorna pi / 2 dentro do conceito de limites.
	 * @return o ângulo que esta reta forma com a horizontal.
	 */
	public double getAngle() {
		return angle;
	}
}
