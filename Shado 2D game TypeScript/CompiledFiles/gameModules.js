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
