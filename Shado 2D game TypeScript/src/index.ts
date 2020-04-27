/***
 *
 * Main JS file all starts from here
 *
 */

/***********************
 ****** GAME LOOP ******
 **********************/
async function gameLoop() {
	// Game loop here
	let t1 = new Date().getTime();
	while (1 == 1) {
		// Render everything here
		render();

		// Calculate real FPS
		let t2 = new Date().getTime();
		Time.deltaTime = t2 - t1;
		t1 = new Date().getTime();

		// Sleep for the target FPS
		// Detect if FPS = 0
		if (EnginGlobal.FPS == 0) {
			console.log("Game loop has been exited");
			break;
		}
		await sleep(1000 / EnginGlobal.FPS);
	}
}

gameLoop();
