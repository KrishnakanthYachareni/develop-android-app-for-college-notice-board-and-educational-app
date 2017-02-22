<!DOCTYPE html>
<html>
<head>
<title>Simple College Notice Board</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel = "stylesheet" href = "../bootstrap/css/bootstrap.css" type = "text/css"/>
<link rel = "stylesheet" href = "../bootstrap/css/bootstrap.min.css" type = "text/css"/>
</head>
<body>
<div id="main" class="container">
<table class="table table-bordered">
<h2 align="center">Staff Message Notice Board</h2><hr/>
    <tbody>
      <tr>
        <td><div id="content">
			<?php
			include('../connect.php');
			$result = mysql_query("SELECT * FROM noticemsg WHERE position='staff'");
					while($row = mysql_fetch_array($result))
						{
							echo '<li>'.$row['message'].'</li>';
						}
			?>
			<div class="clearfix"></div>
			</div></td>
      </tr>
    </tbody>
</table>
<div id="footer" align="center">Sourcecodester &copy 2016</div>
</div>
</body>
</html>
