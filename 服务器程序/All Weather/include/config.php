<?php
/* @All Weather 1.1 */

// MySQL connect
$mysqli = mysqli_connect('host','username','password','database') or die("Database Error");

// API
$api_key = ''; // Your API Key from https://developer.forecast.io/

// Check connection
if (mysqli_connect_errno($mysqli)) {
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

// Check allow_url_fopen
if(!ini_get('allow_url_fopen')) {
	die("Please enable ''allow_url_fopen=On'' from ''php.ini'' to be able to operate the location of the users.");
}

?>