<?php
$con=mysqli_connect("localhost","root","","test");
 
if (mysqli_connect_errno($con))
{
   echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$ID = $_GET['ID'];
$PW = $_GET['PW'];

if ($ID !="" and $PW !=""){
$result = mysqli_query($con,"SELECT pw FROM member_info where id='$ID'");

$row = mysqli_fetch_array($result);
$data = $row[0];
 
if($data == $PW){
	echo "login_success";
}
else{
	
	echo "login_fail";
}
}

else{
	echo "blank_existed";
}
mysqli_close($con);
?>
