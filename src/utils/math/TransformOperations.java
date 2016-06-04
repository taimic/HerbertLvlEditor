package utils.math;

import java.awt.geom.AffineTransform;

public class TransformOperations {
	/**
	 * The number of points that shall be transformed.
	 */
	public static final int TRANSFORM_POINTS_NUMBER = 1;
	/**
	 * The offset of the transformation.
	 */
	public static final int TRANSFORM_OFFSET = 0;
	
	/**
	 * Rotates the given {@see Vector} over itself by the given angle.
	 * @param vector The {@see Vector} to rotate
	 * @param angle The angle to rotate over
	 * @return The new rotated {@see Vector}
	 */
	public static Vector rotate(Vector vector, float angle){
		return rotate(vector, vector, angle);
	}
	
	/**
	 * Rotates the given {@see Vector} over the given center by the given angle.
	 * @param vector The {@see Vector} to rotate
	 * @param center The {@see Vector} to rotate over
	 * @param angle The angle to rotate over
	 * @return The new rotated {@see Vector}
	 */
	public static Vector rotate(Vector vector, Vector center, float angle){
		double[] rotatedPoint = {vector.get(Vector.X), vector.get(Vector.Y)};
		AffineTransform.getRotateInstance(Math.toRadians(angle), center.get(Vector.X), center.get(Vector.Y)).transform(rotatedPoint, TRANSFORM_OFFSET, rotatedPoint, TRANSFORM_OFFSET, TRANSFORM_POINTS_NUMBER);
		return new Vector(rotatedPoint[0], rotatedPoint[1]);
	}
}
