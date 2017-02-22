<?php
include('connect.php');
function clean($str)
	{
		$str = @trim($str);
		if(get_magic_quotes_gpc())
			{
			$str = stripslashes($str);
			}
		return mysql_real_escape_string($str);
	}
$position = clean($_POST['position']);
$message = clean($_POST['message']);

mysql_query("DELETE FROM `noticemsg` WHERE position='$position'");
	header("location: index.php");
?>