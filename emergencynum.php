<?php  

$conn = mysqli_connect("localhost", "root", "", "test");

$ID = $_GET['ID'];

   $select_query1 = "SELECT * FROM emergency WHERE ID = '$ID' AND STEP = 1";
   $result_set1 = mysqli_query($conn, $select_query1);
   $count1 = mysqli_num_rows($result_set1);
   
   $select_query2 = "SELECT * FROM emergency WHERE ID = '$ID' AND STEP = 2";
   $result_set2 = mysqli_query($conn, $select_query2);
   $count2 = mysqli_num_rows($result_set2);
   
   $select_query3 = "SELECT * FROM emergency WHERE ID = '$ID' AND STEP = 3";
   $result_set3 = mysqli_query($conn, $select_query3);
   $count3 = mysqli_num_rows($result_set3);
   
   $select_query4 = "SELECT * FROM emergency WHERE ID = '$ID' AND STEP = 4";
   $result_set4 = mysqli_query($conn, $select_query4);
   $count4 = mysqli_num_rows($result_set4);
   
   $select_query5 = "SELECT * FROM emergency WHERE ID = '$ID' AND STEP = 5";
   $result_set5 = mysqli_query($conn, $select_query5);
   $count5 = mysqli_num_rows($result_set5);



   echo $count1.','.$count2.','.$count3.','.$count4.','.$count5;


   mysqli_close($conn);
   
?>
