using System;

namespace ShadoMathCSharp {


	public class Base {
		public Base() {
			Console.WriteLine("Parent Constructor");
		}

		~Base() {
			Console.WriteLine("Parent Deconstructor");
		}

		public void Print() {
			Console.WriteLine("Parent Print");
		}

	}

	public class Derived : Base {
		public Derived() {
			Console.WriteLine("Child Constructor");
		}

		~Derived() {
			Console.WriteLine("Child Deconstructor");
		}

		public void Print() {
			Console.WriteLine("Child Print");
		}
	}

    class Program {
		static void Main(string[] args) {

			Derived d = new Derived();
			d.Print();
			Base b = d;
			b.Print();
		}

	}
}
