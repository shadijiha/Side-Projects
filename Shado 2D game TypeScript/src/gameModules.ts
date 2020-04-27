/***
 *
 * This file contains the Player, Item, ground classes
 *
 */

class Ground extends GameObject {
	public x: number;
	public y: number;
	public w: number;
	public h: number;
	public background: string = "black";
	public static default = "green";
	public static allGrounds: Ground[] = [];
	public display: boolean = true;

	constructor(x: any, y: any, w: any, h: any, background?: string) {
		super("ground");
		this.x = this.parseToWidth(x);
		this.y = this.parseToHeight(y);
		this.w = this.parseToWidth(w);
		this.h = this.parseToHeight(h);
		this.background = background || Ground.default;

		let exists: boolean = false;
		Ground.allGrounds.forEach(g => {
			if (g.x == this.x && g.y == this.y && g.w == this.w && g.h == this.h) {
				exists = true;
			}
		});

		if (!exists) {
			Ground.allGrounds.push(this);
		}
	}

	public hide(): void {
		this.display = false;
	}

	public render(targetCanvas: Canvas) {
		const rect = new Rectangle(this.x, this.y, this.w, this.h);
		rect.setFill(this.background);
		rect.render(targetCanvas);
	}
}

class Player extends GameObject {
	public x: number;
	public y: number;
	public w: number;
	public h: number;
	public background: string = "black";
	public static default = "pink";

	public constructor(x: any, y: any, w: any, h: any, background?: string) {
		super("ground");
		this.x = this.parseToWidth(x);
		this.y = this.parseToHeight(y);
		this.w = this.parseToWidth(w);
		this.h = this.parseToHeight(h);
		this.background = background || Player.default;
	}

	public render(targetCanvas: Canvas) {
		const rect = new Rectangle(this.x, this.y, this.w, this.h);
		rect.setFill(this.background);
		rect.render(targetCanvas);
	}
}

class Terrain extends GameObject {
	public shape: Shape;
	public static allTerrain: Terrain[] = [];

	public constructor(shape: Shape) {
		super("terrain");
		this.shape = shape;

		// Push to the array
		let exists = false;
		for (let i = 0; i < Terrain.allTerrain.length; i++) {
			if (Terrain.allTerrain[i] == this) exists = true;
		}

		if (!exists) Terrain.allTerrain.push(this);
	}

	render(targetCanvas: Canvas): void {
		this.shape.render(targetCanvas);
	}
}
