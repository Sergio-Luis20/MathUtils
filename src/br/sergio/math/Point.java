package br.sergio.math;

import java.io.Serializable;

/**
 * Classe que representa um ponto no plano ou
 * espaço cartesiano.
 * @author Sergio Luis
 *
 */
public class Point implements Serializable {
	
	private static final long serialVersionUID = -6549017633827092829L;
	public static final Point ORIGIN;
	protected double x, y, z;
	
	/**
	 * Construtor de ponto no plano. Aqui,
	 * a variável z é 0.
	 * @param x a abscissa.
	 * @param y a ordenada.
	 */
	public Point(double x, double y) {
		this(x, y, 0);
	}
	
	/**
	 * Construtor de ponto no espaço.
	 * @param x a abscissa.
	 * @param y a ordenada.
	 * @param z a cota.
	 */
	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Cria uma nova instância de um ponto com as mesmas
	 * coordenadas do fornecido. Por esse motivo, ambos são
	 * iguais de acordo com a sobrecrição do método equals().
	 * @param p o ponto.
	 */
	public Point(Point p) {
		this(p.x, p.y, p.z);
	}
	
	/**
	 * Operação adição.
	 * @param p o ponto com o qual este será somado.
	 * @return o ponto soma.
	 */
	public Point add(Point p) {
		return new Point(x + p.x, y + p.y, z + p.z);
	}
	
	/**
	 * Operação subtração.
	 * @param p o ponto subtraendo.
	 * @return o ponto diferença.
	 */
	public Point subtract(Point p) {
		return new Point(x - p.x, y - p.y, z - p.z);
	}
	
	/**
	 * Operação multiplicação.
	 * @param p o ponto com o qual este será multiplicado.
	 * @return o ponto produto.
	 */
	public Point multiply(Point p) {
		return new Point(x * p.x, y * p.y, z * p.z);
	}
	
	/**
	 * Multiplicação por fator. Aqui, todas as coordenadas são
	 * multiplicadas por esse fator.
	 * @param factor o fator a partir do qual todas as coordenadas
	 * serão multiplicadas.
	 * @return o ponto produto.
	 */
	public Point multiply(double factor) {
		return new Point(x * factor, y * factor, z * factor);
	}
	
	/**
	 * @return o vetor com origem na origem do plano ou espaço cartesiano
	 * e estremidade neste ponto.
	 */
	public Vector toVector() {
		return new Vector(this);
	}
	
	/**
	 * Calcula a distância entre dois pontos no plano ou espaço cartesiano.
	 * @param p o segundo ponto.
	 * @return a distância.
	 */
	public double distance(Point p) {
		return AdvancedMath.sqrt(AdvancedMath.pow(x - p.x, 2) + AdvancedMath.pow(y - p.y, 2) + AdvancedMath.pow(z - p.z, 2));
	}
	
	/**
	 * Retorna um array contendo todas as abscissas de um conjunto de pontos dado.
	 * @param array o array de pontos.
	 * @return o array de abscissas.
	 */
	public static double[] getXArray(Point[] array) {
		double[] x = new double[array.length];
		for(int i = 0; i < array.length; i++) {
			x[i] = array[i].x;
		}
		return x;
	}
	
	/**
	 * Retorna um array contendo todas as ordenadas de um conjunto de pontos dado.
	 * @param array o array de pontos.
	 * @return o array de ordenadas.
	 */
	public static double[] getYArray(Point[] array) {
		double[] y = new double[array.length];
		for(int i = 0; i < array.length; i++) {
			y[i] = array[i].y;
		}
		return y;
	}
	
	/**
	 * Retorna um array contendo todas as cotas de um conjunto de pontos dado.
	 * @param array o array de pontos.
	 * @return o array de cotas.
	 */
	public static double[] getZArray(Point[] array) {
		double[] z = new double[array.length];
		for(int i = 0; i < array.length; i++) {
			z[i] = array[i].z;
		}
		return z;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		if(o instanceof Point p) {
			return x == p.x && y == p.y && z == p.z;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		double d1 = x * y - z;
		double d2 = x * z - y;
		double d3 = y * z - x;
		return (int) (d1 + d2 + d3);
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
	
	/**
	 * @return a abscissa.
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Define uma nova abscissa.
	 * @param x a nova abscissa.
	 */
	public void setX(double x) {
		if(this == ORIGIN) {
			return;
		}
		this.x = x;
	}
	
	/**
	 * @return a ordenada.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Define uma nova ordenada.
	 * @param y a nova ordenada.
	 */
	public void setY(double y) {
		if(this == ORIGIN) {
			return;
		}
		this.y = y;
	}
	
	/**
	 * @return a cota.
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * Define uma nova cota.
	 * @param z a nova cota.
	 */
	public void setZ(double z) {
		if(this == ORIGIN) {
			return;
		}
		this.z = z;
	}
	
	static {
		ORIGIN = new Point(0, 0, 0);
	}
}
