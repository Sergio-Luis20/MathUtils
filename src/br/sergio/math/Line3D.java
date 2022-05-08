package br.sergio.math;

import java.io.Serializable;
import java.util.Objects;

public class Line3D implements Serializable	 {
	
	private static final long serialVersionUID = -1124204248865173201L;
	protected Point point;
	protected Vector vector;
	
	public Line3D(Point point, Vector vector) {
		this.point = Objects.requireNonNull(point);
		if(vector == null) {
			throw new NullPointerException();
		} else if(vector.equals(Vector.NULL)) {
			throw new MathException("O vetor diretor não pode ser o vetor nulo.");
		} else {
			this.vector = vector;
		}
	}
	
	public double distance(Point p) {
		return new Vector(point, p).crossProduct(vector).getMagnitude() / vector.getMagnitude();
	}
	
	public double distance(Line3D line) {
		if(vector.isMultipleOf(line.vector)) {
			return parallel(line);
		} else {
			return competitor(line);
		}
	}
	
	private double parallel(Line3D line) {
		return distance(line.point);
	}
	
	private double competitor(Line3D line) {
		Vector u = new Vector(point, line.point);
		if(u.equals(Vector.NULL)) {
			return 0;
		}
		Vector v = vector.crossProduct(line.vector);
		return u.projection(v).getMagnitude();
	}
	
	public boolean hasPoint(Point p) {
		if(p.equals(Point.ORIGIN)) {
			Vector u = new Vector(p, point);
			if(u.equals(Vector.NULL)) {
				return true;
			} else {
				return u.isMultipleOf(vector);
			}
		} else {
			return vector.isMultipleOf(p.toVector());
		}
	}
	
	public double getT(Point p) {
		if(!hasPoint(p)) {
			throw new MathException("O ponto não pertence à reta.");
		}
		if(vector.getX() != 0) {
			return (p.getX() - point.getX()) / vector.getX();
		} else if(vector.getY() != 0) {
			return (p.getY() - point.getY()) / vector.getY();
		} else {
			return (p.getZ() - point.getZ()) / vector.getZ();
		}
	}
	
	public Point getParametralPoint(double t) {
		return new Point(getX(t), getY(t), getZ(t));
	}
	
	public double getX(double t) {
		return vector.getX() * t + point.getX();
	}
	
	public double getY(double t) {
		return vector.getY() * t + point.getY();
	}
	
	public double getZ(double t) {
		return vector.getZ() * t + point.getZ();
	}
	
	public boolean geometricallyEquals(Line3D line) {
		if(line == null) {
			return false;
		}
		if(line == this) {
			return true;
		}
		return hasPoint(line.point) && vector.isMultipleOf(line.vector);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		if(o == this) {
			return true;
		}
		if(o instanceof Line3D line) {
			return point.equals(line.point) && vector.equals(line.vector);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return point.hashCode() ^ vector.hashCode() << 1;
	}
	
	@Override
	public String toString() {
		return point + " + t(" + vector + ")";
	}
	
	public Point getPoint() {
		return point;
	}
	
	public Vector getVector() {
		return vector;
	}
}
