/***
 * This file contais the render function that will go
 * to the game loop
 *
 */
// Setup main canvas
const canvas = new Graphics2D(0, 0, window.innerWidth, window.innerHeight);
canvas.setBackground("rgb(0, 0, 100)"); // Render is implicitly called

// For game Loop see "index.js"
function render() {
	// Clear canvas
	canvas.clear(0, 0, canvas.width, canvas.height);

	/*****************************
	 ********* DRAW ALL ***********
	 *****************************/
	for (const terrain of Terrain.allTerrain) {
		terrain.draw(canvas);

		if (terrain.shape.collides(new Vertex(mouse.x, mouse.y))) {
			terrain.shape.setFill("green");
		} else {
			terrain.shape.setFill("brown");
		}
	}
}
