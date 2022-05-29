package br.sergio.math;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Function;

public class Matrix implements Serializable {
	
	private static final long serialVersionUID = -6403625144052999103L;
	
	private int lines;
	private int columns;
	
	private double[][] data;
	
	/**
	 * Construtor usado para construir uma matriz de
	 * m linhas por n colunas, tal que m e n sejam maiores
	 * ou iguais a 1.
	 * @param lines a quantidade de linhas.
	 * @param columns a quantidade de colunas.
	 */
	public Matrix(int lines, int columns) {
		if(lines < 1 || columns < 1) {
			throw new IllegalArgumentException("Linhas e colunas não podem ser menores que 1");
		}
		this.lines = lines;
		this.columns = columns;
		
		data = new double[lines][columns];
	}
	
	/**
	 * Construtor usado para construir uma matriz
	 * quadrada de ordem n, tal que n seja maior
	 * ou igual a 1.
	 * @param order a ordem da matriz quadrada.
	 */
	public Matrix(int order) {
		this(order, order);
	}
	
	/**
	 * Construtor usado para construir uma matriz
	 * com dados já precriados num array bidimensional
	 * de m linhas por n colunas. 
	 * @param data o array com os valores precriados.
	 * @throws NullPointerException se o array for nulo.
	 * @throws IllegalArgumentException se m ou n forem 0.
	 * @throws MathException se o tamanho das colunas não for
	 * consistente.
	 */
	public Matrix(double[][] data) {
		if(data == null) {
			throw new NullPointerException("Dados nulos");
		}
		if(data.length == 0) {
			throw new IllegalArgumentException("Linhas vazias");
		}
		if(data[0].length == 0) {
			throw new IllegalArgumentException("Colunas vazias");
		}
		int lines = data.length;
		int columns = data[0].length;
		for(int i = 0; i < lines; i++) {
			if(data[i].length != columns) {
				throw new MathException("O tamanho de todas as colunas deve ser o mesmo");
			}
		}
		this.lines = lines;
		this.columns = columns;
		this.data = data;
	}
	
	/**
	 * @return a quantidade de linhas da matriz.
	 */
	public int getLines() {
		return lines;
	}
	
	/**
	 * @return a quantidade de colunas da matriz.
	 */
	public int getColumns() {
		return columns;
	}
	
	/**
	 * Retora o valor presente na posição ij da matriz,
	 * sendo i a linha e j a coluna (com a contagem começando do 0).
	 * @param line o número i da linha.
	 * @param column o número j da coluna.
	 * @return o valor presente na posição ij.
	 * @throws ArrayIndexOutOfBoundsException se i ou j forem negativos ou
	 * estiverem fora dos limites de tamanho de linhas (para i) e colunas
	 * (para j) da matriz.
	 */
	public double getValue(int line, int column) {
		return data[line][column];
	}
	
	/**
	 * Define o valor na posição ij da matriz, sendo
	 * i a linha e j a coluna (com a contagem começando do 0).
	 * @param line o número i da linha.
	 * @param column o número j da coluna.
	 * @param value o valor a ser colocado na posição ij.
	 * @throws ArrayIndexOutOfBoundsException se i ou j forem negativos ou
	 * estiverem fora dos limites de tamanho de linhas (para i) e colunas
	 * (para j) da matriz.
	 */
	public void setValue(int line, int column, double value) {
		data[line][column] = value;
	}
	
	/**
	 * @return o determinante desta matriz.
	 * @throws MathException se esta matriz não for quadrada.
	 */
	public double determinant() {
		if(!isSquare()) {
			throw new MathException("Determinantes só existem para matrizes quadradas");
		}
		return switch(getOrder()) {
			case 1:
				yield data[0][0];
			case 2:
				yield data[0][0] * data[1][1] - data[0][1] * data[1][0];
			case 3:
				yield data[2][0] * data[0][1] * data[1][2] 
						+ data[0][0] * data[1][1] * data[2][2] 
						+ data[1][0] * data[2][1] * data[0][2]
						- data[2][2] * data[0][1] * data[1][0]
						- data[0][2] * data[1][1] * data[2][0]
						- data[1][2] * data[2][1] * data[0][0];
			default:
				double determinant = 0;
				for(int i = 0; i < columns; i++) {
					double value = data[0][i];
					if(value == 0) {
						continue;
					}
					determinant += value * cofactor(0, i);
				}
				yield determinant;
		};
	}
	
	/**
	 * Cofator de um elemento. O cofator do elemento de uma matriz quadrada
	 * na posição ij é dado por (-1)^(i + j) * Dij, sendo Dij o menor complementar
	 * do elemento.
	 * @param line a linha do elemento.
	 * @param column a coluna do elemento.
	 * @return o cofator do elemento.
	 */
	public double cofactor(int line, int column) {
		return AdvancedMath.pow(-1, line + column) * complementaryMinor(line, column);
	}
	
	/**
	 * Menor complementar. O menor complementar de um elemento é o determinante formado
	 * pela matriz quadrada de ordem n - 1, tal que a linha e a coluna do elemento foram
	 * excluídas. Como exemplo, o menor complementar do elemento 4 (posição 2,1 - ou 1,0
	 * se a contagem começar do 0) da matriz {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}} é o
	 * determinante da matriz {{2, 3}, {8, 9}}, que é igual a -6;
	 * @param line a linha do elemento.
	 * @param column a coluna do elemento.
	 * @return o menor complementar do elemento.
	 * @throws MathException se a matriz não for quadrada ou tiver ordem menor que 2.
	 * @throws ArrayIndexOutOfBoundsException se a posição dada estiver fora dos limites
	 * da matriz.
	 */
	public double complementaryMinor(int line, int column) {
		if(!isSquare()) {
			throw new MathException("Esta matriz não é quadrada");
		}
		int order = getOrder();
		if(line < 0 || line >= order || column < 0 || column >= order) {
			throw new ArrayIndexOutOfBoundsException("Posição (" + line + ", " + column + ") está fora dos"
					+ "limites para o tamanho da matriz quadrada (" + order + ", " + order + ").");
		}
		if(order == 1) {
			throw new MathException("Esta matriz requer ser de no mínimo ordem 2.");
		}
		double[][] complementaryMinorData = new double[order - 1][order - 1];
		for(int i = 0; i < order - 1; i++) {
			for(int j = 0; j < order - 1; j++) {
				int vLine = i >= line ? i + 1 : i;
				int vColumn = j >= column ? j + 1 : j;
				complementaryMinorData[i][j] = data[vLine][vColumn];
			}
		}
		return new Matrix(complementaryMinorData).determinant();
	}
	
	/**
	 * Soma os valores desta matriz com uma nova, retornando a matriz
	 * soma das duas. Se as matrizes não tiverem tamanhos iguais, será
	 * retornada uma matriz que tem a maior quantidade de linhas e colunas
	 * dentre as duas fornecidas.
	 * @param m a matriz para ser somada com esta.
	 * @return a matriz soma.
	 */
	public Matrix add(Matrix m) {
		return sum(m, 1);
	}
	
	/**
	 * Subtrai os valores de uma nova matriz desta, retornando a matriz
	 * diferença das duas. Se as matrizes não tiverem tamanhos iguais, será
	 * retornada uma matriz que tem a maior quantidade de linhas e colunas
	 * dentre as duas fornecidas.
	 * @param m a matriz subtraendo.
	 * @return a matriz diferença.
	 */
	public Matrix subtract(Matrix m) {
		return sum(m, -1);
	}
	
	private Matrix sum(Matrix m, int f) {
		int lineAmount = AdvancedMath.biggest(lines, m.lines);
		int columnAmount = AdvancedMath.biggest(columns, m.columns);
		
		Matrix sum = new Matrix(lineAmount, columnAmount);
		
		for(int i = 0; i < lineAmount; i++) {
			for(int j = 0; j < columnAmount; j++) {
				double value1, value2;
				try {
					value1 = data[i][j];
				} catch(ArrayIndexOutOfBoundsException e) {
					value1 = 0;
				}
				try {
					value2 = m.data[i][j];
				} catch(ArrayIndexOutOfBoundsException e) {
					value2 = 0;
				}
				sum.setValue(i, j, value1 + f * value2);
			}
		}
		return sum;
	}
	
	/**
	 * Multiplicação por escalar. Basicamente multiplica todos os elementos
	 * da matriz pelo escalar fornecido. Tomando os escalares a e b, as
	 * matrizes A e B e a matriz nula O, a multiplicação por escalar de
	 * matrizes tem as seguintes propriedades:
	 * <p>1. a(A + B) = aA + aB;
	 * <p>2. (a + b)A = aA + bA;
	 * <p>3. (ab)A = a(bA);
	 * <p>4. 0A = O.
	 * @param scalar o valor que multiplicará todos os elementos
	 * desta matriz.
	 * @return a matriz multiplicada pelo escalar.
	 */
	public Matrix multiplyByScalar(double scalar) {
		Matrix result = new Matrix(Arrays.copyOf(data, data.length));
		result.modificate(v -> v * scalar);
		return result;
	}
	
	/**
	 * Potência de matrizes. Só funciona para matrizes quadradas e expoentes
	 * naturais. Caso as duas condições anteriores não sejam atendidas, é
	 * retornado null. Elevar a 0 retorna a matriz identidade.
	 * @param exponent o expoente.
	 * @return a matriz elevada ao expoente dado.
	 */
	public Matrix pow(int exponent) {
		if(!isSquare() || exponent < 0) {
			return null;
		}
		Matrix result = getIdentity(getOrder());
		for(int i = 0; i < exponent; i++) {
			result = result.multiply(this);
		}
		return result;
	}
	
	/**
	 * Multiplicação de matrizes. É importante dizer que para que
	 * a multiplicação de matrizes funcione, a quantidade de colunas
	 * da primeira deve ser igual ao número de linhas da segunda. Assim
	 * sendo, a multiplicação de uma matriz MxP por uma PxN retorna uma
	 * matriz MxN. A multiplicação de matrizes não é comutativa; assim sendo,
	 * uma matriz A multiplicada por uma matriz B não retorna o mesmo que
	 * uma matriz B multiplicada por uma matriz A, salvo em dois casos: uma
	 * matriz inversível multiplicada por sua inversa, cujo resultado será
	 * sempre a matriz identidade; uma matriz quadrada multiplicada pela matriz
	 * identidade de mesma ordem, cujo resultado será sempre a própria matriz.
	 * Tomandos as matrizes A, B e C, a matriz nula O e a matriz identidade I,
	 * a multiplicação de matrizes tem as seguintes propriedades:
	 * <p>1. AB nem sempre é igual a BA;
	 * <p>2. (AB)C = A(BC);
	 * <p>3. A(B + C) = AB + AC, (B + C)A = BA + CA;
	 * <p>4. AI = IA = A;
	 * <p>5. AO = OA = O.
	 * @param m a matriz para ser multiplicada com esta.
	 * @return a matriz produto.
	 * @throws MathException se a quantidade de colunas da primeira for diferente
	 * da quantidade de linhas da segunda.
	 */
	public Matrix multiply(Matrix m) {
		if(columns != m.lines) {
			throw new MathException("Não é possível multiplicar matrizes tais que o número de colunas da "
					+ "primeira seja diferente do número de linhas da segunda.");
		}
		Matrix result = new Matrix(lines, m.columns);
		int size = columns;
		for(int i = 0; i < result.lines; i++) {
			for(int j = 0; j < result.columns; j++) {
				double sum = 0;
				for(int k = 0; k < size; k++) {
					sum += data[i][k] * m.data[k][j];
				}
				result.data[i][j] = sum;
			}
		}
		return result;
	}
	
	/**
	 * @return a ordem desta matriz se ela for quadrada,
	 * do contrário, -1.
	 */
	public int getOrder() {
		return isSquare() ? lines : -1;
	}
	
	/**
	 * @return true se esta matriz for quadrada (quantidade de linhas
	 * igual à quantidade de colunas), false caso contrário.
	 */
	public boolean isSquare() {
		return lines == columns;
	}
	
	/**
	 * Retorna se esta matriz é simétrica ou não. Uma matriz simétrica
	 * é aquela que é igual à sua transposta. Em outras palavras, só funciona
	 * para matrizes quadradas. Se esta matriz não for quadrada, é sempre
	 * retornado false.
	 * @return se esta matriz é simétrica (igual à sua transposta).
	 */
	public boolean isSimetric() {
		return equals(transposed());
	}
	
	/**
	 * Retorna se esta matriz é antissimétrica ou não. Uma matriz antissimétrica
	 * é aquela cuja transposta é igual à original multiplicada pelo escalar -1.
	 * Por se tratar de matriz transposta no cálculo, também só funciona para matrizes
	 * quadradas. Se esta matriz não for quadrada, é retornado false.
	 * @return se esta matriz é antissimétrica (transposta igual a -original).
	 */
	public boolean isAntiSimetric() {
		return transposed().equals(multiplyByScalar(-1));
	}
	
	/**
	 * Retorna a matriz transposta a esta. Uma matriz transposta é aquela em que
	 * "linhas viram colunas e colunas viram linhas). De exemplo, uma matriz
	 * {{1, 2, 3}, {4, 5, 6}} tem transposta igual a {{1, 4}, {2, 5}, {3, 6}}.
	 * @return a matriz transposta desta.
	 */
	public Matrix transposed() {
		Matrix transposed = new Matrix(columns, lines);
		for(int i = 0; i < lines; i++) {
			for(int j = 0; j < columns; j++) {
				transposed.data[j][i] = data[i][j];
			}
		}
		return transposed;
	}
	
	/**
	 * @return a matriz dos cofatores dos elementos desta. É retornado null
	 * se esta matriz não for quadrada ou tiver ordem menor que 2.
	 */
	public Matrix cofactorMatrix() {
		if(!isSquare() || getOrder() == 1) {
			return null;
		}
		int order = getOrder();
		double[][] cofactorMatrixData = new double[order][order];
		for(int i = 0; i < order; i++) {
			for(int j = 0; j < order; j++) {
				cofactorMatrixData[i][j] = cofactor(i, j);
			}
		}
		return new Matrix(cofactorMatrixData);
	}
	
	/**
	 * Matriz adjunta. Define-se a matriz adjunta de uma matriz quadrada como
	 * sendo a tranposta da matriz dos cofatores.
	 * @return a matriz adjunta.
	 */
	public Matrix adjugate() {
		return cofactorMatrix().transposed();
	}
	
	/**
	 * Retorna a matriz inversa desta, caso exista. A matriz inversa
	 * só existe para matrizes quadradas; assim sendo, se esta matriz não for
	 * quadrada, é retornado null. A matriz inversa de uma matriz A, denotada
	 * por A^-1, é a matriz tal que A * A^-1 = A^-1 * A = I, sendo I a matriz
	 * identidade. Se a matriz inversa desta não existir, é retornado null.
	 * @return a matriz inversa desta.
	 */
	public Matrix inverse() {
		if(!isSquare()) {
			return null;
		}
		if(getOrder() == 1) {
			return new Matrix(new double[][] {{1 / data[0][0]}});
		}
		double determinant = determinant();
		if(determinant == 0) {
			return null;
		}
		return adjugate().multiplyByScalar(1 / determinant);
	}
	
	/**
	 * Transforma todos os elementos desta matriz com base nos atuais.
	 * @param function a função a ser aplicada aos elementos da matriz.
	 */
	public void modificate(Function<Double, Double> function) {
		for(int i = 0; i < lines; i++) {
			for(int j = 0; j < columns; j++) {
				data[i][j] = function.apply(data[i][j]);
			}
		}
	}
	
	/**
	 * Retorna a matriz identidade de ordem n. A matriz identidade é a
	 * matriz quadrada cujos elementos da diagonal principal são todos iguais
	 * a 1 e fora dela todos iguais a 0. Como exemplo, a matriz identidade de
	 * ordem 3 é dada por {{1, 0, 0}, {0, 1, 0} {0, 0, 1}}. A matriz identidade
	 * é tal que, tomando uma matriz A de mesma ordem, A * I = I * A = A. Funciona
	 * como o elemento neutro da multiplicação de matrizes.
	 * @param order a ordem da matriz identidade.
	 * @return a matriz identidade da ordem dada.
	 */
	public static Matrix getIdentity(int order) {
		Matrix identity = new Matrix(order);
		for(int i = 0; i < order; i++) {
			identity.data[i][i] = 1;
		}
		return identity;
	}
	
	/**
	 * Exibe a matriz no formato tradicional de linhas e colunas.
	 * É útil para visualização em console ou outros lugares onde haja
	 * uma caixa de texto que permita mais de uma linha.
	 * @return a string formatada.
	 */
	public String toFormattedString() {
		String[][] formattedColumns = new String[columns][lines];
		for(int i = 0; i < columns; i++) {
			String preffix = i == 0 ? "|" : "";
			String suffix = i == columns - 1 ? "|" : "";
			double[] column = new double[lines];
			int maxSize = 0;
			for(int j = 0; j < lines; j++) {
				column[j] = data[j][i];
				int size = String.valueOf(column[j]).length();
				if(size > maxSize) {
					maxSize = size;
				}
			}
			String[] formattedColumn = new String[lines];
			for(int j = 0; j < lines; j++) {
				String columnString = String.valueOf(column[j]);
				StringBuilder element = new StringBuilder();
				for(int k = 0; k < maxSize - columnString.length(); k++) {
					element.append(" ");
				}
				element.append(columnString);
				formattedColumn[j] = preffix + element.toString() + suffix;
			}
			formattedColumns[i] = formattedColumn;
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < lines; i++) {
			StringBuilder line = new StringBuilder();
			for(int j = 0; j < columns; j++) {
				line.append(formattedColumns[j][i] + (j == columns - 1 ? "" : " "));
			}
			sb.append(line.toString() + (i == lines - 1 ? "" : "\n"));
		}
		return sb.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for(int i = 0; i < lines; i++) {
			StringBuilder line = new StringBuilder();
			line.append("{");
			for(int j = 0; j < columns; j++) {
				line.append(data[i][j] + (j == columns - 1 ? "" : ", "));
			}
			line.append("}");
			sb.append(line.toString() + (i == lines - 1 ? "" : ", "));
		}
		sb.append("}");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		if(o == this) {
			return true;
		}
		if(o instanceof Matrix m) {
			if(lines != m.lines || columns != m.columns) {
				return false;
			}
			for(int i = 0; i < lines; i++) {
				for(int j = 0; j < columns; j++) {
					if(data[i][j] != m.data[i][j]) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(data);
	}
	
}
