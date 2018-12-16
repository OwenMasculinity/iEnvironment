<?php
	header("Content-Type: text/html; charset=windows-1252");
	setlocale(LC_ALL, 'it_IT.UTF-8', 'it_IT', 'it', 'ita', 'italian', 'it_IT.ISO_8859-1', 'it_IT.ISO_8859-16', 'Italian_Italy.1252');
	
	$lang = array();
	
	$lang['code'] = "it";

	$lang['PAGE_TITLE'] = 'All weather -';
	$lang['HEADER_LOGO_TITLE'] = 'Weather in the world of All-weather';
	
	$lang['HEADER_SEARCH_PLACEHOLDER'] = 'Digitare città..';
	$lang['HEADER_SEARCH_BUTTON'] = 'Trovare';
	$lang['PAGE_FOUND_TITLE'] = "Pagina non trovata";
	
	// Menu
	$lang['MENU_CURRENTLY'] = 'Attualmente';
	$lang['MENU_HOYRLY'] = '24 orario';
	$lang['MENU_WEEK'] = '7 giorni';
	$lang['MENU_MAKE_DEFAULT_CITY'] = 'Fai città predefinita';
	$lang['MENU_REMOVE_DEFAULT_CITY'] = 'Togliere dal difetto';
	
	$land['OPT_REMOVE_TITLE'] = 'Rimuovere';
	$land['OPT_REMOVE_DEFAULT'] = 'Togliere dal difetto';
	$land['OPT_MAKE_DEF_TITLE'] = 'Marca predefinita';
	$land['OPT_MAKE_DEF_CITY'] = 'Fai città predefinita';
	
	
	$lang['WEATHER_TOMORROW'] = 'Domani';
	$lang['WEATHER_DAT'] = 'Dopodomani';
	
	$lang['WEATHER_CURRENTLY_APP_TEMP'] = 'Sembra';
	$lang['WEATHER_IMG_TITLE_CLOUD_COVER'] = 'Nuvolosità';
	$lang['WEATHER_IMG_TITLE_HUMIDITY'] = 'Umidità';
	$lang['WEATHER_IMG_TITLE_RAIN_POS'] = 'Pioggia possibilità';
	
	// Chart
	$lang['CHART_TITLE'] = 'Temperatura e la Pioggia la possibilità per le prossime 12 ore';
	$lang['CHART_YXIS_PRIMARY_TITLE'] = 'Temperatura';
	$lang['CHART_YXIS_SECONDARY_TITLE'] = 'Rain possibility';
	
	// Wind title 
	$lang['WIND_TITLE_NORTH'] = 'Nord';
	$lang['WIND_TITLE_NORTH_NORTHEAST'] = 'nord-a nord-est';
	$lang['WIND_TITLE_NORTH_EAST'] = 'Nord-Est';
	$lang['WIND_TITLE_EAST_NORTHEAST'] = 'Est-nord-est';
	$lang['WIND_TITLE_EAST'] = 'Oriente';
	$lang['WIND_TITLE_EAST_SOUTHEAST'] = 'Est-sudest';
	$lang['WIND_TITLE_SOUTH_EAST'] = 'Sud-Est';
	$lang['WIND_TITLE_SOUTH_SOUTHEAST'] = 'Sud-sud-est';
	$lang['WIND_TITLE_SOUTH'] = 'Sud';
	$lang['WIND_TITLE_SOUTH_SOUTHWEST'] = 'Ovest sud-sud';
	$lang['WIND_TITLE_SOUTHWEST'] = 'Sud-ovest';
	$lang['WIND_TITLE_WEST_SOUTHWEST'] = 'Ovest-sud ovest';
	$lang['WIND_TITLE_WEST'] = 'Occidente';
	$lang['WIND_TITLE_WEST_NORTHWEST'] = 'Ovest - nord-ovest';
	$lang['WIND_TITLE_NORTH_WEST'] = 'Nord-Ovest';
	$lang['WIND_TITLE_NORTH_NORTHWEST'] = 'A nord-nord-ovest';	

?>