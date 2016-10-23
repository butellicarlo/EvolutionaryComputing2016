import java.util.Arrays;
import java.util.Random;
import java.lang.*;

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
	 * Determinant
	 * 
	 * @return determinant
	 */
	public double determinant() {
		if (M != N) {
			throw new RuntimeException("Illegal matrix dimensions. Matrix must be square.");
		}
		if (N == 1) {
			return data[0][0];
		}
		if (N == 2) {
			return (data[0][0] * data[1][1]) - (data[0][1] * data[1][0]);
		}
		double det = 0.0;
		for (int i = 0; i < N; i++) {
			det += alternateSign(i) * data[0][i] * createSubMatrix(0, i).determinant();
		}
		return det;
	}

	/**
	 * Sub-matrix excluding row r and col c
	 * 
	 * @param r
	 * @param c
	 * @return this (with out row r and col c)
	 */
	public Matrix createSubMatrix(int r, int c) {
		Matrix A = new Matrix(M - 1, N - 1);
		int row = -1;
		for (int i = 0; i < M; i++) {
			if (i == r) {
				continue;
			}
			row++;
			int col = -1;
			for (int j = 0; j < N; j++) {
				if (j == c) {
					continue;
				}
				A.data[row][++col] = data[i][j];
			}
		}
		return A;
	}

	/**
	 * Matrix of cofactors
	 * 
	 * @return cofactor matrix
	 */
	public Matrix cofactor() {
		Matrix A = new Matrix(M, N);
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				A.data[i][j] = alternateSign(i) * alternateSign(j) * createSubMatrix(i, j).determinant();
			}
		}
		return A;
	}

	/**
	 * Inverse matrix
	 * 
	 * @return this^-1
	 */
	public Matrix inverse() {
		return this.cofactor().multiply(1.0 / this.determinant()).transpose();
	}

	private static int alternateSign(int i) {
		return (i % 2 == 0) ? 1 : -1;
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

	public void print() {
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(data[i][j] + "\t");
			}
			System.out.println();
		}
	}

	/**
	 * Check if the matrix is symmetric
	 * 
	 * @return true or false
	 */
	public static boolean isSymmetric(Matrix A) {
		int N = A.getNDimension();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < i; j++) {
				if (A.getValue(i, j) != A.getValue(j, i))
					return false;
			}
		}
		return true;
	}

	/**
	 * Cholesky Decomposition
	 * 
	 * @param V
	 * @return V => LL^T
	 */
	public static Matrix choleskyDecomposition(Matrix V) {

		int n = V.getNDimension();
		Matrix L = new Matrix(n);

		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= i; j++) {
				double sum = 0.0;
				for (int k = 0; k < j; k++) {
					sum += L.getValue(i, k) * L.getValue(j, k);
				}
				if (i == j) {
					double tmp = V.getValue(i, i) - sum;
					if (tmp < 0)
						L.setValue(i, i, -1.0 * Math.sqrt(Math.abs(tmp)));
					else
						L.setValue(i, i, Math.sqrt(tmp));
				} else {
					L.setValue(i, j, (1.0 / L.getValue(j, j)) * (V.getValue(i, j) - sum));
				}
			}
		}
		return L;
	}

	/**
	 * Multivariate Gaussian Distribution
	 * 
	 * @param L
	 * @param mean
	 * @param rand
	 * @return LZ + M
	 */
	public static Vector multivariateGaussianDistribution(Matrix V, Vector mean, Random rand) {
		if (!isSymmetric(V))
			V = V.add(V.transpose());
		Matrix L = choleskyDecomposition(V);
		Vector Z = new Vector(L.getNDimension());
		for (int i = 0; i < Z.getDimension(); i++) {
			Z.setValue(i, rand.nextGaussian());
		}
		Vector sample = (L.multiply(mean)).add(mean);
		return sample;
	}

	public static void main(String[] args) {
		Random r = new Random();

		int n = 3;

		Matrix V = new Matrix(n);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				V.setValue(i, j, -5.0 + r.nextDouble() * 10);
			}
		}
		System.out.println("Floor:");
		System.out.println(4 + Math.floor(3 * Math.log(n)));
		System.out.println("|---------------------|");

		System.out.println("V");
		V.print();
		System.out.println("----------------------");

		Vector mean = new Vector(n);
		for (int i = 0; i < n; i++) {
			mean.setValue(i, r.nextGaussian());
		}
		System.out.println("mean");
		mean.print();
		System.out.println("----------------------");

		Vector I = multivariateGaussianDistribution(V, mean, r);

		System.out.println("I");
		I.print();
		System.out.println("----------------------");

	}
}