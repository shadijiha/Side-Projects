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
