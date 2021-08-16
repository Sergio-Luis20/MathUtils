package br.sergio.math;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um número complexo no formato a + bi.
 * Esta classe estende java.lang.Number e implementa java.io.Serializable
 * e java.lang.Comparable.
 * @author Sergio Luis
 *
 */
public class Complex extends Number implements Serializable, Comparable<Complex> {
	
	/**
	 * Constante imaginária i. Representa a raiz quadrada principal do complexo -1.
	 * Esta constante é tal que se elevada ao quadrado (dentro das mecânicas desta
	 * classe), retorna -1. Pode também ser escrita como 0 + 1i.
	 */
	public static transient final Complex I;
	private static final long serialVersionUID = -993600286046044180L;
	private double real;
	private double imaginary;
	private double argument;
	private double modulus;
	private Vector vector;
	
	/**
	 * Construtor.
	 * @param real a parte real.
	 * @param imaginary a parte imaginária.
	 */
	public Complex(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
		setValues();
	}
	
	/**
	 * Construtor. Neste, por padrão a parte imaginária é 0, resultando num número real.
	 * @param real a parte real.
	 */
	public Complex(double real) {
		this(real, 0);
	}
	
	/**
	 * Operação soma. (a + bi) + (c + di).
	 * @param c o complexo com o qual este será somado.
	 * @return o complexo soma.
	 */
	public Complex add(Complex c) {
		return new Complex(real + c.real, imaginary + c.imaginary);
	}
	
	/**
	 * Operação subtração. (a + bi) - (c + di).
	 * @param c o complexo subtraendo deste.
	 * @return o complexo diferença.
	 */
	public Complex subtract(Complex c) {
		return new Complex(real - c.real, imaginary - c.imaginary);
	}
	
	/**
	 * Operação multiplicação. (a + bi) * (c + di).
	 * @param c o complexo que se multiplicará com este.
	 * @return o complexo produto.
	 */
	public Complex multiply(Complex c) {
		double r = real * c.real - imaginary * c.imaginary;
		double i = real * c.imaginary + imaginary * c.real;
		return new Complex(r, i);
	}
	
	/**
	 * Operação divisão. (a + bi) / (c + di).
	 * @param c o complexo divisor deste.
	 * @return o complexo quociente.
	 * @throws MathException se c for 0.
	 */
	public Complex divide(Complex c) {
		double denom = AdvancedMath.pow(c.real, 2) + AdvancedMath.pow(c.imaginary, 2);
		if(denom == 0) {
			throw new MathException("O complexo divisor não pode ser 0.");
		}
		double r = (real * c.real + imaginary * c.imaginary) / denom;
		double i = (imaginary * c.real - real * c.imaginary) / denom;
		return new Complex(r, i);
	}
	
	/**
	 * Raiz quadrada.
	 * @return a raiz quadrada deste número complexo.
	 */
	public Complex sqrt() {
		return pow(new Complex(0.5));
	}
	
	/**
	 * Raiz cúbica.
	 * @return a raiz cúbica deste número complexo.
	 */
	public Complex cbrt() {
		if(isReal() && real < 0) {
			return new Complex(-AdvancedMath.cbrt(-real));
		} else {
			return pow(new Complex(1.0 / 3.0));
		}
	}
	
	/**
	 * Potenciação.
	 * @param exp o expoente.
	 * @return este complexo elevado ao expoente fornecido.
	 */
	public Complex pow(Complex exp) {
		if(modulus == 0) {
			return new Complex(0);
		}
		double theta = exp.real * argument + exp.imaginary * AdvancedMath.ln(modulus);
		double factor = AdvancedMath.pow(modulus, exp.real) * AdvancedMath.pow(AdvancedMath.E, -exp.imaginary * argument);
		double real = AdvancedMath.cos(theta, true) * factor;
		double imaginary = AdvancedMath.sin(theta, true) * factor;
		return new Complex(real, imaginary);
	}
	
	/**
	 * O logaritmo deste complexo na base dada. Importante: não é recomendável utilizar os métodos
	 * de logaritmo desta classe (ainda). Dependendo de alguns casos, podem retornar valores errados.
	 * O método de logaritmo natural (ln) funciona corretamente, mas por não ser uma função injetora,
	 * pode não dar um resultado esperado em alguns casos.
	 * @param base o complexo base.
	 * @return o logaritmo deste complexo na base dada.
	 */
	public Complex log(Complex base) {
		Complex a = ln();
		Complex b = base.ln();
		return a.divide(b);
	}
	
	/**
	 * O logaritmo deste complexo na base 10. Importante: não é recomendável utilizar os métodos
	 * de logaritmo desta classe (ainda). Dependendo de alguns casos, podem retornar valores errados.
	 * O método de logaritmo natural (ln) funciona corretamente, mas por não ser uma função injetora,
	 * pode não dar um resultado esperado em alguns casos.
	 * @return o logaritmo deste complexo na base 10.
	 */
	public Complex log10() {
		return log(new Complex(10));
	}
	
	/**
	 * O logaritmo natural deste complexo. Importante: não é recomendável utilizar os métodos
	 * de logaritmo desta classe (ainda). Dependendo de alguns casos, podem retornar valores errados.
	 * Este método é a exceção, ele funciona (quase) corretamente. Por não ser uma função injetora,
	 * pode não dar um resultado esperado em alguns casos.
	 * @return o logaritmo natural deste complexo.
	 */
	public Complex ln() {
		return new Complex(AdvancedMath.ln(modulus), argument);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o != null && o instanceof Complex c) {
			return real == c.real && imaginary == c.imaginary;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		Vector u = new Vector(real, imaginary, modulus);
		Vector v = new Vector(real, imaginary, argument);
		return (int) u.crossProduct(v).getMagnitude();
	}
	
	@Override
	public String toString() {
		String value;
		if(real == 0 && imaginary == 0) {
			value = "0";
		} else {
			String realPart, imaginaryPart;
			if(AdvancedMath.integer(real)) {
				realPart = real == 0 ? "" : "" + (long) real;
			} else {
				realPart = real == 0 ? "" : "" + real;
			}
			if(AdvancedMath.integer(imaginary)) {
				imaginaryPart = imaginary == 0 ? "" : (long) imaginary + "i";
			} else {
				imaginaryPart = imaginary == 0 ? "" : imaginary + "i";
			}
			if(real != 0 && imaginary > 0) {
				if(imaginary == 1) {
					value = realPart + "+i";
				} else {
					value = realPart + "+" + imaginaryPart;
				}
			} else if(real == 0 && imaginary > 0) {
				if(imaginary == 1) {
					value = "i";
				} else {
					value = imaginaryPart;
				}
			} else {
				if(imaginary == -1) {
					value = realPart + "-i";
				} else {
					value = realPart + imaginaryPart;
				}
			}
		}
		return value;
	}
	
	@Override
	public int compareTo(Complex c) {
		if(real == c.real) {
			return (int) (imaginary - c.imaginary);
		} else {
			return (int) (real - c.real);
		}
	}
	
	/**
	 * Diz se este complexo pertence aos reais. Equivale a verificar se a parte
	 * imaginária é 0.
	 * @return true se for real, false caso contrário.
	 */
	public boolean isReal() {
		return imaginary == 0;
	}
	
	/**
	 * Método que faz o parsing de um complexo em formato String para um objeto Complex.
	 * A String deve obrigatoriamente estar nos mesmos moldes da retornada pelo método toString().
	 * Em outras palavras, deve estar sempre no formato a+bi, com a parte bi sendo nulificável se b = 0
	 * e b omitível se b = 1 ou b = -1, preservando apenas o sinal. Em caso de String nula ou vazia, é
	 * considerado o valor "0". Exemplos funcionais: -1+3i, 2.4-7.88i, 85+i, i, -i, -8i, 4i, 5, 41.12...
	 * @param value o número complexo em String.
	 * @return o objeto Complex que representa o que foi fornecido em String.
	 */
	public static Complex valueOf(String value) {
		List<Integer> indexes = new ArrayList<>();
		if(value == null || value.isEmpty()) {
			value = "0";
		}
		if(value.contains(" ")) {
			value = value.replaceAll(" ", "");
		}
		for(int i = 0; i < value.length(); i++) {
			if(value.charAt(i) == '+' || value.charAt(i) == '-') {
				indexes.add(i);
			}
		}
		if(value.contains("i")) {
			if(value.equals("i") || value.equals("+i")) {
				return I;
			} else if(value.equals("-i")) {
				return new Complex(0, -1);
			} else if((indexes.size() == 1 && indexes.get(0) == 0 && value.length() > 2) || indexes.size() == 0) {
				double imaginary = Double.valueOf(value.substring(0, value.length() - 1));
				return new Complex(0, imaginary);
			} else if(indexes.size() == 1 && indexes.get(0) != 0) {
				double real = Double.valueOf(value.substring(0, indexes.get(0)));
				double imaginary;
				if(value.charAt(indexes.get(0) + 1) == 'i') {
					imaginary = value.charAt(indexes.get(0)) == '-' ? -1 : 1;
				} else {
					imaginary = Double.valueOf(value.substring(indexes.get(0), value.length() - 1));
				}
				return new Complex(real, imaginary);
			} else if(indexes.size() == 2) {
				double real = Double.valueOf(value.substring(0, indexes.get(1)));
				double imaginary;
				if(value.charAt(indexes.get(1) + 1) == 'i') {
					imaginary = value.charAt(indexes.get(1)) == '-' ? -1 : 1;
				} else {
					imaginary = Double.valueOf(value.substring(indexes.get(1), value.length() - 1));
				}
				return new Complex(real, imaginary);
			} else {
				throw new MathException("Valor inaceitável: " + value + ".");
			}
		} else {
			double real = Double.valueOf(value);
			return new Complex(real, 0);
		}
	}
	
	private void setValues() {
		vector = new Vector(real, imaginary);
		modulus = vector.getMagnitude();
		argument = Vector.VERSOR_I.angle(vector) * (imaginary >= 0 ? 1 : -1);
	}
	
	/**
	 * @return a parte real.
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Define uma nova parte real.
	 * @param real a nova parte real.
	 */
	public void setReal(double real) {
		this.real = real;
		setValues();
	}
	
	/**
	 * @return a parte imaginária.
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Define uma nova parte imaginária.
	 * @param imaginary a nova parte imaginária.
	 */
	public void setImaginary(double imaginary) {
		this.imaginary = imaginary;
		setValues();
	}
	
	/**
	 * @return o argumento deste complexo. O argumento é nada mais nada menos que o ângulo
	 * que a reta constituída pela origem e pelo par ordenado (a, b) (sendo a a parte real
	 * e b a parte imaginária) formam com a reta real no plano complexo.
	 */
	public double getArgument() {
		return argument;
	}
	
	/**
	 * @return o módulo deste complexo. O módulo é a distância da origem até o ponto (a, b)
	 * (sendo a a parte real e b a parte imaginária) no plano complexo. É sempre positivo.
	 */
	public double getModulus() {
		return modulus;
	}
	
	/**
	 * @return o conjugado deste complexo. O conjugado de um número complexo é nada mais, nada
	 * menos, que um novo complexo com a mesma parte real do original, porém com a parte imaginária
	 * multiplicada por -1, isto é: conjugado(a + bi) = a - bi.
	 */
	public Complex conjugate() {
		return new Complex(real, -imaginary);
	}
	
	@Override
	public int intValue() {
		return (int) real;
	}
	
	@Override
	public long longValue() {
		return (long) real;
	}
	
	@Override
	public float floatValue() {
		return (float) real;
	}
	
	@Override
	public double doubleValue() {
		return real;
	}
	
	static {
		I = new Complex(0, 1);
	}
}
