
<?php

/**
 *  These structures must be the same as the javascript once
 * 
 */

class User	{
	private $id = -1;
	public $username = "";
	public $password = "";
	public $date;
	public $admin;

	function __construct($int_id, $string_username, $string_password, $int_date, $boolean_admin) {
		$id = $int_id;
		$username = $string_username;
		$password = $string_password;
		$date = $int_date;
		$admin = $boolean_admin;
	 }

	 function getID()	{
		 return $id;
	 }
}

class Message	{
	private $id = -1;
	public $title = "";
	public $content = "";
	public $author = "";
	public $tags = array();
	public $date;
	public $readby;

	function __construct($_id, $_author, $_title, $_content, $_tags, $_date, $_readby)	{
		$id = $_id;
		$author = $_author;
		$title = $_title;
		$content = $_content;
		$tags = $_tags;
		$date = $_date;
		$readby = $_readby;
	}
}

?>
