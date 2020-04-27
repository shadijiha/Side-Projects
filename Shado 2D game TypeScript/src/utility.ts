/***
 *
 * Utility functions
 */

// Call this function like so:
// await sleep(time in ms);
function sleep(ms: number): Promise<any> {
	return new Promise(resolve => setTimeout(resolve, ms));
}

function random(min: number, max?: number): number {
	if (max == undefined || max == null) {
		max = min;
		min = 0;
	}
	return Math.random() * (max - min) + min;
}

function floor(number: number): number {
	return Math.floor(number);
}

function randomColor(): string {
	return `rgb(${floor(random(0, 255))}, ${floor(random(0, 255))}, ${floor(
		random(0, 255)
	)})`;
}

function distance(x1: number, y1: number, x2: number, y2: number): number {
	return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
}

function pause(): void {
	EnginGlobal.oldFPS = EnginGlobal.FPS;
	EnginGlobal.FPS = 0;
	EnginGlobal.PAUSED = true;
}

function resume(): void {
	EnginGlobal.FPS = EnginGlobal.oldFPS;
	EnginGlobal.PAUSED = false;
	gameLoop();
}

function map(
	input: number,
	input_start: number,
	input_end: number,
	output_start: number,
	output_end: number
): number {
	return (
		output_start +
		((output_end - output_start) / (input_end - input_start)) *
			(input - input_start)
	);
}
