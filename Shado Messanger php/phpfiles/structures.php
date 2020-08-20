<?php

/**
 *  These structures must be the same as the javascript once
 *
 */

class User
{
    public $id = -1;
    public $username = "";
    public $password = "";
    public $date;
    public $admin;
    public $color;

    function __construct($int_id, $string_username, $string_password, $int_date, $boolean_admin, string $_color)
    {
        $this->id = $int_id;
        $this->username = $string_username;
        $this->password = $string_password;
        $this->date = $int_date;
        $this->admin = $boolean_admin;
        $this->color = $_color;
    }

    function getID()
    {
        return $this->id;
    }
}

class Message
{
    public $id = -1;
    public $title = "";
    public $content = "";
    public $author = "";
    public $tags = array();
    public $date;
    public $readby;

    function __construct($_id, $_author, $_title, $_content, $_tags, $_date, $_readby)
    {
        $this->id = $_id;
        $this->author = $_author;
        $this->title = $_title;
        $this->content = $_content;
        $this->tags = $_tags;
        $this->date = $_date;
        $this->readby = $_readby;
    }
}

?>
