package br.sergio.math;

public final class Rational extends Number implements Comparable<Rational> {
	
	public static final Rational ZERO = new Rational(0);
	public static final Rational ONE = new Rational(1);
	private static final long serialVersionUID = -2045964487308847792L;
	private static final String format = "A String deve estar no formato a/b, com a e b inteiros e b diferente de 0.";
	private int num;
	private int denom;
	
	public Rational(int num, int denom) {
		if(denom == 0) {
			throw new MathException("Denominador não pode ser 0");
		}
		if(num % denom == 0) {
			num = num / denom;
			denom = 1;
		}
		if(denom < 0) {
			num = -num;
			denom = -denom;
		}
		int gcd = AdvancedMath.gcd(num, denom);
		if(gcd != 1) {
			num /= gcd;
			denom /= gcd;
		}
		this.num = num;
		this.denom = denom;
	}
	
	public Rational(int num) {
		this(num, 1);
	}
	
	public Rational add(Rational rational) {
		int newNum, newDenom;
		if(denom == rational.denom) {
			newNum = num + rational.num;
			newDenom = denom;
		} else {
			newNum = num * rational.denom + rational.num * denom;
			newDenom = denom * rational.denom;
		}
		return new Rational(newNum, newDenom);
	}
	
	public Rational subtract(Rational rational) {
		return add(new Rational(-rational.num, rational.denom));
	}
	
	public Rational multiply(Rational rational) {
		return new Rational(num * rational.num, denom * rational.denom);
	}
	
	public Rational divide(Rational rational) {
		return multiply(rational.getInverse());
	}
	
	public boolean isInteger() {
		return denom == 1;
	}
	
	public static int[] getNumerators(Rational[] array) {
		int[] numerators = new int[array.length];
		for(int i = 0; i < array.length; i++) {
			numerators[i] = array[i].num;
		}
		return numerators;
	}
	
	public static int[] getDenominators(Rational[] array) {
		int[] denominators = new int[array.length];
		for(int i = 0; i < array.length; i++) {
			denominators[i] = array[i].denom;
		}
		return denominators;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		if(o == this) {
			return true;
		}
		if(o instanceof Rational rational) {
			return num == rational.num && denom == rational.denom;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return ((num & denom) << 2) | ((num ^ denom) << 1);
	}
	
	@Override
	public String toString() {
		return num + "/" + denom;
	}
	
	@Override
	public int compareTo(Rational o) {
		int lcm = AdvancedMath.lcm(denom, o.denom);
		int n1 = (lcm / denom) * num;
		int n2 = (lcm / o.denom) * o.num;
		return n1 - n2;
	}
	
	public Rational pow(int k) {
		int n, d;
		if(k < 0) {
			if(num == 0) {
				throw new MathException("0 não pode ser elevado a expoentes negativos");
			} else {
				n = denom;
				d = num;
				k = -k;
			}
		} else if(k > 0) {
			n = num;
			d = denom;
		} else {
			if(num == 0) {
				throw new MathException("0 não pode se elevado a 0");
			} else {
				return ONE;
			}
		}
		int newNum = (int) Math.pow(n, k);
		int newDenom = (int) Math.pow(d, k);
		return new Rational(newNum, newDenom);
	}
	
	public static Rational valueOf(String value) {
		if(value.contains("/")) {
			String[] values = value.split("/");
			if(values.length > 2) {
				throw new MathException(format);
			}
			for(int i = 0; i < values.length; i++) {
				values[i] = values[i].trim();
				if(values[i].isEmpty()) {
					throw new MathException(format);
				}
			}
			int n = Integer.parseInt(values[0]);
			int d = Integer.parseInt(values[1]);
			return new Rational(n, d);
		} else {
			return new Rational(Integer.parseInt(value));
		}
	}
	
	public Rational getInverse() {
		return new Rational(denom, num);
	}
	
	public int getNum() {
		return num;
	}
	
	public int getDenom() {
		return denom;
	}

	@Override
	public int intValue() {
		return num / denom;
	}

	@Override
	public long longValue() {
		return num / denom;
	}

	@Override
	public float floatValue() {
		return (float) num / denom;
	}

	@Override
	public double doubleValue() {
		return (double) num / denom;
	}
}
