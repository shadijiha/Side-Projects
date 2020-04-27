/***
 *
 * This file contains the GAME class
 *
 */

//https://stackoverflow.com/questions/44849831/responsive-canvas-on-window-resize-event

class GameObject {
	protected name: string;
	protected id: string;
	protected buffer: any;
	protected collision: boolean;
	public static: boolean;
	public x: number;
	public y: number;
	public dx: number;
	public dy: number;
	public r: number;

	public constructor(name: string) {
		this.name = name;
		this.id = this.name + "_" + Math.floor(random(0, 1e8));
		this.buffer = {};

		this.collision = false;
		this.static = true;
	}

	/**
	 * Renders a gameobject to a target canvas.
	 * @abstract
	 * @return {void}
	 */
	render(targetCanvas: Canvas): void {
		throw new Error("must be implemented by subclass!");
	}
	/**
	 * move is an abstract function. It moves the object
	 * a specific amount of X and Y each frame modified
	 * with FPS (Time.deltaTime)
	 * @param {number, Vector} amountX: the amount of x to move the object
	 * @param {number} amountY: the amount of y to move the object
	 * @returns {void}
	 */
	move(amountX: any, amountY?: number): void {
		// If the argument passed is a vector
		if (amountX instanceof Vector) {
			const tempVector = amountX;
			amountX = tempVector.x;
			amountY = tempVector.y;
		}

		if (!this.static) {
			this.x += amountX * Time.deltaTime;
			this.y += amountY * Time.deltaTime;
		} else {
			throw new Error(
				`Cannot move ${this.name} (id: ${this.id}) because object is immovable`
			);
		}
	}

	draw(targetCanvas: Canvas): void {
		this.render(targetCanvas);
	}

	enableCollision(): void {
		// If the object is an instance of canvas, don't add it
		if (this instanceof Canvas) {
			throw new Error("Cannnot tag CANVAS objects as collidable.");
		}
		this.collision = true;

		// See if the object exits already int the EnginGlobal.collisionObjects array
		let exist = false;
		for (let element of EnginGlobal.collisionObjects) {
			if (element.id == this.id) {
				exist = true;
				break;
			}
		}

		// Add object if it doesn't exist
		if (!exist) EnginGlobal.collisionObjects.push(this);
	}

	disableCollision(): void {
		this.collision = false;

		// See if the object exits already int the EnginGlobal.collisionObjects array
		let exist = false;
		for (let element of EnginGlobal.collisionObjects) {
			if (element.id == this.id) {
				exist = true;
				break;
			}
		}

		// Remove the object if it exists
		if (exist) {
			EnginGlobal.collisionObjects.splice(
				EnginGlobal.collisionObjects.indexOf(this),
				1
			);
		}
	}

	collides(other: GameObject): boolean {
		if (!this.collision || !other.collision) {
			// Warn user that he is trying to use collision while turned off on objects
			for (const element of Logger.allLoggers) {
				if (Logger.collisionWarn && Logger.maxCollisionWarn <= 10) {
					element.warn(
						"Attemting to evaluat collision on disableCollision objects. Use Logger.disableCollisionWarn() if you wish to hide this message"
					);
					Logger.maxCollisionWarn += 1;
				}
			}

			if (Logger.allLoggers.length == 0) {
				throw new Error("No valid instance of Logger was found");
			}
			return;
		}

		if (this instanceof Circle && other instanceof Circle) {
			return distance(this.x, this.y, other.x, other.y) <= this.r + other.r;
		} else if (this instanceof Circle && other instanceof Rectangle) {
			const hitbox = new Rectangle(this.x, this.y, this.r * 2, this.r * 2);

			return (
				hitbox.x + hitbox.w >= other.x &&
				hitbox.x <= other.x + other.w &&
				hitbox.y + hitbox.h >= other.y &&
				hitbox.y <= other.y + other.h
			);
		} else if (this instanceof Rectangle && other instanceof Circle) {
			const hitbox = new Rectangle(other.x, other.y, other.r * 2, other.r * 2);

			return (
				this.x + this.w >= hitbox.x &&
				this.x <= hitbox.x + hitbox.w &&
				this.y + this.h >= hitbox.y &&
				this.y <= hitbox.y + hitbox.h
			);
		} else if (this instanceof Rectangle && other instanceof Rectangle) {
			return (
				this.x + this.w >= other.x &&
				this.x <= other.x + other.w &&
				this.y + this.h >= other.y &&
				this.y <= other.y + other.h
			);
		} else if (this instanceof Circle && other instanceof Vertex) {
			return distance(this.x, this.y, other.x, other.y) <= this.r;
		} else if (this instanceof Rectangle && other instanceof Vertex) {
			return (
				other.x > this.x &&
				other.x < this.x + this.w &&
				other.y > this.y &&
				other.y < this.y + this.h
			);
		}
	}

	equals(other: GameObject) {
		return this.id == other.id;
	}

	protected parseToWidth(input: number | string): number {
		if (typeof input === "string") {
			const array: string[] = input.split("");
			array.pop();
			const percentage: string = array.join("");
			const result: number = (Number(percentage) / 100) * window.innerWidth;
			return result;
		} else {
			return input;
		}
	}

	protected parseToHeight(input: number | string): number {
		if (typeof input === "string") {
			let array: string[] = input.split("");
			array.pop();
			const percentage: string = array.join("");
			const result: number = (Number(percentage) / 100) * window.innerHeight;
			return result;
		} else {
			return input;
		}
	}
}

class Canvas extends GameObject {
	public w: number;
	public h: number;
	public width: number;
	public height: number;
	public positionStyle: string;
	public background: string;
	public canvas: any;
	public ctx: any;
	public overwrite: boolean;
	public parent: HTMLElement;
	public static: boolean;

	public constructor(
		posX: number | string,
		posY: number | string,
		width: number | string,
		height: number | string,
		positionStyle?: string,
		parent?: HTMLElement
	) {
		super("canvas");
		this.x = this.parseToWidth(posX);
		this.y = this.parseToHeight(posY);
		this.w = this.parseToWidth(width);
		this.h = this.parseToHeight(height);
		this.width = this.w;
		this.height = this.h;
		this.positionStyle = positionStyle || "absolute";
		this.background = "transparent";
		this.canvas = null;
		this.ctx = null;
		this.overwrite = false;
		this.parent = parent || document.querySelector("body");
		this.static = true;
	}

	render(): void {
		let DOM = `<canvas id="${this.id}" style="position: ${this.positionStyle}; top: ${this.y}; left: ${this.x}; background: ${this.background};" width="${this.w}" height="${this.h}">`;

		// if element doesn't exist add it to the page
		if (!document.getElementById(this.id)) {
			this.parent.innerHTML += DOM;
		} else {
			if (this.overwrite) {
				this.parent.removeChild(document.getElementById(this.id));
				this.parent.innerHTML += DOM;
			}
		}

		// Set context variables
		this.canvas = document.getElementById(this.id);
		this.ctx = this.canvas.getContext("2d");
	}

	clear(fromX?: number, fromY?: number, toX?: number, toY?: number): void {
		fromX = fromX || 0;
		fromY = fromY || 0;
		toX = toX || this.w;
		toY = toY || this.h;

		this.ctx.clearRect(fromX, fromY, toX, toY);
	}

	scale(x: number, y: number) {
		this.ctx.scale(x, y);
	}

	setPosition(newX?: number | string, newY?: number | string): void {
		this.x = this.parseToWidth(newX);
		this.y = this.parseToHeight(newY);
		this.render();
	}

	setBackground(color: string): void {
		this.background = color;
		this.render();
		document.getElementById(this.id).style.background = this.background;
	}

	setWidth(newWidth: number | string): void {
		this.w = this.parseToWidth(newWidth);
		this.width = this.w;
		this.render();
	}

	setHeight(newHeight: number | string): void {
		this.h = this.parseToHeight(newHeight);
		this.height = this.h;
		this.render();
	}

	getMousePosition(): { x: number; y: number } {
		const x = mouse.x - getOffsetLeft(this.canvas);
		const y = mouse.y - getOffsetTop(this.canvas);
		return { x, y };
	}

	toggleOverwrite(booleanValue: boolean): void {
		if (booleanValue == undefined) this.overwrite = !this.overwrite;
		else this.overwrite = booleanValue;
	}
}

class Graphics2D extends Canvas {}

class Circle extends GameObject {
	public x: number;
	public y: number;
	public r: any;
	public fill: string;
	public stroke: string;
	public lineWidth: number;
	public static: boolean;

	public constructor(x: number, y: number, r: any) {
		super("circle");
		this.x = x;
		this.y = y;
		this.r = r;
		this.fill = "white";
		this.stroke = "black";
		this.lineWidth = 1;
		this.static = false;

		if (this.r < 0)
			throw new Error(
				"Cannot initialize a " + this.name + " with a negative radius"
			);
	}

	render(targetCanvas: Canvas): void {
		// Handle no Canvas error
		if (!targetCanvas) {
			throw new Error(
				"Connot render " +
					this.name +
					" on a non specific Canvas. Must provide Canvas object in argument of Object.render()"
			);
		}

		// Handle no Corrdinates error
		if (
			this.x == undefined ||
			this.x == null ||
			isNaN(this.x) ||
			this.y == undefined ||
			this.y == null ||
			isNaN(this.y)
		) {
			throw new Error(
				`${this.name} (${this.id}) X and/or coordinate is/are either undefined, null or NaN.`
			);
		}

		targetCanvas.ctx.beginPath();
		targetCanvas.ctx.arc(this.x, this.y, this.r, 0, Math.PI * 2, false);
		targetCanvas.ctx.fillStyle = this.fill;
		targetCanvas.ctx.strokeStyle = this.stroke;
		targetCanvas.ctx.lineWidth = this.lineWidth;
		targetCanvas.ctx.fill();
		targetCanvas.ctx.stroke();
	}

	hover(): boolean {
		let d = Math.sqrt(
			Math.pow(mouse.x - this.x, 2) + Math.pow(mouse.y - this.y, 2)
		);
		if (d <= this.r) {
			return true;
		}
	}

	clicked(): boolean {
		let d = Math.sqrt(
			Math.pow(mouse.lastClicked.x - this.x, 2) +
				Math.pow(mouse.lastClicked.y - this.y, 2)
		);
		if (d <= this.r) {
			return true;
		}
	}

	area(): number {
		return Math.PI * Math.pow(this.r, 2);
	}

	setFill(newFill: string): void {
		this.fill = newFill;
	}

	setStroke(newStroke: string): void {
		this.stroke = newStroke;
	}

	setLineWidth(newLineWidth: number): void {
		this.lineWidth = newLineWidth;
	}
}

class Rectangle extends GameObject {
	public x: number;
	public y: number;
	public w: any;
	public h: any;
	public fill: string;
	public stroke: string;
	public lineWidth: number;
	public static: boolean;

	public constructor(x: number, y: number, w: any, h: any) {
		super("rectangle");
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.fill = "white";
		this.stroke = "black";
		this.lineWidth = 1;
		this.static = false;

		if (this.w < 0)
			throw new Error(
				"Cannot initialize a " + this.name + " with a negative width"
			);
		if (this.h < 0)
			throw new Error(
				"Cannot initialize a " + this.name + " with a negative height"
			);
	}

	render(targetCanvas: Canvas): void {
		// Handle no Canvas error
		if (!targetCanvas) {
			throw new Error(
				"Connot render " +
					this.name +
					" on a non specific Canvas. Must provide Canvas object in argument of Object.render()"
			);
		}

		// Handle no Corrdinates error
		if (
			this.x == undefined ||
			this.x == null ||
			isNaN(this.x) ||
			this.y == undefined ||
			this.y == null ||
			isNaN(this.y)
		) {
			throw new Error(
				`${this.name} (${this.id}) X and/or coordinate is/are either undefined, null or NaN.`
			);
		}

		targetCanvas.ctx.beginPath();
		targetCanvas.ctx.rect(this.x, this.y, this.w, this.h);
		targetCanvas.ctx.fillStyle = this.fill;
		targetCanvas.ctx.strokeStyle = this.stroke;
		targetCanvas.ctx.lineWidth = this.lineWidth;
		targetCanvas.ctx.fill();
		targetCanvas.ctx.stroke();
	}

	hover(): boolean {
		// is mouse to right of the left-side of the rectangle
		// is mouse to left of the right-side of the rectangle
		// is mouse below the top of the rectangle
		// is mouse above the bottom of the rectangle
		if (
			mouse.x > this.x &&
			mouse.x < this.x + this.w &&
			mouse.y > this.y &&
			mouse.y < this.y + this.h
		) {
			return true;
		}
	}

	clicked(): boolean {
		if (
			mouse.lastClicked.x > this.x &&
			mouse.lastClicked.x < this.x + this.w &&
			mouse.lastClicked.y > this.y &&
			mouse.lastClicked.y < this.y + this.h
		) {
			return true;
		}
	}

	area(): number {
		return this.w * this.h;
	}

	setFill(newFill: string): Rectangle {
		this.fill = newFill;
		return this;
	}

	setStroke(newStroke: string): Rectangle {
		this.stroke = newStroke;
		return this;
	}

	setLineWidth(newLineWidth: number): Rectangle {
		this.lineWidth = newLineWidth;
		return this;
	}
}

class Vertex extends GameObject {
	public x: number;
	public y: number;
	public collision: boolean = true;

	public constructor(x: number, y: number) {
		super("vertex");
		this.x = x;
		this.y = y;
		this.collision = true;
	}
}

class ShadoText extends GameObject {
	public text: string;
	public x: number;
	public y: number;
	public font: string;
	public size: number;
	public color: string;
	public stroke: string;
	public background: string;
	public fullStyle: string;
	public hitBox: Rectangle;

	public constructor(
		text: string,
		x: number,
		y: number,
		{
			font,
			size,
			color,
			stroke,
			background
		}: {
			font?: string;
			size?: number;
			color?: string;
			stroke?: string;
			background?: string;
		}
	) {
		super("text");
		this.text = text;
		this.x = x;
		this.y = y;
		this.font = font || "sans-serif";
		this.size = size || 14;
		this.color = color || "black";
		this.stroke = stroke || this.color;
		this.background = background || "transparent";
		this.static = false;
		this.fullStyle = `${this.size}px ${this.font}`;
		this.hitBox = null;
	}

	render(targetCanvas: Canvas): void {
		// Handle no canvas error
		if (!targetCanvas) {
			throw new Error(
				"Connot render " +
					this.name +
					" on a non specific Canvas. Must provide Canvas object in argument of Object.render()"
			);
		}

		// Handle no Corrdinates error
		if (
			this.x == undefined ||
			this.x == null ||
			isNaN(this.x) ||
			this.y == undefined ||
			this.y == null ||
			isNaN(this.y)
		) {
			throw new Error(
				`${this.name} (${this.id}) X and/or coordinate is/are either undefined, null or NaN.`
			);
		}

		// Draw text
		targetCanvas.ctx.font = `${this.size}px ${this.font}`;

		// Draw background
		// Draw BG from -10% to +20%
		this.buildHitBox(targetCanvas);

		targetCanvas.ctx.fillStyle = this.color;
		targetCanvas.ctx.strokeStyle = this.stroke;
		targetCanvas.ctx.fillText(this.text, this.x, this.y);
		targetCanvas.ctx.strokeText(this.text, this.x, this.y);
	}

	buildHitBox(targetCanvas: Canvas): void {
		// Hitbox from -10% to +20%
		this.hitBox = new Rectangle(
			this.x - this.width(targetCanvas) * 0.1,
			this.y - this.height(targetCanvas) / 2,
			this.width(targetCanvas) * 1.2,
			this.height(targetCanvas)
		);
		this.hitBox.setFill(this.background);
		this.hitBox.setStroke("transparent");
		this.hitBox.render(targetCanvas);
	}

	width(targetCanvas: Canvas): number {
		targetCanvas.ctx.font = this.fullStyle;
		return targetCanvas.ctx.measureText(this.text).width;
	}

	height(targetCanvas: Canvas): number {
		//return this.size - this.size / 5;
		targetCanvas.ctx.font = this.fullStyle;
		let height = parseInt(targetCanvas.ctx.font.match(/\d+/), 10) * 2;
		return height;
	}

	hover(targetCanvas: Canvas): boolean {
		if (this.hitBox == null) {
			this.buildHitBox(targetCanvas);
		}
		return this.hitBox.hover();
	}

	clicked(targetCanvas: Canvas): boolean {
		if (this.hitBox == null) {
			this.buildHitBox(targetCanvas);
		}
		return this.hitBox.clicked();
	}
}

class ShadoImage extends GameObject {
	public src: string;
	public x: number;
	public y: number;
	public w: any;
	public h: any;
	public id: string;
	public hitBox: Rectangle;
	public showHitBox: boolean;

	public constructor(
		src: string,
		x: number,
		y: number,
		w: any,
		h: any,
		id: string,
		showHitBox: boolean
	) {
		super("image");
		this.src = src;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.id = id;
		this.showHitBox = showHitBox;
		this.static = false;

		let allImgs = document.getElementById(this.id);

		if (allImgs == undefined || allImgs == null) {
			let body = document.querySelector("body");
			let img = document.createElement("img");
			img.setAttribute("id", this.id);
			img.setAttribute("src", this.src);
			img.setAttribute("style", "display: none");
			body.appendChild(img);
		}

		this.hitBox = new Rectangle(this.x, this.y, this.w, this.h);
		this.hitBox.setStroke("red");
		this.hitBox.setFill("transparent");

		// Detect errors
		if (this.w < 0)
			throw new Error(
				"Cannot initialize a " + this.name + " with a negative width"
			);
		if (this.h < 0)
			throw new Error(
				"Cannot initialize a " + this.name + " with a negative height"
			);
	}

	render(targetCanvas: Canvas): void {
		// Handle no Canvas error
		if (!targetCanvas) {
			throw new Error(
				"Connot render " +
					this.name +
					" on a non specific Canvas. Must provide Canvas object in argument of Object.render()"
			);
		}

		// Handle no Corrdinates error
		if (
			this.x == undefined ||
			this.x == null ||
			isNaN(this.x) ||
			this.y == undefined ||
			this.y == null ||
			isNaN(this.y)
		) {
			throw new Error(
				`${this.name} (${this.id}) X and/or coordinate is/are either undefined, null or NaN.`
			);
		}

		this.hitBox.x = this.x;
		this.hitBox.y = this.y;

		if (this.x + this.w >= 0 && this.x <= targetCanvas.width) {
			let myImage = <HTMLImageElement>document.getElementById(this.id);
			myImage.src = this.src;

			targetCanvas.ctx.drawImage(myImage, this.x, this.y, this.w, this.h);

			if (this.showHitBox) {
				this.hitBox.render(targetCanvas);
			}
		}
	}

	hover(): boolean {
		return this.hitBox.hover();
	}

	clicked(): boolean {
		/*if (lastClick.x > this.x && lastClick.x < (this.x + this.width) && lastClick.y < this.y && lastClick.y > (this.y - this.height))	{
			return true;
		}*/
		/*if (this.hitBox.clicked()) {
			lastClick = { x: undefined, y: undefined };
			return true;
		} else {
			return false;
		}*/
		throw new Error("lastClick has not been defined yet @ Image");
	}

	updateDimensions(newW: any, newH: any): void {
		this.w = newW;
		this.h = newH;
		this.hitBox.w = newW;
		this.hitBox.h = newH;
	}
}

class Line extends GameObject {
	public fromX: number;
	public fromY: number;
	public toX: number;
	public toY: number;

	public constructor(fromX: any, fromY: any, toX: number, toY: number) {
		super("line");
		if (fromX instanceof Vertex && fromY instanceof Vertex) {
			this.fromX = fromX.x;
			this.fromY = fromX.y;
			this.toX = fromY.x;
			this.toY = fromY.y;
		} else {
			this.fromX = fromX;
			this.fromY = fromY;
			this.toX = toX;
			this.toY = toY;
		}
		this.static = false;
	}

	public render(targetCanvas: Canvas): void {
		targetCanvas.ctx.beginPath();
		targetCanvas.ctx.moveTo(this.fromX, this.fromY);
		targetCanvas.ctx.lineTo(this.toX, this.toY);
		targetCanvas.ctx.stroke();
	}

	public length(): number {
		const temp = new Vector(this.toX - this.fromX, this.toY - this.fromY);
		return temp.mag();
	}

	public split(x: number | string, y?: number): Line[] {
		// if x is a percentage e.g. "50%"
		if (typeof x === "string") {
			let tempX: string[] = x.split("");
			tempX.pop();
			const str = tempX.join("");
			let percentage = Number(str) / 100;

			// Cut the line at that percentage
			const line1Length: number = this.length() * percentage;
			const line2Length: number = this.length() - line1Length;

			// find the angle the line forms
			const angle = Math.atan2(this.toX - this.fromX, this.toY - this.fromY);

			const line1Coord: Vertex = new Vertex(
				Math.sin(angle) * line1Length + this.fromX,
				Math.cos(angle) * line1Length + this.fromY
			);
			const line2Coord: Vertex = new Vertex(
				Math.sin(angle) * line2Length + this.fromX,
				Math.cos(angle) * line2Length + this.fromY
			);

			const line1: Line = new Line(
				this.fromX,
				this.fromY,
				line1Coord.x,
				line1Coord.y
			);
			const line2: Line = new Line(
				line1Coord.x,
				line1Coord.y,
				this.toX,
				this.toY
			);

			return [line1, line2];
		} else {
			throw new Error("This code isn't working @ Line.split(number, number)");
		}
	}

	move(amountX: number | Vector, amountY?: number): void {
		// If the argument passed is a vector
		if (amountX instanceof Vector) {
			const tempVector = amountX;
			amountX = tempVector.x;
			amountY = tempVector.y;
		}

		if (!this.static) {
			this.fromX += amountX * Time.deltaTime;
			this.toX += amountX * Time.deltaTime;
			this.fromY += amountY * Time.deltaTime;
			this.toY += amountY * Time.deltaTime;
		} else {
			throw new Error(
				`Cannot move ${this.name} (id: ${this.id}) because object is imstatic`
			);
		}
	}
}

class Shape extends GameObject {
	private vertices: Vertex[] = [];
	private hitBox: Rectangle[] = [];
	public stringHitBox: string[] = [];
	public fill: string;
	public stroke: string;
	public lineWidth: number;
	public window: ShadoWindow;
	private editor: Canvas;
	private showHitbox: boolean = false;

	public constructor(
		vertices: Vertex[],
		{
			fill,
			stroke,
			lineWidth
		}: {
			fill?: string;
			stroke?: string;
			lineWidth?: number;
		}
	) {
		super("shape");
		this.vertices = vertices;
		this.fill = fill || "transparent";
		this.stroke = stroke || "black";
		this.lineWidth = lineWidth || 1;
		this.static = false;
		this.collision = true;
	}

	public render(targetCanvas: Canvas): void {
		if (this.hitBox.length <= 0) {
			this.generateHitBox();
		}

		targetCanvas.ctx.beginPath();
		targetCanvas.ctx.fillStyle = this.fill;
		targetCanvas.ctx.strokeStyle = this.stroke;
		targetCanvas.ctx.lineWidth = this.lineWidth;

		targetCanvas.ctx.moveTo(this.vertices[0].x, this.vertices[0].y);

		for (const vertex of this.vertices) {
			targetCanvas.ctx.lineTo(vertex.x, vertex.y);
		}

		targetCanvas.ctx.fill();
		targetCanvas.ctx.stroke();

		// Draw hitboxes
		if (this.showHitbox) {
			for (const temp of this.hitBox) {
				temp.draw(targetCanvas);
			}
		}
	}

	public collides(ver: Vertex): any {
		for (const temp of this.hitBox) {
			if (temp.collides(ver)) return temp;
		}
		return false;
	}

	private generateHitBox(): void {
		/*const x = this.vertices[0].x;
		const y = this.vertices[0].y;
		const w = this.vertices[1].x - x;
		const h = this.vertices[1].y - y;

		const temp = new Rectangle(x, y, w, h);
		temp.setFill("rgba(255, 0, 100, 0.3)");

		this.hitBox.push(temp);*/
	}

	public addHitBox(rect: Rectangle): void {
		rect.enableCollision();
		this.hitBox.push(rect);
	}

	public setHitBox(array: Rectangle[]): void {
		this.hitBox = [];
		for (const rect of array) {
			rect.enableCollision();
			this.hitBox.push(rect);
		}
	}

	// setters
	public setFill(color: string): void {
		this.fill = color;
	}
}

class ShadoWindow extends GameObject {
	private DOM: HTMLElement;
	private titleBar: HTMLElement;
	private body: HTMLElement;
	private x: number;
	private y: number;
	private w: number;
	private h: number;
	private title: string;
	private generated: boolean;
	private openned: boolean = false;
	constructor(
		x: number | string,
		y: number | string,
		width: number | string,
		height: number | string,
		title: string
	) {
		super("ShadoWindow");
		this.x = this.parseToWidth(x);
		this.y = this.parseToHeight(y);
		this.w = this.parseToWidth(width);
		this.h = this.parseToHeight(height);

		this.title = title;
		this.generated = false;
		this.static = false;

		this.DOM = createElement("div", $("body"));
		this.DOM.id = this.id;
		this.DOM.style.position = "absolute";
	}

	generate(): void {
		const COLOR = "rgb(50, 0, 190)";

		// Set correct width
		this.DOM.draggable = false;
		this.DOM.style.left = this.x.toString();
		this.DOM.style.top = this.y.toString();
		this.DOM.style.width = this.w + "px";
		this.DOM.style.height = this.h + "px";
		this.DOM.style.zIndex = "+3";
		this.DOM.style.backgroundColor = "white";
		this.DOM.style.border = "solid 2px " + COLOR;
		this.DOM.style.borderBottomRightRadius = "10px";
		this.DOM.style.borderBottomLeftRadius = "10px";
		this.DOM.style.overflow = "auto";

		// Generate Title bar
		const TITLEBAR_PADDING = 10;
		const TITLEBAR_HEIGHT = 35;
		this.titleBar = createElement("div", this.DOM);
		this.titleBar.id = this.id + "_titleBar";
		this.titleBar.draggable = false;
		this.titleBar.style.userSelect = "none"; // Don't allow selection On titleBar
		this.titleBar.style.width = `calc(100% - ${TITLEBAR_PADDING * 2}px)`;
		this.titleBar.style.height = TITLEBAR_HEIGHT + "px";
		this.titleBar.style.backgroundColor = COLOR;
		this.titleBar.style.color = "white";
		this.titleBar.style.padding = TITLEBAR_PADDING + "px";
		this.titleBar.style.fontWeight = "bold";
		this.titleBar.style.fontFamily = "'IBM Plex Serif', sans-serif";
		this.titleBar.style.fontSize = "16pt";
		this.titleBar.innerHTML = this.title;

		// Generate close Button
		const closeButton: HTMLElement = createElement("div", this.DOM);
		closeButton.id = this.id + "_closeButton";
		closeButton.style.position = "absolute";
		closeButton.style.userSelect = "none"; // Don't allow selection On closeButton
		closeButton.style.left = `calc(100% - ${TITLEBAR_HEIGHT +
			TITLEBAR_PADDING * 2}px)`;
		closeButton.style.top = "0px";
		closeButton.style.width = TITLEBAR_HEIGHT + TITLEBAR_PADDING * 2 + "px";
		closeButton.style.height = TITLEBAR_HEIGHT + TITLEBAR_PADDING * 2 + "px";
		closeButton.style.backgroundColor = "rgb(230, 50, 50)";
		closeButton.style.textAlign = "center";
		closeButton.style.verticalAlign = "middle";
		closeButton.style.color = "white";
		closeButton.style.fontFamily = "'IBM Plex Serif', sans-serif";
		closeButton.style.fontSize = "20pt";
		closeButton.style.cursor = "pointer";
		closeButton.innerHTML = "X";

		// Add close window event
		window.addEventListener("load", () => {
			$(`#${this.id}_closeButton`).addEventListener("click", () => {
				this.close();
			});

			$(`#${this.id}_titleBar`).addEventListener("mousemove", () => {
				if (mouse.isDown) {
					this.setX(mouse.x - this.w / 2);
					this.setY(mouse.y - TITLEBAR_HEIGHT / 2);
				}
			});
		});

		// Generate the Body dive
		const BODY_PADDING = 10;

		this.body = createElement("div", this.DOM);
		this.body.id = this.id + "_body";
		this.body.style.width = `calc(100% - ${BODY_PADDING * 2}px)`;
		this.body.style.padding = BODY_PADDING + "px";
		this.body.style.fontFamily = "'IBM Plex Serif', serif";
		this.body.style.overflow = "auto";

		this.body.innerHTML += "Placeholder...";
		this.generated = true;
	}

	CENTER_X(): void {
		let newPosX = window.innerWidth / 2 - this.w / 2;
		this.setX(newPosX);
	}

	CENTER_Y(): void {
		let newPosY = window.innerHeight / 2 - this.h / 2;
		this.setY(newPosY);
	}

	show(): void {
		if (!this.generated) {
			this.generate();
		}

		$(`#${this.id}`).style.display = "block";
		this.openned = true;
	}

	open(): void {
		this.show();
	}

	hide(): void {
		$(`#${this.id}`).style.display = "none";
		this.openned = false;
	}

	close(): void {
		this.hide();
	}

	move(): void {}

	// Getters
	getX(): number {
		return this.x;
	}

	getY(): number {
		return this.y;
	}

	getWidth(): number {
		return this.w;
	}

	getHeight(): number {
		return this.h;
	}

	getTitle(): string {
		return this.title;
	}

	getContent(): string {
		return this.body.innerHTML;
	}

	getID(): string {
		return this.id;
	}

	getBodyElement(): HTMLElement {
		return $(`#${this.id}_body`);
	}

	isOpen() {
		return this.openned;
	}

	// Setters
	setX(newX: number | string): void {
		this.x = this.parseToWidth(newX);
		$(`#${this.id}`).style.left = this.x.toString() + "px";
	}

	setY(newY: number | string): void {
		this.y = this.parseToHeight(newY);
		$(`#${this.id}`).style.top = this.y.toString() + "px";
	}

	setWidth(newWidth: number | string): void {
		this.w = this.parseToWidth(newWidth);
		$(`#${this.id}`).style.width = this.w + "px";
	}

	setHeight(newHeight: number | string): void {
		this.h = this.parseToHeight(newHeight);
		$(`#${this.id}`).style.height = this.h + "px";
	}

	setTitle(newTitle: string): void {
		$(`#${this.id}_titleBar`).innerHTML = newTitle;
	}

	setContent(newContent: string): void {
		$(`#${this.id}_body`).innerHTML = newContent;
	}

	addContent(content: string): void {
		$(`#${this.id}_body`).innerHTML += content;
	}
}
