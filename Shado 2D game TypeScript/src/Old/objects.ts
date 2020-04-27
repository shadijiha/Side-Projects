/***
 * 
 * Shado objects file with %
 * 
 */

import * as ShadoMath from "./MatrixVector";
import * as Renderer from "./core";

export class Rectangle {

    private percentX: number;
    private percentY: number;
    private percentW: number;
    private percentH: number;
    private styles: any;

    private x: number;
    private y: number;
    private w: number;
    private h: number;

    private autoUpdate: boolean;


    public constructor(percentX: number, percentY: number, percentW: number, percentH: number, styles?: any) {

        this.percentX = percentX > 1 ? (percentX / 100) : percentX;
        this.percentY = percentY > 1 ? (percentY / 100) : percentY;
        this.percentW = percentW > 1 ? (percentW / 100) : percentW;
        this.percentH = percentH > 1 ? (percentH / 100) : percentH;

        this.x = this.percentX * Renderer.canvas.width;
        this.y = this.percentY * Renderer.canvas.height;
        this.w = this.percentW * Renderer.canvas.width;
        this.h = this.percentH * Renderer.canvas.height;

        this.autoUpdate = true;

        // Styles
        this.styles = styles || { fill: "white", stroke: "black", lineWidth: 1 };

    }

    private updatePercentages(): void {
        this.x = this.percentX * Renderer.canvas.width;
        this.y = this.percentY * Renderer.canvas.height;
        this.w = this.percentW * Renderer.canvas.width;
        this.h = this.percentH * Renderer.canvas.height;
    }

    public draw(context: any): void {

        // if context is undefined default to "c"
        context = context || Renderer.c;

        // Draw rectangle
        Renderer.c.beginPath();
        Renderer.c.strokeStyle = this.styles.stroke;
        Renderer.c.fillStyle = this.styles.fill;
        Renderer.c.lineWidth = this.styles.lineWidth;
        Renderer.c.rect(this.x, this.y, this.w, this.h);
        Renderer.c.stroke();
        Renderer.c.fill();

        // Auto update
        if (this.autoUpdate) {
            this.updatePercentages();
        }
    }

    // Other functions
    public hover(mousePosition: any): boolean {
        if (mousePosition.x > this.x && mousePosition.x < (this.x + this.w) && mousePosition.y > this.y && mousePosition.y < (this.y + this.h)) {
            return true;
        } else {
            return false;
        }
    }

    public clicked(lastClickPos: any): boolean {
        if (lastClickPos.x > this.x && lastClickPos.x < (this.x + this.w) && lastClickPos.y > this.y && lastClickPos.y < (this.y + this.h)) {
            return true;
        }
    }

    public move(percentDX: number, percentDY: number): void {

        const tempX = percentDX > 1 ? (percentDX / 100) : percentDX;
        const tempY = percentDY > 1 ? (percentDY / 100) : percentDY;

        this.x += Renderer.canvas.width * tempX;
        this.y += Renderer.canvas.height * tempY;

    }

    // Setters
    public stroke(color: string): void {
        this.styles.stroke = color;
    }

    public fill(color: string): void {
        this.styles.fill = color;
    }

    public lineWidth(size: number): void {
        this.styles.lineWidth = size;
    }

    public toggleAutoUpdate(): void {
        if (this.autoUpdate) {
            this.autoUpdate = false;
        } else {
            this.autoUpdate = true;
        }
    }

    // getters
    public getCoordinates(): any {
        return { x: this.x, y: this.y };
    }

    public getDimensions(): any {
        return { w: this.w, h: this.h };
    }

    public getArea(): number {
        return this.w * this.h;
    }

    public getPerimetre(): number {
        return (this.w + this.h) * 2;
    }
}

export class Circle {

    private percentX: number;
    private percentY: number;
    private percentR: number;
    private styles: any;

    private x: number;
    private y: number;
    private r: number;

    private autoUpdate: boolean;

    public constructor(percentX: number, percentY: number, percentR: number, styles?: any) {

        this.percentX = percentX > 1 ? (percentX / 100) : percentX;
        this.percentY = percentY > 1 ? (percentY / 100) : percentY;
        this.percentR = percentR > 1 ? (percentR / 100) : percentR;


        this.x = this.percentX * Renderer.canvas.width;
        this.y = this.percentY * Renderer.canvas.height;
        this.r = this.percentR * Renderer.canvas.width;

        this.autoUpdate = true;

        // Styles
        this.styles = styles || { fill: "white", stroke: "black", lineWidth: 1 };

    }

    private updatePercentages(): void {
        this.x = this.percentX * Renderer.canvas.width;
        this.y = this.percentY * Renderer.canvas.height;
        this.r = this.percentR * Renderer.canvas.width;
    }

    public draw(context: any): void {

        // if context is undefined default to "c"
        context = context || Renderer.c;

        // Draw rectangle
        Renderer.c.beginPath();
        Renderer.c.strokeStyle = this.styles.stroke;
        Renderer.c.fillStyle = this.styles.fill;
        Renderer.c.lineWidth = this.styles.lineWidth;
        Renderer.c.arc(this.x, this.y, this.r, 0, Math.PI * 2, false);
        Renderer.c.stroke();
        Renderer.c.fill();

        // Auto update
        if (this.autoUpdate) {
            this.updatePercentages();
        }
    }

    // Other functions
    public hover(mousePosition: any): boolean {
        let d = Math.sqrt(Math.pow(mousePosition.x - this.x, 2) + Math.pow(mousePosition.y - this.y, 2));

        if (d <= this.r) {
            return true;
        } else {
            return false;
        }
    }

    public clicked(lastClickPos: any): boolean {
        let d = Math.sqrt(Math.pow(lastClickPos.x - this.x, 2) + Math.pow(lastClickPos.y - this.y, 2));

        if (d <= this.r) {
            return true;
        } else {
            return false;
        }
    }

    public move(percentDX: number, percentDY: number): void {

        const tempX = percentDX > 1 ? (percentDX / 100) : percentDX;
        const tempY = percentDY > 1 ? (percentDY / 100) : percentDY;

        this.x += Renderer.canvas.width * tempX;
        this.y += Renderer.canvas.height * tempY;
    }

    // Setters
    public stroke(color: string): void {
        this.styles.stroke = color;
    }

    public fill(color: string): void {
        this.styles.fill = color;
    }

    public lineWidth(size: number): void {
        this.styles.lineWidth = size;
    }

    public toggleAutoUpdate(): void {
        if (this.autoUpdate) {
            this.autoUpdate = false;
        } else {
            this.autoUpdate = true;
        }
    }

    // getters
    public getCoordinates(): any {
        return { x: this.x, y: this.y };
    }

    public getRadius(): number {
        return this.r;
    }

    public getArea(): number {
        return Math.PI * this.r * this.r;
    }

    public getPerimetre(): number {
        return 2 * Math.PI * this.r;
    }

}
