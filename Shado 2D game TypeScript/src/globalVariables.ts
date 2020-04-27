import { timingSafeEqual } from "crypto";

/***
 *
 * This file contains all the EnginGlobal variables used in the game
 * This file contains @Gameobject super class and @Time and @EnginGlobal namespaces
 *
 */

class Namespace {
	public name: string;
	public id: number;
	public FPS: number;
	public EnginGlobalBuffer: Object;
	public collisionObjects: any[];
	public deltaTime: number;
	public setFPS: Function;
	public PAUSED: boolean;
	public oldFPS: number;

	constructor(name: string) {
		this.name = name;
		this.id = random(0, 1e6);
	}
}

class Logger {
	public static allLoggers: Logger[] = [];
	private static collisionWarn: boolean = true;
	public static maxCollisionWarn: number = 0;

	private logLevel: number;
	public static LOG_LEVEL_ERROR: number = 1;
	public static LOG_LEVEL_WARNNING: number = 2;
	public static LOG_LEVEL_INFO: number = 3;
	private buffer: string[] = [];

	public constructor(level?: number) {
		this.logLevel = level || Logger.LOG_LEVEL_WARNNING;

		// push to global array if object doesn't already exit
		let exits: boolean = false;
		Logger.allLoggers.forEach(temp => {
			exits = temp == this ? true : false;
		});
		if (!exits) Logger.allLoggers.push(this);
	}

	public setLevel(newLevel: number) {
		this.logLevel = newLevel;
	}

	public error(...messages: any[]): void {
		for (const temp of messages) {
			const msg = "%cERROR:	%c" + temp;
			this.buffer.push(msg);

			if (this.logLevel >= Logger.LOG_LEVEL_ERROR)
				console.log(msg, "color: red; font-weight: bold;", "");
		}
	}

	public warn(...messages: any[]): void {
		for (const temp of messages) {
			const msg = "%cWARNNING:	%c" + temp;
			this.buffer.push(msg);

			if (this.logLevel >= Logger.LOG_LEVEL_WARNNING)
				console.log(msg, "color: yellow; font-weight: bold;", "");
		}
	}

	public info(...messages: any[]): void {
		for (const temp of messages) {
			const msg: string = "%cINFO:	%c" + temp;
			this.buffer.push(msg);

			if (this.logLevel >= Logger.LOG_LEVEL_INFO)
				console.log(msg, "color: green; font-weight: bold;", "");
		}
	}

	public log(...messages: any[]): void {
		for (const msg of messages) {
			if (msg instanceof Array) {
				console.table(msg);
			} else if (msg instanceof Object) {
				console.log(msg);
			} else {
				this.info(msg);
			}
		}
	}

	public history(): void {
		this.buffer.forEach(e =>
			console.log(e, "color:orange; font-weight:bold;", "")
		);
	}

	public static disableCollisionWarn() {
		Logger.collisionWarn = !Logger.collisionWarn;
	}
}

// Setup EnginGlobal Variables
const EnginGlobal = new Namespace("EnginEnginGlobal");
EnginGlobal.EnginGlobalBuffer = {};
EnginGlobal.FPS = 60;
EnginGlobal.setFPS = (newFPS: number) => {
	EnginGlobal.FPS = newFPS;
};
EnginGlobal.collisionObjects = [];

const Time = new Namespace("time");
Time.deltaTime = 1000 / EnginGlobal.FPS;

// Setup Logger
const debug = new Logger(Logger.LOG_LEVEL_INFO);
