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
