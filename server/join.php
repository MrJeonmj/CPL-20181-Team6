<?php
$link=mysqli_connect("localhost","root","","test"); 
if (!$link)  
{ 
   echo "MySQL ?묒냽 ?먮윭 : ";
   echo mysqli_connect_error();
   exit();
}  
 
$ID = $_GET['ID'];
$PW = $_GET['PW'];
$NAME = $_GET['NAME'];
$CONTACT = $_GET['CONTACT'];
$ADDRESS = $_GET['ADDRESS'];
$BIRTHDATE = $_GET['BIRTHDATE'];
$IS_PATIENT = $_GET['IS_PATIENT'];

if ($ID !="" and $PW !="" and $NAME !="" and $CONTACT !="" and $BIRTHDATE !="" and $ADDRESS !="" and $IS_PATIENT !=""){   

$sql="insert into member_info(ID,PW,NAME,CALL_NUM,ADDRESS,DATE_OF_BIRTH,IS_PATIENT) values('$ID','$PW','$NAME','$CONTACT','$ADDRESS','$BIRTHDATE','$IS_PATIENT')";
    $result=mysqli_query($link,$sql);  
 
    if($result){  
       echo "join_success";  
    }  
    else{  
       echo "id_overlap"; 
    } 
}
else {
    echo "blank_existed";
}

mysqli_close($link);
?>

