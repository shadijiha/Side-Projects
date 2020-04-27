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
