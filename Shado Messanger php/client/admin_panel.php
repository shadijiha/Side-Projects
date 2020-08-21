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
    <h1 style="margin-top: 30px">Admin panel</h1>
    <br/>

    <table class="admin_panel_table">
        <tr>
            <th>ID</th>
            <th>username</th>
            <th>password</th>
            <th>date</th>
            <th>color</th>
        </tr>
        <?php

        $users = GetAllUsers();

        foreach ($users as $temp) {

            $date = FormatDate($temp->date);

            echo "<tr>
                    <td>$temp->id</td>
                    <td>$temp->username</td>
                    <td>$temp->password</td>
                    <td>$date</td>
                    <td><div style='background-color: $temp->color; border-radius: 50%; width: 30px; height: 30px; color: transparent;'>FILLER</div></td>
                    </tr>";

        }

        ?>
    </table>

</div>

<?php
if (isset($_POST['save'])) {

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
