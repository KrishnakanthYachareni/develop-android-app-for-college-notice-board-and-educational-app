<!DOCTYPE html>
<html>
<head>
<title>Simple College Notification Board</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel = "stylesheet" href = "bootstrap/css/bootstrap.css" type = "text/css"/>
<link rel = "stylesheet" href = "bootstrap/css/bootstrap.min.css" type = "text/css"/>
</head>
<style type="text/css">
a {
	text-decoration: none;
	color: #838383;
}

a:hover {
	color: black;
}

#menu {
	position: relative;
	margin-left: 30px;
}

#menu a {
	display: block;
	width: 100px;
}

#menu ul {
	list-style-type: none;
}

#menu li {
	float: left;
	position: relative;
	text-align: center;
}

#menu ul.sub-menu {
	position: absolute;
	left: -10px;
	z-index: 90;
	display:none;
}

#menu ul.sub-menu li {
	text-align: left;
}

#menu li:hover ul.sub-menu {
	display: block;
}
a
{	text-decoration:none; }
	

.egg{
position:relative;
box-shadow: 0 3px 8px rgba(0, 0, 0, 0.25);
background-color:#fff;
border-radius: 3px 3px 3px 3px;
border: 1px solid rgba(100, 100, 100, 0.4);
}
.egg_Body{border-top:1px solid #D1D8E7;color:#808080;}
.egg_Message{font-size:13px !important;font-weight:normal;overflow:hidden}

h3{font-size:13px;color:#333;margin:0;padding:0}
.comment_ui
{
border-bottom:1px solid #e5eaf1;clear:left;float:none;overflow:hidden;padding:6px 4px 3px 6px;width:431px; cursor:pointer;
}
.comment_ui:hover{
background-color: #F7F7F7;
}
.dddd
{
background-color:#f2f2f2;border-bottom:1px solid #e5eaf1;clear:left;float:none;overflow:hidden;margin-bottom:2px;padding:6px 4px 3px 6px;width:431px; 
}
.comment_text{padding:2px 0 4px; color:#333333}
.comment_actual_text{display:inline;padding-left:.4em}

ol { 
	list-style:none;
	margin:0 auto;
	width:500px;
	margin-top: 20px;
}
#mes{
	padding: 0px 3px;
	border-radius: 2px 2px 2px 2px;
	background-color: rgb(240, 61, 37);
	font-size: 9px;
	font-weight: bold;
	color: #fff;
	position: absolute;
	top: 5px;
	left: 73px;
}
.toppointer{
background-image:url(images/top.png);
    background-position: -82px 0;
    background-repeat: no-repeat;
    height: 11px;
    position: absolute;
    top: -11px;
    width: 20px;
	right: 354px;
}
.clean { display:none}
</style>
<body>
<div class="container">
	<div id="content" align="center">
		<center><img src="images/ic.png" height="" width="60" width="60" border="10">
		<form action="" method="POST">

 <table width="260" height="168">
				 <tr>
				   <td width="97">   <label>User Name </label> </td>
						 <td width="151"><input type="text"  name="uname" id="uname" class="form-control"  required/></td>
				 </tr>
				  <tr>
						  <td>      <label>Password </label> </td>
						  <td> <input type="password"  name="password" id="password"  class="form-control"  required /></td>
				  </tr>
					
					<tr>
						<td colspan="2" align="center">
							<input type="submit" value="Log In" name="admsubmit" class="btn btn-primary"  />
					  </td>
			   </tr>  
			 </table>	
			 </form>
	</div><hr/>
	<html>
<?php
if(!empty($_POST))
{
	$unme=$_POST['uname'];
	$pass=$_POST['password'];
	if($unme == 'jntuhcem' && $pass == 'admin')
		header("location: staff.php");
	else{
		echo 'Login Failed... Please enter Valid Info';
	}
}
?>
</html>