using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Text;
using UnityEngine;

namespace Core
{
    public static class FileLoader
    {
        /// <summary>
        /// This function loads all the file as a giant string
        /// </summary>
        /// <param name="path">The path of the file</param>
        /// <returns>Returns the content of the file</returns>
        /// <exception cref="FileNotFoundException">throws this exception if the file doesn't exist</exception>
        public static string LoadTextFile(string path) {

            if (!File.Exists(path))
                throw new FileNotFoundException(path + " not found!");

            using (StreamReader reader = new StreamReader(path)) {

                StringBuilder builder = new StringBuilder();
                string line = reader.ReadLine();

                while (line != null) {
                    builder.Append(line).Append("\n");
                    line = reader.ReadLine();
                }

                return builder.ToString();
            }
        }

        /// <summary>
        /// This function Loads a file as Shado File in a List of ShadoObjects(attribute, value)
        /// </summary>
        /// <param name="path">File path</param>
        /// <returns>Returns a list of Shado Object each with its attribute and value</returns>
        /// <exception cref="IllegalFileNameException">If the file name doesn't contain .shado</exception>
        /// <exception cref="SyntaxErrorException">If the file has a syntax error</exception>
        public static List<ShadoObject> LoadShadoFile(string path) {

            string content = LoadTextFile(path);
            string[] lines = content.Split('\n');

            List<ShadoObject> array = new List<ShadoObject>();

            int line_number = 1;
            foreach (var line in lines) {
                string[] parts = line.Split(':');

                if (parts[0] == "") {
                    Debug.Log($"Empty attribute at {path} @ line {line_number}");
                    continue;
                }

                if (parts.Length != 2)
                    throw new SyntaxErrorException(path + " Shado file has a syntax error at line " + line_number);

                array.Add(new ShadoObject(parts[0].Trim(), parts[1].Trim()));

                line_number++;
            }

            return array;
        }

        /// <summary>
        /// This function writes Data to a File
        /// </summary>
        /// <param name="path">The filepath</param>
        /// <param name="data">The data to write</param>
        public static void WriteToFile(string path, string data) {
            using (StreamWriter writer = new StreamWriter(path)) {
                writer.WriteLine(data);
            }
        }

        /// <summary>
        /// This function finds an object in a shado file by its attribute
        /// </summary>
        /// <param name="filepath">The file to search</param>
        /// <param name="attribute">The attribute to match</param>
        /// <returns>Returns a ShadoObject containing this attribute or null if it wasn't found</returns>
        public static ShadoObject GetShadoObjectFromFile(string filepath, string attribute) {

            var data = LoadShadoFile(filepath);

            return data.Find(e => e.attribute.Equals(attribute));
        }

        /// <summary>
        /// This function modifies the value of a shado object found by its attribute
        /// </summary>
        /// <param name="filepath">The file containing that object</param>
        /// <param name="attribute">The attribute of the value to modify</param>
        /// <param name="newValue">The new Value</param>
        /// <exception cref="InvalidAttributeException">Throws this exception if the attribute wasn't found inside the file</exception>
        public static void ModifyShadoObject(string filepath, string attribute, string newValue) {

            // Attribute doesn't exist
            if (GetShadoObjectFromFile(filepath, attribute) == null)
                throw new InvalidAttributeException(attribute + " attribute was not found in " + filepath);

            StringBuilder raw_format = new StringBuilder();
            var data = LoadShadoFile(filepath);

            foreach (var obj in data) {
                if (obj.attribute.Equals(attribute)) {
                    obj.value = newValue;
                }

                raw_format.AppendFormat("%s\t:\t%s\n", obj.attribute, obj.value);
            }

            // Write to File
            WriteToFile(filepath, raw_format.ToString());
        }

        public class ShadoObject
        {
            public readonly string attribute;
            public string value;

            public ShadoObject(string attribute, string value) {
                this.attribute = attribute;
                this.value = value;
            }
        }
    }
}