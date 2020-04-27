"use strict";
/***
 *
 * Shado objects file with %
 *
 */
Object.defineProperty(exports, "__esModule", { value: true });
var Renderer = require("./core");
var Rectangle = /** @class */ (function () {
    function Rectangle(percentX, percentY, percentW, percentH, styles) {
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
    Rectangle.prototype.updatePercentages = function () {
        this.x = this.percentX * Renderer.canvas.width;
        this.y = this.percentY * Renderer.canvas.height;
        this.w = this.percentW * Renderer.canvas.width;
        this.h = this.percentH * Renderer.canvas.height;
    };
    Rectangle.prototype.draw = function (context) {
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
    };
    // Other functions
    Rectangle.prototype.hover = function (mousePosition) {
        if (mousePosition.x > this.x && mousePosition.x < (this.x + this.w) && mousePosition.y > this.y && mousePosition.y < (this.y + this.h)) {
            return true;
        }
        else {
            return false;
        }
    };
    Rectangle.prototype.clicked = function (lastClickPos) {
        if (lastClickPos.x > this.x && lastClickPos.x < (this.x + this.w) && lastClickPos.y > this.y && lastClickPos.y < (this.y + this.h)) {
            return true;
        }
    };
    Rectangle.prototype.move = function (percentDX, percentDY) {
        var tempX = percentDX > 1 ? (percentDX / 100) : percentDX;
        var tempY = percentDY > 1 ? (percentDY / 100) : percentDY;
        this.x += Renderer.canvas.width * tempX;
        this.y += Renderer.canvas.height * tempY;
    };
    // Setters
    Rectangle.prototype.stroke = function (color) {
        this.styles.stroke = color;
    };
    Rectangle.prototype.fill = function (color) {
        this.styles.fill = color;
    };
    Rectangle.prototype.lineWidth = function (size) {
        this.styles.lineWidth = size;
    };
    Rectangle.prototype.toggleAutoUpdate = function () {
        if (this.autoUpdate) {
            this.autoUpdate = false;
        }
        else {
            this.autoUpdate = true;
        }
    };
    // getters
    Rectangle.prototype.getCoordinates = function () {
        return { x: this.x, y: this.y };
    };
    Rectangle.prototype.getDimensions = function () {
        return { w: this.w, h: this.h };
    };
    Rectangle.prototype.getArea = function () {
        return this.w * this.h;
    };
    Rectangle.prototype.getPerimetre = function () {
        return (this.w + this.h) * 2;
    };
    return Rectangle;
}());
exports.Rectangle = Rectangle;
var Circle = /** @class */ (function () {
    function Circle(percentX, percentY, percentR, styles) {
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
    Circle.prototype.updatePercentages = function () {
        this.x = this.percentX * Renderer.canvas.width;
        this.y = this.percentY * Renderer.canvas.height;
        this.r = this.percentR * Renderer.canvas.width;
    };
    Circle.prototype.draw = function (context) {
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
    };
    // Other functions
    Circle.prototype.hover = function (mousePosition) {
        var d = Math.sqrt(Math.pow(mousePosition.x - this.x, 2) + Math.pow(mousePosition.y - this.y, 2));
        if (d <= this.r) {
            return true;
        }
        else {
            return false;
        }
    };
    Circle.prototype.clicked = function (lastClickPos) {
        var d = Math.sqrt(Math.pow(lastClickPos.x - this.x, 2) + Math.pow(lastClickPos.y - this.y, 2));
        if (d <= this.r) {
            return true;
        }
        else {
            return false;
        }
    };
    Circle.prototype.move = function (percentDX, percentDY) {
        var tempX = percentDX > 1 ? (percentDX / 100) : percentDX;
        var tempY = percentDY > 1 ? (percentDY / 100) : percentDY;
        this.x += Renderer.canvas.width * tempX;
        this.y += Renderer.canvas.height * tempY;
    };
    // Setters
    Circle.prototype.stroke = function (color) {
        this.styles.stroke = color;
    };
    Circle.prototype.fill = function (color) {
        this.styles.fill = color;
    };
    Circle.prototype.lineWidth = function (size) {
        this.styles.lineWidth = size;
    };
    Circle.prototype.toggleAutoUpdate = function () {
        if (this.autoUpdate) {
            this.autoUpdate = false;
        }
        else {
            this.autoUpdate = true;
        }
    };
    // getters
    Circle.prototype.getCoordinates = function () {
        return { x: this.x, y: this.y };
    };
    Circle.prototype.getRadius = function () {
        return this.r;
    };
    Circle.prototype.getArea = function () {
        return Math.PI * this.r * this.r;
    };
    Circle.prototype.getPerimetre = function () {
        return 2 * Math.PI * this.r;
    };
    return Circle;
}());
exports.Circle = Circle;
