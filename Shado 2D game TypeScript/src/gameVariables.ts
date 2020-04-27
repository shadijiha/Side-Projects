/***
 *
 * Declare objects here to render them in "animator.ts"
 *
 */

// Main Stuff
const terrain1 = new Terrain(
	new Shape([new Vertex(0, 0), new Vertex(600, 0), new Vertex(0, 600)], {
		fill: "brown"
	})
);

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
