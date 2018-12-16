<?php
	/* @All Weather 1.1 */
	// Include config file
	include("include/config.php");
	// Include common file
	include("common.php");
	// Include functions
	include("include/functions.php");
	
	$rootdir = $_SERVER['HTTP_HOST'] . rtrim(dirname($_SERVER['PHP_SELF']), '/\\');
?>
<!DOCTYPE html>
<html lang="en">
<head>	
	<title><?php echo $lang['PAGE_TITLE'] . $lang['PAGE_FOUND_TITLE']; ?></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="shortcut icon" type="image/x-icon" href="img/favicon.ico" />
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<!-- Autocomplete CSS -->
	<link rel="stylesheet" type="text/css" href="css/jquery.autocomplete.css" />
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
	<!-- Autocomplete JS -->
	<script type="text/javascript" src="js/jquery.autocomplete.js"></script>
	<!-- Autocomplete JS -->
	<script>
	 $(document).ready(function(){
	  $("#search").autocomplete("autocomplete.php", {
			selectFirst: true,
			minChars: 3,
			delay: 400,
			cacheLength: 10
	  });
		$("#search").result(function(event, data, formatted) {
			var input = $(this);
			if(input.val()!=="")
			input.next(".searchbtn").click();
		});
	});
	</script>
</head>
<body>
	<!-- Header -->
	<div id="header">
		<div class="header-top">
			<a href="http://<?php echo $rootdir; ?>" class="logo" title="<?php echo $lang['HEADER_LOGO_TITLE']; ?>"><?php echo $lang['HEADER_LOGO_TITLE']; ?></a>
			<div id="languages">
				<a href="http://<?php echo $rootdir . "/" . basename($_SERVER['PHP_SELF']) . "?location=" . $_GET['location']; ?>&lang=en" title="English"><img src="http://icons.iconarchive.com/icons/famfamfam/flag/16/england-icon.png" /></a>
				<a href="http://<?php echo $rootdir . "/" . basename($_SERVER['PHP_SELF']) . "?location=" . $_GET['location']; ?>&lang=de" title="German"><img src="http://icons.iconarchive.com/icons/famfamfam/flag/16/de-icon.png" /></a>
				<a href="http://<?php echo $rootdir . "/" . basename($_SERVER['PHP_SELF']) . "?location=" . $_GET['location']; ?>&lang=es" title="Spanish"><img src="http://icons.iconarchive.com/icons/famfamfam/flag/16/es-icon.png" /></a>
				<a href="http://<?php echo $rootdir . "/" . basename($_SERVER['PHP_SELF']) . "?location=" . $_GET['location']; ?>&lang=fr" title="French"><img src="http://icons.iconarchive.com/icons/famfamfam/flag/16/fr-icon.png" /></a>
				<a href="http://<?php echo $rootdir . "/" . basename($_SERVER['PHP_SELF']) . "?location=" . $_GET['location']; ?>&lang=nl" title="Dutch"><img src="http://icons.iconarchive.com/icons/famfamfam/flag/16/nl-icon.png" /></a>
				<a href="http://<?php echo $rootdir . "/" . basename($_SERVER['PHP_SELF']) . "?location=" . $_GET['location']; ?>&lang=tet" title="Indonesian"><img src="http://icons.iconarchive.com/icons/famfamfam/flag/16/id-icon.png" /></a>
				<a href="http://<?php echo $rootdir . "/" . basename($_SERVER['PHP_SELF']) . "?location=" . $_GET['location']; ?>&lang=it" title="Italian"><img src="http://icons.iconarchive.com/icons/famfamfam/flag/16/it-icon.png" /></a>
				<a href="http://<?php echo $rootdir . "/" . basename($_SERVER['PHP_SELF']) . "?location=" . $_GET['location']; ?>&lang=pl" title="Polish"><img src="http://icons.iconarchive.com/icons/famfamfam/flag/16/pl-icon.png" /></a>
				<a href="http://<?php echo $rootdir . "/" . basename($_SERVER['PHP_SELF']) . "?location=" . $_GET['location']; ?>&lang=pt" title="Portuguese"><img src="http://icons.iconarchive.com/icons/famfamfam/flag/16/pt-icon.png" /></a>
				<a href="http://<?php echo $rootdir . "/" . basename($_SERVER['PHP_SELF']) . "?location=" . $_GET['location']; ?>&lang=bs" title="Bosnian"><img src="http://icons.iconarchive.com/icons/famfamfam/flag/16/ba-icon.png" /></a>
			</div>
		</div>
		<div class="clear"></div>
		<div class="header-main">
			<form action="index.php" method="GET">
				<div id="panel">
					<input name="location" class="search" id="search" placeholder="<?php echo $lang['HEADER_SEARCH_PLACEHOLDER']; ?>" required />
					<input type="submit" name="submit" class="searchbtn" value="<?php echo $lang['HEADER_SEARCH_BUTTON']; ?>" />
				</div>				
			</form>	
		</div>
	</div>
	<!-- End of Header -->

	<!-- Content -->
	<div id="content">
		<!-- Menu -->
		<ul class="tab-menu">
			<li><a href="index.php" class="active"><?php echo $lang['MENU_CURRENTLY']; ?></a></li>
			<li><a href="hourly.php"><?php echo $lang['MENU_HOYRLY']; ?></a></li>
			<li><a href="week.php"><?php echo $lang['MENU_WEEK']; ?></a></li>
		</ul>
		<!-- End of Menu -->
		<div class="clear"></div>
		
		<!-- Page not found -->
		<center><img src="img/404.png" width="770" height="482" alt="<?php echo $lang['PAGE_FOUND_TITLE']; ?>" title="<?php echo $lang['PAGE_FOUND_TITLE']; ?>" /></center>
		
	</div>
	<!-- End Content -->
	
	<div class="paper-line"></div>
	
</body>
</html>