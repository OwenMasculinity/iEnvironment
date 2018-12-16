<?php
/* @All Weather 1.1 */

function GetTime($i,$forecast,$showby = "hourly") {
	$unixtime = $forecast->$showby->data[$i]->time;
	echo "<span class='daydatetime'>" . iconv('Windows-1252', 'UTF-8//TRANSLIT',strftime("%a %d.%m.%y <br> %H:%M", $unixtime)) . "</span>";
}

function GetTimeWeek($i,$forecast,$showby = "daily") {
	$unixtime = $forecast->$showby->data[$i]->time;
	echo "<span class='daydatetime'>" . iconv('Windows-1252', 'UTF-8//TRANSLIT',strftime("%A <br> %d.%m.%y", $unixtime)) . "</span>";
}

function WindPos($i,$forecast,$showby = "hourly",$lang) {
	
	$windbearing = $forecast->$showby->data[$i]->windBearing;
	$windspeed = round($forecast->$showby->data[$i]->windSpeed, 1, PHP_ROUND_HALF_UP);
	if ($windbearing <= 22.49) {
		echo "<img src='img/icons/windImgN.png' width='32' height='32' title='". $lang['WIND_TITLE_NORTH'] ."' class='icon' /> <span class='point'>" . $windspeed;
	} else if (($windbearing >= 22.50) AND ($windbearing <= 44)) {
		echo "<img src='img/icons/windImgNNE.png' width='32' height='32' title='". $lang['WIND_TITLE_NORTH_NORTHEAST'] ."' class='icon' /> <span class='point'>" . $windspeed;
	} else if (($windbearing >= 45) AND ($windbearing <= 67.49)) {
		echo "<img src='img/icons/windImgNE.png' width='32' height='32' title='". $lang['WIND_TITLE_NORTH_EAST'] ."' class='icon' /> <span class='point'>" . $windspeed;
	} else if (($windbearing >= 67.50) AND ($windbearing <= 89)) {
		echo "<img src='img/icons/windImgENE.png' width='32' height='32' title='". $lang['WIND_TITLE_EAST_NORTHEAST'] ."' class='icon' /> <span class='point'>" . $windspeed;
	} else if (($windbearing >= 90) AND ($windbearing <= 112.49)) {
		echo "<img src='img/icons/windImgE.png' width='32' height='32' title='". $lang['WIND_TITLE_EAST'] ."' class='icon' /> <span class='point'>" . $windspeed;
	} else if (($windbearing >= 112.50) AND ($windbearing <= 134)) {
		echo "<img src='img/icons/windImgESE.png' width='32' height='32' title='". $lang['WIND_TITLE_EAST_SOUTHEAST'] ."' class='icon' /> <span class='point'>" . $windspeed;
	} else if (($windbearing >= 135) AND ($windbearing <= 157.49)){
		echo "<img src='img/icons/windImgSE.png' width='32' height='32' title='". $lang['WIND_TITLE_SOUTH_EAST'] . "' class='icon' /> <span class='point'>" . $windspeed;
	} else if (($windbearing >= 157.50) AND ($windbearing <= 179)){
		echo "<img src='img/icons/windImgSSE.png' width='32' height='32' title='". $lang['WIND_TITLE_SOUTH_SOUTHEAST'] . "' class='icon' /> <span class='point'>" . $windspeed;
	} else if (($windbearing >= 180) AND ($windbearing <= 202.49)){
		echo "<img src='img/icons/windImgS.png' width='32' height='32' title='". $lang['WIND_TITLE_SOUTH'] ."' class='icon' /> <span class='point'>" . $windspeed;
	} else if (($windbearing >= 202.50) AND ($windbearing <= 224)){
		echo "<img src='img/icons/windImgSSW.png' width='32' height='32' title='". $lang['WIND_TITLE_SOUTH_SOUTHWEST'] ."' class='icon' /> <span class='point'>" . $windspeed;
	} else if (($windbearing >= 225) AND ($windbearing <= 247.49)){
		echo "<img src='img/icons/windImgSW.png' width='32' height='32' title='". $lang['WIND_TITLE_SOUTHWEST'] ."' class='icon' /> <span class='point'>" . $windspeed;
	} else if (($windbearing >= 247.50) AND ($windbearing <= 269)){
		echo "<img src='img/icons/windImgWSW.png' width='32' height='32' title='". $lang['WIND_TITLE_WEST_SOUTHWEST'] ."' class='icon' /> <span class='point'>" . $windspeed;
	} else if (($windbearing >= 270) AND ($windbearing <= 292.49)){
		echo "<img src='img/icons/windImgW.png' width='32' height='32' title='". $lang['WIND_TITLE_WEST'] ."' class='icon' /> <span class='point'>" . $windspeed;
	} else if (($windbearing >= 292.50) AND ($windbearing <= 314)){
		echo "<img src='img/icons/windImgWNW.png' width='32' height='32' title='". $lang['WIND_TITLE_WEST_NORTHWEST'] ."' class='icon' /> <span class='point'>" . $windspeed;
	} else if (($windbearing >= 315) AND ($windbearing <= 337.49)){
		echo "<img src='img/icons/windImgNW.png' width='32' height='32' title='". $lang['WIND_TITLE_NORTH_WEST'] ."' class='icon' /> <span class='point'>" . $windspeed;
	} else if (($windbearing >= 337.50) AND ($windbearing <= 360)){
		echo "<img src='img/icons/windImgNNW.png' width='32' height='32' title='" . $lang['WIND_TITLE_NORTH'] ."' class='icon' /> <span class='point'>" . $windspeed;
	} else  {
		echo "ERROR";
	}	
}
?>