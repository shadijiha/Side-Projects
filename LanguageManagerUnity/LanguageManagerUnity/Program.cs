using System;
using System.IO;

namespace LanguageManagerUnity {
	class Program {
		static void Main(string[] args) {
			Console.WriteLine("Hello World!");
		}
	}

	public sealed class LanguageManager {

		public static readonly LanguageManager singleton = new LanguageManager();

		// ========================
		public string directory { get; set; }
		public string default_extension { get; set; }

		public enum Langs {
			EN
		}

		private LanguageManager() {
			default_extension = "lang";

			// Go through the directory and generate all languages
			string[] files =
				Directory.GetFiles(directory, $"*.{default_extension}", SearchOption.AllDirectories);

			// Get all languages
			foreach (string file in files) {
				var name = file.Split("\\")[^1].Split(".")[0];


			}

		}

		public static string Lang(string key) => LanguageManager.singleton.Translate(key);


		public string Translate(string key) {
			return null;
		}
	}
}
