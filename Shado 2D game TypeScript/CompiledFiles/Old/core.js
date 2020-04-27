"use strict";
/***
 *
 *
 * Core setter and renderer for Shado engin
 *
 */
Object.defineProperty(exports, "__esModule", { value: true });
exports.render = undefined;
exports.FPS = 60;
// Error handler
function error(message) {
    throw new Error(message);
}
exports.error = error;
// Canvas animator
function setRenderer(domHTMLelement) {
    exports.canvas = domHTMLelement;
    exports.c = domHTMLelement.getContext("2d");
}
exports.setRenderer = setRenderer;
function getFPS() {
    return exports.FPS;
}
exports.getFPS = getFPS;
function setFPS(newFPS) {
    exports.FPS = newFPS;
}
exports.setFPS = setFPS;
function clearFrame() {
    exports.c.clearRect(0, 0, exports.canvas.width, exports.canvas.height);
}
exports.clearFrame = clearFrame;
var animator = setInterval(function () {
    if (exports.render == undefined || exports.render == null) {
        error("The function \"render\" is undefined or null. Thus, cannot render canvas.");
        return;
    }
    else {
        exports.render();
    }
}, 1000 / exports.FPS);
function stopAnimation() {
    clearInterval(animator);
}
exports.stopAnimation = stopAnimation;
