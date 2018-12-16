<?php
	/* @All Weather 1.1 */
	
	session_start();
	header('Cache-control: private'); // IE 6 FIX

	if(isset($_GET['lang'])) {
		$lang = $_GET['lang'];
		// register the session and set the cookie
		$_SESSION['lang'] = $lang;
		setcookie('lang', $lang, time() + (3600 * 24 * 30));
		
	} else if(isset($_SESSION['lang'])) {
		$lang = $_SESSION['lang'];
	} else if(isset($_COOKIE['lang'])) {
		$lang = $_COOKIE['lang'];
	} else {
		setcookie('lang', 'en', time() + (3600 * 24 * 30));
		$lang = 'en';
	}
	
	switch ($lang) {
		case 'en':
		$lang_file = 'lang.en.php';
		break;

		case 'de':
		$lang_file = 'lang.de.php';
		break;

		case 'es':
		$lang_file = 'lang.es.php';
		break;
		
		case 'fr':
		$lang_file = 'lang.fr.php';
		break;
		
		case 'nl':
		$lang_file = 'lang.nl.php';
		break;
		
		case 'tet':
		$lang_file = 'lang.tet.php';
		break;
		
		case 'it':
		$lang_file = 'lang.it.php';
		break;
		
		case 'pl':
		$lang_file = 'lang.pl.php';
		break;
		
		case 'pt':
		$lang_file = 'lang.pt.php';
		break;
		
		case 'bs':
		$lang_file = 'lang.bs.php';
		break;

		default:
		$lang_file = 'lang.en.php';
	}

	include ('lang/'.$lang_file);
?>