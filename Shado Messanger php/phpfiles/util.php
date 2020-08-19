<?php

/**
 *
 */

include 'structures.php';

/**
 * Logs a user in the returns the logged user
 * Returns null if an error has occured
 *
 * @param username The username of the user
 * @param password The password of the user
 * @return Returns the Logged user
 */
function Login(string $username, string $password): ?User
{

    // See if the user exists
    $query = "SELECT * FROM users WHERE username='$username' AND password='$password'";

    $conn = $GLOBALS['conn'];

    $query_run = mysqli_query($conn, $query);
    if (mysqli_num_rows($query_run) > 0) {
        $row = mysqli_fetch_assoc($query_run);
        // valid

        $temp = new User($row['id'], $row['username'], $row['password'], $row['date'], $row['admin']);
        $_SESSION['user'] = $temp;

        return $temp;
    } else {
        // invalid
        $_SESSION['user'] = null;
        return null;
    }

}

function RegisterUser(string $username, string $password, string $date)
{

    $conn = $GLOBALS['conn'];

    $query = "SELECT * FROM users WHERE username='$username'";
    $query_run = mysqli_query($conn, $query);

    if (mysqli_num_rows($query_run) > 0) {
        // there is already a user with the same username
        return null;
    } else {

        $admin = 0;
        $query4 = "INSERT INTO `users` (`id`, `username`, `password`, `date`, `admin`) VALUES (NULL, '$username', '$password', '$date', '$admin')";

        $result = $conn->query($query4);

        if (!$result) {
            echo $conn->error . "";
            return null;
        } else {
            // Log in the user
            return new User(-1, $username, $password, -1, false);
        }
    }
}

function Logout()
{
    $_SESSION['user'] = null;
}

function GetUserByName(string $username): ?User
{

    $servername = $GLOBALS['db_server'];
    $username = $GLOBALS['db_username'];
    $password = $GLOBALS['db_password'];
    $dbname = $GLOBALS['db_name'];
    $sql = "";

    // Create connection
    //$conn = new mysqli($servername, $username, $password, $dbname);
    // Check connection
    $conn = $GLOBALS['conn'];
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }


    $sql = "SELECT * FROM items WHERE username='$username'";


    $result = $conn->query($sql);
    $array = array();
    while ($row = $result->fetch_assoc()) {
        array_push(
            $array,
            new User($row['id'], $row['username'], $row['password'], $row['date'], $row['admin'])
        );
    }

    if ($result->num_rows > 0) {
        // output data of each row
        return $array[0];
    } else {
        return null;
    }
}

function AddMessage(string $username, string $title, string $content, string $tags, string $date): ?Message
{

    $message = new Message(-1, $username, $title, $content, $tags, $date, "");

    $conn = $GLOBALS['conn'];

    $admin = 0;
    $query = "INSERT INTO `messages` (`id`, `author`, `title`, `content`, `tags`, `date`, `readby`) VALUES (NULL, '$username', '$title', '$content', '$tags', '$date', '')";

    $result = $conn->query($query);

    if (!$result) {
        echo $conn->error . "";
        return null;
    } else {
        // Log in the user
        return $message;
    }
}

function GetAllMessagesOfUser(User $user)
{


}

?>