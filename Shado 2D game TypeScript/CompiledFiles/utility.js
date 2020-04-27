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
