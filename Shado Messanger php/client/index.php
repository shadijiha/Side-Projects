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


<div id="all_messages">
    <div>
        <h1>Bienvenue <?php echo $_SESSION['user']->username; ?>!</h1>
    </div>
    <div id="today_messages">
        <h2>Messages destinés à moi</h2>
        <?php
        $messages = GetMessageToUser($_SESSION['user']);

        // Pas de message a moi
        if (count($messages) == 0)  {
            echo "<div style='text-align: center; font-style: italic;'>Aucun message à moi</div>";
        }

        foreach ($messages as &$message) {

            // Get the from user color
            $from = GetUserByName($message->author);
            $color = "lightblue";
            if ($from != null)
                $color = $from->color;

                DisplayMessage($message, $color);
        }

        ?>
    </div>
    <div id="user_messages">
        <h2>Messages envoyés</h2>
        <!-- Load all messages submited by user -->
        <?php
            $messages = GetAllMessagesSent($_SESSION['user']);

            // No message Sent
            if (count($messages) == 0)  {
                echo "<div style='text-align: center; font-style: italic;'>Aucun message</div>";
            }

            foreach ($messages as &$message) {

                // Get the from user color
                $from = GetUserByName($message->author);
                $color = "lightblue";
                if ($from != null)
                    $color = $from->color;
    
                    DisplayMessage($message, $color);
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
