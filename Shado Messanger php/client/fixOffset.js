/**
 * This script is here to fix some problems with the offset
 */

window.addEventListener("load", function () {
	const div = document.getElementById("all_messages");
	div.style.position = "absolute";
	div.style.left =
		document.getElementById("main_panel").offsetWidth + 50 + "px";

	setInterval(() => {
		const div = document.getElementById("all_messages");
		div.style.position = "absolute";
		div.style.left =
			document.getElementById("main_panel").offsetWidth + 50 + "px";
	}, 1000);
});

Number.prototype.padLeft = function (base, chr) {
	var len = String(base || 10).length - String(this).length + 1;
	return len > 0 ? new Array(len).join(chr || "0") + this : this;
};
