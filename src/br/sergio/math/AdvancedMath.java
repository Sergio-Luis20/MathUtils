package br.sergio.math;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Classe que acrescenta várias funcionalidades matemáticas
 * que o Java não possui por si próprio e delega as principais operações
 * que ele já possui à classe java.lang.Math.
 * @author Sergio Luis
 *
 */
public final class AdvancedMath {
	
	private AdvancedMath() {
		
	}
	
	/**
	 * Constante pi. Resultado do quociente do perímetro ou
	 * comprimento de uma circunferência pelo seu diâmetro.
	 * Aproximação mais comum: 3,14.
	 */
	public static final double PI = Math.PI;
	
	/**
	 * Constante e. Representa o número de Euler, às vezes
	 * também chamado de número neperiano. É a base dos logaritmos
	 * naturais ou neperianos e pode ser produzido de várias formas, uma
	 * delas é através de um dos limites fundamentais.
	 * Aproximação mais comum: 2,72.
	 */
	public static final double E = Math.E;
	
	/**
	 * Constante fi. Também chamada de número de ouro ou razão áurea.
	 * É um número bastante intrigante, uma vez que costuma aparecer em vários
	 * lugares. É uma das raízes do polinômio x² - x - 1, a outra é o seu conjugado.
	 * Equivale a [1 + sqrt(5)] / 2. Aproximação mais comum: 1,618.
	 */
	public static final double PHI = (1 + sqrt(5)) / 2;
	private static String[] superscript = {"⁰", "¹", "²", "³", "⁴", "⁵", "⁶", "⁷", "⁸", "⁹", "⁻"};
	private static String[] subscript = {"₀", "₁", "₂", "₃", "₄", "₅", "₆", "₇", "₈", "₉", "₋"};
	
	/**
	 * Retorna a raiz quadrada de um número, o qual obrigatoriamente deve
	 * ser um real positivo. Caso deseje ir aos complexos, utilize a classe
	 * Complex.
	 * @param positiveReal um valor real positivo.
	 * @return a raiz quadrada do referido valor.
	 * @throws MathException se o valor dado é menor que 0.
	 */
	public static double sqrt(double positiveReal) {
		if(positiveReal < 0) {
			throw new MathException("O valor dado deve ser um real positivo.");
		}
		return Math.sqrt(positiveReal);
	}
	
	/**
	 * Retorna a raiz cúbica de um número real qualquer.
	 * @param real um valor real.
	 * @return a referida raiz cúbica.
	 */
	public static double cbrt(double real) {
		return (real < 0 ? -1 : 1) * Math.cbrt(abs(real));
	}
	
	/**
	 * Retorna a raiz real de um determinado número.
	 * @param rooting o radicando.
	 * @param index o índice da raiz.
	 * @return a referida raiz.
	 * @throws MathException se o índice for 0,
	 * o radicando for negativo e o índice for inteiro par ou não inteiro ou
	 * o radicando for 0 e o índice um real negativo.
	 */
	public static double root(double rooting, double index) {
		if(index == 0) {
			throw new MathException("O índice da raiz não pode ser 0.");
		}
		if(rooting == 0 && index < 0) {
			throw new MathException("Se o radicando for 0, o índice não pode ser negativo para evitar divisão por 0.");
		}
		if(rooting < 0 && (!integer(index) || even((long) index))) {
			throw new MathException("A raiz não é real. Utilize a classe Complex para fazer este cálculo.");
		}
		return (rooting < 0 ? -1 : 1) * pow(abs(rooting), 1 / index);
	}
	
	/**
	 * Retorna o resultado de uma potenciação.
	 * @param base a base.
	 * @param exponent o expoente.
	 * @return o resultado da potenciação.
	 * @throws MathException se a base for 0 e o expoente menor ou
	 * igual a 0 ou a base negativa e o expoente não inteiro.
	 */
	public static double pow(double base, double exponent) {
		if(base == 0 && exponent <= 0) {
			throw new MathException("A base não pode ser 0 se o expoente for negativo ou o próprio 0.");
		}
		if(base < 0 && !integer(exponent)) {
			throw new MathException("A base não pode ser negativa se o expoente não for inteiro. Utilize a classe Complex para este caso.");
		}
		return Math.pow(base, exponent);
	}
	
	/**
	 * Retorna o resultado de uma potenciação com
	 * expoente racional.
	 * @param base a base.
	 * @param rational o expoente.
	 * @return o resultado da potenciação.
	 * @throws MathException se a base for 0 e o expoente menor ou
	 * igual a 0 ou a base menor que 0, o numerador ímpar e o denominador par.
	 */
	public static double pow(double base, Rational rational) {
		if(base == 0 && rational.getNum() <= 0) {
			throw new MathException("A base não pode ser 0 se o expoente for negativo.");
		}
		double n = Math.pow(base, rational.getNum());
		if(n < 0) {
			if(odd(rational.getDenom())) {
				return -Math.pow(-n, 1.0 / rational.getDenom());
			} else {
				throw new MathException("Impossível tirar raiz par de um número negativo. Utilize a classe Complex para este caso.");
			}
		} else {
			return Math.pow(n, 1.0 / rational.getDenom());
		}
	}
	
	/**
	 * Retorna o logaritmo com uma dada base e um dado logaritmando. O logaritmo é
	 * a função inversa à função exponencial. Representa um expoente que quando colocado
	 * numa base b, retorna um certo valor c, chamado logaritmando. Para permanecer nos
	 * reais, a base deve ser maior que 0 e diferente de 1 e o logaritmando deve ser maior
	 * que 0.
	 * @param base a base.
	 * @param logarithming o logaritmando.
	 * @return o logaritmo.
	 * @throws MathException se a base for menor ou igual a 0 ou igual a 1 ou
	 * o logaritmando for menor ou igual a 0.
	 */
	public static double log(double base, double logarithming) {
		if(base <= 0 || base == 1 || logarithming <= 0) {
			throw new MathException("A base não pode ser menor ou igual a 0 nem igual a 1 e o logaritmando não pode ser "
					+ "menor ou igual a 0.");
		}
		return ln(logarithming) / ln(base);
	}
	
	/**
	 * Retorna o logaritmo de base 10 para um dado logaritmando.
	 * @param logarithming o logaritmando.
	 * @return o logaritmo.
	 * @throws MathException se o logaritmando é menor ou igual a 0.
	 */
	public static double log10(double logarithming) {
		if(logarithming <= 0) {
			throw new MathException("O logaritmando não pode ser menor ou igual a 0.");
		}
		return Math.log10(logarithming);
	}
	
	/**
	 * Retorna o logaritmo natural ou neperiano para um dado logaritmando.
	 * Este logaritmo tem como base o número de Euler (a constante e).
	 * @param logarithming o logaritmando.
	 * @return o logaritmo.
	 * @throws MathException se o logaritmando for menor ou igual a 0.
	 */
	public static double ln(double logarithming) {
		if(logarithming <= 0) {
			throw new MathException("O logaritmando não pode ser menor ou igual a 0.");
		}
		return Math.log(logarithming);
	}
	
	/**
	 * Retorna o logaritmo binário (de base 2) para um dado logaritmando.
	 * @param logarithming o logaritmando.
	 * @return o logaritmo.
	 * @throws MathException se o logaritmando for menor ou igual a 0.
	 */
	public static double lb(double logarithming) {
		if(logarithming <= 0) {
			throw new MathException("O logaritmando não pode ser menor ou igual a 0.");
		}
		return log(2, logarithming);
	}
	
	/**
	 * Permutação. Basicamente a quantidade de formas que podemos alternar n objetos.
	 * Este método equivale a chamar factorial(n).
	 * @param natural a quantidade de objetos. Como o próprio nome diz, deve ser um número natural.
	 * @return a quantidade de permutações possíveis.
	 */
	public static long perm(int natural) {
		return factorial(natural);
	}
	
	/**
	 * Permutação com elementos repetidos. Ambos total e repeated devem ser números naturais.
	 * @param total A quantidade total de objetos.
	 * @param repeated A quantidade de objetos repetidos.
	 * @return a quantidade de permutações possíveis.
	 */
	public static long permWithRepet(int total, int repeated) {
		return factorial(total) / factorial(repeated);
	}
	
	/**
	 * Combinação. Representa a quantidade de formas que podemos escolher k objetos de um
	 * total de n. A permutação de objetos aqui não gera uma nova combinação.
	 * @param n a quantidade total de objetos.
	 * @param k a quantidade de objetos para se escolher a cada combinação.
	 * @return o número de combinações possíveis para n objetos tomados k a k.
	 */
	public static long comb(int n, int k) {
		return factorial(n) / (factorial(k) * factorial(n - k));
	}
	
	/**
	 * Combinação com repetição. Diz a quantidade de combinações que podemos obter de um total de n
	 * objetos tomados k a k, podendo que alguns se repitam.
	 * @param n a quantidade total de objetos.
	 * @param k a quantidade de objetos que devemos tomar a cada combinação.
	 * @return a quantidade de combinações.
	 */
	public static long combWithRepet(int n, int k) {
		return comb(n + k - 1, k);
	}
	
	/**
	 * Representa um arranjo de objetos. A quantidade de formas que temos de escolher k objetos
	 * de um total de n. A posição de um objeto em relação a outro gera um novo arranjo.
	 * @param n a quantidade total de objetos.
	 * @param k a qantidade de objetos que devemos escolher a cada arranjo.
	 * @return a quantidade de arranjos possíveis.
	 */
	public static long arr(int n, int k) {
		return factorial(n) / factorial(n - k);
	}
	
	/**
	 * Arranjo com repetição. Funciona da mesma forma que um arranjo comum, só que considerando que
	 * hajam objetos repetidos.
	 * @param n a quantidade total de objetos.
	 * @param k a quantidade de objetos que devemos escolher a cada arranjo.
	 * @return a quantidade total de arranjos.
	 * @throws MathException se n ou k for menor que 0 ou ambos iguais a 0.
	 */
	public static int arrWithRepet(int n, int k) {
		if(n < 0 || k < 0) {
			throw new MathException("n e k devem ser naturais.");
		} else if(n == 0 && k == 0) {
			throw new MathException("0 elevado a 0.");
		} else {
			return (int) pow(n, k);
		}
	}
	
	/**
	 * Fatorial. Aqui é utilizada a definição mais simplista:
	 * n! = n(n - 1)(n - 2)...3 * 2 * 1
	 * Por definição, 0! = 1.
	 * @param natural um valor pertencente aos naturais.
	 * @return o valor fatorial.
	 * @throws MathException se o valor for menor que 0.
	 */
	public static long factorial(int natural) {
		if(natural < 0) {
			throw new MathException("O fatorial só aceita números naturais.");
		} else if(natural == 0) {
			return 1;
		} else {
			int x = natural;
			for(int i = 1; i < natural; i++) {
				x *= (natural - i);
			}
			return x;
		}
	}
	
	/**
	 * Retorna os valores do triângulo de pascal para uma determinada linha.
	 * @param line o número da linha, com a contagem iniciando do 0.
	 * @return um array do tipo long contendo todos os valores da linha fornecida.
	 * @throws MathException se a linha fornecida for menor que 0.
	 */
	public static long[] pascalTriangle(int line) {
		if(line < 0) {
			throw new MathException("A linha não pode ser negativa.");
		} else {
			long[] coef = new long[line + 1];
			for(int i = 0; i < coef.length; i++) {
				coef[i] = comb(line, i);
			}
			return coef;
		}
	}
	
	/**
	 * Retorna o módulo de um número real. Se o valor for positivo, é retornado ele mesmo,
	 * se for negativo, é retornado o seu simétrico.
	 * @param value o valor.
	 * @return o módulo.
	 */
	public static double abs(double value) {
		return value >= 0 ? value : -value;
	}
	
	/**
	 * Transforma um número em base decimal em seu representante em binário.
	 * A entrada e a saída é feita em Strings para permitir todos os tamanhos possíveis para
	 * a maior parte dos números usuais. Se o número for negativo, é retornada a mesma String
	 * em binário, porém com um sinal de menos na frente, apenas. Exemplo: de 2 vai para 10, de
	 * -2 vai para -10.
	 * @param integerDecimal a representação em String de um número inteiro em base decimal.
	 * @return a representação em String desse número em base binária.
	 * @throws MathException se a String fornecida não for uma representação válida
	 * de um inteiro.
	 */
	public static String decimalToBinary(String integerDecimal) {
		boolean negative = integerDecimal.startsWith("-");
		if(negative) {
			integerDecimal = integerDecimal.substring(1);
		}
		BigInteger bi = null;
		try {
			bi = new BigInteger(integerDecimal);
		} catch(NumberFormatException e) {
			throw new MathException("O valor não se assemelha a um número inteiro.", e);
		}
		if(bi.toString().equals("0")) {
			return "0";
		}
		BigInteger two = new BigInteger("2");
		ArrayList<String> remains = new ArrayList<String>();
		while(!bi.toString().equals("0")) {
			remains.add(bi.mod(two).toString());
			bi = bi.divide(two);
		}
		String[] array = remains.toArray(new String[remains.size()]);
		StringBuffer sb = new StringBuffer();
		for(int i = array.length - 1; i >= 0; i--) {
			sb.append(array[i]);
		}
		return negative ? "-" + sb.toString() : sb.toString();
	}
	
	/**
	 * Transforma um número inteiro em base binária em seu representante em base decimal.
	 * A entrada e saída é feita em Strings para permitir a entrada de todos os números
	 * usuais possíveis. Valores negativos são simplesmente representados com um sinal
	 * de menos na frente. Por exemplo: Se a entrada for 10, a saída é 2, se for -10,
	 * a saída é -2.
	 * @param integerBinary um valor inteiro em base binária.
	 * @return a representação em String desse valor em base decimal.
	 */
	public static String binaryToDecimal(String integerBinary) {
		boolean negative = integerBinary.startsWith("-");
		if(negative) {
			integerBinary = integerBinary.substring(1);
		}
		String exceptionMessage = "O parâmetro deve ser um número inteiro em base binária.";
		String nonBinaries = "23456789";
		for(int i = 0; i < nonBinaries.length(); i++) {
			if(integerBinary.contains(String.valueOf(nonBinaries.charAt(i)))) {
				throw new MathException(exceptionMessage);
			}
		}
		BigInteger binary = null;
		try {
			binary = new BigInteger(integerBinary);
		} catch(NumberFormatException e) {
			throw new MathException(exceptionMessage);
		}
		String cons = binary.toString();
		BigInteger[] coef = new BigInteger[cons.length()];
		BigInteger[] exp = new BigInteger[cons.length()];
		for(int i = 0; i < cons.length(); i++) {
			coef[i] = new BigInteger(String.valueOf(cons.charAt(i)));
			exp[i] = new BigInteger(String.valueOf(cons.length() - 1 - i));
		}
		BigInteger bi = new BigInteger("0");
		BigInteger two = new BigInteger("2");
		for(int i = 0; i < cons.length(); i++) {
			bi = bi.add(coef[i].multiply(two.pow(exp[i].intValue())));
		}
		return negative ? "-" + bi.toString() : bi.toString();
	}
	
	/**
	 * Retorna o seno de um dado valor.
	 * @param value o ângulo cujo seno será retornado.
	 * @param radians true se o valor dado estiver em radianos, false caso esteja em graus.
	 * @return o seno.
	 */
	public static double sin(double value, boolean radians) {
		double rad = radians ? value : toRadians(value);
		if(multiple(rad, PI / 2)) {
			int k = (int) (2 * rad / PI);
			if(even(k)) {
				return 0;
			} else {
				int n = (k - 1) / 2;
				return even(n) ? 1 : -1;
			}
		}
		return Math.sin(rad);
	}
	
	/**
	 * Retorna o seno de um dado valor em radianos.
	 * @param value o valor.
	 * @return o seno.
	 */
	public static double sin(double value) {
		return sin(value, true);
	}
	
	/**
	 * Retorna o cosseno de um dado valor.
	 * @param value o ângulo cujo cosseno será retornado.
	 * @param radians true se o valor dado estiver em radiano, false caso esteja em graus.
	 * @return o cosseno.
	 */
	public static double cos(double value, boolean radians) {
		double rad = radians ? value : toRadians(value);
		if(multiple(rad, PI / 2)) {
			int k = (int) (2 * rad / PI);
			if(odd(k)) {
				return 0;
			} else {
				return k % 4 == 0 ? 1 : -1;
			}
		}
		return Math.cos(rad);
	}
	
	/**
	 * Retorna o cosseno de um dado valor em radianos.
	 * @param value o valor.
	 * @return o cosseno.
	 */
	public static double cos(double value) {
		return cos(value, true);
	}
	
	/**
	 * Retorna a tangente de um dado valor. A tangente é definida como sen(x) / cos(x) ou
	 * 1 / cotan(x) (inverso da cotangente). Por esse motivo, caso o cosseno (ou a cotangente)
	 * nesse ângulo valha 0, a tangente não está definida nele. Isso acontece em ângulos
	 * múltiplos ímpares de pi / 2.
	 * @param value o ângulo cuja tangente será retornada.
	 * @param radians true se o valor dado estiver em radiano, false caso esteja em graus.
	 * @return a tangente.
	 */
	public static double tan(double value, boolean radians) {
		double rad = radians ? value : toRadians(value);
		double sine = sin(rad, true);
		if(sine == 0) {
			return 0;
		} else if(sine == 1) {
			return Double.POSITIVE_INFINITY;
		} else if(sine == -1) {
			return Double.NEGATIVE_INFINITY;
		} else {
			return Math.tan(rad);
		}
	}
	
	/**
	 * Retorna a tangente de um dado valor em radianos.
	 * @param value o valor.
	 * @return a tangente.
	 */
	public static double tan(double value) {
		return tan(value, true);
	}
	
	/**
	 * Retorna a cotangente de um dado valor. A cotangente é definida como cos(x) / sen(x) ou
	 * 1 / tan(x) (inverso da tangente). Por esse motivo, caso o seno (ou a tangente) nesse
	 * ângulo valha 0, a cotangente não está definida nele. Isso acontece em ângulos múltiplos
	 * de pi.
	 * @param value o ângulo cuja cotangente será retornada.
	 * @param radians true se o valor dado estiver em radiano, false caso esteja em graus.
	 * @return a cotangente.
	 */
	public static double cotan(double value, boolean radians) {
		double rad = radians ? value : toRadians(value);
		double cosine = cos(rad, true);
		if(cosine == 0) {
			return 0;
		} else if(cosine == 1) {
			return Double.POSITIVE_INFINITY;
		} else if(cosine == -1) {
			return Double.NEGATIVE_INFINITY;
		} else {
			return 1 / tan(rad, true);
		}
	}
	
	/**
	 * Retorna a cotangente de um dado valor em radianos.
	 * @param value o valor.
	 * @return a cotangente.
	 */
	public static double cotan(double value) {
		return cotan(value, true);
	}
	
	/**
	 * Retorna a secante de um dado valor. A secante é definida como 1 / cos(x) (inverso do cosseno).
	 * Por esse motivo, caso o cosseno valha 0, a secante não está definida nesse ângulo.
	 * Isso ocorre quando o ângulo é múltiplo ímpar de pi / 2.
	 * @param value o ângulo cuja secante será retornada.
	 * @param radians true se o valor dado estiver em radiano, false caso esteja em graus.
	 * @return a secante.
	 */
	public static double sec(double value, boolean radians) {
		double rad = radians ? value : toRadians(value);
		double sine = sin(rad, true);
		if(sine == 1) {
			return Double.POSITIVE_INFINITY;
		} else if(sine == -1) {
			return Double.NEGATIVE_INFINITY;
		} else {
			return 1 / cos(rad, true);
		}
	}
	
	/**
	 * Retorna a secante de um dado valor em radianos.
	 * @param value o valor.
	 * @return a secante.
	 */
	public static double sec(double value) {
		return sec(value, true);
	}
	
	/**
	 * Retorna a cossecante de um dado valor. A cossecante é definida como 1 / sen(x) (inverso do seno).
	 * Por esse motivo, caso o seno valha 0, a cossecante não está definida nesse ângulo.
	 * Isso ocorre quando o ângulo é múltiplo de pi.
	 * @param value o ângulo cuja cossecante será retornada.
	 * @param radians true se o valor dado estiver em radiano, false caso esteja em graus.
	 * @return a cossecante.
	 */
	public static double cosec(double value, boolean radians) {
		double rad = radians ? value : toRadians(value);
		double cosine = cos(rad, true);
		if(cosine == 1) {
			return Double.POSITIVE_INFINITY;
		} else if(cosine == -1) {
			return Double.NEGATIVE_INFINITY;
		} else {
			return 1 / sin(rad, true);
		}
	}
	
	/**
	 * Retorna a cossecante de um dado valor em radianos.
	 * @param value o valor.
	 * @return a cossecante.
	 */
	public static double cosec(double value) {
		return cosec(value, true);
	}
	
	/**
	 * Transforma um ângulo em graus em seu correspondente em radianos.
	 * @param degrees o ângulo em graus.
	 * @return o ângulo em radianos.
	 */
	public static double toRadians(double degrees) {
		return Math.toRadians(degrees);
	}
	
	/**
	 * Transforma um ângulo em radianos em seu correspondente em graus.
	 * @param radians o ângulo em radianos.
	 * @return o ângulo em graus.
	 */
	public static double toDegrees(double radians) {
		return Math.toDegrees(radians);
	}
	
	/**
	 * Retorna o ângulo no intervalo [-pi / 2, pi / 2] cujo seno é o valor dado.
	 * Se o valor dado é em módulo maior que 1 ou igual a Double.NaN, o retorno é Double.NaN.
	 * @param value o seno.
	 * @param radians true se deseja que o resultado venha em radianos,
	 * false caso deseja que venha em graus.
	 * @return o ângulo.
	 */
	public static double arcsin(double value, boolean radians) {
		double arcsin = Math.asin(value);
		return radians ? arcsin : toDegrees(arcsin);
	}
	
	/**
	 * Retorna o arco seno em radianos de um dado valor.
	 * @param value o seno.
	 * @return o arco seno.
	 */
	public static double arcsin(double value) {
		return arcsin(value, true);
	}
	
	/**
	 * Retorna o ângulo no intervalo [0, pi] cujo cosseno é o valor dado.
	 * Se o valor dado é em módulo maior que 1 ou igual a Double.NaN, o retorno é Double.NaN.
	 * @param value o cosseno.
	 * @param radians true se deseja que o resultado venha em radianos,
	 * false caso deseja que venha em graus.
	 * @return o ângulo.
	 */
	public static double arccos(double value, boolean radians) {
		double arccos = Math.acos(value);
		return radians ? arccos : toDegrees(arccos);
	}
	
	/**
	 * Retorna o arco cosseno em radianos de um dado valor.
	 * @param value o cosseno.
	 * @return o arco cosseno.
	 */
	public static double arccos(double value) {
		return arccos(value, true);
	}
	
	/**
	 * Retorna o ângulo no intevalo [-pi / 2, pi / 2] cuja tangente é o valor dado.
	 * Em teoria matemática, isto não é possível, uma vez que para que -pi / 2 e pi / 2
	 * entrem no intervalo, a tangente teria de estar definida nesses ângulos. O que acontece
	 * de fato é que o limite da tangente quando o ângulo tende a um desses dois valores
	 * é infinito ou -infinito. Trabalhando em limites, isso é em teoria correto, apesar de
	 * na realidade não ser possível atingir o infinito, seja ele positivo ou negativo. Para
	 * entrada de valor Double.POSITIVE_INFINITY, é retornado pi / 2, e para Double.NEGATIVE_INFINITY
	 * é retornado -pi / 2. Para Double.NaN é retornado Double.NaN.
	 * @param value o valor da tangente.
	 * @param radians true se deseja que o resultado venha em radianos,
	 * false caso deseja que venha em graus.
	 * @return o ângulo.
	 */
	public static double arctan(double value, boolean radians) {
		double arctan = Math.atan(value);
		return radians ? arctan : toDegrees(arctan);
	}
	
	/**
	 * Retorna o arco tangente em radianos de um dado valor.
	 * @param value a tangente.
	 * @return o arco tangente.
	 */
	public static double arctan(double value) {
		return arctan(value, true);
	}
	
	/**
	 * Retorna o ângulo no intervalo [0, pi] cuja cotangente é o valor dado.
	 * Em teoria matemática, isto não é possível, uma vez que para que 0 e pi entrem
	 * no intervalo, a cotangente teria de estar definida nesses ângulos. O que acontece
	 * de fato é que o limite da cotangente quando o ângulo tende a um desses dois valores
	 * é infinito ou -infinito. Trabalhando em limites, isso é em teoria correto, apesar de
	 * na realidade não ser possível atingir o infinito, seja ele positivo ou negativo. Para
	 * entrada de valor Double.POSITIVE_INFINITY, é retornado 0, e para Double.NEGATIVE_INFINITY
	 * é retornado pi. Para Double.NaN é retornado Double.NaN.
	 * @param value o valor da cotangente.
	 * @param radians true se deseja que o resultado venha em radianos,
	 * false caso deseja que venha em graus.
	 * @return o ângulo.
	 */
	public static double arccotan(double value, boolean radians) {
		if(value == 0) {
			return PI / 2;
		} else if(value == Double.POSITIVE_INFINITY) {
			return 0;
		} else if(value == Double.NEGATIVE_INFINITY) {
			return PI;
		} else {
			double tan = 1 / value;
			double arctan = arctan(tan, true);
			double arccotan = arctan > 0 ? arctan : PI + arctan;
			return radians ? arccotan : toDegrees(arccotan);
		}
	}
	
	/**
	 * Retorna o arco cotangente em radianos de um dado valor.
	 * @param value a cotangente.
	 * @return o arco cotangente.
	 */
	public static double arccotan(double value) {
		return arccotan(value, true);
	}
	
	/**
	 * Retorna o ângulo no intervalo [0, pi] cuja secante é o valor dado.
	 * A secante não está definida quando o ângulo é múltiplo ímpar de pi / 2; por esse
	 * motivo, por convenção, para valores de entrada Double.POSITIVE_INFINITY ou
	 * Double.NEGATIVE_INFINITY, é retornado pi / 2. Caso o valor de entrada não
	 * pertença ao conjunto {x pertence R | x &gt; -1 ou x &lt; 1} ou seja equivalente a Double.NaN,
	 * é retornado Double.NaN.
	 * @param value o valor da secante.
	 * @param radians true se deseja que o resultado venha em radianos,
	 * false caso deseja que venha em graus.
	 * @return o ângulo.
	 */
	public static double arcsec(double value, boolean radians) {
		if(value == 1) {
			return 0;
		} else if(value == Double.POSITIVE_INFINITY || value == Double.NEGATIVE_INFINITY) {
			return PI / 2;
		} else if(value == -1) {
			return PI;
		} else {
			double cos = 1 / value;
			double arccos = arccos(cos, true);
			return radians ? arccos : toDegrees(arccos);
		}
	}
	
	/**
	 * Retorna o arco secante em radianos de um dado valor.
	 * @param value a secante.
	 * @return o arco secante.
	 */
	public static double arcsec(double value) {
		return arcsec(value, true);
	}
	
	/**
	 * Retorna o ângulo no intervalo [-pi / 2, pi / 2] cuja cossecante é o valor dado.
	 * A cossecante não está definida quando o ângulo é múltiplo de pi; por esse motivo,
	 * por convenção, para valores de entrada Double.POSITIVE_INFINITY ou Double.NEGATIVE_INFINITY,
	 * é retornado 0. Caso o valor de entrada não pertença ao conjunto {x percente R | x &gt; -1 ou x &lt; 1}
	 * ou seja equivalente a Double.NaN, é retornado Double.NaN.
	 * @param value o valor da cossecante.
	 * @param radians true se deseja que o resultado venha em radianos,
	 * false caso deseja que venha em graus.
	 * @return o ângulo.
	 */
	public static double arccosec(double value, boolean radians) {
		if(value == 1) {
			return PI / 2;
		} else if(value == Double.POSITIVE_INFINITY || value == Double.NEGATIVE_INFINITY) {
			return 0;
		} else if(value == -1) {
			return -PI / 2;
		} else {
			double sin = 1 / value;
			double arcsin = arcsin(sin, true);
			return radians ? arcsin : toDegrees(arcsin);
		}
	}
	
	/**
	 * Retorna o arco cossecante em radianos de um dado valor.
	 * @param value a cossecante.
	 * @return o arco cossecante.
	 */
	public static double arccosec(double value) {
		return arccosec(value, true);
	}
	
	/**
	 * Retorna o seno hiperbólico para um dado valor real.
	 * @param value o valor.
	 * @return o seno hiperbólico.
	 */
	public static double sinh(double value) {
		return (pow(E, value) - pow(E, -value)) / 2;
	}
	
	/**
	 * Retorna o cosseno hiperbólico para um dado valor real.
	 * @param value o valor.
	 * @return o cosseno hiperbólico.
	 */
	public static double cosh(double value) {
		return (pow(E, value) + pow(E, -value)) / 2;
	}
	
	/**
	 * Retorna a tangente hiperbólica para um dado valor real.
	 * @param value o valor.
	 * @return a tangente hiperbólica.
	 */
	public static double tanh(double value) {
		return (pow(E, value) - pow(E, -value)) / (pow(E, value) + pow(E, -value));
	}
	
	/**
	 * Retorna a cotangente hiperbólica para um dado valor real.
	 * @param value o valor.
	 * @return a cotangente hiperbólica.
	 */
	public static double cotanh(double value) {
		return (pow(E, value) + pow(E, -value)) / (pow(E, value) - pow(E, -value));
	}
	
	/**
	 * Retorna a secante hiperbólica para um dado valor real.
	 * @param value o valor.
	 * @return a secante hiperbólica.
	 */
	public static double sech(double value) {
		return 2 / (pow(E, value) + pow(E, -value));
	}
	
	/**
	 * Retorna a cossecante hiperbólica para um dado valor real.
	 * @param value o valor.
	 * @return a cossecante hiperbólica.
	 */
	public static double cosech(double value) {
		return 2 / (pow(E, value) - pow(E, -value));
	}
	
	/**
	 * Retorna o ângulo cujo seno hiperbólico é o valor dado. O domínio
	 * são os reais.
	 * @param value o seno hiperbólico.
	 * @return o ângulo.
	 */
	public static double arcsinh(double value) {
		return ln(value + sqrt(pow(value, 2) + 1));
	}
	
	/**
	 * Retorna o ângulo cujo cosseno hiperbólico é o valor dado. O domínio
	 * é o intervalo [1, +infinito).
	 * @param value o cosseno hiperbólico.
	 * @return o ângulo.
	 * @throws MathException se o valor encontra-se fora do domínio.
	 */
	public static double arccosh(double value) {
		if(value < 1) {
			throw new MathException("O valor do cosseno hiperbólico não pode ser menor que 1.");
		}
		return ln(value + sqrt(pow(value, 2) - 1));
	}
	
	/**
	 * Retorna o ângulo cuja tangente hiperbólica é o valor dado. O domínio
	 * é o intervalo (-1, 1).
	 * @param value a tangente hiperbólica.
	 * @return o ângulo.
	 * @throws MathException se o valor encontra-se fora do domínio.
	 */
	public static double arctanh(double value) {
		if(value <= -1 || value >= 1) {
			throw new MathException("O valor da tangente hiperbólica não pode ser menor ou igual a -1 ou maior ou igual a 1.");
		}
		return ln((1 + value) / (1 - value)) / 2;
	}
	
	/**
	 * Retorna o ângulo cuja cotangente hiperbólica é o valor dado. O domínio
	 * é a união dos intervalos (-infinito, -1) e (1, +infinito).
	 * @param value a cotangente hiperbólica.
	 * @return o ângulo.
	 * @throws MathException se o valor encontra-se fora do domínio.
	 */
	public static double arccotanh(double value) {
		if(value >= -1 || value <= 1) {
			throw new MathException("O valor da cotangente hiperbólica não pode estar entre -1 e 1, incluindo os próprios -1 e 1.");
		}
		return ln((value + 1) / (value - 1)) / 2;
	}
	
	/**
	 * Retorna o ângulo cuja secante hiperbólica é o valor dado. O domínio
	 * é o intervalo (0, 1].
	 * @param value a secante hiperbólica.
	 * @return o ângulo.
	 * @throws MathException se o valor encontra-se fora do domínio.
	 */
	public static double arcsech(double value) {
		if(value <= 0 || value > 1) {
			throw new MathException("O valor da secante hiperbólica não pode ser menor ou igual a 0 ou maior que 1.");
		}
		return ln((1 + sqrt(1 - pow(value, 2))) / value);
	}
	
	/**
	 * Retorna o ângulo cuja cossecante hiperbólica é o valor dado. O domínio
	 * são os reais - {0}.
	 * @param value a cossecante hiperbólica.
	 * @return o ângulo.
	 * @throws MathException se o valor encontra-se fora do domínio.
	 */
	public static double arccosech(double value) {
		if(value == 0) {
			throw new MathException("O valor da cossecante hiperbólica não pode ser 0.");
		}
		return ln((1 + sqrt(1 + pow(value, 2))) / value);
	}
	
	/**
	 * Função piso. Retorna o maior inteiro que não é maior que o valor dado.
	 * Exemplos: piso(1,7) = 1; piso(-3,8) = -4. Em outras palavras:
	 * "arredonda para baixo".
	 * @param value o valor.
	 * @return a imagem da função piso para o valor dado.
	 */
	public static double floor(double value) {
		return Math.floor(value);
	}
	
	/**
	 * Função teto. Retorna o menor inteiro que não é menor que o valor dado.
	 * Exemplos: teto(1,7) = 2; teto(-3,8) = -3. Em outras palavras:
	 * "arredonda para cima".
	 * @param value o valor.
	 * @return a imagem da função teto para o valor dado.
	 */
	public static double ceil(double value) {
		return Math.ceil(value);
	}
	
	/**
	 * Arredonda de forma geral para o inteiro mais próximo da seguinte forma:
	 * se a primeira casa da parte decimal for de 0 a 4, arredonda para baixo, se for
	 * se 5 a 9, arredonda para cima.
	 * @param value o valor.
	 * @return o valor arredondado.
	 */
	public static long round(double value) {
		return Math.round(value);
	}
	
	/**
	 * Arredonda um valor decimal para uma determinada quantidade de casas.
	 * De 0 a 4, arredonda para baixo; de 5 a 9, arredonda para cima.
	 * O número de casas deve estar no intervalo [0, 15].
	 * @param value o valor para arredondar.
	 * @param places o número de casas para o qual deseja-se arredondar.
	 * @return o valor arredondado.
	 * @throws MathException se o valor das casas não estiver no intervalo [0, 15].
	 */
	public static double round(double value, int places) {
		if(places < 0 || places > 15) {
			throw new MathException("As casas devem estar no intervalo fechado de 0 a 15.");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("#.");
		for(int i = 0; i < places; i++) {
			sb.append("#");
		}
		String format = sb.toString();
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator('.');
		DecimalFormat df = new DecimalFormat(format);
		String n = df.format(value);
		double rounded = Double.valueOf(n.replace(",", "."));
		return rounded;
	}
	
	/**
	 * Retorna a parte decimal de um número real.
	 * Exemplo: decimalPart(42,5786) retorna 5786.
	 * @param value o valor.
	 * @return a parte decimal do valor dado. Nota: o sinal não é preservado.
	 */
	public static long decimalPart(double value) {
		return Long.parseLong(String.valueOf(value).split("[.]")[1]);
	}
	
	/**
	 * Teorema de Pitágoras. Retorna o valor da hipotenusa de um triângulo
	 * retângulo para dois catetos dados. Apesar de logicamente inconsistente,
	 * esta função aceita valores nulos e negativos.
	 * @param catheter1 primeiro cateto.
	 * @param catheter2 segundo cateto.
	 * @return a hipotenusa.
	 */
	public static double hypotenuse(double catheter1, double catheter2) {
		return sqrt(pow(catheter1, 2) + pow(catheter2, 2));
	}
	
	/**
	 * Teorema de Pitágoras. Retorna o valor de um dos catetos de um triângulo
	 * retângulo para uma dada hipotenusa e outro cateto. Apesar de logicamente inconsistente,
	 * esta função aceita valores nulos e negativos.
	 * @param hypotenuse a hipotenusa.
	 * @param catheter o outro cateto.
	 * @return o cateto procurado.
	 * @throws MathException se o cateto fornecido for maior que a hipotenusa.
	 */
	public static double catheter(double hypotenuse, double catheter) {
		if(catheter > hypotenuse) {
			throw new MathException("O cateto não pode ser maior que a hipotenusa.");
		}
		return sqrt(pow(hypotenuse, 2) - pow(catheter, 2));
	}
	
	/**
	 * Superescrição. Um método não muito recomendável, uma vez que, dependendo da formatação,
	 * pode não funcionar corretamente. Ele transforma um valor inteiro em forma superescrita.
	 * Exemplo: superscript(21) retorna ²¹. Aceita valores negativos.
	 * @param value o valor.
	 * @return a representação em String do valor superescrito.
	 */
	public static String superscript(int value) {
		char[] array = String.valueOf(value).toCharArray();
		StringBuffer sb = new StringBuffer();
		if(array[0] == '-') {
			sb.append(superscript[10]);
			for(int i = 1; i < array.length; i++) {
				int index = Integer.parseInt(String.valueOf(array[i]));
				sb.append(superscript[index]);
			}
		} else {
			for(int i = 0; i < array.length; i++) {
				int index = Integer.parseInt(String.valueOf(array[i]));
				sb.append(superscript[index]);
			}
		}
		return sb.toString();
	}
	
	/**
	 * Sobrescrição. Um método não muito recomendável, uma vez que, dependendo da formatação,
	 * pode não funcionar corretamente. Ele transforma um valor inteiro em forma sobrescrita.
	 * Exemplo: subscript(21) retorna ₂₁. Aceita valores negativos.
	 * @param valor o valor.
	 * @return a representação em String do valor sobrescrito.
	 */
	public static String subscript(int valor) {
		char[] array = String.valueOf(valor).toCharArray();
		StringBuffer sb = new StringBuffer();
		if(array[0] == '-') {
			sb.append(subscript[10]);
			for(int i = 1; i < array.length; i++) {
				int index = Integer.parseInt(String.valueOf(array[i]));
				sb.append(subscript[index]);
			}
		} else {
			for(int i = 0; i < array.length; i++) {
				int index = Integer.parseInt(String.valueOf(array[i]));
				sb.append(subscript[index]);
			}
		}
		return sb.toString();
	}
	
	/**
	 * Verifica se um determinado ponto encontra-se dentro de um polígono formado pelas
	 * equações de reta geradas pelos pontos dados no array de vértices. É importante
	 * ressaltar que é necessário seguir uma ordem específica na colocação dos vértices:
	 * deve-se "desenhar" o polígono linearmente, seguindo um sentido horário ou anti-horário
	 * (qualquer um dos dois serve e será detectado pelo método). Também é possível optar ou
	 * não por deixar o último ponto da lista como sendo idêntico ao primeiro; caso não seja,
	 * o método considerará como sendo. É altamente recomendável que o polígono seja convexo
	 * e que retas não se cruzem.
	 * @param p o ponto que se deseja verificar se está dentro do polígono.
	 * @param vertices o array de vértices do polígono.
	 * @return true se o ponto estiver dentro ou na borda do polígono, false caso contrário.
	 * @throws MathException se o polígono tiver menos de 3 lados, dois pontos seguidos forem
	 * o mesmo ou o polígono tiver 3 ou mais pontos alinhados.
	 */
	public static boolean pointBelongsPolygon(Point p, Point[] vertices) {
		if(vertices.length < 3) {
			throw new MathException("Não é possível formar um polígono com menos de 3 vértices.");
		}
		if(!vertices[vertices.length - 1].equals(vertices[0])) {
			vertices = Arrays.copyOf(vertices, vertices.length + 1);
			vertices[vertices.length - 1] = new Point(vertices[0]);
		}
		double[] x = Point.getXArray(vertices);
		double[] y = Point.getYArray(vertices);
		Line2D[] lines = new Line2D[vertices.length];
		try {
			for(int i = 0; i < vertices.length - 1; i++) {
				lines[i] = new Line2D(vertices[i], vertices[i + 1]);
			}
		} catch(MathException e) {
			throw new MathException("Dois pontos seguidos não podem ser o mesmo.", e);
		}
		List<Boolean> conditions = new ArrayList<>();
		if((x[0] < x[1] && y[0] < y[1] && arithmeticAverage(y[0], y[1]) >= arithmeticAverage(biggest(y), smallest(y)))
				|| (x[0] < x[1] && y[0] > y[1] && arithmeticAverage(x[0], x[1]) >= arithmeticAverage(biggest(x), smallest(x)))
				|| (x[0] > x[1] && y[0] > y[1] && arithmeticAverage(y[0], y[1]) <= arithmeticAverage(biggest(y), smallest(y)))
				|| (x[0] > x[1] && y[0] < y[1] && arithmeticAverage(x[0], x[1]) <= arithmeticAverage(biggest(x), smallest(x)))) {
			for(int i = 0; i < vertices.length; i++) {
				if(i != vertices.length - 1) {
					if((x[i] < x[i + 1] && y[i] < y[i + 1] && arithmeticAverage(y[i], y[i + 1]) >= arithmeticAverage(biggest(y), 
							smallest(y)))
							|| (x[i] < x[i + 1] && y[i] > y[i + 1] && arithmeticAverage(x[i], x[i + 1]) >= arithmeticAverage(biggest(x), 
									smallest(x)))) {
						if(lines[i].getB() != 0) {
							conditions.add(p.getY() <= lines[i].fx(p.getX()));
						} else {
							conditions.add(p.getX() <= lines[i].fy(p.getY()));
						}
					} else if((x[i] > x[i + 1] && y[i] > y[i + 1] && arithmeticAverage(y[i], y[i + 1]) <= arithmeticAverage(biggest(y), 
							smallest(y)))
							|| (x[i] > x[i + 1] && y[i] < y[i + 1] && arithmeticAverage(x[i], x[i + 1]) <= arithmeticAverage(biggest(x), 
									smallest(x)))) {
						if(lines[i].getB() != 0) {
							conditions.add(p.getY() >= lines[i].fx(p.getX()));
						} else {
							conditions.add(p.getX() >= lines[i].fy(p.getY()));
						}
					}
				} else {
					if((x[i] < x[0] && y[i] < y[0] && arithmeticAverage(y[i], y[0]) >= arithmeticAverage(biggest(y), smallest(y)))
							|| (x[i] < x[0] && y[i] > y[0] && arithmeticAverage(x[i], x[0]) >= arithmeticAverage(biggest(x), smallest(x)))) {
						if(lines[i].getB() != 0) {
							conditions.add(p.getY() <= lines[i].fx(p.getX()));
						} else {
							conditions.add(p.getX() <= lines[i].fy(p.getY()));
						}
					} else if((x[i] > x[0] && y[i] > y[0] && arithmeticAverage(y[i], y[0]) <= arithmeticAverage(biggest(y), smallest(y)))
							|| (x[i] > x[0] && y[i] < y[0] && arithmeticAverage(x[i], x[0]) <= arithmeticAverage(biggest(x), smallest(x)))) {
						if(lines[i].getB() != 0) {
							conditions.add(p.getY() >= lines[i].fx(p.getX()));
						} else {
							conditions.add(p.getX() >= lines[i].fy(p.getY()));
						}
					}
				}
			}

		} else if((x[0] > x[1] && y[0] < y[1] && arithmeticAverage(x[0], x[1]) >= arithmeticAverage(biggest(x), smallest(x)))
				|| (x[0] > x[1] && y[0] > y[1] && arithmeticAverage(y[0], y[1]) >= arithmeticAverage(biggest(y), smallest(y)))
				|| (x[0] < x[1] && y[0] > y[1] && arithmeticAverage(x[0], x[1]) <= arithmeticAverage(biggest(x), smallest(x)))
				|| (x[0] < x[1] && y[0] < y[1] && arithmeticAverage(y[0], y[1]) <= arithmeticAverage(biggest(y), smallest(y)))) {
			for(int i = 0; i < vertices.length; i++) {
				if(i != vertices.length - 1) {
					if((x[i] > x[i + 1] && y[i] < y[i + 1] && arithmeticAverage(x[i], x[i + 1]) >= arithmeticAverage(biggest(x), smallest(x)))
							|| (x[i] > x[i + 1] && y[i] > y[i + 1] && arithmeticAverage(y[i], y[i + 1]) >= arithmeticAverage(biggest(y), 
									smallest(y)))) {
						if(lines[i].getB() != 0) {
							conditions.add(p.getY() <= lines[i].fx(p.getX()));
						} else {
							conditions.add(p.getX() <= lines[i].fy(p.getY()));
						}
					} else if((x[i] < x[i + 1] && y[i] > y[i + 1] && arithmeticAverage(x[i], x[i + 1]) <= arithmeticAverage(biggest(x), 
							smallest(x)))
							|| (x[i] < x[i + 1] && y[i] < y[i + 1] && arithmeticAverage(y[i], y[i + 1]) <= arithmeticAverage(biggest(y), 
									smallest(y)))) {
						if(lines[i].getB() != 0) {
							conditions.add(p.getY() >= lines[i].fx(p.getX()));
						} else {
							conditions.add(p.getX() >= lines[i].fy(p.getY()));
						}
					}
				} else {
					if((x[i] > x[0] && y[i] < y[0] && arithmeticAverage(x[i], x[0]) >= arithmeticAverage(biggest(x), smallest(x)))
							|| (x[i] > x[0] && y[i] > y[0] && arithmeticAverage(y[i], y[0]) >= arithmeticAverage(biggest(y), smallest(y)))) {
						if(lines[i].getB() != 0) {
							conditions.add(p.getY() <= lines[i].fx(p.getX()));
						} else {
							conditions.add(p.getX() <= lines[i].fy(p.getY()));
						}
					} else if((x[i] < x[1] && y[i] > y[1] && arithmeticAverage(x[i], x[0]) <= arithmeticAverage(biggest(x), smallest(x)))
							|| (x[i] < x[1] && y[i] < y[1] && arithmeticAverage(y[i], y[0]) <= arithmeticAverage(biggest(y), smallest(y)))) {
						if(lines[i].getB() != 0) {
							conditions.add(p.getY() >= lines[i].fx(p.getX()));
						} else {
							conditions.add(p.getX() >= lines[i].fy(p.getY()));
						}
					}
				}
			}
		} else {
			throw new MathException("O polígono não pode ter 3 pontos alinhados.");
		}
		boolean belongs = true;
		for(Boolean condition : conditions) {
			belongs = belongs && condition;
			if(!belongs) {
				break;
			}
		}
		return belongs;
	}
	
	/**
	 * Calcula a área de um polígono formado pelas equações de reta geradas pelos pontos dados
	 * na lista de vértices. Neste método, diferentemente daquele que diz se um ponto está ou não
	 * no polígono, este não requer uma ordem específica dos vértices (sentido horário ou anti-horário)
	 * e as retas podem se cruzar. No entanto, lembre-se que ainda assim o método desenhará o polígono
	 * na ordem em que os pontos forem colocados. Também é possível optar ou não por deixar o último
	 * ponto da lista como sendo idêntico ao primeiro; caso não seja, o método considerará como sendo.
	 * @param vertices o array de vértices do polígono.
	 * @return a área do polígono no plano cartesiano.
	 * @throws MathException se o polígono tiver menos de 3 lados.
	 */
	public static double polygonArea(Point[] vertices) {
		if(vertices.length < 3) {
			throw new MathException("Não é possível formar um polígono com menos de 3 vértices.");
		}
		if(!vertices[vertices.length - 1].equals(vertices[0])) {
			vertices = Arrays.copyOf(vertices, vertices.length + 1);
			vertices[vertices.length - 1] = new Point(vertices[0]);
		}
		double mainDiagonal = 0;
		double secondaryDiagonal = 0;
		for(int i = 0; i < vertices.length - 1; i++) {
			mainDiagonal += vertices[i].getX() * vertices[i + 1].getY();
			secondaryDiagonal += vertices[i].getY() * vertices[i + 1].getX();;
		}
		return abs(mainDiagonal - secondaryDiagonal) / 2;
	}
	
	/**
	 * Média aritmética.
	 * @param values os valores.
	 * @return a média.
	 */
	public static double arithmeticAverage(double... values) {
		double x = 0;
		for(int i = 0; i < values.length; i++) {
			x += values[i];
		}
		return x / values.length;
	}
	
	/**
	 * Métida aritmética ponderada.
	 * @param weights os pesos.
	 * @param values os valores.
	 * @return a média.
	 * @throws MathException se algum dos pesos for menor ou igual a 0 ou algum dos
	 * valores for menor que 0.
	 */
	public static double weightedAverage(double[] weights, double[] values) {
		double denom = 0;
		for(int i = 0; i < weights.length; i++) {
			if(weights[i] <= 0 || values[i] < 0) {
				throw new MathException("Nenhum dos pesos pode ser menor ou igual a 0 e nenhum dos valores pode ser negativo.");
			}
			denom += weights[i];
		}
		double num = 0;
		for(int i = 0; i < weights.length; i++) {
			num += weights[i] * values[i];
		}
		return num / denom;
	}
	
	/**
	 * Média geométrica.
	 * @param values os valores.
	 * @return a média.
	 * @throws MathException se algum dos valores for menor que 0.
	 */
	public static double geometricAverage(double... values) {
		double x = 1;
		for(int i = 0; i < values.length; i++) {
			x *= values[i];
			if(x < 0) {
				throw new MathException("Os valores da média geométrica não podem ser menores que 0.");
			}
		}
		return root(x, values.length);
	}
	
	/**
	 * Média harmônica.
	 * @param values os valores.
	 * @return a média.
	 * @throws MathException se algum dos valores for menor ou igual a 0.
	 */
	public static double harmonicAverage(double... values) {
		double num = (double) values.length;
		double denom = 0;
		for(int i = 0; i < values.length; i++) {
			double doubleValue = (double) values[i];
			if(doubleValue <= 0) {
				throw new MathException("Nenhum dos valores pode ser menor ou igual a 0.");
			}
			denom += 1 / doubleValue;
		}
		return num / denom;
	}
	
	/**
	 * Diz se um número é par.
	 * @param value o número.
	 * @return true se for par, false caso contrário.
	 */
	public static boolean even(long value) {
		return value % 2 == 0;
	}
	
	/**
	 * Diz se um número é ímpar.
	 * @param value o número.
	 * @return true se for ímpar, false caso contrário.
	 */
	public static boolean odd(long value) {
		return value % 2 != 0;
	}
	
	/**
	 * Calcula o comprimento (ou perímetro) de uma circunferência ou círculo.
	 * @param radius o raio.
	 * @return o comprimento.
	 */
	public static double circleLength(double radius) {
		return 2 * radius * PI;
	}
	
	/**
	 * Calcula a área de um círculo.
	 * @param radius o raio.
	 * @return a área.
	 */
	public static double circleArea(double radius) {
		return PI * pow(radius, 2);
	}
	
	/**
	 * Calcula a área de um cilindro.
	 * @param radius o raio.
	 * @param height a altura.
	 * @return a área.
	 */
	public static double cylinderArea(double radius, double height) {
		return circleLength(radius) * height + 2 * circleArea(radius);
	}
	
	/**
	 * Calcula o volume de uma esfera.
	 * @param radius o raio.
	 * @return o volume.
	 */
	public static double sphereVolume(double radius) {
		return 4 * PI * pow(radius, 3) / 3;
	}
	
	/**
	 * Calcula a área superficial de uma esfera.
	 * @param radius o raio.
	 * @return a área superficial.
	 */
	public static double sphereArea(double radius) {
		return 4 * PI * pow(radius, 2);
	}
	
	/**
	 * Diz se um número (a) é múltiplo de outro número (b).
	 * @param a o primeiro número.
	 * @param b o segundo número.
	 * @return true se for múltiplo, false caso contrário.
	 * @throws MathException se b for 0.
	 */
	public static boolean multiple(double a, double b) {
		try {
			return a % b == 0;
		} catch(ArithmeticException e) {
			throw new MathException("b não pode ser 0.", e);
		}
	}
	
	/**
	 * Diz se um número (a) é divisor de outro número (b).
	 * @param a o primeiro número.
	 * @param b o segundo número.
	 * @return true se for divisor, false caso contrário.
	 * @throws MathException se a for 0.
	 */
	public static boolean divider(double a, double b) {
		try {
			return b % a == 0;
		} catch(ArithmeticException e) {
			throw new MathException("a não pode ser 0.", e);
		}
	}
	
	/**
	 * Mínimo Múltiplo Comum (MMC). Se o array for vazio, contiver só 1 elemento,
	 * ou todos os elementos forem 0, é retornado 1. Este método trabalha com
	 * valores positivos; se algum elemento negativo for encontrado, seu módulo
	 * será usado. O resultado retornado é sempre positivo.
	 * @param values os valores para calcular o mmc.
	 * @return o mmc.
	 */
	public static int lcm(int... values) {
		if(values.length == 0 || values.length == 1) {
			return 1;
		}
		boolean allZero = true;
		for(int i = 0; i < values.length; i++) {
			if(values[i] != 0) {
				allZero = false;
				break;
			}
		}
		if(allZero) {
			return 1;
		}
		List<Integer> significantList = new ArrayList<>();
		for(int i = 0; i < values.length; i++) {
			int value = values[i];
			if(value != 0) {
				significantList.add((int) abs(value));
			}
		}
		int size = significantList.size();
		if(size == 1) {
			return 1;
		}
		Integer[] significantArray = significantList.toArray(new Integer[size]);
		int[] array = new int[size];
		for(int i = 0; i < size; i++) {
			array[i] = significantArray[i];
		}
		List<Integer> y = new ArrayList<>();
		boolean end = false;
		while(!end) {
			for(int i = 0; i < array.length; i++) {
				for(int j = 2; j <= array[i]; j++) {
					if(array[i] % j == 0) {
						y.add(j);
						for(int k = 0; k < array.length; k++) {
							if(array[k] % j == 0) {
								array[k] /= j;
							}
						}
						for(int k = 0; k < array.length; k++) {
							if(array[k] != 1) {
								end = false;
								break;
							} else {
								end = true;
							}
						}
						break;
					}
				}
			}
		}
		int lcm = 1;
		for(int z : y) {
			lcm *= z;
		}
		return lcm;
	}
	
	/**
	 * Máximo Divisor Comum (MDC). Se o array for vazio, todos os valores
	 * forem 0 ou algum deles for 1, é retornado 1. Este método trabalha
	 * com valores positivos: se algum elemento negativo for encontrado,
	 * seu módulo será usado. O resultado retornado é sempre positivo.
	 * @param values os valores para calcular o mdc.
	 * @return o mdc.
	 */
	public static int gcd(int... values) {
		if(values.length == 0) {
			return 1;
		} else if(values.length == 1) {
			return values[0] == 0 ? 1 : (int) abs(values[0]);
		}
		boolean allZero = true;
		for(int i = 0; i < values.length; i++) {
			int value = values[i];
			if(value == 1) {
				return 1;
			}
			if(value != 0) {
				allZero = false;
				break;
			}
		}
		if(allZero) {
			return 1;
		}
		List<Integer> significantList = new ArrayList<>();
		for(int i = 0; i < values.length; i++) {
			int value = values[i];
			if(value != 0) {
				significantList.add((int) abs(value));
			}
		}
		int size = significantList.size();
		if(size == 1) {
			return significantList.get(0);
		}
		Integer[] significantArray = significantList.toArray(new Integer[size]);
		int[] array = new int[size];
		for(int i = 0; i < size; i++) {
			array[i] = significantArray[i];
		}
		int min = smallest(array);
		int divisor = 2;
		List<Integer> portions = new ArrayList<>();
		while(divisor <= min) {
			int currentDivisor = divisor;
			for(int num : array) {
				if(!(num % divisor == 0)) {
					if(divisor == min / 2) {
						divisor = min;
					} else {
						divisor++;
					}
					break;
				}
			}
			if(currentDivisor == divisor) {
				portions.add(divisor);
				for(int i = 0; i < array.length; i++) {
					array[i] = array[i] / divisor;
				}
			}
		}
		int result = 1;
		for(int portion : portions) {
			result *= portion;
		}
		return result;
	}
	
	/**
	 * Calcula os divisores inteiros (positivos e negativos) de um dado inteiro.
	 * A ordem é sempre alternada no array retornado. Melhor explicando:
	 * array[0] = 1, array[1] = -1, array[2] = 2, array[3] = -2 e assim por diante.
	 * @param n um inteiro não nulo.
	 * @return um array contendo os divisores de n.
	 * @throws MathException se n for 0.
	 */
	public static int[] dividers(int n) {
		List<Integer> list = new ArrayList<>();
		int k = (int) abs(n);
		if(k != 0) {
			for(int i = 1; i <= k; i++) {
				if(k % i == 0) {
					list.add(i);
					list.add(-i);
					if(k / i == 2) {
						list.add(k);
						list.add(-k);
						break;
					}
				}
			}
			int[] dividers = new int[list.size()];
			for(int i = 0; i < dividers.length; i++) {
				dividers[i] = list.get(i);
			}
			return dividers;
		} else {
			throw new MathException("0 não é aceitável.");
		}
	}
	
	/**
	 * Diz se um inteiro é ou não primo.
	 * @param value o inteiro.
	 * @return true se for primo, false caso contrário.
	 * @throws MathException se o valor for 0.
	 */
	public static boolean prime(int value) {
		return dividers(value).length == 4;
	}
	
	/**
	 * Checa se um número é natural ou não.
	 * @param number o número.
	 * @return true se for natural, false caso contrário.
	 */
	public static boolean natural(double number) {
		long num = (long) number;
		double num2 = (double) num;
		return number == num2 && number >= 0;
	}
	
	/**
	 * Checa se um número é inteiro ou não.
	 * @param number o número.
	 * @return true se for inteiro, false caso contrário.
	 */
	public static boolean integer(double number) {
		long num = (long) number;
		double num2 = (double) num;
		return number == num2;
	}
	
	/**
	 * Retorna o maior valor de um array de valores dados.
	 * Se o array estiver vazia, é retornado 0.
	 * @param values os valores.
	 * @return o maior dos valores.
	 */
	public static double biggest(double... values) {
		if(values.length == 0) {
			return 0;
		}
		double biggest = values[0];
		for(int i = 0; i < values.length - 1; i++) {
			double value = values[i + 1];
			if(value > biggest) {
				biggest = value;
			}
		}
		return biggest;
	}
	
	/**
	 * Retorna o maior valor de um array de valores dados.
	 * Se o array estiver vazia, é retornado 0.
	 * @param values os valores.
	 * @return o maior dos valores.
	 */
	public static int biggest(int... values) {
		if(values.length == 0) {
			return 0;
		}
		int biggest = values[0];
		for(int i = 0; i < values.length - 1; i++) {
			int value = values[i + 1];
			if(value > biggest) {
				biggest = value;
			}
		}
		return biggest;
	}
	
	/**
	 * Retorna o menor valor de um array de valores dados.
	 * Se o array estiver vazio, é retornado 0.
	 * @param values os valores.
	 * @return o menor dos valores.
	 */
	public static double smallest(double... values) {
		if(values.length == 0) {
			return 0;
		}
		double smallest = values[0];
		for(int i = 0; i < values.length - 1; i++) {
			double value = values[i + 1];
			if(value < smallest) {
				smallest = value;
			}
		}
		return smallest;
	}
	
	/**
	 * Retorna o menor valor de um array de valores dados.
	 * Se o array estiver vazio, é retornado 0.
	 * @param values os valores.
	 * @return o menor dos valores.
	 */
	public static int smallest(int... values) {
		if(values.length == 0) {
			return 0;
		}
		int smallest = values[0];
		for(int i = 0; i < values.length - 1; i++) {
			int value = values[i + 1];
			if(value < smallest) {
				smallest = value;
			}
		}
		return smallest;
	}
	
	/**
	 * Um método que muitos se perguntariam o motivo de estar nesta classe. A resposta é:
	 * apenas por conveniência. Basicamente diz se um ano é bissexto ou não.
	 * @param year o ano.
	 * @return true se for bissexto, false caso contrário.
	 */
	public static boolean leapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
	}
	
	/**
	 * Método que gera uma String aleatória representando um número em base hexadecimal com
	 * a quantidade de caracteres fornecida.
	 * @param charAmount a quantidade de caracteres que a String deve ter.
	 * @return a String.
	 * @throws MathException se a quantidade de caracteres fornecida for menor ou igual a 0.
	 */
	public static String randomHexString(int charAmount) {
		if(charAmount <= 0) {
			throw new MathException("O número de caracteres não pode ser menor ou igual a 0.");
		}
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		String hexChars = "0123456789abcdf";
		for(int i = 0; i < charAmount; i++) {
			sb.append(hexChars.charAt(r.nextInt(15)));
		}
		return sb.toString();
	}
}
