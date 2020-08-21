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

    <script>
        window.addEventListener("load", function () {

            setInterval(() => {
                const div = document.getElementById("all_messages");
                div.style.position = "absolute";
                div.style.left = document.getElementById("main_panel").offsetWidth + 50 + "px";
            }, 1000);

        });
    </script>
</head>
<body>
	<!-- Side Menu -->
	<?php include "common/sideMenu.php" ?>

	<!-- Display The teams of user -->
	<div id="all_messages">
	   	<h1 style="margin-top: 30px">Mes équipes</h1>

		<?php
		
		$teams = GetTeamsOf($_SESSION['user']);

		if ($teams == null)	{
			echo "<div style='text-align: center; margin: 20%;'>Vous n'êtes membre d'aucune équipe</div>";
			exit();
		}

		foreach($teams as $team)	{
			
			// Parse members to string
			$members_str = "";
			foreach($team->members as $mem)	{

				$fontColor = GetPreferableFontColor($mem->color);

				$members_str = $members_str . "
					<td>
						<div class='receipient_display' id='member_$mem->id' style='background-color: $mem->color; color: $fontColor;'>
							$mem->username
						</div>
					</td>
				";
			}

			$ad = $team->admin;
			$fontColor = GetPreferableFontColor($ad->color);

			echo "
				<div class='team'>
					<div class='team_name'>Équipe $team->id</div>
					<table>
						<tr>
							<td>Administrateur</td>
							<td>
								<div class='receipient_display' style='background-color: $ad->color;color: $fontColor;'>$ad->username</div>
							</td>
						</tr>
						<tr>
							<td>Membres</td>
							$members_str
						</tr>
					</table>
				</div>
				<br />
			";

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
