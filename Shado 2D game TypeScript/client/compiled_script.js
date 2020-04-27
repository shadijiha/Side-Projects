function $(args) {
    return document.querySelector(args);
}
function getElement(args) {
    return $(args);
}
function createElement(type, parent) {
    var BODY = $("body");
    var ele = document.createElement(type);
    if (parent) {
        parent.appendChild(ele);
    }
    else {
        BODY.appendChild(ele);
    }
    return ele;
}
function deleteElement(element) {
    try {
        var PARENT = element.parentElement;
        PARENT.removeChild(element);
    }
    catch (e) {
        console.error(e.message);
    }
}
function getOffsetLeft(elem) {
    var offsetLeft = 0;
    do {
        if (!isNaN(elem.offsetLeft)) {
            offsetLeft += elem.offsetLeft;
        }
    } while ((elem = elem.offsetParent));
    return offsetLeft;
}
function getOffsetTop(elem) {
    var offsetTop = 0;
    do {
        if (!isNaN(elem.offsetTop)) {
            offsetTop += elem.offsetTop;
        }
    } while ((elem = elem.offsetParent));
    return offsetTop;
}
function sleep(ms) {
    return new Promise(function (resolve) { return setTimeout(resolve, ms); });
}
function random(min, max) {
    if (max == undefined || max == null) {
        max = min;
        min = 0;
    }
    return Math.random() * (max - min) + min;
}
function floor(number) {
    return Math.floor(number);
}
function randomColor() {
    return "rgb(" + floor(random(0, 255)) + ", " + floor(random(0, 255)) + ", " + floor(random(0, 255)) + ")";
}
function distance(x1, y1, x2, y2) {
    return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
}
function pause() {
    EnginGlobal.oldFPS = EnginGlobal.FPS;
    EnginGlobal.FPS = 0;
    EnginGlobal.PAUSED = true;
}
function resume() {
    EnginGlobal.FPS = EnginGlobal.oldFPS;
    EnginGlobal.PAUSED = false;
    gameLoop();
}
function map(input, input_start, input_end, output_start, output_end) {
    return (output_start +
        ((output_end - output_start) / (input_end - input_start)) *
            (input - input_start));
}
var mouse = {
    x: undefined,
    y: undefined,
    lastClicked: { x: undefined, y: undefined },
    isDown: false
};
window.addEventListener("click", function (event) {
    mouse.lastClicked = { x: event.x, y: event.y };
});
window.addEventListener("mousemove", function (event) {
    mouse.x = event.x;
    mouse.y = event.y;
});
window.addEventListener("mousedown", function () {
    mouse.isDown = true;
});
window.addEventListener("mouseup", function () {
    mouse.isDown = false;
});
var Matrix = (function () {
    function Matrix(rows, cols) {
        this.rows = rows || 2;
        this.cols = cols || this.rows;
        this.data = [];
        for (var i = 0; i < this.rows; i++) {
            this.data[i] = [];
            for (var j = 0; j < this.cols; j++) {
                this.data[i][j] = 0;
            }
        }
    }
    Matrix.prototype.setData = function (row, col, value) {
        this.data[row][col] = value;
    };
    Matrix.prototype.setMatrix = function (array) {
        this.data = array;
        this.rows = array.length;
        this.cols = array[0].length;
    };
    Matrix.prototype.getData = function (row, col) {
        return this.data[row][col];
    };
    Matrix.prototype.randomize = function (max) {
        max = Number(max) || 10;
        for (var i = 0; i < this.rows; i++) {
            for (var j = 0; j < this.cols; j++) {
                this.data[i][j] = Math.floor(Math.random() * max);
            }
        }
    };
    Matrix.prototype.identity = function () {
        if (this.rows != this.cols) {
            console.log("Error! Connot transform current matrix to Identity matrix because rows and colums count doesn't match.");
            return;
        }
        for (var i = 0; i < this.rows; i++) {
            for (var j = 0; j < this.cols; j++) {
                if (i == j) {
                    this.data[i][j] = 1;
                }
                else {
                    this.data[i][j] = 0;
                }
            }
        }
    };
    Matrix.prototype.add = function (n, overwrite) {
        overwrite = overwrite || true;
        if (n instanceof Matrix) {
            if (this.cols != n.cols || this.rows != n.rows) {
                console.log("Error! Connot proform Matrix sum operation on matrices with diffrent rows and/or colums count.");
                return;
            }
            var temp = new Matrix(this.rows, this.cols);
            for (var i = 0; i < temp.rows; i++) {
                for (var j = 0; j < temp.cols; j++) {
                    temp.data[i][j] = this.data[i][j] + n.data[i][j];
                }
            }
            if (overwrite) {
                this.data = temp.data;
            }
            else {
                return temp;
            }
        }
        else if (!isNaN(Number(n))) {
            for (var i = 0; i < this.rows; i++) {
                for (var j = 0; j < this.cols; j++) {
                    this.data[i][j] += n;
                }
            }
        }
        else {
            console.log("Error! Connot proform Matrix sum because passed argument is neither a %c number %c nor a %c matrix.", "color: red; font-weight: bold", "", "color: blue; font-weight: bold");
            return;
        }
    };
    Matrix.prototype.multiply = function (n, overwrite) {
        overwrite = overwrite || true;
        if (n instanceof Matrix) {
            if (this.cols != n.rows) {
                console.log("Error! Connot proform Matrix multiplication operation on matrices because Matrix A colums is not equal to Matrix B rows.");
                return;
            }
            var temp = new Matrix(this.rows, n.cols);
            for (var i = 0; i < temp.rows; i++) {
                for (var j = 0; j < temp.cols; j++) {
                    var sum = 0;
                    for (var k = 0; k < this.cols; k++) {
                        sum += this.data[i][k] * n.data[k][j];
                    }
                    temp.data[i][j] = sum;
                }
            }
            if (overwrite) {
                this.data = temp.data;
            }
            else {
                return temp;
            }
        }
        else if (!isNaN(Number(n))) {
            for (var i = 0; i < this.rows; i++) {
                for (var j = 0; j < this.cols; j++) {
                    this.data[i][j] *= n;
                }
            }
        }
        else {
            console.log("Error! Connot proform Matrix multiplication because passed argument is neither a %c number %c nor a %c matrix.", "color: red; font-weight: bold", "", "color: blue; font-weight: bold");
            return;
        }
    };
    Matrix.prototype.transpose = function () {
        var temp = new Matrix(this.cols, this.rows);
        for (var i = 0; i < temp.rows; i++) {
            for (var j = 0; j < temp.cols; j++) {
                temp.data[i][j] = this.data[j][i];
            }
        }
        this.data = temp.data;
    };
    Matrix.prototype.LUdecomposition = function () {
        var U = new Matrix(this.rows, this.cols);
        var L = new Matrix(this.rows, this.cols);
        for (var i = 0; i < this.rows; i++) {
            for (var j = 0; j < this.cols; j++) {
                var sum = 0;
                for (var k = 0; k <= i - 1; k++) {
                    sum = sum + L.data[j][k] * U.data[k][i];
                }
                if (i === j) {
                    U.data[i][j] = 1;
                }
                if (i <= j) {
                    L.data[j][i] = this.data[j][i] - sum;
                }
                else {
                    U.data[j][i] = (1 / L.data[j][j]) * (this.data[j][i] - sum);
                }
            }
        }
        return [L, U];
    };
    Matrix.prototype.determinant = function () {
        if (this.rows != this.cols) {
            console.log("Error! This matrix has diffrent row and colums count. Thus, the determinant cannot be calculated.");
            return;
        }
        var decomposition = this.LUdecomposition();
        var temp = decomposition[0];
        var mult = 1;
        for (var i = 0; i < temp.rows; i++) {
            for (var j = 0; j < temp.cols; j++) {
                if (i == j) {
                    mult *= temp.data[i][j];
                }
            }
        }
        return Math.floor(mult);
    };
    Matrix.prototype.log = function () {
        console.table(this.data);
    };
    return Matrix;
}());
var Vector = (function () {
    function Vector(x, y, z) {
        this.x = x;
        this.y = y;
        this.z = z || 0;
    }
    Vector.prototype.random2D = function (max) {
        max = max || 1;
        this.x = Math.random() * max;
        this.y = Math.random() * max;
    };
    Vector.prototype.random3D = function (max) {
        max = max || 1;
        this.random2D(max);
        this.z = Math.random() * max;
    };
    Vector.prototype.floor = function () {
        this.x = Math.floor(this.x);
        this.y = Math.floor(this.y);
        this.z = Math.floor(this.z);
    };
    Vector.prototype.getX = function () {
        return this.x;
    };
    Vector.prototype.getY = function () {
        return this.y;
    };
    Vector.prototype.getZ = function () {
        return this.z;
    };
    Vector.prototype.add = function (objVector) {
        return new Vector(this.x + objVector.x, this.y + objVector.y, this.z + objVector.z);
    };
    Vector.prototype.substract = function (objVector) {
        return new Vector(this.x - objVector.x, this.y - objVector.y, this.z - objVector.z);
    };
    Vector.prototype.multiply = function (k) {
        if (k instanceof Vector) {
            return this.crossProduct(k);
        }
        else if (!isNaN(k)) {
            var copy = new Vector(this.x, this.y, this.x);
            copy.scale(k);
            return copy;
        }
    };
    Vector.prototype.scale = function (k) {
        this.x *= k;
        this.y *= k;
        this.z *= k;
    };
    Vector.prototype.mag = function () {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
    };
    Vector.prototype.normalize = function () {
        var TEMP = this.mag();
        this.x = this.x / TEMP;
        this.y = this.y / TEMP;
        this.z = this.z / TEMP;
    };
    Vector.prototype.project = function (objVector) {
        var CONSTANT = this.dotProduct(objVector) / Math.pow(objVector.mag(), 2);
        var result = new Vector(objVector.x, objVector.y, objVector.z);
        result.multiply(CONSTANT);
        return result;
    };
    Vector.prototype.dotProduct = function (objVector) {
        return this.x * objVector.x + this.y * objVector.y + this.z * objVector.z;
    };
    Vector.prototype.crossProduct = function (objVector) {
        var i = this.y * objVector.z - this.z * objVector.y;
        var j = -(this.x * objVector.z - this.z * objVector.x);
        var k = this.x * objVector.y - this.y * objVector.x;
        return new Vector(i, j, k);
    };
    return Vector;
}());
var Complex = (function () {
    function Complex(a, b) {
        this.a = a || 0;
        this.b = b || 0;
        this.phi = Math.atan(b / a);
        this.r = Math.sqrt(a * a + b * b);
    }
    Complex.prototype.fromPolar = function (r, phi) {
        this.phi = phi;
        this.r = r;
        this.a = r * Math.cos(phi);
        this.b = r * Math.sin(phi);
    };
    Complex.prototype.equals = function (complexNum) {
        if (this.a == complexNum.a && this.b == complexNum.b) {
            return true;
        }
        else {
            return false;
        }
    };
    Complex.prototype.render = function (arg) {
        if (arg != undefined && arg.toLowerCase() == "polar") {
            return this.r + " * ( cos(" + this.phi + ") + i sin(" + this.phi + ") )";
        }
        else {
            return this.a + " + " + this.b + " i";
        }
    };
    Complex.prototype.sum = function (numB) {
        return new Complex(this.a + numB.a, this.b + numB.b);
    };
    Complex.prototype.substract = function (numB) {
        return new Complex(this.a - numB.a, this.b - numB.b);
    };
    Complex.prototype.multiply = function (numB) {
        var result = new Complex();
        result.fromPolar(this.r * numB.r, this.phi + numB.phi);
        return result;
    };
    Complex.prototype.divide = function (numB) {
        var result = new Complex();
        result.fromPolar(this.r / numB.r, this.phi - numB.phi);
        return result;
    };
    Complex.prototype.conjugate = function () {
        return new Complex(this.a, -1 * this.b);
    };
    Complex.prototype.power = function (exposant) {
        var result = new Complex();
        result.fromPolar(Math.pow(this.r, exposant), this.phi * exposant);
        return result;
    };
    Complex.prototype.root = function (degree) {
        var result = [];
        var tempR = Math.pow(this.r, 1 / degree);
        for (var i = 0; i < degree; i++) {
            var tempPhi = (this.phi + 2 * Math.PI * i) / degree;
            var temp = new Complex();
            temp.fromPolar(tempR, tempPhi);
            result.push(temp);
        }
        return result;
    };
    Complex.prototype.renderRoot = function (degree) {
        var roots = this.root(degree);
        var str = "";
        for (var _i = 0, roots_1 = roots; _i < roots_1.length; _i++) {
            var root = roots_1[_i];
            str += root.render();
            str += "\n";
        }
        return str;
    };
    Complex.prototype.realPart = function () {
        return this.a;
    };
    Complex.prototype.imaginaryPart = function () {
        return this.b;
    };
    Complex.prototype.mag = function () {
        return this.r;
    };
    Complex.prototype.log = function (arg) {
        console.log(this.render(arg));
    };
    return Complex;
}());
"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var Namespace = (function () {
    function Namespace(name) {
        this.name = name;
        this.id = random(0, 1e6);
    }
    return Namespace;
}());
var Logger = (function () {
    function Logger(level) {
        var _this = this;
        this.buffer = [];
        this.logLevel = level || Logger.LOG_LEVEL_WARNNING;
        var exits = false;
        Logger.allLoggers.forEach(function (temp) {
            exits = temp == _this ? true : false;
        });
        if (!exits)
            Logger.allLoggers.push(this);
    }
    Logger.prototype.setLevel = function (newLevel) {
        this.logLevel = newLevel;
    };
    Logger.prototype.error = function () {
        var messages = [];
        for (var _i = 0; _i < arguments.length; _i++) {
            messages[_i] = arguments[_i];
        }
        for (var _a = 0, messages_1 = messages; _a < messages_1.length; _a++) {
            var temp = messages_1[_a];
            var msg = "%cERROR:	%c" + temp;
            this.buffer.push(msg);
            if (this.logLevel >= Logger.LOG_LEVEL_ERROR)
                console.log(msg, "color: red; font-weight: bold;", "");
        }
    };
    Logger.prototype.warn = function () {
        var messages = [];
        for (var _i = 0; _i < arguments.length; _i++) {
            messages[_i] = arguments[_i];
        }
        for (var _a = 0, messages_2 = messages; _a < messages_2.length; _a++) {
            var temp = messages_2[_a];
            var msg = "%cWARNNING:	%c" + temp;
            this.buffer.push(msg);
            if (this.logLevel >= Logger.LOG_LEVEL_WARNNING)
                console.log(msg, "color: yellow; font-weight: bold;", "");
        }
    };
    Logger.prototype.info = function () {
        var messages = [];
        for (var _i = 0; _i < arguments.length; _i++) {
            messages[_i] = arguments[_i];
        }
        for (var _a = 0, messages_3 = messages; _a < messages_3.length; _a++) {
            var temp = messages_3[_a];
            var msg = "%cINFO:	%c" + temp;
            this.buffer.push(msg);
            if (this.logLevel >= Logger.LOG_LEVEL_INFO)
                console.log(msg, "color: green; font-weight: bold;", "");
        }
    };
    Logger.prototype.log = function () {
        var messages = [];
        for (var _i = 0; _i < arguments.length; _i++) {
            messages[_i] = arguments[_i];
        }
        for (var _a = 0, messages_4 = messages; _a < messages_4.length; _a++) {
            var msg = messages_4[_a];
            if (msg instanceof Array) {
                console.table(msg);
            }
            else if (msg instanceof Object) {
                console.log(msg);
            }
            else {
                this.info(msg);
            }
        }
    };
    Logger.prototype.history = function () {
        this.buffer.forEach(function (e) {
            return console.log(e, "color:orange; font-weight:bold;", "");
        });
    };
    Logger.disableCollisionWarn = function () {
        Logger.collisionWarn = !Logger.collisionWarn;
    };
    Logger.allLoggers = [];
    Logger.collisionWarn = true;
    Logger.maxCollisionWarn = 0;
    Logger.LOG_LEVEL_ERROR = 1;
    Logger.LOG_LEVEL_WARNNING = 2;
    Logger.LOG_LEVEL_INFO = 3;
    return Logger;
}());
var EnginGlobal = new Namespace("EnginEnginGlobal");
EnginGlobal.EnginGlobalBuffer = {};
EnginGlobal.FPS = 60;
EnginGlobal.setFPS = function (newFPS) {
    EnginGlobal.FPS = newFPS;
};
EnginGlobal.collisionObjects = [];
var Time = new Namespace("time");
Time.deltaTime = 1000 / EnginGlobal.FPS;
var debug = new Logger(Logger.LOG_LEVEL_INFO);
var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var GameObject = (function () {
    function GameObject(name) {
        this.name = name;
        this.id = this.name + "_" + Math.floor(random(0, 1e8));
        this.buffer = {};
        this.collision = false;
        this.static = true;
    }
    GameObject.prototype.render = function (targetCanvas) {
        throw new Error("must be implemented by subclass!");
    };
    GameObject.prototype.move = function (amountX, amountY) {
        if (amountX instanceof Vector) {
            var tempVector = amountX;
            amountX = tempVector.x;
            amountY = tempVector.y;
        }
        if (!this.static) {
            this.x += amountX * Time.deltaTime;
            this.y += amountY * Time.deltaTime;
        }
        else {
            throw new Error("Cannot move " + this.name + " (id: " + this.id + ") because object is immovable");
        }
    };
    GameObject.prototype.draw = function (targetCanvas) {
        this.render(targetCanvas);
    };
    GameObject.prototype.enableCollision = function () {
        if (this instanceof Canvas) {
            throw new Error("Cannnot tag CANVAS objects as collidable.");
        }
        this.collision = true;
        var exist = false;
        for (var _i = 0, _a = EnginGlobal.collisionObjects; _i < _a.length; _i++) {
            var element = _a[_i];
            if (element.id == this.id) {
                exist = true;
                break;
            }
        }
        if (!exist)
            EnginGlobal.collisionObjects.push(this);
    };
    GameObject.prototype.disableCollision = function () {
        this.collision = false;
        var exist = false;
        for (var _i = 0, _a = EnginGlobal.collisionObjects; _i < _a.length; _i++) {
            var element = _a[_i];
            if (element.id == this.id) {
                exist = true;
                break;
            }
        }
        if (exist) {
            EnginGlobal.collisionObjects.splice(EnginGlobal.collisionObjects.indexOf(this), 1);
        }
    };
    GameObject.prototype.collides = function (other) {
        if (!this.collision || !other.collision) {
            for (var _i = 0, _a = Logger.allLoggers; _i < _a.length; _i++) {
                var element = _a[_i];
                if (Logger.collisionWarn && Logger.maxCollisionWarn <= 10) {
                    element.warn("Attemting to evaluat collision on disableCollision objects. Use Logger.disableCollisionWarn() if you wish to hide this message");
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
        }
        else if (this instanceof Circle && other instanceof Rectangle) {
            var hitbox = new Rectangle(this.x, this.y, this.r * 2, this.r * 2);
            return (hitbox.x + hitbox.w >= other.x &&
                hitbox.x <= other.x + other.w &&
                hitbox.y + hitbox.h >= other.y &&
                hitbox.y <= other.y + other.h);
        }
        else if (this instanceof Rectangle && other instanceof Circle) {
            var hitbox = new Rectangle(other.x, other.y, other.r * 2, other.r * 2);
            return (this.x + this.w >= hitbox.x &&
                this.x <= hitbox.x + hitbox.w &&
                this.y + this.h >= hitbox.y &&
                this.y <= hitbox.y + hitbox.h);
        }
        else if (this instanceof Rectangle && other instanceof Rectangle) {
            return (this.x + this.w >= other.x &&
                this.x <= other.x + other.w &&
                this.y + this.h >= other.y &&
                this.y <= other.y + other.h);
        }
        else if (this instanceof Circle && other instanceof Vertex) {
            return distance(this.x, this.y, other.x, other.y) <= this.r;
        }
        else if (this instanceof Rectangle && other instanceof Vertex) {
            return (other.x > this.x &&
                other.x < this.x + this.w &&
                other.y > this.y &&
                other.y < this.y + this.h);
        }
    };
    GameObject.prototype.equals = function (other) {
        return this.id == other.id;
    };
    GameObject.prototype.parseToWidth = function (input) {
        if (typeof input === "string") {
            var array = input.split("");
            array.pop();
            var percentage = array.join("");
            var result = (Number(percentage) / 100) * window.innerWidth;
            return result;
        }
        else {
            return input;
        }
    };
    GameObject.prototype.parseToHeight = function (input) {
        if (typeof input === "string") {
            var array = input.split("");
            array.pop();
            var percentage = array.join("");
            var result = (Number(percentage) / 100) * window.innerHeight;
            return result;
        }
        else {
            return input;
        }
    };
    return GameObject;
}());
var Canvas = (function (_super) {
    __extends(Canvas, _super);
    function Canvas(posX, posY, width, height, positionStyle, parent) {
        var _this = _super.call(this, "canvas") || this;
        _this.x = _this.parseToWidth(posX);
        _this.y = _this.parseToHeight(posY);
        _this.w = _this.parseToWidth(width);
        _this.h = _this.parseToHeight(height);
        _this.width = _this.w;
        _this.height = _this.h;
        _this.positionStyle = positionStyle || "absolute";
        _this.background = "transparent";
        _this.canvas = null;
        _this.ctx = null;
        _this.overwrite = false;
        _this.parent = parent || document.querySelector("body");
        _this.static = true;
        return _this;
    }
    Canvas.prototype.render = function () {
        var DOM = "<canvas id=\"" + this.id + "\" style=\"position: " + this.positionStyle + "; top: " + this.y + "; left: " + this.x + "; background: " + this.background + ";\" width=\"" + this.w + "\" height=\"" + this.h + "\">";
        if (!document.getElementById(this.id)) {
            this.parent.innerHTML += DOM;
        }
        else {
            if (this.overwrite) {
                this.parent.removeChild(document.getElementById(this.id));
                this.parent.innerHTML += DOM;
            }
        }
        this.canvas = document.getElementById(this.id);
        this.ctx = this.canvas.getContext("2d");
    };
    Canvas.prototype.clear = function (fromX, fromY, toX, toY) {
        fromX = fromX || 0;
        fromY = fromY || 0;
        toX = toX || this.w;
        toY = toY || this.h;
        this.ctx.clearRect(fromX, fromY, toX, toY);
    };
    Canvas.prototype.scale = function (x, y) {
        this.ctx.scale(x, y);
    };
    Canvas.prototype.setPosition = function (newX, newY) {
        this.x = this.parseToWidth(newX);
        this.y = this.parseToHeight(newY);
        this.render();
    };
    Canvas.prototype.setBackground = function (color) {
        this.background = color;
        this.render();
        document.getElementById(this.id).style.background = this.background;
    };
    Canvas.prototype.setWidth = function (newWidth) {
        this.w = this.parseToWidth(newWidth);
        this.width = this.w;
        this.render();
    };
    Canvas.prototype.setHeight = function (newHeight) {
        this.h = this.parseToHeight(newHeight);
        this.height = this.h;
        this.render();
    };
    Canvas.prototype.getMousePosition = function () {
        var x = mouse.x - getOffsetLeft(this.canvas);
        var y = mouse.y - getOffsetTop(this.canvas);
        return { x: x, y: y };
    };
    Canvas.prototype.toggleOverwrite = function (booleanValue) {
        if (booleanValue == undefined)
            this.overwrite = !this.overwrite;
        else
            this.overwrite = booleanValue;
    };
    return Canvas;
}(GameObject));
var Graphics2D = (function (_super) {
    __extends(Graphics2D, _super);
    function Graphics2D() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    return Graphics2D;
}(Canvas));
var Circle = (function (_super) {
    __extends(Circle, _super);
    function Circle(x, y, r) {
        var _this = _super.call(this, "circle") || this;
        _this.x = x;
        _this.y = y;
        _this.r = r;
        _this.fill = "white";
        _this.stroke = "black";
        _this.lineWidth = 1;
        _this.static = false;
        if (_this.r < 0)
            throw new Error("Cannot initialize a " + _this.name + " with a negative radius");
        return _this;
    }
    Circle.prototype.render = function (targetCanvas) {
        if (!targetCanvas) {
            throw new Error("Connot render " +
                this.name +
                " on a non specific Canvas. Must provide Canvas object in argument of Object.render()");
        }
        if (this.x == undefined ||
            this.x == null ||
            isNaN(this.x) ||
            this.y == undefined ||
            this.y == null ||
            isNaN(this.y)) {
            throw new Error(this.name + " (" + this.id + ") X and/or coordinate is/are either undefined, null or NaN.");
        }
        targetCanvas.ctx.beginPath();
        targetCanvas.ctx.arc(this.x, this.y, this.r, 0, Math.PI * 2, false);
        targetCanvas.ctx.fillStyle = this.fill;
        targetCanvas.ctx.strokeStyle = this.stroke;
        targetCanvas.ctx.lineWidth = this.lineWidth;
        targetCanvas.ctx.fill();
        targetCanvas.ctx.stroke();
    };
    Circle.prototype.hover = function () {
        var d = Math.sqrt(Math.pow(mouse.x - this.x, 2) + Math.pow(mouse.y - this.y, 2));
        if (d <= this.r) {
            return true;
        }
    };
    Circle.prototype.clicked = function () {
        var d = Math.sqrt(Math.pow(mouse.lastClicked.x - this.x, 2) +
            Math.pow(mouse.lastClicked.y - this.y, 2));
        if (d <= this.r) {
            return true;
        }
    };
    Circle.prototype.area = function () {
        return Math.PI * Math.pow(this.r, 2);
    };
    Circle.prototype.setFill = function (newFill) {
        this.fill = newFill;
    };
    Circle.prototype.setStroke = function (newStroke) {
        this.stroke = newStroke;
    };
    Circle.prototype.setLineWidth = function (newLineWidth) {
        this.lineWidth = newLineWidth;
    };
    return Circle;
}(GameObject));
var Rectangle = (function (_super) {
    __extends(Rectangle, _super);
    function Rectangle(x, y, w, h) {
        var _this = _super.call(this, "rectangle") || this;
        _this.x = x;
        _this.y = y;
        _this.w = w;
        _this.h = h;
        _this.fill = "white";
        _this.stroke = "black";
        _this.lineWidth = 1;
        _this.static = false;
        if (_this.w < 0)
            throw new Error("Cannot initialize a " + _this.name + " with a negative width");
        if (_this.h < 0)
            throw new Error("Cannot initialize a " + _this.name + " with a negative height");
        return _this;
    }
    Rectangle.prototype.render = function (targetCanvas) {
        if (!targetCanvas) {
            throw new Error("Connot render " +
                this.name +
                " on a non specific Canvas. Must provide Canvas object in argument of Object.render()");
        }
        if (this.x == undefined ||
            this.x == null ||
            isNaN(this.x) ||
            this.y == undefined ||
            this.y == null ||
            isNaN(this.y)) {
            throw new Error(this.name + " (" + this.id + ") X and/or coordinate is/are either undefined, null or NaN.");
        }
        targetCanvas.ctx.beginPath();
        targetCanvas.ctx.rect(this.x, this.y, this.w, this.h);
        targetCanvas.ctx.fillStyle = this.fill;
        targetCanvas.ctx.strokeStyle = this.stroke;
        targetCanvas.ctx.lineWidth = this.lineWidth;
        targetCanvas.ctx.fill();
        targetCanvas.ctx.stroke();
    };
    Rectangle.prototype.hover = function () {
        if (mouse.x > this.x &&
            mouse.x < this.x + this.w &&
            mouse.y > this.y &&
            mouse.y < this.y + this.h) {
            return true;
        }
    };
    Rectangle.prototype.clicked = function () {
        if (mouse.lastClicked.x > this.x &&
            mouse.lastClicked.x < this.x + this.w &&
            mouse.lastClicked.y > this.y &&
            mouse.lastClicked.y < this.y + this.h) {
            return true;
        }
    };
    Rectangle.prototype.area = function () {
        return this.w * this.h;
    };
    Rectangle.prototype.setFill = function (newFill) {
        this.fill = newFill;
        return this;
    };
    Rectangle.prototype.setStroke = function (newStroke) {
        this.stroke = newStroke;
        return this;
    };
    Rectangle.prototype.setLineWidth = function (newLineWidth) {
        this.lineWidth = newLineWidth;
        return this;
    };
    return Rectangle;
}(GameObject));
var Vertex = (function (_super) {
    __extends(Vertex, _super);
    function Vertex(x, y) {
        var _this = _super.call(this, "vertex") || this;
        _this.collision = true;
        _this.x = x;
        _this.y = y;
        _this.collision = true;
        return _this;
    }
    return Vertex;
}(GameObject));
var ShadoText = (function (_super) {
    __extends(ShadoText, _super);
    function ShadoText(text, x, y, _a) {
        var font = _a.font, size = _a.size, color = _a.color, stroke = _a.stroke, background = _a.background;
        var _this = _super.call(this, "text") || this;
        _this.text = text;
        _this.x = x;
        _this.y = y;
        _this.font = font || "sans-serif";
        _this.size = size || 14;
        _this.color = color || "black";
        _this.stroke = stroke || _this.color;
        _this.background = background || "transparent";
        _this.static = false;
        _this.fullStyle = _this.size + "px " + _this.font;
        _this.hitBox = null;
        return _this;
    }
    ShadoText.prototype.render = function (targetCanvas) {
        if (!targetCanvas) {
            throw new Error("Connot render " +
                this.name +
                " on a non specific Canvas. Must provide Canvas object in argument of Object.render()");
        }
        if (this.x == undefined ||
            this.x == null ||
            isNaN(this.x) ||
            this.y == undefined ||
            this.y == null ||
            isNaN(this.y)) {
            throw new Error(this.name + " (" + this.id + ") X and/or coordinate is/are either undefined, null or NaN.");
        }
        targetCanvas.ctx.font = this.size + "px " + this.font;
        this.buildHitBox(targetCanvas);
        targetCanvas.ctx.fillStyle = this.color;
        targetCanvas.ctx.strokeStyle = this.stroke;
        targetCanvas.ctx.fillText(this.text, this.x, this.y);
        targetCanvas.ctx.strokeText(this.text, this.x, this.y);
    };
    ShadoText.prototype.buildHitBox = function (targetCanvas) {
        this.hitBox = new Rectangle(this.x - this.width(targetCanvas) * 0.1, this.y - this.height(targetCanvas) / 2, this.width(targetCanvas) * 1.2, this.height(targetCanvas));
        this.hitBox.setFill(this.background);
        this.hitBox.setStroke("transparent");
        this.hitBox.render(targetCanvas);
    };
    ShadoText.prototype.width = function (targetCanvas) {
        targetCanvas.ctx.font = this.fullStyle;
        return targetCanvas.ctx.measureText(this.text).width;
    };
    ShadoText.prototype.height = function (targetCanvas) {
        targetCanvas.ctx.font = this.fullStyle;
        var height = parseInt(targetCanvas.ctx.font.match(/\d+/), 10) * 2;
        return height;
    };
    ShadoText.prototype.hover = function (targetCanvas) {
        if (this.hitBox == null) {
            this.buildHitBox(targetCanvas);
        }
        return this.hitBox.hover();
    };
    ShadoText.prototype.clicked = function (targetCanvas) {
        if (this.hitBox == null) {
            this.buildHitBox(targetCanvas);
        }
        return this.hitBox.clicked();
    };
    return ShadoText;
}(GameObject));
var ShadoImage = (function (_super) {
    __extends(ShadoImage, _super);
    function ShadoImage(src, x, y, w, h, id, showHitBox) {
        var _this = _super.call(this, "image") || this;
        _this.src = src;
        _this.x = x;
        _this.y = y;
        _this.w = w;
        _this.h = h;
        _this.id = id;
        _this.showHitBox = showHitBox;
        _this.static = false;
        var allImgs = document.getElementById(_this.id);
        if (allImgs == undefined || allImgs == null) {
            var body = document.querySelector("body");
            var img = document.createElement("img");
            img.setAttribute("id", _this.id);
            img.setAttribute("src", _this.src);
            img.setAttribute("style", "display: none");
            body.appendChild(img);
        }
        _this.hitBox = new Rectangle(_this.x, _this.y, _this.w, _this.h);
        _this.hitBox.setStroke("red");
        _this.hitBox.setFill("transparent");
        if (_this.w < 0)
            throw new Error("Cannot initialize a " + _this.name + " with a negative width");
        if (_this.h < 0)
            throw new Error("Cannot initialize a " + _this.name + " with a negative height");
        return _this;
    }
    ShadoImage.prototype.render = function (targetCanvas) {
        if (!targetCanvas) {
            throw new Error("Connot render " +
                this.name +
                " on a non specific Canvas. Must provide Canvas object in argument of Object.render()");
        }
        if (this.x == undefined ||
            this.x == null ||
            isNaN(this.x) ||
            this.y == undefined ||
            this.y == null ||
            isNaN(this.y)) {
            throw new Error(this.name + " (" + this.id + ") X and/or coordinate is/are either undefined, null or NaN.");
        }
        this.hitBox.x = this.x;
        this.hitBox.y = this.y;
        if (this.x + this.w >= 0 && this.x <= targetCanvas.width) {
            var myImage = document.getElementById(this.id);
            myImage.src = this.src;
            targetCanvas.ctx.drawImage(myImage, this.x, this.y, this.w, this.h);
            if (this.showHitBox) {
                this.hitBox.render(targetCanvas);
            }
        }
    };
    ShadoImage.prototype.hover = function () {
        return this.hitBox.hover();
    };
    ShadoImage.prototype.clicked = function () {
        throw new Error("lastClick has not been defined yet @ Image");
    };
    ShadoImage.prototype.updateDimensions = function (newW, newH) {
        this.w = newW;
        this.h = newH;
        this.hitBox.w = newW;
        this.hitBox.h = newH;
    };
    return ShadoImage;
}(GameObject));
var Line = (function (_super) {
    __extends(Line, _super);
    function Line(fromX, fromY, toX, toY) {
        var _this = _super.call(this, "line") || this;
        if (fromX instanceof Vertex && fromY instanceof Vertex) {
            _this.fromX = fromX.x;
            _this.fromY = fromX.y;
            _this.toX = fromY.x;
            _this.toY = fromY.y;
        }
        else {
            _this.fromX = fromX;
            _this.fromY = fromY;
            _this.toX = toX;
            _this.toY = toY;
        }
        _this.static = false;
        return _this;
    }
    Line.prototype.render = function (targetCanvas) {
        targetCanvas.ctx.beginPath();
        targetCanvas.ctx.moveTo(this.fromX, this.fromY);
        targetCanvas.ctx.lineTo(this.toX, this.toY);
        targetCanvas.ctx.stroke();
    };
    Line.prototype.length = function () {
        var temp = new Vector(this.toX - this.fromX, this.toY - this.fromY);
        return temp.mag();
    };
    Line.prototype.split = function (x, y) {
        if (typeof x === "string") {
            var tempX = x.split("");
            tempX.pop();
            var str = tempX.join("");
            var percentage = Number(str) / 100;
            var line1Length = this.length() * percentage;
            var line2Length = this.length() - line1Length;
            var angle = Math.atan2(this.toX - this.fromX, this.toY - this.fromY);
            var line1Coord = new Vertex(Math.sin(angle) * line1Length + this.fromX, Math.cos(angle) * line1Length + this.fromY);
            var line2Coord = new Vertex(Math.sin(angle) * line2Length + this.fromX, Math.cos(angle) * line2Length + this.fromY);
            var line1 = new Line(this.fromX, this.fromY, line1Coord.x, line1Coord.y);
            var line2 = new Line(line1Coord.x, line1Coord.y, this.toX, this.toY);
            return [line1, line2];
        }
        else {
            throw new Error("This code isn't working @ Line.split(number, number)");
        }
    };
    Line.prototype.move = function (amountX, amountY) {
        if (amountX instanceof Vector) {
            var tempVector = amountX;
            amountX = tempVector.x;
            amountY = tempVector.y;
        }
        if (!this.static) {
            this.fromX += amountX * Time.deltaTime;
            this.toX += amountX * Time.deltaTime;
            this.fromY += amountY * Time.deltaTime;
            this.toY += amountY * Time.deltaTime;
        }
        else {
            throw new Error("Cannot move " + this.name + " (id: " + this.id + ") because object is imstatic");
        }
    };
    return Line;
}(GameObject));
var Shape = (function (_super) {
    __extends(Shape, _super);
    function Shape(vertices, _a) {
        var fill = _a.fill, stroke = _a.stroke, lineWidth = _a.lineWidth;
        var _this = _super.call(this, "shape") || this;
        _this.vertices = [];
        _this.hitBox = [];
        _this.stringHitBox = [];
        _this.showHitbox = false;
        _this.vertices = vertices;
        _this.fill = fill || "transparent";
        _this.stroke = stroke || "black";
        _this.lineWidth = lineWidth || 1;
        _this.static = false;
        _this.collision = true;
        return _this;
    }
    Shape.prototype.render = function (targetCanvas) {
        if (this.hitBox.length <= 0) {
            this.generateHitBox();
        }
        targetCanvas.ctx.beginPath();
        targetCanvas.ctx.fillStyle = this.fill;
        targetCanvas.ctx.strokeStyle = this.stroke;
        targetCanvas.ctx.lineWidth = this.lineWidth;
        targetCanvas.ctx.moveTo(this.vertices[0].x, this.vertices[0].y);
        for (var _i = 0, _a = this.vertices; _i < _a.length; _i++) {
            var vertex = _a[_i];
            targetCanvas.ctx.lineTo(vertex.x, vertex.y);
        }
        targetCanvas.ctx.fill();
        targetCanvas.ctx.stroke();
        if (this.showHitbox) {
            for (var _b = 0, _c = this.hitBox; _b < _c.length; _b++) {
                var temp = _c[_b];
                temp.draw(targetCanvas);
            }
        }
    };
    Shape.prototype.collides = function (ver) {
        for (var _i = 0, _a = this.hitBox; _i < _a.length; _i++) {
            var temp = _a[_i];
            if (temp.collides(ver))
                return temp;
        }
        return false;
    };
    Shape.prototype.generateHitBox = function () {
    };
    Shape.prototype.addHitBox = function (rect) {
        rect.enableCollision();
        this.hitBox.push(rect);
    };
    Shape.prototype.setHitBox = function (array) {
        this.hitBox = [];
        for (var _i = 0, array_1 = array; _i < array_1.length; _i++) {
            var rect = array_1[_i];
            rect.enableCollision();
            this.hitBox.push(rect);
        }
    };
    Shape.prototype.setFill = function (color) {
        this.fill = color;
    };
    return Shape;
}(GameObject));
var ShadoWindow = (function (_super) {
    __extends(ShadoWindow, _super);
    function ShadoWindow(x, y, width, height, title) {
        var _this = _super.call(this, "ShadoWindow") || this;
        _this.openned = false;
        _this.x = _this.parseToWidth(x);
        _this.y = _this.parseToHeight(y);
        _this.w = _this.parseToWidth(width);
        _this.h = _this.parseToHeight(height);
        _this.title = title;
        _this.generated = false;
        _this.static = false;
        _this.DOM = createElement("div", $("body"));
        _this.DOM.id = _this.id;
        _this.DOM.style.position = "absolute";
        return _this;
    }
    ShadoWindow.prototype.generate = function () {
        var _this = this;
        var COLOR = "rgb(50, 0, 190)";
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
        var TITLEBAR_PADDING = 10;
        var TITLEBAR_HEIGHT = 35;
        this.titleBar = createElement("div", this.DOM);
        this.titleBar.id = this.id + "_titleBar";
        this.titleBar.draggable = false;
        this.titleBar.style.userSelect = "none";
        this.titleBar.style.width = "calc(100% - " + TITLEBAR_PADDING * 2 + "px)";
        this.titleBar.style.height = TITLEBAR_HEIGHT + "px";
        this.titleBar.style.backgroundColor = COLOR;
        this.titleBar.style.color = "white";
        this.titleBar.style.padding = TITLEBAR_PADDING + "px";
        this.titleBar.style.fontWeight = "bold";
        this.titleBar.style.fontFamily = "'IBM Plex Serif', sans-serif";
        this.titleBar.style.fontSize = "16pt";
        this.titleBar.innerHTML = this.title;
        var closeButton = createElement("div", this.DOM);
        closeButton.id = this.id + "_closeButton";
        closeButton.style.position = "absolute";
        closeButton.style.userSelect = "none";
        closeButton.style.left = "calc(100% - " + (TITLEBAR_HEIGHT +
            TITLEBAR_PADDING * 2) + "px)";
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
        window.addEventListener("load", function () {
            $("#" + _this.id + "_closeButton").addEventListener("click", function () {
                _this.close();
            });
            $("#" + _this.id + "_titleBar").addEventListener("mousemove", function () {
                if (mouse.isDown) {
                    _this.setX(mouse.x - _this.w / 2);
                    _this.setY(mouse.y - TITLEBAR_HEIGHT / 2);
                }
            });
        });
        var BODY_PADDING = 10;
        this.body = createElement("div", this.DOM);
        this.body.id = this.id + "_body";
        this.body.style.width = "calc(100% - " + BODY_PADDING * 2 + "px)";
        this.body.style.padding = BODY_PADDING + "px";
        this.body.style.fontFamily = "'IBM Plex Serif', serif";
        this.body.style.overflow = "auto";
        this.body.innerHTML += "Placeholder...";
        this.generated = true;
    };
    ShadoWindow.prototype.CENTER_X = function () {
        var newPosX = window.innerWidth / 2 - this.w / 2;
        this.setX(newPosX);
    };
    ShadoWindow.prototype.CENTER_Y = function () {
        var newPosY = window.innerHeight / 2 - this.h / 2;
        this.setY(newPosY);
    };
    ShadoWindow.prototype.show = function () {
        if (!this.generated) {
            this.generate();
        }
        $("#" + this.id).style.display = "block";
        this.openned = true;
    };
    ShadoWindow.prototype.open = function () {
        this.show();
    };
    ShadoWindow.prototype.hide = function () {
        $("#" + this.id).style.display = "none";
        this.openned = false;
    };
    ShadoWindow.prototype.close = function () {
        this.hide();
    };
    ShadoWindow.prototype.move = function () { };
    ShadoWindow.prototype.getX = function () {
        return this.x;
    };
    ShadoWindow.prototype.getY = function () {
        return this.y;
    };
    ShadoWindow.prototype.getWidth = function () {
        return this.w;
    };
    ShadoWindow.prototype.getHeight = function () {
        return this.h;
    };
    ShadoWindow.prototype.getTitle = function () {
        return this.title;
    };
    ShadoWindow.prototype.getContent = function () {
        return this.body.innerHTML;
    };
    ShadoWindow.prototype.getID = function () {
        return this.id;
    };
    ShadoWindow.prototype.getBodyElement = function () {
        return $("#" + this.id + "_body");
    };
    ShadoWindow.prototype.isOpen = function () {
        return this.openned;
    };
    ShadoWindow.prototype.setX = function (newX) {
        this.x = this.parseToWidth(newX);
        $("#" + this.id).style.left = this.x.toString() + "px";
    };
    ShadoWindow.prototype.setY = function (newY) {
        this.y = this.parseToHeight(newY);
        $("#" + this.id).style.top = this.y.toString() + "px";
    };
    ShadoWindow.prototype.setWidth = function (newWidth) {
        this.w = this.parseToWidth(newWidth);
        $("#" + this.id).style.width = this.w + "px";
    };
    ShadoWindow.prototype.setHeight = function (newHeight) {
        this.h = this.parseToHeight(newHeight);
        $("#" + this.id).style.height = this.h + "px";
    };
    ShadoWindow.prototype.setTitle = function (newTitle) {
        $("#" + this.id + "_titleBar").innerHTML = newTitle;
    };
    ShadoWindow.prototype.setContent = function (newContent) {
        $("#" + this.id + "_body").innerHTML = newContent;
    };
    ShadoWindow.prototype.addContent = function (content) {
        $("#" + this.id + "_body").innerHTML += content;
    };
    return ShadoWindow;
}(GameObject));
var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var Ground = (function (_super) {
    __extends(Ground, _super);
    function Ground(x, y, w, h, background) {
        var _this = _super.call(this, "ground") || this;
        _this.background = "black";
        _this.display = true;
        _this.x = _this.parseToWidth(x);
        _this.y = _this.parseToHeight(y);
        _this.w = _this.parseToWidth(w);
        _this.h = _this.parseToHeight(h);
        _this.background = background || Ground.default;
        var exists = false;
        Ground.allGrounds.forEach(function (g) {
            if (g.x == _this.x && g.y == _this.y && g.w == _this.w && g.h == _this.h) {
                exists = true;
            }
        });
        if (!exists) {
            Ground.allGrounds.push(_this);
        }
        return _this;
    }
    Ground.prototype.hide = function () {
        this.display = false;
    };
    Ground.prototype.render = function (targetCanvas) {
        var rect = new Rectangle(this.x, this.y, this.w, this.h);
        rect.setFill(this.background);
        rect.render(targetCanvas);
    };
    Ground.default = "green";
    Ground.allGrounds = [];
    return Ground;
}(GameObject));
var Player = (function (_super) {
    __extends(Player, _super);
    function Player(x, y, w, h, background) {
        var _this = _super.call(this, "ground") || this;
        _this.background = "black";
        _this.x = _this.parseToWidth(x);
        _this.y = _this.parseToHeight(y);
        _this.w = _this.parseToWidth(w);
        _this.h = _this.parseToHeight(h);
        _this.background = background || Player.default;
        return _this;
    }
    Player.prototype.render = function (targetCanvas) {
        var rect = new Rectangle(this.x, this.y, this.w, this.h);
        rect.setFill(this.background);
        rect.render(targetCanvas);
    };
    Player.default = "pink";
    return Player;
}(GameObject));
var Terrain = (function (_super) {
    __extends(Terrain, _super);
    function Terrain(shape) {
        var _this = _super.call(this, "terrain") || this;
        _this.shape = shape;
        var exists = false;
        for (var i = 0; i < Terrain.allTerrain.length; i++) {
            if (Terrain.allTerrain[i] == _this)
                exists = true;
        }
        if (!exists)
            Terrain.allTerrain.push(_this);
        return _this;
    }
    Terrain.prototype.render = function (targetCanvas) {
        this.shape.render(targetCanvas);
    };
    Terrain.allTerrain = [];
    return Terrain;
}(GameObject));
function initHitBoxDrawer() {
    var win = new ShadoWindow(100, 100, 600, 800, "Test window");
    win.CENTER_X();
    win.CENTER_Y();
    win.open();
    win.setContent("\n        <h1>Unregular shap hitBox setter:</h1>        \n    ");
    EnginGlobal.winCanvas = new Canvas(0, 0, win.getWidth() * 0.9, win.getWidth() * 0.9, "relative", $("#" + win.getID() + "_body"));
    EnginGlobal.winCanvas.setBackground("rgb(150, 150, 150)");
    EnginGlobal.winCanvas.render();
    EnginGlobal.circleTest = new Circle(50, 50, 50);
}
var terrain1 = new Terrain(new Shape([new Vertex(0, 0), new Vertex(600, 0), new Vertex(0, 600)], {
    fill: "brown"
}));
terrain1.shape.setHitBox([
    new Rectangle(0, 0, 281, 319),
    new Rectangle(280, 2, 180, 138),
    new Rectangle(0, 320, 131, 147),
    new Rectangle(131, 317, 75, 77),
    new Rectangle(207, 319, 39, 37),
    new Rectangle(133, 396, 35, 36),
    new Rectangle(279, 140, 89, 94),
    new Rectangle(367, 141, 47, 47),
    new Rectangle(276, 230, 6, 11),
    new Rectangle(284, 237, 39, 41),
    new Rectangle(460, 2, 71, 66),
    new Rectangle(530, 1, 33, 39),
    new Rectangle(564, 3, 13, 20),
    new Rectangle(579, 2, 8, 7),
    new Rectangle(462, 67, 35, 39),
    new Rectangle(493, 67, 20, 22),
    new Rectangle(460, 108, 19, 17),
    new Rectangle(416, 142, 22, 22),
    new Rectangle(368, 190, 26, 20),
    new Rectangle(325, 234, 24, 22),
    new Rectangle(283, 277, 21, 20),
    new Rectangle(250, 319, 13, 16),
    new Rectangle(207, 353, 16, 20),
    new Rectangle(166, 396, 22, 18),
    new Rectangle(0, 467, 58, 74),
    new Rectangle(58, 467, 38, 42),
    new Rectangle(1, 540, 24, 37),
    new Rectangle(129, 434, 23, 20),
    new Rectangle(96, 466, 16, 22),
    new Rectangle(59, 512, 13, 14),
    new Rectangle(25, 540, 21, 17)
]);
var canvas = new Graphics2D(0, 0, window.innerWidth, window.innerHeight);
canvas.setBackground("rgb(0, 0, 100)");
function render() {
    canvas.clear(0, 0, canvas.width, canvas.height);
    for (var _i = 0, _a = Terrain.allTerrain; _i < _a.length; _i++) {
        var terrain = _a[_i];
        terrain.draw(canvas);
        if (terrain.shape.collides(new Vertex(mouse.x, mouse.y))) {
            terrain.shape.setFill("green");
        }
        else {
            terrain.shape.setFill("brown");
        }
    }
}
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __generator = (this && this.__generator) || function (thisArg, body) {
    var _ = { label: 0, sent: function() { if (t[0] & 1) throw t[1]; return t[1]; }, trys: [], ops: [] }, f, y, t, g;
    return g = { next: verb(0), "throw": verb(1), "return": verb(2) }, typeof Symbol === "function" && (g[Symbol.iterator] = function() { return this; }), g;
    function verb(n) { return function (v) { return step([n, v]); }; }
    function step(op) {
        if (f) throw new TypeError("Generator is already executing.");
        while (_) try {
            if (f = 1, y && (t = op[0] & 2 ? y["return"] : op[0] ? y["throw"] || ((t = y["return"]) && t.call(y), 0) : y.next) && !(t = t.call(y, op[1])).done) return t;
            if (y = 0, t) op = [op[0] & 2, t.value];
            switch (op[0]) {
                case 0: case 1: t = op; break;
                case 4: _.label++; return { value: op[1], done: false };
                case 5: _.label++; y = op[1]; op = [0]; continue;
                case 7: op = _.ops.pop(); _.trys.pop(); continue;
                default:
                    if (!(t = _.trys, t = t.length > 0 && t[t.length - 1]) && (op[0] === 6 || op[0] === 2)) { _ = 0; continue; }
                    if (op[0] === 3 && (!t || (op[1] > t[0] && op[1] < t[3]))) { _.label = op[1]; break; }
                    if (op[0] === 6 && _.label < t[1]) { _.label = t[1]; t = op; break; }
                    if (t && _.label < t[2]) { _.label = t[2]; _.ops.push(op); break; }
                    if (t[2]) _.ops.pop();
                    _.trys.pop(); continue;
            }
            op = body.call(thisArg, _);
        } catch (e) { op = [6, e]; y = 0; } finally { f = t = 0; }
        if (op[0] & 5) throw op[1]; return { value: op[0] ? op[1] : void 0, done: true };
    }
};
function gameLoop() {
    return __awaiter(this, void 0, void 0, function () {
        var t1, t2;
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0:
                    t1 = new Date().getTime();
                    _a.label = 1;
                case 1:
                    if (!(1 == 1)) return [3, 3];
                    render();
                    t2 = new Date().getTime();
                    Time.deltaTime = t2 - t1;
                    t1 = new Date().getTime();
                    if (EnginGlobal.FPS == 0) {
                        console.log("Game loop has been exited");
                        return [3, 3];
                    }
                    return [4, sleep(1000 / EnginGlobal.FPS)];
                case 2:
                    _a.sent();
                    return [3, 1];
                case 3: return [2];
            }
        });
    });
}
gameLoop();
