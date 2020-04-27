/***
 *
 * In order to contruct terrain and detect collision you
 * need to seperate the hitbox into a collection of
 * rectangles.
 * This is really hard to do so instead, when debug mode is
 * on you can create the hitboxes on a shape with a GUI.
 *
 */

function initHitBoxDrawer() {
	// Create a floating window
	const win = new ShadoWindow(100, 100, 600, 800, "Test window");
	win.CENTER_X();
	win.CENTER_Y();
	win.open();

	win.setContent(`
        <h1>Unregular shap hitBox setter:</h1>        
    `);

	EnginGlobal.winCanvas = new Canvas(
		0,
		0,
		win.getWidth() * 0.9,
		win.getWidth() * 0.9,
		"relative",
		$(`#${win.getID()}_body`)
	);
	EnginGlobal.winCanvas.setBackground("rgb(150, 150, 150)");
	EnginGlobal.winCanvas.render();

	EnginGlobal.circleTest = new Circle(50, 50, 50);
}

//initHitBoxDrawer();
