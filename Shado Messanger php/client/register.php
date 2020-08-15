<?php
	session_start();
	require_once('../phpfiles/config.php');
	//phpinfo();
?>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="styles.css" />
		<title>Shado Messanger</title>
		<script src="./client.js"></script>
		<script>
			window.onload = function()	{
				document.getElementById("date").value = Date.now();

				setInterval(() => {
					document.getElementById("date").value = Date.now();
				}, 1000);
			}
		</script>
	</head>
	<body>
		<div id="_title">
			Shado Messanger
		</div>
		<form action="register.php" method="post">
			<div id="login_panel">
				<h1>Enregistrement</h1>
				<div id="error_handler_div"></div>
				<br />
				<span>Nom d'utilisateur</span>
				<br />
				<br />
				<input type="text" name="username" id="username_input" />
				<br />
				<br />
				<br />
				<span>Mot de pass</span>
				<br />
				<br />
				<input type="password" name="password" id="password_input" />
				<br />
				<br />
				<br />
				<span>Confirmer le Mot de pass</span>
				<br />
				<br />
				<input type="password" name="cpassword" id="confirm_password_input" />
				<br />
				<br />
				<input type="hidden" id="date" name="date" value="" />
				<input
					type="submit"
					value="Enregistrer"
					id="login_btn"
					name="register_btn"
				/>
				<br />
				<br />
				<a href="login.php">Déjà un utalisateur? Connectez-vous</a>
			</div>
		</form>
		<?php

			include "../phpfiles/util.php";

			if (isset($_POST['register_btn']))	{

				$username = $_POST['username'];
				$password = $_POST['password'];
				$cpassword = $_POST['cpassword'];
				$date = $_POST['date'];

				if ($password != $cpassword)	{
					echo '<script>ErrorDisplay("Les mots de pass ne sont pas identiques");</script>';
				} else	{

					// Register user
					$user = RegisterUser($username, $password, $date);

					// Verify that the registration is successful
					if ($user == null)	{
						echo '<script>ErrorDisplay("Le nom de l\'utilisateur est déjà pris!");</script>';
					} else	{
						Login($user->username, $user->password);
						
						// Goto index
						echo "<script>window.location.href = 'index.php';</script>";
					}

				}
			}

		?>
	</body>
</html>
