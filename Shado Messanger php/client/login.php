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
	</head>
	<body>
		<div id="_title">
			Shado Messanger
		</div>
		<form action="login.php" method="post">
			<div id="login_panel">				
				<h1>Connexion</h1>
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
				<input
					type="submit"
					value="Connexion"
					id="login_btn"
					name = "login_btn"
				/>
				<br />
				<br />
				<a href="register.php">Première utilisation? Créer un compte</a>
			</div>
		</form>
		<?php

			include '../phpfiles/util.php';
		
			if (isset($_POST['login_btn']))	{

				// Login
				$user = Login($_POST['username'], $_POST['password']);

				if ($user == null)	{
					echo "<script>ErrorDisplay('Erreur! Le nom d\'utalisateur ou le mot de pass sont incorrects');</script>";
				} else	{
					echo "<script>window.location.href = 'index.php';</script>";
				}
			}
		
		?>
	</body>
</html>
