"use strict";
/***
 *
 * ENGR-213 AS6
 *
 */
Object.defineProperty(exports, "__esModule", { value: true });
var ShadoMath = require("./MatrixVector");
var z1 = new ShadoMath.Complex();
z1.fromPolar(0, 3);
var z2 = new ShadoMath.Complex();
z2.fromPolar(0, -3);
var z3 = new ShadoMath.Complex(0, 2);
var result = z1.substract(z2);
result = result.divide(z3);
z2.log();
