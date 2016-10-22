import java.util.Arrays;

public class Matrix {

	private int M; // number of rows
	private int N; // number of columns
	private double[][] data; // M-by-N array

	/**
	 * Create new M-by-N matrix
	 * 
	 * @param M
	 * @param N
	 */
	public Matrix(int M, int N) {
		this.M = M;
		this.N = N;
		data = new double[M][N];
	}

	/**
	 * Create new square N-by-N matrix
	 * 
	 * @param N
	 */
	public Matrix(int N) {
		this.M = N;
		this.N = N;
		data = new double[N][N];
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

	private Matrix(Matrix A) {
		this(A.data);
	}

	public int getMDimension() {
		return M;
	}

	public int getNDimension() {
		return N;
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
	public static Matrix Identity(int N) {
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

	/**
	 * Swap rows i and j
	 * 
	 * @param i
	 * @param j
	 */
	private void swap(int i, int j) {
		double[] temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}

	/**
	 * Matrix inversion using Gauss–Jordan elimination
	 * 
	 * @return this^-1
	 */
	public Matrix invert() {
		if (M != N) {
			throw new RuntimeException("Illegal matrix dimensions. Matrix must be square.");
		}
		Matrix X = new Matrix(N);
		Matrix B = Matrix.Identity(N);
		// Upper triangle form
		int index[] = this.gaussian();
		for (int i = 0; i < N - 1; ++i) {
			for (int j = i + 1; j < N; ++j) {
				for (int k = 0; k < N; ++k) {
					B.data[index[j]][k] -= this.data[index[j]][i] * B.data[index[i]][k];
				}
			}
		}
		// Backward substitutions
		for (int i = 0; i < N; ++i) {
			X.data[N - 1][i] = B.data[index[N - 1]][i] / this.data[index[N - 1]][N - 1];
			for (int j = N - 2; j >= 0; --j) {
				X.data[j][i] = B.data[index[j]][i];
				for (int k = j + 1; k < N; ++k) {
					X.data[j][i] -= this.data[index[j]][k] * X.data[k][i];
				}
				X.data[j][i] /= this.data[index[j]][j];
			}
		}
		return X;
	}

	/**
	 * Partial-pivoting Gaussian elimination
	 * 
	 * @return
	 */
	private int[] gaussian() {
		double c[] = new double[N];
		int index[] = new int[N];
		for (int i = 0; i < N; ++i) {
			index[i] = i;
		}
		for (int i = 0; i < N; ++i) {
			double c1 = 0;
			for (int j = 0; j < N; ++j) {
				double c0 = Math.abs(this.data[i][j]);
				if (c0 > c1) {
					c1 = c0;
				}
			}
			c[i] = c1;
		}
		// Pivoting element from each column
		int k = 0;
		for (int j = 0; j < N - 1; ++j) {
			double pi1 = 0;
			for (int i = j; i < N; ++i) {
				double pi0 = Math.abs(this.data[index[i]][j]);
				pi0 /= c[index[i]];
				if (pi0 > pi1) {
					pi1 = pi0;
					k = i;
				}
			}
			// Interchange rows
			int itmp = index[j];
			index[j] = index[k];
			index[k] = itmp;
			for (int i = j + 1; i < N; ++i) {
				double pj = this.data[index[i]][j] / this.data[index[j]][j];
				this.data[index[i]][j] = pj;
				for (int l = j + 1; l < N; ++l) {
					this.data[index[i]][l] -= pj * this.data[index[j]][l];
				}
			}
		}
		return index;
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