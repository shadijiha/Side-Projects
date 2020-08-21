<?php
require_once('../phpfiles/config.php');
include '../phpfiles/util.php';     // THIS HAS TO BE HERE BEFORE SESSION START

session_start();
?>
<html lang="fr">

<head>
    <link rel="stylesheet" type="text/css" href="styles.css"/>
    <link rel="stylesheet" type="text/css" href="autocomplete.css"/>
    <title>Shado Messanger</title>
    <script src="./client.js"></script>
    <script src="./autocomplete.js"></script>

    <!-- See if a user is already login in -->
    <?php
    if ($_SESSION['user'] == null || $_SESSION['user'] == "") {
        echo "<script>window.location.href = 'login.php';</script>";
    }
    ?>

    <script src="./fixOffset.js"></script>
    <script>
        const users = [];

        <?php
        // at the start get all users from database for javascript menu suggestions
        $query = "SELECT * FROM `users`";

        $conn = $GLOBALS['conn'];
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }

        $result = $conn->query($query);
        $array = array();
        while ($row = $result->fetch_assoc()) {
            $array[$row['username']] = $row['color'];
        }

        // Convert php array to javascript array
        foreach ($array as $k => $v) {
            echo "users.push({username: '$k', color: '$v'});\n";
        }

        ?>
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

    <form action="compose.php" method="post" autocomplete="off">
        <h1>Composer un nouveau message</h1>
        <br/>
        <br/>

        <table>
            <td>À</td>
            <td>
                <div class="autocomplete" style="width:300px;">
                    <input type="text" placeholder="Nom(s)..." onkeydown="Suggest();" id="receipients_input">
                </div>
            </td>
            <td id="receipients"></td>
        </table>
        <br/>
        <span class="bigger">Titre </span>
        <input type="text" id="new_message_title" name="new_message_title"
               placeholder="Titre"/>
        <br/>
        <br/>
        <label>
            <textarea name="message_content" placeholder="message"></textarea>
        </label>
        <br/>
        <br/>
        <input type="submit" value="Envoyer" class="blue_btn" name="send_message"/>

        <!-- Hidden inputs -->
        <input type="hidden" value="" id="recepients_buffer" name="recepients_buffer"/>
        <input type="hidden" value="" id="data_value" name="date_value"/>

        <script>

            setInterval(function () {
                document.getElementById("data_value").value = Date.now();
            }, 1000);

            HandleAutoCompleteClick = (element) => {

                // See if the buffer already contains that receipiant
                if (document.getElementById("recepients_buffer").value.includes(element.username + ","))
                    return;

                // Add to buffer                
                document.getElementById("recepients_buffer").value += element.username + ",";

                // Add to the HTML page
                document.getElementById("receipients").innerHTML += `<div class="receipient_display" style="background-color: ${element.color};" id="receipiant_${element.username}">${element.username}</div>`;

                // Clear the input
                document.getElementById("receipients_input").value = "";

                // Add the delete functionality
                document.getElementById("receipiant_" + element.username).addEventListener("click", function () {

                    // Remove from buffer
                    document.getElementById("recepients_buffer").value = document.getElementById("recepients_buffer").value.replace(element.username + ",", "");

                    document.getElementById("receipiant_" + element.username).remove();
                });
            }

            function Suggest() {
                autocomplete(document.getElementById("receipients_input"), users);
            }

        </script>
    </form>

</div>

<?php

if (isset($_POST['logout'])) {
    Logout();
}

// Sending a message
if (isset($_POST['send_message'])) {

    $author = $_SESSION['user'];
    $recepients = $_POST['recepients_buffer'];
    $date = $_POST['date_value'];
    $content = $_POST['message_content'];
    $title = $_POST['new_message_title'];

    //$recepients = explode(",", $recepients);

    $msg = AddMessage($author, $title, $content, $recepients, $date);

    if ($msg == null) {
        echo "<script>alert('Erreur! Le message m\'a pas été envoyé')</script>";
    }
}

?>
</body>

</html>