using System;

namespace ShadoMathCSharp {
	class Program {
		static void Main(string[] args) {

			Vec2D v = new Vec2D(5, 10);
			Vec2D u = new Vec2D(5, 10);
			v.x = 10;

			Console.WriteLine("v: {0}, u: {1} ----> v == u = {2}", v.Mag(), u.Mag(), v == u);
		}

	}
}
