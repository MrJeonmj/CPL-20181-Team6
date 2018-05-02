<?php  

$conn = mysqli_connect("localhost", "root", "", "test");

$ID = $_GET['ID'];
$FORM = $_GET['FORM'];

if($FORM == 0){
	$select_query = "SELECT AVG(HEART_RATE) FROM heartbeat WHERE ID = '$ID'";
}
if($FORM == 1){
	$select_query = "SELECT MAX(HEART_RATE) FROM heartbeat WHERE ID = '$ID'";
}
if($FORM == 2){
	$select_query = "SELECT MIN(HEART_RATE) FROM heartbeat WHERE ID = '$ID'";
}


 $result_set = mysqli_query($conn, $select_query);
   $row = mysqli_fetch_array($result_set);



   echo $row[0];



   mysqli_close($conn);
   
?>
