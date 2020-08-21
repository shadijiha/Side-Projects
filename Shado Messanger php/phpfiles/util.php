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

    echo "<script>window.location.href = 'login.php';";
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
        if (count($array) > 0)
            return $array[0];
        else   
            return null;
    } else {
        return null;
    }
}

function GetMessageById(string $id): ?Message   {
       // Create connection
    //$conn = new mysqli($servername, $username, $password, $dbname);
    // Check connection
    $conn = $GLOBALS['conn'];
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }

    $sql = "SELECT * FROM `messages` WHERE id='$id'";

    $result = $conn->query($sql);
    $array = array();

    if ($result) {

        while ($row = mysqli_fetch_array($result, MYSQLI_ASSOC)) {
            array_push(
                $array,
                new Message($row['id'], $row['author'], $row['title'], $row['content'], $row['tags'], $row['date'], $row['readby'])
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

function GetAllMessagesSent(User $user)
{
    $conn = $GLOBALS['conn'];

    $query = "SELECT * FROM `messages` WHERE author='$user->username'";
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

function DisplayMessage(Message $message, string $color): void    {

    // Cut the message content if it is too big
    $tempContent = $message->content;
    if (strlen($message->content) > 60)    {
        $tempContent = substr($message->content, 0, 60) . "... <br /><br />[Cliquez pour le message complet]";
    }

    // Get proper font color
    $fontColor = GetPreferableFontColor($color);
    $date = FormatDate($message->date);

    echo "<div onclick='window.location.href = `message.php?id=$message->id`;'
             class='message_block' id='message_$message->id' style='background-color: $color; color: $fontColor;'>
                <h3 class='message_title'>$message->title</h3>
                <table style='color: $fontColor;'>
                    <tr>
                        <td>De: </td>
                        <td>$message->author</td>
                    </tr>
                    <tr>
                        <td>À: </td>
                        <td>$message->tags</td>
                    </tr>
                    <tr>
                        <td>Lu par: </td>
                        <td>$message->readby</td>
                    </tr>
                    <tr>
                        <td>Envoyé le: </td>
                        <td>$date</td>
                    </tr>
                </table>
                <br />
                <div class='message_body'>
                    $tempContent
                </div>
            </div>";
}

function FormatDate(int $date): string  {
    return "<script>
            var d = new Date($date);
            dformat = [d.getMonth()+1,
                    d.getDate(),
                    d.getFullYear()].join('/')+' '+
                    [d.getHours(),
                    d.getMinutes(),
                    d.getSeconds()].join(':');

            document.write(dformat);
        </script>";
}

function DeleteMessage(int $id): void   {

    $conn = $GLOBALS['conn'];

    $query = "DELETE FROM `messages` WHERE id=$id";
    $result = $conn->query($query);

    if ($conn->query($query) === TRUE) {
        echo "Record deleted successfully";
      } else {
        echo "Error deleting record: " . $conn->error;
      } 

}

function GetTeamsOf(User $user): ?array  {

    $conn = $GLOBALS['conn'];

    $query = "SELECT * FROM `teams` WHERE `admin`='$user->username' OR `members` LIKE '%{$user->username}%'";
    $result = $conn->query($query);

    $array = array();

    if (!$result) {
        echo $conn->error . "";
        return null;
    } else {

        while ($row = mysqli_fetch_array($result, MYSQLI_ASSOC)) {

            $members = array();
            $rawMembers = explode(",", $row['members']);

            foreach($rawMembers as $val)    {
                array_push($members, GetUserByName(trim($val)));
            }

            array_push($array, new Team($row['id'], GetUserByName(trim($row['admin'])), $members));
        }

        return $array;
    }

}

function GetPreferableFontColor(string $hex)  {

    $hex = str_replace("#", "", $hex);

    if(strlen($hex) == 3) {
       $r = hexdec(substr($hex,0,1).substr($hex,0,1));
       $g = hexdec(substr($hex,1,1).substr($hex,1,1));
       $b = hexdec(substr($hex,2,1).substr($hex,2,1));
    } else {
       $r = hexdec(substr($hex,0,2));
       $g = hexdec(substr($hex,2,2));
       $b = hexdec(substr($hex,4,2));
    }

    if (($r*0.299 + $g*0.587 + $b*0.114) > 186) {
        return "#000000";    
    } else  {
        return "#ffffff";
    }
}

function UpdateUser(User $user, string $password, string $color): void    {

    $conn = $GLOBALS['conn'];

    $query = "UPDATE `users` SET `password`='$password', `color`='$color' WHERE id=$user->id";
    $result = $conn->query($query);

    if ($conn->query($query) === FALSE) {
        echo "Error Updating record: " . $conn->error;
        return;
    }

    $_SESSION['user']->password = $password;
    $_SESSION['user']->color = $color;
}

function MarkMessageAsRed(Message $message, User $user): void {

    $conn = $GLOBALS['conn'];

    // See if old read by already contes the user
    if (strpos($message->readby, $user->username . ",") !== false) {
        return;
    } else  {

        // Otherwise add it to the read by
        $message->readby .= $user->username . ",";

        $query = "UPDATE `messages` SET `readby`='$message->readby' WHERE id=$message->id";
        $result = $conn->query($query);

        if ($conn->query($query) === FALSE) {
            echo "Error Updating record: " . $conn->error;
            return;
        }
    }
}

?>