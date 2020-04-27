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
