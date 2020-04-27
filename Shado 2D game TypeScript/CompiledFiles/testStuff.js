var testShape = new Shape([
    new Vertex(100, 200),
    new Vertex(210, 250),
    new Vertex(190, 400),
    new Vertex(100, 390),
    new Vertex(100, 200)
], { fill: "lightblue" });
testShape.setHitBox([
    new Rectangle(99, 200, 19, 191),
    new Rectangle(118, 391 - 148, 75, -148),
    new Rectangle(118, 212, 34, 29),
    new Rectangle(152, 228, 26, 13),
    new Rectangle(193, 247, 13, 38),
    new Rectangle(191, 282, 12, 29),
    new Rectangle(192, 312, 8, 18),
    new Rectangle(193, 330, 5, 10),
    new Rectangle(149, 390, 41, 10),
    new Rectangle(193, 242, -73, 148)
]);
var testSplit = new Line(100, 100, 500, 500).split("75%");
