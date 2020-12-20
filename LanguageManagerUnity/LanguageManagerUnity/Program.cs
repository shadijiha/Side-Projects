using System;
using System.Collections.Generic;
using System.IO;
using System.Threading;
using ShadoObjectFileCSharp;

namespace LanguageManagerUnity {
	class Program {
		static void Main(string[] args) {
			Console.OutputEncoding = System.Text.Encoding.UTF8;

			LanguageManager manager = new LanguageManager(@"D:\Wamp64\www\GitHub\Side-Projects\LanguageManagerUnity\LanguageManagerUnity\lang\", "shado");
			Console.WriteLine(manager.Lang("welcome"));
		}
	}

	public sealed class LanguageManager {
		// ========================
		public string directory {get;}
		public string defaultExtension { get; }
		private string[] files;
		private string currentLang;

		private ShadoFile langFile;
		private Dictionary<string, string> langs;

		//private TranslationClient client;

		public LanguageManager(string langFilesDirectory, string langFilesPath, string defaultLang = "en") {
			defaultExtension = langFilesPath;
			directory = langFilesDirectory;
			langs = new Dictionary<string, string>();
			//client = TranslationClient.CreateFromApiKey();

			indexLanguages();
			SetLanguage(defaultLang);

			//loadFile();
		}

		/// <summary>
		/// Translates a key to the current language set
		/// </summary>
		/// <param name="key"></param>
		/// <returns>Returns the value depending on the current language set</returns>
		public string Lang(string key) {

			if (!langFile.HasKey(key))
				return "Key not found!";
			return langFile.GetValue(key);
		}

		public void SetLanguage(string language) {

			language = language.ToLower();

			if (!langs.ContainsKey(language))
				throw new InvalidLanguageException($"Language {language} is not supported");

			currentLang = language;
			loadFile();
		}

		// public void SetLookDirectory(string path) {
		// 	directory = path;
		// }

		// public void SetExtension(string extension) {
		// 	defaultExtension = extension;
		// }

		private void indexLanguages() {

			langs.Clear();

			// Go through the directory and generate all languages
			files = Directory.GetFiles(directory, $"*.{defaultExtension.Replace(".", "")}", SearchOption.AllDirectories);

			// Get all languages
			foreach (string file in files) {
				var name = file.Split("\\")[^1].Split(".")[0];
				langs.Add(name.ToLower(), file);
			}
		}

		private void loadFile() {
			this.langFile = new ShadoFile(langs[currentLang]);
		}
	}

	[System.Serializable]
	public class InvalidLanguageException : Exception {
		public InvalidLanguageException() { }
		public InvalidLanguageException(string message) : base(message) { }
		public InvalidLanguageException(string message, Exception inner) : base(message, inner) { }
		protected InvalidLanguageException(
		  System.Runtime.Serialization.SerializationInfo info,
		  System.Runtime.Serialization.StreamingContext context) : base(info, context) { }
	}
}
