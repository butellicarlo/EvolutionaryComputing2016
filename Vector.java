import java.util.Arrays;

public class Vector {

	private int N; // dimension
	private double fitness; // fitness
	protected double[] data;

	/**
	 * Create new N-dimensional vector
	 * 
	 * @param N
	 */
	public Vector(int N) {
		this.N = N;
		this.fitness = 0;
		data = new double[N];
	}

	public Vector(double[] data) {
		N = data.length;
		this.fitness = 0;
		this.data = new double[N];
		for (int i = 0; i < N; i++) {
			this.data[i] = data[i];
		}
	}
	
	public Vector(Vector other) {
		this(other.data);
		this.fitness = 0;
	}

	public int getDimension() {
		return N;
	}

	public void setFitness(double fit){
		this.fitness = fit;
	}

	public double getFitness(){
		return this.fitness;
	}

	public double getValue(int n) {
		if (n < 0 || n >= this.N) {
			throw new IllegalArgumentException("Get: Vector out of bounds.");
		}
		return this.data[n];
	}

	public void setValue(int n, double value) {
		if (n < 0 || n >= this.N) {
			throw new IllegalArgumentException("Set: Vector out of bounds.");
		}
		this.data[n] = value;
	}

	public double[] getDoubleArray(){
		double[] v = new double[this.N];
		for(int i = 0; i < this.N; i++)
			v[i] = this.getValue(i);
		return v;
	}

	/**
	 * Zero vector
	 * 
	 * @return zero vector
	 */
	public static Vector zero(int N) {
		double[] data = new double[N];
		for (int i = 0; i < N; i++) {
			data[i] = 0.0;
		}
		return new Vector(data);
	}

	/**
	 * Vector-vector addition
	 * 
	 * @param y
	 * @return z = this + y
	 */
	public Vector add(Vector y) {
		if (this.N != y.N) {
			throw new RuntimeException("Illegal vector dimensions.");
		}
		Vector z = new Vector(N);
		for (int i = 0; i < N; i++) {
			z.data[i] = this.data[i] + y.data[i];
		}
		return z;
	}

	/**
	 * Vector-vector subtraction
	 * 
	 * @param y
	 * @return z = this - y
	 */
	public Vector subtract(Vector y) {
		if (this.N != y.N) {
			throw new RuntimeException("Illegal vector dimensions.");
		}
		Vector z = new Vector(N);
		for (int i = 0; i < N; i++) {
			z.data[i] = this.data[i] - y.data[i];
		}
		return z;
	}

	/**
	 * Negative vector
	 * 
	 * @return -this
	 */
	public Vector negative() {
		Vector y = new Vector(N);
		for (int i = 0; i < N; i++) {
			y.data[i] = -this.data[i];
		}
		return y;
	}

	/**
	 * Computes the euclidean norm also called length, magnitude or norm
	 * 
	 * @return norm
	 */
	public double norm() {
		double sum = 0.0;
		for (int i = 0; i < N; i++) {
			sum += data[i] * data[i];
		}
		return Math.sqrt(sum);
	}

	/**
	* Computes the sum of the elements in the vector
	* @return sum
	*/
	public double sum(){
		double sum = 0.0;
		for(int i = 0; i < N; i++){
			sum += data[i];
		}
		return sum;
	}

	/**
	* Computes the sum of the suared elements
	* @return sum
	*/
	public double sum_squares(){
		double sum = 0.0;
		for (int i = 0; i < N; i++) {
			sum += data[i] * data[i];
		}
		return sum;
	}

	/**
	 * Vector-matrix multiplication
	 * 
	 * @param A
	 * @return y = x^T * A
	 */
	public Vector multiply(Matrix A) {
		int m = A.getMDimension();
		int n = A.getNDimension();
		if (N != m) {
			throw new RuntimeException("Illegal matrix dimensions.");
		}
		Vector y = new Vector(n);
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < m; i++) {
				y.data[j] += A.getValue(i, j) * this.data[i];
			}
		}
		return y;
	}

	/**
	 * Vector-scalar multiplication
	 * 
	 * @param scalar
	 * @return y = this * scalar
	 */
	public Vector multiply(double scalar) {
		Vector y = new Vector(N);
		for (int i = 0; i < N; i++) {
			y.data[i] = this.data[i] * scalar;
		}
		return y;
	}
	
	/**
	 * Vector-scalar division
	 * 
	 * @param scalar
	 * @return y = this * scalar
	 */
	public Vector divide(double scalar) {
		Vector y = new Vector(N);
		for (int i = 0; i < N; i++) {
			y.data[i] = this.data[i] / scalar;
		}
		return y;
	}
	
	public Matrix rankOneMatrix() {
		throw new RuntimeException("implement rankOneMatrix()");
		// TODO: implement this
		// TODO: this * this^T
	}

	/**
	 * Dot product
	 * 
	 * @param y
	 * @return this · y
	 */
	public double dot(Vector y) {
		if (N != y.N) {
			throw new RuntimeException("Illegal vector dimensions.");
		}
		double sum = 0.0;
		for (int i = 0; i < N; i++) {
			sum += this.data[i] * y.data[i];
		}
		return sum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + N;
		result = prime * result + Arrays.hashCode(data);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Vector)) {
			return false;
		}
		Vector other = (Vector) obj;
		if (N != other.N) {
			return false;
		}
		if (!Arrays.equals(data, other.data)) {
			return false;
		}
		return true;
	}

	public void print() {
		System.out.print("[ ");
		for (int i = 0; i < N; i++) {
			System.out.print(data[i] + " - ");
		}
		System.out.print("]\n");
	}
}