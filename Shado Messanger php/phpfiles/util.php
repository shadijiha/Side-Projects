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
        $temp = new User($row['id'], $row['username'], $row['password'], $row['date'], $row['admin'], $row['color']);
        $_SESSION['user'] = $temp;

        return $temp;
    } else {
        // invalid
        $_SESSION['user'] = null;
        return null;
    }
}

function RegisterUser(string $username, string $password, string $date, string $color)
{

    $conn = $GLOBALS['conn'];

    $query = "SELECT * FROM users WHERE username='$username'";
    $query_run = mysqli_query($conn, $query);

    if (mysqli_num_rows($query_run) > 0) {
        // there is already a user with the same username
        return null;
    } else {

        $admin = 0;
        $query4 = "INSERT INTO `users` (`id`, `username`, `password`, `date`, `admin`, `color`) VALUES (NULL, '$username', '$password', '$date', '$admin', '$color')";

        $result = $conn->query($query4);

        if (!$result) {
            echo $conn->error . "";
            return null;
        } else {
            // Log in the user
            return new User(-1, $username, $password, -1, false, $color);
        }
    }
}

function Logout()
{
    $_SESSION['user'] = null;
}

function GetUserByName(string $username): ?User
{

    // Create connection
    //$conn = new mysqli($servername, $username, $password, $dbname);
    // Check connection
    $conn = $GLOBALS['conn'];
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }

    $sql = "SELECT * FROM `users` WHERE username='$username'";

    $result = $conn->query($sql);
    $array = array();

    if ($result) {

        while ($row = mysqli_fetch_array($result, MYSQLI_ASSOC)) {
            array_push(
                $array,
                new User($row['id'], $row['username'], $row['password'], $row['date'], $row['admin'], $row['color'])
            );
        }

        // output data of each row
        return $array[0];
    } else {
        return null;
    }
}

function AddMessage(User $user, string $title, string $content, string $tags, string $date): ?Message
{

    $message = new Message(-1, $user->username, $title, $content, $tags, $date, "");

    $conn = $GLOBALS['conn'];

    $admin = 0;
    $query = "INSERT INTO `messages` (`id`, `author`, `title`, `content`, `tags`, `date`, `readby`) VALUES (NULL, '$user->username', '$title', '$content', '$tags', '$date', '')";

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

function GetMessageToUser(User $user): ?array
{
    $conn = $GLOBALS['conn'];

    $query = "SELECT * FROM `messages` WHERE `tags` LIKE '%{$user->username}%'";
    $result = $conn->query($query);

    $array = array();

    if (!$result) {
        echo $conn->error . "";
        return null;
    } else {

        while ($row = mysqli_fetch_array($result, MYSQLI_ASSOC)) {
            array_push($array, new Message($row['id'], $row['author'], $row['title'], $row['content'], $row['tags'], $row['date'], $row['readby']));
        }

        return $array;
    }

}

?>