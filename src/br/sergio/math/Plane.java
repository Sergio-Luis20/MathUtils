package br.sergio.math;

import java.io.Serializable;
import java.util.Objects;

public class Plane implements Serializable {
	
	private static final long serialVersionUID = -4182579565786526374L;
	protected Point point;
	protected Vector vector;
	protected double d;
	
	public Plane(Point point, Vector vector) {
		this.point = Objects.requireNonNull(point);
		if(vector == null) {
			throw new NullPointerException();
		} else if(vector.equals(Vector.NULL)) {
			throw new MathException("O vetor normal não pode ser o vetor nulo.");
		} else {
			this.vector = vector;
		}
		d = -(vector.getX() * point.getX() + vector.getY() * point.getY() + vector.getZ() * point.getZ());
	}
	
	public double distance(Point p) {
		return new Vector(point, p).projection(vector).getMagnitude();
	}
	
	public double distance(Line3D line) {
		if(vector.dotProduct(line.getVector()) == 0) {
			return distance(line.getPoint());
		} else {
			return 0;
		}
	}
	
	public double distance(Plane p) {
		if(vector.isMultipleOf(p.vector)) {
			return distance(p.point);
		} else {
			return 0;
		}
	}
	
	public boolean hasPoint(Point p) {
		return vector.getX() * p.getX() + vector.getY() * p.getY() + vector.getZ() * p.getZ() + d == 0;
	}
	
	public Point getPoint() {
		return point;
	}
	
	public Vector getVector() {
		return vector;
	}
	
	public double getD() {
		return d;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o != null && o instanceof Plane p) {
			return point.equals(p.point) && vector.equals(p.vector);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return point.hashCode() ^ vector.hashCode() << 1;
	}
	
	@Override
	public String toString() {
		String ax = vector.getX() + "x";
		String by = (vector.getY() >= 0 ? "+" : "") + vector.getY() + "y";
		String cz = (vector.getZ() >= 0 ? "+" : "") + vector.getZ() + "z";
		String d = (this.d >= 0 ? "+" : "") + this.d;
		return ax + by + cz + d + "=0.0";
	}
}
