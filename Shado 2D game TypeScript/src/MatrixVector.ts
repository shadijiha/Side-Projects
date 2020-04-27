/***
 *
 * Shado MATRIX and VECTOR Library
 *
 */
class Matrix {
	private rows: number;
	private cols: number;
	private data: any[];

	public constructor(rows: number, cols: number) {
		this.rows = rows || 2;
		this.cols = cols || this.rows;
		this.data = [];

		// Initialize matrix with 0s
		for (let i = 0; i < this.rows; i++) {
			this.data[i] = [];

			for (let j = 0; j < this.cols; j++) {
				this.data[i][j] = 0;
			}
		}
	}

	public setData(row: number, col: number, value: any): void {
		this.data[row][col] = value;
	}

	public setMatrix(array: any[]): void {
		this.data = array;
		this.rows = array.length;
		this.cols = array[0].length;
	}

	public getData(row: number, col: number): any {
		return this.data[row][col];
	}

	public randomize(max?: number): void {
		max = Number(max) || 10;

		// Fill matrix with random digits
		for (let i = 0; i < this.rows; i++) {
			for (let j = 0; j < this.cols; j++) {
				this.data[i][j] = Math.floor(Math.random() * max);
			}
		}
	}

	public identity(): void {
		if (this.rows != this.cols) {
			console.log(
				"Error! Connot transform current matrix to Identity matrix because rows and colums count doesn't match."
			);
			return;
		}

		for (let i = 0; i < this.rows; i++) {
			for (let j = 0; j < this.cols; j++) {
				if (i == j) {
					this.data[i][j] = 1;
				} else {
					this.data[i][j] = 0;
				}
			}
		}
	}

	public add(n: any, overwrite?: boolean): any {
		overwrite = overwrite || true;

		if (n instanceof Matrix) {
			// Detect errors
			if (this.cols != n.cols || this.rows != n.rows) {
				console.log(
					"Error! Connot proform Matrix sum operation on matrices with diffrent rows and/or colums count."
				);
				return;
			}

			// Sum
			var temp = new Matrix(this.rows, this.cols);
			for (let i = 0; i < temp.rows; i++) {
				for (let j = 0; j < temp.cols; j++) {
					temp.data[i][j] = this.data[i][j] + n.data[i][j];
				}
			}

			// Update current Matrix or return new one
			if (overwrite) {
				this.data = temp.data;
			} else {
				return temp;
			}
		} else if (!isNaN(Number(n))) {
			for (let i = 0; i < this.rows; i++) {
				for (let j = 0; j < this.cols; j++) {
					this.data[i][j] += n;
				}
			}
		} else {
			console.log(
				"Error! Connot proform Matrix sum because passed argument is neither a %c number %c nor a %c matrix.",
				"color: red; font-weight: bold",
				"",
				"color: blue; font-weight: bold"
			);
			return;
		}
	}

	public multiply(n: any, overwrite?: boolean): any {
		overwrite = overwrite || true;

		if (n instanceof Matrix) {
			// Detect error
			if (this.cols != n.rows) {
				console.log(
					"Error! Connot proform Matrix multiplication operation on matrices because Matrix A colums is not equal to Matrix B rows."
				);
				return;
			}

			var temp = new Matrix(this.rows, n.cols);

			for (let i = 0; i < temp.rows; i++) {
				for (let j = 0; j < temp.cols; j++) {
					var sum = 0;

					for (let k = 0; k < this.cols; k++) {
						sum += this.data[i][k] * n.data[k][j];
					}

					temp.data[i][j] = sum;
				}
			}

			// Update current Matrix
			if (overwrite) {
				this.data = temp.data;
			} else {
				return temp;
			}
		} else if (!isNaN(Number(n))) {
			for (let i = 0; i < this.rows; i++) {
				for (let j = 0; j < this.cols; j++) {
					this.data[i][j] *= n;
				}
			}
		} else {
			console.log(
				"Error! Connot proform Matrix multiplication because passed argument is neither a %c number %c nor a %c matrix.",
				"color: red; font-weight: bold",
				"",
				"color: blue; font-weight: bold"
			);
			return;
		}
	}

	public transpose(): void {
		let temp = new Matrix(this.cols, this.rows);

		for (let i = 0; i < temp.rows; i++) {
			for (let j = 0; j < temp.cols; j++) {
				temp.data[i][j] = this.data[j][i];
			}
		}

		this.data = temp.data;
	}

	private LUdecomposition(): Matrix[] {
		let U = new Matrix(this.rows, this.cols);
		let L = new Matrix(this.rows, this.cols);
		for (let i = 0; i < this.rows; i++) {
			for (let j = 0; j < this.cols; j++) {
				let sum = 0;

				for (let k = 0; k <= i - 1; k++) {
					sum = sum + L.data[j][k] * U.data[k][i];
				}

				if (i === j) {
					U.data[i][j] = 1;
				}
				if (i <= j) {
					L.data[j][i] = this.data[j][i] - sum;
				} else {
					U.data[j][i] = (1 / L.data[j][j]) * (this.data[j][i] - sum);
				}
			}
		}

		return [L, U];
	}

	public determinant(): number {
		// Detect Error
		if (this.rows != this.cols) {
			console.log(
				"Error! This matrix has diffrent row and colums count. Thus, the determinant cannot be calculated."
			);
			return;
		}

		var decomposition = this.LUdecomposition();
		var temp = decomposition[0];
		var mult = 1;

		for (let i = 0; i < temp.rows; i++) {
			for (let j = 0; j < temp.cols; j++) {
				if (i == j) {
					mult *= temp.data[i][j];
				}
			}
		}

		return Math.floor(mult);
	}

	public log(): void {
		console.table(this.data);
	}
}

class Vector {
	public x: number;
	public y: number;
	public z: number;

	public constructor(x: number, y: number, z?: number) {
		this.x = x;
		this.y = y;
		this.z = z || 0;
	}

	public random2D(max?: number): void {
		max = max || 1;
		this.x = Math.random() * max;
		this.y = Math.random() * max;
	}

	public random3D(max?: number): void {
		max = max || 1;
		this.random2D(max);
		this.z = Math.random() * max;
	}

	public floor(): void {
		this.x = Math.floor(this.x);
		this.y = Math.floor(this.y);
		this.z = Math.floor(this.z);
	}

	public getX(): number {
		return this.x;
	}

	public getY(): number {
		return this.y;
	}

	public getZ(): number {
		return this.z;
	}

	public add(objVector: Vector): Vector {
		return new Vector(
			this.x + objVector.x,
			this.y + objVector.y,
			this.z + objVector.z
		);
	}

	public substract(objVector: Vector): Vector {
		return new Vector(
			this.x - objVector.x,
			this.y - objVector.y,
			this.z - objVector.z
		);
	}

	public multiply(k: any): Vector {
		if (k instanceof Vector) {
			return this.crossProduct(k);
		} else if (!isNaN(k)) {
			const copy = new Vector(this.x, this.y, this.x);
			copy.scale(k);
			return copy;
		}
	}

	public scale(k: number) {
		this.x *= k;
		this.y *= k;
		this.z *= k;
	}

	public mag(): number {
		return Math.sqrt(
			Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2)
		);
	}

	public normalize(): void {
		const TEMP = this.mag();

		this.x = this.x / TEMP;
		this.y = this.y / TEMP;
		this.z = this.z / TEMP;
	}

	public project(objVector: Vector): Vector {
		const CONSTANT = this.dotProduct(objVector) / Math.pow(objVector.mag(), 2);
		let result = new Vector(objVector.x, objVector.y, objVector.z);
		result.multiply(CONSTANT);

		return result;
	}

	public dotProduct(objVector: Vector): number {
		return this.x * objVector.x + this.y * objVector.y + this.z * objVector.z;
	}

	public crossProduct(objVector: Vector): Vector {
		let i = this.y * objVector.z - this.z * objVector.y;
		let j = -(this.x * objVector.z - this.z * objVector.x);
		let k = this.x * objVector.y - this.y * objVector.x;

		return new Vector(i, j, k);
	}
}

class Complex {
	private a: number;
	private b: number;
	private phi: number;
	private r: number;

	public constructor(a?: number, b?: number) {
		this.a = a || 0;
		this.b = b || 0;
		this.phi = Math.atan(b / a);
		this.r = Math.sqrt(a * a + b * b);
	}

	public fromPolar(r: number, phi: number): void {
		this.phi = phi;
		this.r = r;
		this.a = r * Math.cos(phi);
		this.b = r * Math.sin(phi);
	}

	public equals(complexNum: Complex): boolean {
		if (this.a == complexNum.a && this.b == complexNum.b) {
			return true;
		} else {
			return false;
		}
	}

	public render(arg?: string): string {
		if (arg != undefined && arg.toLowerCase() == "polar") {
			return `${this.r} * ( cos(${this.phi}) + i sin(${this.phi}) )`;
		} else {
			return `${this.a} + ${this.b} i`;
		}
	}

	public sum(numB: Complex): Complex {
		return new Complex(this.a + numB.a, this.b + numB.b);
	}

	public substract(numB: Complex): Complex {
		return new Complex(this.a - numB.a, this.b - numB.b);
	}

	public multiply(numB: Complex): Complex {
		var result = new Complex();
		result.fromPolar(this.r * numB.r, this.phi + numB.phi);

		return result;
	}

	public divide(numB: Complex): Complex {
		var result = new Complex();
		result.fromPolar(this.r / numB.r, this.phi - numB.phi);

		return result;
	}

	public conjugate(): Complex {
		return new Complex(this.a, -1 * this.b);
	}

	public power(exposant: number): Complex {
		var result = new Complex();
		result.fromPolar(Math.pow(this.r, exposant), this.phi * exposant);

		return result;
	}

	public root(degree: number): Complex[] {
		var result = [];
		var tempR = Math.pow(this.r, 1 / degree);

		for (var i = 0; i < degree; i++) {
			var tempPhi = (this.phi + 2 * Math.PI * i) / degree;
			var temp = new Complex();
			temp.fromPolar(tempR, tempPhi);

			result.push(temp);
		}

		return result;
	}

	public renderRoot(degree: number): string {
		var roots = this.root(degree);
		var str = "";

		for (let root of roots) {
			str += root.render();
			str += "\n";
		}

		return str;
	}

	public realPart(): number {
		return this.a;
	}

	public imaginaryPart(): number {
		return this.b;
	}

	public mag(): number {
		return this.r;
	}

	public log(arg?: string): void {
		console.log(this.render(arg));
	}
}
