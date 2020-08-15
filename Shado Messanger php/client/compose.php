<?php
	session_start();
	require_once('../phpfiles/config.php');
	//phpinfo();
?>
<html lang="fr">
	<head>
		<link rel="stylesheet" type="text/css" href="styles.css" />
		<title>Shado Messanger</title>
		<script src="./client.js"></script>

		<!-- See if a user is already login in -->
<?php
	if ($_SESSION['user'] == null || $_SESSION['user'] == "")	{
		echo "<script>window.location.href = 'login.php';</script>";
	}
?>

<script>

	window.addEventListener("load",  function()	{

		setInterval(() => {
			const div = document.getElementById("all_messages");
			div.style.position = "absolute";
			div.style.left = document.getElementById("main_panel").offsetWidth + 50 + "px";
		}, 1000);
		
	});

</script>
	</head>
	<body>
		<div id="main_panel">
			<h1>Menu</h1>
			<a href="compose.php">
				<button id="new_message">
					<img src="images/edit_img_2 - Copy.png" />
					Nouveau Message
				</button>
			</a>
			<br />
			<br />
			<ul>
				<form action="index.php" method="post">
					<a href="index.php"><li>Mes Messages</li></a>
					<a href="settings.php"><li>Paramètre</li></a>
					<li><input type="submit" value="Fermer ma session" name="logout" /></li>
				</form>
			</ul>
		</div>
		<div id="all_messages">
			
			<h1>Composer un nouveau message</h1>
			<br />
			<br />
			<table>
				<td>À</td>
				<td><input type="text" placeholder="Nom(s)..."></td>
				<td></td>
			</table>
			<input type="text" id="new_message_title" placeholder="Titre" />

		</div>

		<?php

			include '../phpfiles/util.php';

			if (isset($_POST['logout']))	{
				Logout();
			}

		?>
	</body>
</html>
