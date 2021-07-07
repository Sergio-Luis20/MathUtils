package br.sergio.math;

/**
 * Classe que representa uma função polinomial.
 * @author Sergio Luis
 *
 */
public class Polynomial {
	
	private double[] coefficients;
	
	/**
	 * Constrói um polinômio com os dados coeficientes.
	 * O último é sempre o termo independente, e o primeiro é sempre o de
	 * maior expoente de parte literal e é o que ditará o grau. Coloque
	 * 0 na posição de uma parte literal que não deseja que apareça. Exemplo:
	 * Polynomial(2, 0, 7) retorna 2x²+7. Qualquer elemento pode ser 0, com exceção
	 * do primeiro caso o array tenha mais de 1 elemento.
	 * @param coefficients os coeficientes do polinômio.
	 * @throws MathException se existe mais de 1 elemento no array e o primeiro vale 0.
	 */
	public Polynomial(double... coefficients) {
		if(coefficients.length != 1 && coefficients[0] == 0) {
			throw new MathException("O coeficiente \"a\" não pode ser 0.");
		}
		this.coefficients = coefficients;
	}
	
	/**
	 * Obtém o coeficiente numa dada posição.
	 * @param index o índice do elemento.
	 * @return o coeficiente no índice fornecido.
	 * @throws MathException se o índice é menor que 0 ou maior ou igual à quantidade total
	 * de coeficientes.
	 */
	public double getCoef(int index) {
		if(index < 0 || index >= coefficients.length) {
			throw new MathException("Não existe coeficiente com índice " + index + ".");
		}
		return coefficients[index];
	}
	
	/**
	 * Define um novo coeficiente para uma dada posição.
	 * @param index a posição.
	 * @param coef o novo coeficiente.
	 * @throws MathException se o índice é menor que 0 ou maior ou igual à quantidade total
	 * de coeficientes ou o índice é 0, a quantidade de coeficientes é 1 e o novo coeficiente
	 * vale 0.
	 */
	public void setCoef(int index, double coef) {
		if(index < 0 || index >= coefficients.length) {
			throw new MathException("Não existe coeficiente com índice " + index + ".");
		} else if(index == 0 && coefficients.length == 1 && coef == 0) {
			throw new MathException("O coeficiente \"a\" não pode ser 0.");
		} else {
			coefficients[index] = coef;
		}
	}
	
	/**
	 * Retorna o f(x) para um dado x real considerando esta função polinomial.
	 * @param x um real qualquer. Se há apenas 1 coeficiente, é retornado 0.
	 * @return o f(x).
	 */
	public double f(double x) {
		double fx = 0;
		for(int i = 0; i < coefficients.length; i++) {
			int exponent = coefficients.length - i - 1;
			fx += coefficients[i] * Math.pow(x, exponent);
		}
		return fx;
	}
	
	/**
	 * Retorna o f(x) para um dado x complexo considerando esta função polinomial.
	 * @param x um complexo qualquer. Se há apenas 1 coeficiente, é retornado 0.
	 * @return o f(x).
	 */
	public Complex f(Complex x) {
		Complex fx = new Complex(0);
		for(int i = 0; i < coefficients.length; i++) {
			int exponent = coefficients.length - i - 1;
			fx = fx.add(new Complex(coefficients[i]).multiply(x.pow(new Complex(exponent))));
		}
		return fx;
	}
	
	/**
	 * @return o termo independente. É o último coeficiente do array fornecido
	 * pelo construtor.
	 */
	public double independentElement() {
		return coefficients[coefficients.length - 1];
	}
	
	/**
	 * @return o grau deste polinômio. Equivale ao expoente da parte liteal do
	 * primeiro coeficiente. Se há apenas 1 coeficiente, é retornado 0, uma vez que
	 * não existe parte literal.
	 */
	public int degree() {
		return coefficients.length - 1;
	}
	
	/**
	 * @return o array de coeficientes provido pelo construtor.
	 */
	public double[] getCoefficients() {
		return coefficients;
	}
	
	/**
	 * Verifica se um determinado ponto pertence a esta função polinomial. Equivale
	 * a verificar se o f do x fornecido equivale ao y dado. Por esse motivo, se este
	 * polinômio tem apenas 1 coeficiente, f(x) para todo x sempre retorna 0, e com isso
	 * este método retornará true se, e somente se, para a condição em questão, o y
	 * fornecido for 0.
	 * @param x a abscissa do ponto.
	 * @param y a ordenada do ponto.
	 * @return true se o ponto pertence a esta função, false caso contrário.
	 */
	public boolean hasPoint(double x, double y) {
		return f(x) == y;
	}
	
	/**
	 * Retorna a derivada deste polinômio, que por sua vez é um novo polinômio. A derivada de
	 * uma função é o coeficiente angular da reta tangente a um determinado ponto pertencente
	 * a essa função. Para que o ponto seja parametral, esse valor geralmente é dado na forma
	 * de uma outra função. No caso, a derivada de um polinômio é outro polinômio. Como a 
	 * derivada de uma constante é 0, se o total de coeficientes for 1, é retornado um novo
	 * polinômio também com quantidade total de coeficientes 1, porém esse valendo 0.
	 * @return a derivada deste polinômio.
	 */
	public Polynomial derivative() {
		if(coefficients.length == 1) {
			return new Polynomial(0);
		}
		double[] newCoefficients = new double[coefficients.length - 1];
		for(int i = 0; i < newCoefficients.length; i++) {
			newCoefficients[i] = (coefficients.length - i - 1) * coefficients[i];
		}
		return new Polynomial(newCoefficients);
	}
	
	public void multiplyBy(double factor) {
		for(int i = 0; i < coefficients.length; i++) {
			coefficients[i] *= factor;
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		if(o instanceof Polynomial) {
			Polynomial p = (Polynomial) o;
			if(coefficients.length != p.coefficients.length) {
				return false;
			}
			for(int i = 0; i < coefficients.length; i++) {
				if(coefficients[i] != p.coefficients[i]) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		double product = 1;
		double sum = 0;
		for(double coef : coefficients) {
			product *= coef;
			sum += coef;
		}
		double rq = Math.sqrt(product);
		return (int) (sum * rq);
	}
	
	@Override
	public String toString() {
		if(coefficients.length == 1) {
			String coefficient = String.valueOf(coefficients[0]);
			if(Math.integer(coefficients[0])) {
				coefficient = coefficient.substring(0, coefficient.indexOf("."));
			}
			return coefficient;
		}
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < coefficients.length; i++) {
			if(coefficients[i] == 0) {
				continue;
			}
			String literalPart;
			if(i == coefficients.length - 2) {
				literalPart = "x";
			} else if(i == coefficients.length - 1) {
				literalPart = "";
			} else {
				literalPart = "x" + Math.superscript(coefficients.length - i - 1);
			}
			String coefficient;
			if((i != coefficients.length - 1)) {
				if(coefficients[i] == 1) {
					coefficient = i == 0 ? "" : "+";
				} else if(coefficients[i] == -1) {
					coefficient = "-";
				} else {
					String sign = coefficients[i] > 0 ? (i == 0 ? "" : "+") : "";
					coefficient = sign + coefficients[i];
				}
			} else {
				String sign = coefficients[i] > 0 ? "+" : "";
				coefficient = sign + coefficients[i];
			}
			if(Math.integer(coefficients[i]) && coefficient.length() != 1 && !coefficient.isEmpty()) {
				coefficient = coefficient.substring(0, coefficient.indexOf("."));
			}
			sb.append(coefficient + literalPart);
		}
		return sb.toString();
	}
}
