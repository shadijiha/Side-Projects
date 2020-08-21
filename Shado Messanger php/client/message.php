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

	<!-- Display Disred message -->
	<div id="all_messages">
		<?php

		if (!isset($_GET['id']))	{
			echo "<div style='text-align: center; margin: 20%;'>Cette message n'exist pas</div>";
			exit();
		}

		$id = $_GET['id'];
		$message = GetMessageById($id);
		$date = FormatDate($message->date);

		if ($message == null)	{
			echo "<div style='text-align: center;'>Cette message n'exist pas</div>";
		} else	{

			// See if User has the permission
			if (strpos($message->tags, $_SESSION['user']->username . ",") === false && strcmp($_SESSION['user']->username, $message->author) !== 0)	{
				echo "<div style='text-align: center; margin: 20%;'>Je ne sais pas comment vous vous êtes rendu là. Mais vous n'avez pas la permission de voir ce message</div>";
				exit();
			}

			// Mark as red by this reader
			MarkMessageAsRed($message, $_SESSION['user']);

			// See and format the readers of the message
			$readByStr = "";
			$readersRaw = explode(",", $message->readby);
			foreach($readersRaw as $u)	{
				$temp = GetUserByName(trim($u));
				if ($temp == null)
					continue;

				$fontColor = GetPreferableFontColor($temp->color);

				$readByStr .= "
					<div class='receipient_display' style='background-color: $temp->color; color: $fontColor;'>
						$temp->username
					</div>
				";
			}
			
			echo "
				<h1>$message->title</h1>
				<br />
				<table>
					<tr>
						<td><b>De: </b></td>
						<td>$message->author</td>
					</tr>
					<tr>
						<td><b>À: </b></td>
						<td>$message->tags</td>
					</tr>
					<tr>
						<td><b>Lu par: </b></td>
						<td>$readByStr</td>
					</tr>
					<tr>
						<td><b>Envoyé le: </b></td>
						<td>$date</td>
					</tr>
				</table>
				<br />
				<div style='width: 600px; text-align: justify; padding: 20px'>
					$message->content
				</div>

				<br />
				<form action='message.php?id=$message->id' method='post'>
					<input type='button' onclick='window.location.href = `index.php`;' value='Messages' class='blue_btn' />
					<input type='submit' value='Supprimer' name='delete' class='red_btn' />
				</form>
			";

			if (isset($_POST['delete']))	{
				DeleteMessage($_GET['id']);
			}
		}	
		?>  
	</div> 
</div>

<?php

if (isset($_POST['logout'])) {
    Logout();
}

?>
</body>
</html>
