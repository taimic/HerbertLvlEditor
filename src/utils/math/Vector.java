package utils.math;

import java.io.Serializable;

public class Vector implements Serializable{
	/**
	 * The serialization ID.
	 */
	private static final long serialVersionUID = 5706080421446300872L;

	/**
	 * The operating system dependent line separator.
	 */
	private static final String NL = System.getProperty("line.separator");

	/**
	 * For accessing the x-coordinate.
	 */
	public static final int X = 0;
	
	/**
	 * For accessing the y-coordinate.
	 */
	public static final int Y = 1;
	
	/**
	 * For accessing the z-coordinate.
	 */
	public static final int Z = 2;
	
	/**
	 * The coordinates of the vector. The length correspond to the dimension.
	 */
	private double[] coordinates;
	
	/**
	 * The dimension of the vector.
	 */
	private int dimension;
	
	/**
	 * Constructor. Creates a vector with the given coordinates.
	 * @param coordinates All coordinates of the vector, which ultimately set the dimension.
	 */
	public Vector(double... coordinates){
		this.coordinates = coordinates;
		this.dimension = this.coordinates.length;
	}
	
	/**
	 * @return Whether or not the current vector is zero, i.e. all coordinates are zero.
	 */
	public boolean isZero(){
		for(double coordinate : coordinates){
			if(coordinate != 0) return false;
		}
		return true;
	}
	
	/**
	 * @return A proper string representation of this string.
	 */
	public String getType(){
		return this.getClass().getSimpleName() + getDimension();
	}
	
	/**
	 * @return The dimension of the vector.
	 */
	public int getDimension(){
		return dimension;
	}

	/**
	 * @return A proper string representation of the vector.
	 */
	@Override
	public String toString(){
		String output = getType() + " {" + NL;
		for(double coordinate : coordinates){
			output += coordinate + NL;
		}
		return (output + "}");
	}
	
	/**
	 * @param sqrt Whether or not to return the squared length
	 * @return The (squared) length of the vector.
	 */
	public double length(boolean sqrt){
		double length = 0;
		for(double coordinate : coordinates){
			length += coordinate * coordinate;
		}
		if(sqrt) return Math.sqrt(length);
		return length;
	}
	
	/**
	 * @param vector The vector to check the dimension against
	 * @return Whether or not the dimensions is valid, i.e. the dimensions are of equal length.
	 */
	public boolean isDimensionValid(Vector vector){
		return this.getDimension() == vector.getDimension();
	}
	
	/**
	 * @param vector The second vector to use for the calculation
	 * @return The calculated dot product.
	 */
    public double dot(Vector vector) {
        if (!isDimensionValid(vector)) throw new InvalidDimensionsException();
        double sum = 0.0;
        for (int i = 0; i < dimension; i++) {
        	sum += (this.coordinates[i] * vector.coordinates[i]);
        }
        return sum;
    }

    /**
     * @return The (Euclidean) magnitude of the vector.
     */
    public double magnitude() {
        return Math.sqrt(this.dot(this));
    }

    /**
     * @param vector The vector to measure the distance to
     * @return The distance to the given vector.
     */
    public double distanceTo(Vector vector) {
        if (!isDimensionValid(vector)) throw new InvalidDimensionsException();
        return this.sub(vector).magnitude();
    }

    /**
     * Adds the given vector with the current one and returns a new vector, thus the original vector is not changed.
     * @param vector The vector to add to the current one
     * @return The sum of the current and the given vector.
     */
    public Vector add(Vector vector) {
        if (!isDimensionValid(vector)) throw new InvalidDimensionsException();
        Vector c = this.getZeroVector();
        for (int i = 0; i < dimension; i++) {
        	c.coordinates[i] = this.coordinates[i] + vector.coordinates[i];
        }
        return c;
    }
    
    /**
     * Adds the given values to the current vector and returns a new vector, thus the original vector is not changed.
     * @param values The values to add, in order of the coordinates
     * @return The sum of the current and the given vector.
     */
    public Vector add(double... values){
    	return this.add(new Vector(values));
    }

    /**
     * Subtracts the given vector from the current one and returns a new vector, thus the original vector is not changed.
     * @param vector The vector to subtract from the current one
     * @return The result of the subtraction.
     */
    public Vector sub(Vector vector) {
        if (!isDimensionValid(vector)) throw new InvalidDimensionsException();
        Vector c = this.getZeroVector();
        for (int i = 0; i < dimension; i++) {
        	c.coordinates[i] = this.coordinates[i] - vector.coordinates[i];
        }
        return c;
    }

    /**
     * @param index The index of the coordinate to get
     * @return The value of the coordinate.
     */
    public double get(int index) {
        return this.coordinates[index];
    }

    /**
     * Sets the coordinate with the given index to the given value.
     * @param index The index of the coordinate to set
     * @param value The value to set
     * @return The object itself, for chaining.
     */
    public Vector set(int index, double value){
    	this.coordinates[index] = value;
    	return this;
    }
    
    /**
     * Multiplies the current vector with the given factor and returns a new vector, thus the original vector is not changed.
     * @param factor The factor to multiply with
     * @return A new, scaled vector.
     */
    public Vector scale(double factor) {
        Vector c = this.getZeroVector();
        for (int i = 0; i < dimension; i++){
            c.coordinates[i] = factor * coordinates[i];        	
        }
        return c;
    }
    
    /**
     * @return The direction of the current vector. Or a zero-vector, if the magnitude is zero.
     */
    public Vector direction() {
        if (this.magnitude() == 0.0) return getZeroVector();
        return this.scale(1.0 / this.magnitude());
    }
    
    /**
     * Copies the current vector into a new instance.
     * @return A new instance with the value of the current vector.
     */
    public Vector copy(){
    	return new Vector(this.coordinates);
    }

    /**
     * @return A zero vector with the dimension of the current vector, i.e. all coordinates are zero.
     */
    public Vector getZeroVector(){
    	return new Vector(new double[this.coordinates.length]);
    }
    
    /**
     * @return The coordinates of the vector.
     */
    public double[] getCoordinates(){
    	return this.coordinates;
    }
    
    public Vector multiplyComponentwise(Vector vector){
        if (!isDimensionValid(vector)) throw new InvalidDimensionsException();
        Vector buffer = Vector.getZeroVector(vector.getDimension());
    	for(int i = 0; i < this.coordinates.length; i++){
    		buffer.set(i, this.coordinates[i] * vector.getCoordinates()[i]);
    	}
    	return buffer;
    }
    
    /**
     * @param dimension The dimension of the zero vector.
     * @return The new vector.
     */
    public static Vector getZeroVector(int dimension){
    	return new Vector(new double[dimension]);
    }
    
    /**
     * This class provides an exception for the vector. This should be thrown when the dimensions of two vectors to not fit,
     * but have to for the operation to be successfully executed.
     */
    class InvalidDimensionsException extends RuntimeException{
    	/**
    	 * The serialization ID.
    	 */
		private static final long serialVersionUID = 8437598043802590426L;

		/**
		 * The message that shall be displayed when this exception is thrown.
		 */
		public String getMessage(){
    		return "Invalid dimensions for the given vectors.";
    	}
    }
}