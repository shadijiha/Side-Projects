using System;
using System.Collections.Generic;
using System.ComponentModel.Design;
using System.Text;

namespace ShadoMathCSharp {
	public class Vec2D : IComparable {

		private const double EPSILON = 0.0000001D;

		public double x { get; set; }
		public double y { get; set; }

		public Vec2D(double _x, double _y)
		{
			x = _x;
			y = _y;
		}

		public Vec2D(ref Vec2D other)
		{
			x = other.x;
			y = other.y;
		}

		public Vec2D() {
			x = 0.0d;
			y = 0.0d;
		}

		public Vec2D Add(ref Vec2D other) {
			return new Vec2D {
				x = other.x + x,
				y = other.y + y
			};
		}

		public Vec2D Sub(ref Vec2D other) {
			Vec2D temp = other.Mult(-1f);
			return this.Add(ref temp);
		}

		public Vec2D Mult(double scale) {
			return new Vec2D() {
				x = x * scale,
				y = y * scale
			};
		}

		public Vec2D Div(double scale) {
			return Mult(1.0d / scale);
		}

		public double Mag() {
			return Math.Sqrt(x * x + y * y);
		}

		public override string ToString() {
			return String.Format("(x: {0}, y: {1})", x, y);
		}

		public override bool Equals(Object other) {
			return this.CompareTo(other) == 0;
		}

		public int CompareTo(Object obj) {
			if (obj == null || obj.GetType() != this.GetType())
				return -1;
			else {
				Vec2D v = (Vec2D)obj;
				if (this.Mag() < v.Mag())
					return -1;
				else if (Math.Abs(this.Mag() - v.Mag()) < EPSILON)
					return 0;
				else
					return 1;
			}
		}

		public override int GetHashCode() {
			return base.GetHashCode();
		}

		public static Vec2D operator+(Vec2D left, Vec2D right) {
			return left.Add(ref right);
		}

		public static Vec2D operator -(Vec2D left, Vec2D right) {
			return left.Sub(ref right);
		}

		public static Vec2D operator *(Vec2D left, double scale) {
			return left.Mult(scale);
		}

		public static Vec2D operator /(Vec2D left, double scale) {
			return left.Div(scale);
		}

		public static bool operator ==(Vec2D left, Vec2D right) {
			return left.Equals(right);
		}

		public static bool operator !=(Vec2D left, Vec2D right) {
			return !left.Equals(right);
		}

		public static bool operator<(Vec2D left, Vec2D right) {
			return left.CompareTo(right) < 0;
		}

		public static bool operator >(Vec2D left, Vec2D right) {
			return left.CompareTo(right) > 0;
		}

	}
}
