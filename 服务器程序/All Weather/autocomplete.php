<?php
	// Include config file
	include("include/config.php");

	$q = $_GET['q'];
	$my_data = mysqli_real_escape_string($mysqli,$q);
	$sql = "SELECT * FROM location WHERE city LIKE '$my_data%'";
	$result = mysqli_query($mysqli,$sql) or die(mysqli_error($mysqli));

	if($result) {
		while($row=mysqli_fetch_array($result))
		{
			echo $row['city']. ", " . $row['country'] . "\n";
		}
	}
?>