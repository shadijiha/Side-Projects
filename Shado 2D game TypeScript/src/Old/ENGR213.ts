/***
 * 
 * ENGR-213 AS6
 * 
 */


import * as ShadoMath from "./MatrixVector";

let z1 = new ShadoMath.Complex();
z1.fromPolar(0, 3);

let z2 = new ShadoMath.Complex();
z2.fromPolar(0, -3);

let z3 = new ShadoMath.Complex(0, 2);

let result = z1.substract(z2);
result = result.divide(z3);

z2.log();