package br.sergio.math;

/**
 * Classe que representa um vetor no plano ou espaço
 * cartesiano.
 * @author Sergio Luis
 *
 */
public class Vector {
	
	/**
	 * Representa o vetor nulo, cujo módulo é 0 e forma um ângulo reto com qualquer outro vetor.
	 */
	public static final Vector NULL;
	protected double modulus;
	protected double x, y, z;
	protected Point origin, end;
	
	/**
	 * Construtor que gera o vetor nulo.
	 * Basicamente posiciona-o no centro do plano ou espaço
	 * cartesiano e têm módulo 0. Este vetor forma um ângulo reto
	 * com qualquer outro vetor, incluindo consigo mesmo.
	 */
	private Vector() {
		origin = Point.ORIGIN;
		end = Point.ORIGIN;
	}
	
	/**
	 * Cria um vetor no plano ou espaço cartesiano. Neste construtor, o primeiro
	 * parâmetro representa a origem do vetor e o segundo a sua extremidade, mas os métodos
	 * getters sempre retornação as coordenadas da extremidade subtraídas das coordenadas da
	 * origem, uma vez que representam os múltiplos dos versores i, j e k, respectivamente.
	 * Para obter os pontos originais, utilize os métodos getOrigin() e getEnd().
	 * @param origin o ponto origem.
	 * @param end o ponto extremidade.
	 */
	public Vector(Point origin, Point end) {
		this.origin = origin;
		this.end = end;
		Point difference = end.subtract(origin);
		x = difference.getX();
		y = difference.getY();
		z = difference.getZ();
		modulus = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}
	
	/**
	 * Cria um vetor no plano ou espaço cartesiano. Este construtor implica no vetor
	 * ter origem na origem do plano ou espaço e extremidade no ponto fornecido. Lembrando
	 * que os métodos getters para as coordenadas x, y e z sempre retornam as do ponto extremidade,
	 * uma vez que representam os múltiplos dos versores i, j e k, respectivamente.
	 * @param p o ponto extremidade.
	 */
	public Vector(Point p) {
		origin = Point.ORIGIN;
		end = p;
		x = p.getX();
		y = p.getY();
		z = p.getZ();
		modulus = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}
	
	/**
	 * Constrói um vetor usando a informação do módulo e do ângulo fornecido.
	 * O ângulo deve estar em radianos.
	 * @param modulus o módulo.
	 * @param angle o ângulo.
	 * @return o referido vetor.
	 */
	public static Vector trigonometric(double modulus, double angle) {
		return new Point(Math.abs(modulus) * Math.cos(angle, true), Math.abs(modulus) * Math.sin(angle, true)).toVector();
	}
	
	/**
	 * Retorna o vetor soma deste com o fornecido.
	 * @param v o vetor com o qual este será somado.
	 * @return o vetor soma.
	 */
	public Vector add(Vector v) {
		return new Point(x + v.x, y + v.y, z + v.z).toVector();
	}
	
	/**
	 * Retorna o vetor diferença deste com o fornecido.
	 * @param v o vetor subtraendo.
	 * @return o vetor diferença.
	 */
	public Vector subtract(Vector v) {
		return new Point(x - v.x, y - v.y, z - v.z).toVector();
	}
	
	/**
	 * Multiplica este vetor por um valor escalar real qualquer.
	 * Se o valor for positivo, o vetor retornado tem o mesmo sentido que o
	 * original e módulo igual ao módulo original multiplicado pelo escalar
	 * fornecido. Se o valor for negativo, o vetor retornado tem o sentido
	 * oposto ao original e módulo igual ao módulo original multiplicado pelo
	 * módulo do escalar fornecido. Multiplicar por 0 retorna o vetor nulo.
	 * @param scalar um valor escalar qualquer.
	 * @return este vetor multiplicado pelo valor fornecido.
	 */
	public Vector multiplyByScalar(double scalar) {
		double x = this.x * scalar;
		double y = this.y * scalar;
		double z = this.z * scalar;
		return new Point(x, y, z).toVector();
	}
	
	/**
	 * Retorna o produto escalar deste vetor com o fornecido. Se os dois forem ortogonais
	 * entre si ou pelo menos 1 deles for nulo, é retornado 0.
	 * @param v o vetor para fazer o produto escalar.
	 * @return o produto escalar.
	 */
	public double scalarProduct(Vector v) {
		return x * v.x + y * v.y + z * v.z;
	}
	
	/**
	 * Retorna o produto vetorial deste vetor com o fornecido.
	 * É importante ressaltar que o produto vetorial não é comutativo, ou seja,
	 * AxB (A vetorial B) não retorna o mesmo vetor que BxA (B vetorial A).
	 * Neste método, a conta AxB (A vetorial B) toma este objeto como sendo o
	 * vetor A e o vetor fornecido como sendo o vetor B.
	 * @param v o vetor para fazer o produto vetorial.
	 * @return o produto vetorial.
	 */
	public Vector vectorialProduct(Vector v) {
		double i = y * v.z - v.y * z;
		double j = v.x * z - x * v.z;
		double k = x * v.y - v.x * y;
		return new Point(i, j, k).toVector();
	}
	
	/**
	 * Retorna a projeção deste vetor em relação a um outro.
	 * @param v o vetor no qual este terá sua projeção.
	 * @return o vetor projeção.
	 */
	public Vector projection(Vector v) {
		if(v.equals(NULL)) {
			return NULL;
		} else {
			return v.multiplyByScalar(scalarProduct(v) / v.scalarProduct(v));
		}
	}
	
	/**
	 * O versor (também chamado de vetor unitário) é um vetor com a mesma direção
	 * e sentido do original, porém com módulo igual a 1.
	 * @return o versor deste vetor.
	 * @throws MathException se este vetor for nulo.
	 */
	public Vector versor() {
		if(equals(NULL)) {
			throw new MathException("O vetor nulo não possui versor.");
		}
		return multiplyByScalar(1 / modulus);
	}
	
	/**
	 * Retorna um vetor com a mesma direção e sentido deste, porém com o valor do
	 * novo módulo fornecido. É importante salientar que valores negativos não inverterão
	 * o sentido aqui, uma vez que será tomado o módulo do escalar dado.
	 * @param scalar o novo módulo.
	 * @return o vetor com a mesma direção e sentido deste, porém com o módulo fornecido.
	 */
	public Vector toNewModulus(double scalar) {
		if(equals(NULL)) {
			throw new MathException("O vetor nulo não pode gerar um vetor com módulo diferente de 0 através de uma multiplicação.");
		}
		return multiplyByScalar(Math.abs(scalar) / modulus);
	}
	
	/**
	 * Retorna o ângulo entre este vetor e o vetor fornecido.
	 * Se este ou o vetor fornecido for equivalente ao vetor nulo, é
	 * retornado um ângulo reto, caso contrário, é retornado um ângulo
	 * no intervalo (-pi, pi].
	 * @param v o vetor que forma um ângulo com este.
	 * @return o ângulo.
	 */
	public double angle(Vector v) {
		if(equals(NULL) || v.equals(NULL)) {
			return Math.PI / 2;
		} else {
			return Math.arccos(scalarProduct(v) / (modulus * v.modulus), true);
		}
	}
	
	/**
	 * @return o módulo deste vetor. É sempre positivo.
	 */
	public double getModulus() {
		return modulus;
	}
	
	/**
	 * @return a abscissa da extremidade deste vetor em relação ao centro
	 * do plano ou espaço cartesiano. Também representa o múltiplo do versor i.
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * @return a ordenada da extremidade deste vetor em relação ao centro
	 * do plano ou espaço cartesiano. Também representa o múltiplo do versor j.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * @return a cota da extremidade deste vetor em relação ao centro
	 * do espaço cartesiano. Também representa o múltiplo do versor k.
	 */
	public double getZ() {
		return z;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		if(o instanceof Vector) {
			Vector v = (Vector) o;
			return x == v.x && y == v.y && z == v.z;
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
		String i = x + "i";
		String j = (y >= 0 ? "+" : "") + y + "j";
		String k = (z >= 0 ? "+" : "") + z + "k";
		return i + j + k;
	}
	
	static {
		NULL = new Vector();
	}
}
