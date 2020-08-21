<?php 

    $adminResult = "";
    if ($_SESSION['user']->admin)   {
        $adminResult = "<a href=\"admin_panel.php\">
                            <li>Admin Panel</li>
                        </a>";
    }

    echo  "
<div id=\"main_panel\">
    <h1>Menu</h1>
    <a href=\"compose.php\">
        <button id=\"new_message\">
            <img src=\"images/edit_img_2 - Copy.png\"/>
            Nouveau Message
        </button>
    </a>
    <br/>
    <br/>
    <ul>
        <form action=\"index.php\" method=\"post\">
            <a href=\"index.php\">
                <li>Mes Messages</li>
            </a>
            <a href=\"teams.php\">
                <li>Mes équipes</li>
            </a>
            $adminResult 
            <a href=\"settings.php\">
                <li>Paramètre</li>
            </a>
            <li><input type=\"submit\" value=\"Fermer ma session\" name=\"logout\" style=\"width: 100%; text-align: left;\"/></li>
        </form>
    </ul>
</div>";
?>