<?php
require_once('../phpfiles/config.php');
include '../phpfiles/util.php';     // THIS HAS TO BE HERE BEFORE SESSION START

session_start();

//phpinfo();
?>
<html lang="fr">
<head>
    <link rel="stylesheet" type="text/css" href="styles.css"/>
    <title>Shado Messanger</title>
    <script src="./client.js"></script>

    <!-- See if a user is already login in -->
    <?php
    if ($_SESSION['user'] == null || $_SESSION['user'] == "") {
        echo "<script>window.location.href = 'login.php';</script>";
    }
    ?>

	<script src="./fixOffset.js"></script>
</head>
<body>
	<!-- Side Menu -->
	<?php include "common/sideMenu.php" ?>

	<!-- Display The teams of user -->
	<div id="all_messages">
	   	<h1 style="margin-top: 30px">Paramètres</h1>
		<br />
	   	<form action="settings.php" method="post">
	   		<table>
	   			<tr>
				   <td>Nom d'utilisateur</td>
				   <td>
	   				<input type="text" disabled="disabled" value="<?php echo $_SESSION['user']->username ?>">
				   </td>
				</tr>
				<tr>
				   <td>Mot de pass</td>
				   <td>
	   				<input id="pass" name="password" type="password" value="<?php echo $_SESSION['user']->password ?>">
				   </td>
				   <td>
	   				<img src="https://cdn.onlinewebfonts.com/svg/img_374056.png" width="30" height="20" title="montrer mot de pass" alt="montrer mot de pass" onclick="document.getElementById('pass').type= 'text';" />
				   </td>
				</tr>
				<tr>
				   <td>Couleur</td>
				   <td>
	   				<input name="color" type="color" value="<?php echo $_SESSION['user']->color ?>">
				   </td>
				</tr>
				<tr>
				   <td>Date de création</td>
				   <td>
	   				<?php echo FormatDate($_SESSION['user']->date); ?>
				   </td>
				</tr>
			</table>
			<br />
			<br />
			<input type="submit" name="save" value="Sauvgarder" class="blue_btn" />
		</form>
	</div> 

	<?php
		if (isset($_POST['save']))	{

			$pass = $_POST['password'];
			$color = $_POST['color'];

			UpdateUser($_SESSION['user'], $pass, $color);

			header("Refresh:0");
		}	
	?>
</div>

<?php

if (isset($_POST['logout'])) {
    Logout();
}

?>
</body>
</html>
