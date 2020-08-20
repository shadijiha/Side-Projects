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

        Number.prototype.padLeft = function (base, chr) {
            var len = (String(base || 10).length - String(this).length) + 1;
            return len > 0 ? new Array(len).join(chr || '0') + this : this;
        }

    </script>
</head>
<body>
<div id="main_panel">
    <h1>Menu</h1>
    <a href="compose.php">
        <button id="new_message">
            <img src="images/edit_img_2 - Copy.png"/>
            Nouveau Message
        </button>
    </a>
    <br/>
    <br/>
    <ul>
        <form action="index.php" method="post">
            <a href="index.php">
                <li>Mes Messages</li>
            </a>
            <a href="settings.php">
                <li>Paramètre</li>
            </a>
            <li><input type="submit" value="Fermer ma session" name="logout"/></li>
        </form>
    </ul>
</div>
<div id="all_messages">
    <div>
        <h1>Bienvenue <?php echo $_SESSION['user']->username; ?>!</h1>
    </div>
    <div id="user_messages">

        <!-- Load all messages submited by user -->
        <?php

        ?>
    </div>
    <div id="today_messages">
        <h2>Messages destinés à moi</h2>
        <?php
        $messages = GetMessageToUser($_SESSION['user']);

        foreach ($messages as &$message) {

            // Get the from user color
            $from = GetUserByName($message->author);
            $color = "lightblue";
            if ($from != null)
                $color = $from->color;

            echo "<div class='message_block' id='message_$message->id' style='background-color: $color;'>
                        <span class='message_title'>$message->title</span>
                        <span class='message_date'>
                            <script>
                                var d = new Date($message->date);
                                dformat = [d.getMonth()+1,
                                           d.getDate(),
                                           d.getFullYear()].join('/')+' '+
                                          [d.getHours(),
                                           d.getMinutes(),
                                           d.getSeconds()].join(':');
                            
                                document.write(dformat);
                            </script>
                        <br />
                        <br />
                        De: <span class='message_author'>$message->author</span>
                        <br />
                        À: <span class='message_to'>$message->tags</span>
                        <br />
                        <br />
                        <div class='message_body'>
                            $message->content
                        </div>
                    </div>";
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
