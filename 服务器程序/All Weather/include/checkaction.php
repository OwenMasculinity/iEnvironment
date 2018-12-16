<?php
	/* @All Weather 1.1 */

	ob_start();
	
	$rootdir = $_SERVER['HTTP_HOST'] . rtrim(dirname($_SERVER['PHP_SELF']), '/\\');
	
	if(isset($_GET['submit'])) { // Check if form is submit 
		$getloc = $_GET['location']; // Get location from databese (city, country)
		$locexp = explode(", ", $getloc); // Explode city and country name
		$sql = "SELECT * FROM location WHERE '".$locexp['0']."' = city AND '".$locexp['1']."' = country"; // Check that sent city and country exist in databese
		$result = mysqli_query($mysqli,$sql) or die(mysqli_error($mysqli));
		$row = mysqli_fetch_array($result);
		if(!$locexp['0'] == $row['city']) { // If not exist redirect to another page
			// Redirect to 404 page
			header('Location:  http://'.$rootdir.'/404.php'); // If not exist
		} else { // If exist get information			
				$lat = $row['latitude'];  // Latitude
				$lon = $row['longitude']; // Longitude
				$cityname = $row['city'] . ", " . $row['country']; // Show city name and country name
				header('Location:  http://'.$rootdir.'/?location='.$row['id'].''); // Redirect to selected city			
		}
	} else if(empty($_GET['location']) OR !isset($_GET['location'])) { // If location is empty or no set location find my location via ip
		// Check if coockie (default city) is set
		if(isset($_COOKIE['defaultcity'])) {
			header('Location:  http://'.$rootdir.'/?location='.$_COOKIE['defaultcity'].''); // Redirect to selected city
		} else { // If not set find my location via IP
			$ip = $_SERVER['REMOTE_ADDR']; // Get my ip to get my location
			$details = json_decode(@file_get_contents("http://ip-api.com/json")); // Get data with json for my ip
			$sql = "SELECT * FROM location WHERE '".$details->city."' = city AND '".$details->country."' = country"; // Check that sent city and state exist in databese
			$result = mysqli_query($mysqli,$sql) or die(mysqli_error($mysqli));
			$row = mysqli_fetch_array($result);
			if(empty($details->city)) { // If is empty redirect to default city
				// Coordinates of default city
				header('Location:  http://'.$rootdir.'/?location=609'); // Default city (Melbourne, Australia)
			} else if ($details->city == $row['city']) { // If exist get information
				// Coordinates of the selected city			
				$lat = $row['latitude'];  // Latitude
				$lon = $row['longitude']; // Longitude
				$cityname = $row['city'] . ", " . $row['country']; // Show city name and country name
				header('Location:  http://'.$rootdir.'/?location='.$row['id'].''); // Redirect to selected city
			} else { // If not exist redirect to default city
				// Coordinates of default city
				header('Location:  http://'.$rootdir.'/?location=609'); // Default city (Melbourne, Australia)
			}
		}
	} else { // If form is not submit
		$getloc = $_GET['location']; // Get location from url
		$sql = "SELECT * FROM location WHERE ".$getloc." = id"; // Check in databese that current location is exist
		$result = mysqli_query($mysqli,$sql) or die(mysqli_error($mysqli));
		$row = mysqli_fetch_array($result);
		if(!$getloc == $row['id']) { // If not exist redirect to another page
			// Redirect to 404 page
			header('Location:  http://'.$rootdir.'/404.php');
		} else { // If exist get information
			$sql = "SELECT * FROM location WHERE ".$getloc." = id";
			$result = mysqli_query($mysqli,$sql) or die(mysqli_error($mysqli));
			$row = mysqli_fetch_array($result);				
			if($result) {	
				$lat = $row['latitude']; // Latitude
				$lon = $row['longitude']; // Longitude
				$cityname = $row['city'] . ", " . $row['country']; // Show city name and country name
			}
		}
	}
	
	// Default temperature format (Celsius)
	if(isset($_COOKIE['tempformat'])) { // Check if coockie is set
		$tempformat = $_COOKIE['tempformat']; // Get cookie
	} else { // If coockie is not set (set default to "si")
		setcookie("tempformat", "si", time()+60*60*24*30); // Default is Celsius, for Fahrenheit replace "si" with "us"
		$tempformat = "si"; // Default is Celsius, for Fahrenheit replace "si" with "us"
		header('Location:  http://'.$rootdir.'/index.php');	
	}
	
	// Cookie for default city
	$defaultcity = "";
	if(isset($_COOKIE['defaultcity']) AND $_COOKIE['defaultcity'] == $_GET['location']) { // If cookie is set and you are in currently page show button for remove
		$defaultcity = "<a href='javascript:void(0);' class='tempformat' title='".$land['OPT_REMOVE_TITLE']."' alt='".$land['OPT_REMOVE_TITLE']."' onClick=\"SetCookie('defaultcity', '', -1); \">" . $land['OPT_REMOVE_DEFAULT'] . "</a>";
	} else { // Show button for make default city
		$defaultcity = "<a href='javascript:void(0);' class='tempformat' title='".$land['OPT_MAKE_DEF_TITLE']."' alt='".$land['OPT_MAKE_DEF_TITLE']."' onClick=\"SetCookie('defaultcity'," . $_GET['location'] . ",'7'); \">" . $land['OPT_MAKE_DEF_CITY'] . "</a>";
	}
	
	// Cookie for format temperature
	$tempsign = "";
	if($_COOKIE['tempformat'] == "si") { // If cookie is "si"(celsius) show button to Fahrenheit
		$showtempformat = "<a href='javascript:void(0);' class='tempformat' onClick=\"SetCookie('tempformat','us','7'); \">&#176;F</a>";
		$tempsign = "&#176; C";
		$windformat = "m/s";
	} else { // If cookie is not "si"(celsius) show button to Celsius
		$showtempformat = "<a href='javascript:void(0);' class='tempformat' onClick=\"SetCookie('tempformat','si','7'); \">&#176;C</a>";
		$tempsign = "&#176; F";
		$windformat = "m/h";
	}

	// Check if api key is placed
	if($api_key == "") {
		die("Please make your registration <a href='https://developer.forecast.io/' target='_blank'>here</a> to get the API key and place it in ''include/config.php''.<br>If you have any problems, please email me at dian.koychev@gmail.com");
	}
	
	$url = "https://api.forecast.io/forecast/".$api_key."/".$lat.",".$lon."?lang=".$lang['code']."&units=".$tempformat."&extend=currently,minutely,hourly,alerts,flags,daily"; // Get information for weather
	$forecast = json_decode(@file_get_contents($url)); // Returns the value encoded in json
	if(!$forecast) { // If something is wrong
		die("There is a problem. Please check your API key or the settings of the server.");
	}
	date_default_timezone_set($forecast->timezone); // Get and set timezone
	
	// Get weather information for next 12 and added to chart
	for($i=0;$i<12;$i++) { // Default is 12 hours but it could be 24
		$chartTemp[] = ceil($forecast->hourly->data[$i]->temperature); // Temperatur
		$unixtime = $forecast->hourly->data[$i]->time;
		$chartTime[] = iconv('Windows-1252', 'UTF-8//TRANSLIT',strftime("%a <br> %H:%M", $unixtime)); // Datetime
		$chartRainPos[] = $forecast->hourly->data[$i]->precipProbability * 100; // Rain possibility in percent
	}
	ob_end_flush();
?>