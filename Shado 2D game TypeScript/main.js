const mergeFiles = require("merge-files");
const path = require("path");
const fs = require("fs");

// Merge all files
async function compileFiles() {
	const OUTPUT = "compiled_script";
	const outputPath = __dirname + `/client/${OUTPUT}.js`;

	priorityList = path.join(__dirname, "/src/priority.txt");
	fs.readFile(priorityList, { encoding: "utf-8" }, async function(err, data) {
		if (!err) {
			// Get files from priority list
			// Patse files to get only names
			// Add the directory "D://Folder/Folder//..."
			let inputPathList = data.split("\r");
			for (let i = inputPathList.length - 1; i >= 0; i--) {
				inputPathList[i] = inputPathList[i].split("\n");
				inputPathList[i] = inputPathList[i].join("");
				if (!inputPathList[i].includes(".js")) {
					inputPathList.splice(i, 1);
				}
				inputPathList[i] = __dirname + "/CompiledFiles/" + inputPathList[i];
			}

			// if the status is secuss {true} => Go read from the file just MERGED to remove spaces
			const status = await mergeFiles(inputPathList, outputPath);

			if (status) {
				// Log message
				console.log("All Files have been merged successfully");

				// When finish, Read the same file to remove all white spaces from the file
				/*fs.readFile(
					path.join(__dirname, `/client/${OUTPUT}.js`),
					{ encoding: "utf-8" },
					async function(err, data) {
						if (!err) {
							// Repalce white spaces
							data = data.replace(/  |\n|\r/g, "");

							// When finish, Read the same file to remove all white spaces from the file
							//const status = await mergeFiles(inputPathList, outputPath);
							console.log(
								`White spaces from ${OUTPUT}.js has been removed successfully`
							);

							// write to file
							fs.writeFile(
								path.join(__dirname, `/client/${OUTPUT}.js`),
								data,
								function(err) {
									if (err) {
										return console.log(err);
									}
									console.log("The file was saved!");
								}
							);
						} else {
							console.log(err);
						}
					}
				);
				*/
			} else {
				console.log("Could not merge files! Error!");
			}
		} else {
			console.log(err);
		}
	});
}
compileFiles();
