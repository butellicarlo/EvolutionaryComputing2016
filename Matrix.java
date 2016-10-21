import java.util.Arrays;

public class Matrix {

	private int M; // number of rows
	private int N; // number of columns
	private double[][] data; // M-by-N array

	public Matrix(int M, int N) {
		this.M = M;
		this.N = N;
		data = new double[M][N];
	}

	public Matrix(double[][] data) {
		M = data.length;
		N = data[0].length;
		this.data = new double[M][N];
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				this.data[i][j] = data[i][j];
			}
		}
	}

	public int getNDimension() {
		return N;
	}

	public int getMDimension() {
		return M;
	}

	public double getValue(int row, int column) {
		if (row < 0 || row >= M || column < 0 || column >= N) {
			throw new IllegalArgumentException("Matrix out of bounds.");
		}
		return this.data[row][column];
	}

	public void setValue(int row, int column, double value) {
		if (row < 0 || row >= M || column < 0 || column >= N) {
			throw new IllegalArgumentException("Matrix out of bounds.");
		}
		this.data[row][column] = value;
	}

	/**
	 * Identity matrix
	 * 
	 * @param N
	 * @return I (N-by-N)
	 */
	public static Matrix identity(int N) {
		Matrix I = new Matrix(N, N);
		for (int i = 0; i < N; i++) {
			I.data[i][i] = 1.0;
		}
		return I;
	}

	/**
	 * Matrix transposition
	 * 
	 * @return A = this^T
	 */
	public Matrix transpose() {
		Matrix A = new Matrix(N, M);
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				A.data[j][i] = this.data[i][j];
			}
		}
		return A;
	}

	/**
	 * Matrix-matrix addition
	 * 
	 * @param B
	 * @return this + B
	 */
	public Matrix add(Matrix B) {
		if (this.N != B.N || this.M != B.M) {
			throw new RuntimeException("Illegal matrix dimensions.");
		}
		Matrix C = new Matrix(M, N);
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				C.data[i][j] = this.data[i][j] + B.data[i][j];
			}
		}
		return C;
	}

	/**
	 * Matrix-matrix subtraction
	 * 
	 * @param B
	 * @return C = this - B
	 */
	public Matrix substract(Matrix B) {
		if (this.N != B.N || this.M != B.M) {
			throw new RuntimeException("Illegal matrix dimensions.");
		}
		Matrix C = new Matrix(M, N);
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				C.data[i][j] = this.data[i][j] - B.data[i][j];
			}
		}
		return C;
	}

	/**
	 * Matrix-matrix multiplication
	 * 
	 * @param B
	 * @return C = this * B
	 */
	public Matrix multiply(Matrix B) {
		if (this.N != B.M) {
			throw new RuntimeException("Illegal matrix dimensions.");
		}
		Matrix C = new Matrix(this.M, B.N);
		for (int i = 0; i < this.M; i++) {
			for (int j = 0; j < B.N; j++) {
				for (int k = 0; k < this.N; k++) {
					C.data[i][j] += this.data[i][k] * B.data[k][j];
				}
			}
		}
		return C;
	}

	/**
	 * Matrix-vector multiplication
	 * 
	 * @param x
	 * @return y = this * x
	 */
	public Vector multiply(Vector x) {
		if (x.getDimension() != N) {
			throw new RuntimeException("Illegal matrix dimensions.");
		}
		Vector y = new Vector(M);
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				double value = y.getValue(i) + this.data[i][j] * x.getValue(j);
				y.setValue(i, value);
			}
		}
		return y;
	}

	/**
	 * Matrix-scalar multiplication
	 * 
	 * @param scalar
	 * @return C = this * scalar
	 */
	public Matrix multiply(double scalar) {
		Matrix C = new Matrix(M, N);
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				C.data[i][j] = this.data[i][j] * scalar;
			}
		}
		return C;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + M;
		result = prime * result + N;
		result = prime * result + Arrays.deepHashCode(data);
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
		if (!(obj instanceof Matrix)) {
			return false;
		}
		Matrix other = (Matrix) obj;
		if (M != other.M) {
			return false;
		}
		if (N != other.N) {
			return false;
		}
		if (!Arrays.deepEquals(data, other.data)) {
			return false;
		}
		return true;
	}
}