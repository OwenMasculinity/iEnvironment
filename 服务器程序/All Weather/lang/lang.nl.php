<?php
	header("Content-Type: text/html; charset=windows-1252");
	setlocale(LC_ALL, 'da_DK.UTF-8', 'da_DK', 'da', 'dan', 'danish', 'da_DK.ISO_8859-1', 'Danish_Denmark.1252');

	$lang = array();
	
	$lang['code'] = "nl";

	$lang['PAGE_TITLE'] = 'al slags vejr -';
	$lang['HEADER_LOGO_TITLE'] = 'Vejret i verden af ??al slags vejr';
	
	$lang['HEADER_SEARCH_PLACEHOLDER'] = 'skriv by.';
	$lang['HEADER_SEARCH_BUTTON'] = 'Find';
	$lang['PAGE_FOUND_TITLE'] = "Siden blev ikke fundet";
	
	// Menu
	$lang['MENU_CURRENTLY'] = 'I ojeblikket';
	$lang['MENU_HOYRLY'] = '24 timer';
	$lang['MENU_WEEK'] = '7 dage';
	$lang['MENU_MAKE_DEFAULT_CITY'] = 'Gor til standard city';
	$lang['MENU_REMOVE_DEFAULT_CITY'] = 'Fjern fra standard';
	
	$land['OPT_REMOVE_TITLE'] = 'Fjern';
	$land['OPT_REMOVE_DEFAULT'] = 'Fjern fra standard';
	$land['OPT_MAKE_DEF_TITLE'] = 'Gor til standard';
	$land['OPT_MAKE_DEF_CITY'] = 'Gor til standard city';
	
	
	$lang['WEATHER_TOMORROW'] = 'I morgen';
	$lang['WEATHER_DAT'] = 'I overmorgen';
	
	$lang['WEATHER_CURRENTLY_APP_TEMP'] = 'Foles';
	$lang['WEATHER_IMG_TITLE_CLOUD_COVER'] = 'skydaekke';
	$lang['WEATHER_IMG_TITLE_HUMIDITY'] = 'Fugtighed';
	$lang['WEATHER_IMG_TITLE_RAIN_POS'] = 'Regn mulighed';
	
	// Chart
	$lang['CHART_TITLE'] = 'Temperatur og regn mulighed for n?ste 12 timer';
	$lang['CHART_YXIS_PRIMARY_TITLE'] = 'Temperatur';
	$lang['CHART_YXIS_SECONDARY_TITLE'] = 'Regn mulighed';
	
	// Wind title 
	$lang['WIND_TITLE_NORTH'] = 'Nord';
	$lang['WIND_TITLE_NORTH_NORTHEAST'] = 'Nord-nordost';
	$lang['WIND_TITLE_NORTH_EAST'] = 'Nordostlige';
	$lang['WIND_TITLE_EAST_NORTHEAST'] = 'Ost-nordost';
	$lang['WIND_TITLE_EAST'] = 'Ost';
	$lang['WIND_TITLE_EAST_SOUTHEAST'] = 'Ost-sydost';
	$lang['WIND_TITLE_SOUTH_EAST'] = 'Syd-Ost';
	$lang['WIND_TITLE_SOUTH_SOUTHEAST'] = 'Syd-sydost';
	$lang['WIND_TITLE_SOUTH'] = 'Syd';
	$lang['WIND_TITLE_SOUTH_SOUTHWEST'] = 'Syd-sydvest';
	$lang['WIND_TITLE_SOUTHWEST'] = 'Sydvest';
	$lang['WIND_TITLE_WEST_SOUTHWEST'] = 'Vest-sydvest';
	$lang['WIND_TITLE_WEST'] = 'Vest';
	$lang['WIND_TITLE_WEST_NORTHWEST'] = 'Vest-nordvest';
	$lang['WIND_TITLE_NORTH_WEST'] = 'nordvestlige';
	$lang['WIND_TITLE_NORTH'] = 'Nord-nordvest';	

?>