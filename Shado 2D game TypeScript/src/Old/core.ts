/***
 * 
 * 
 * Core setter and renderer for Shado engin
 * 
 */


export let canvas: any;
export let c: any;
export let render: any = undefined;
export let FPS: number = 60;

// Error handler
export function error(message: string): never {
    throw new Error(message);
}

// Canvas animator
export function setRenderer(domHTMLelement: any): void {

    canvas = domHTMLelement;
    c = domHTMLelement.getContext("2d");

}

export function getFPS(): number {
    return FPS;
}

export function setFPS(newFPS: number): void {
    FPS = newFPS;
}

export function clearFrame(): void {
    c.clearRect(0, 0, canvas.width, canvas.height);
}

let animator = setInterval(function () {

    if (render == undefined || render == null) {
        error("The function \"render\" is undefined or null. Thus, cannot render canvas.");
        return;
    } else {
        render();
    }

}, 1000 / FPS);

export function stopAnimation(): void {
    clearInterval(animator);
}

