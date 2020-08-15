/**
 *
 */

function ErrorDisplay(msg) {
	const DIV = document.getElementById("error_handler_div");

	if (DIV) {
		DIV.innerHTML = msg;
		DIV.style.display = "block";
	}
}

async function Login(username, password) {
	const data = { username, password };

	const options = {
		method: "POST",
		headers: {
			"Content-type": "application/json",
		},
		body: JSON.stringify(data),
	};

	const response = await fetch("/login", options);
	const json = await response.json();

	// Display errors
	if (json.status.includes("error")) {
		ErrorDisplay(json.message);
		return;
	}

	// TODO: Start session
	const user = json.results[0];
	user.password = null;
	localStorage.setItem("user", JSON.stringify(user));

	window.location.href = "index.html";
}

async function Register(username, password, confirmPassword) {
	if (password != confirmPassword) {
		ErrorDisplay("Les mots de pass ne sont pas identiques!");
		return;
	}

	const data = { username, password };

	const options = {
		method: "POST",
		headers: {
			"Content-type": "application/json",
		},
		body: JSON.stringify(data),
	};

	const response = await fetch("/register", options);
	const json = await response.json();

	// See if there's any error
	if (json.status.includes("error")) {
		ErrorDisplay(json.message);
		return;
	}

	// Go to Login.html
	window.location.href = "login.html";
	Login(username, password);
}

async function Logout() {
	window.localStorage.removeItem("user");
}

const VerifyUser = () => {};
