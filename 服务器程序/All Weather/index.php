<?php
	/* @All Weather 1.1 */
	// Include config file
	include("include/config.php");
	// Include common file
	include("common.php");
	// Include functions
	include("include/functions.php");
	// Include check actions
	include("include/checkaction.php");
?>
<!DOCTYPE html>
<html lang="en">
<head>	
	<title><?php echo $lang['PAGE_TITLE'] . $cityname; ?></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="shortcut icon" type="image/x-icon" href="img/favicon.ico" />
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<!-- Autocomplete CSS -->
	<link rel="stylesheet" type="text/css" href="css/jquery.autocomplete.css" />
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
	<!-- Autocomplete JS -->
	<script type="text/javascript" src="js/jquery.autocomplete.js"></script>
	<!-- Charts JS -->
	<script src="http://code.highcharts.com/highcharts.js"></script>
	<script src="js/exporting.js"></script>
	<script type="text/javascript">
	$(function () {
        $('#container').highcharts({
			credits: {
				enabled: false
			},
            chart: {
				plotBackgroundImage: 'img/chartbg.png' // Background chart
            },
            title: {
                text: '<?php echo $lang['CHART_TITLE']; ?>'
            },
            subtitle: {
                text: '<?php echo $cityname; ?>',
            },
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
            xAxis: [{
                categories: <?php echo json_encode($chartTime); // Get time and set to graph ?>
            }],
            yAxis: [{ // Primary yAxis
                labels: {
					useHTML:true,
                    format: '{value} <?php echo $tempsign; ?>',
                    style: {
                        color: '#EC5454'
                    }
                },
                title: {
                    text: '<?php echo $lang['CHART_YXIS_PRIMARY_TITLE']; ?>',
                    style: {
                        color: '#EC5454'
                    }
                }
            }, { // Secondary yAxis
                title: {
                    text: '<?php echo $lang['CHART_YXIS_SECONDARY_TITLE']; ?>',
                    style: {
                        color: '#8ad6d7'
                    }
                },
                labels: {
					useHTML:true,
                    format: '{value} %',
                    style: {
                        color: '#8ad6d7'
                    }
                },
                opposite: true
            }],
            tooltip: {
				useHTML:true,
                shared: false
            },
            legend: {
				floating: true,
				layout: 'vertical',
                align: 'right',
                verticalAlign: 'top',
                borderWidth: 0,
				align: 'right'
            },
            series: [{
                name: '<?php echo $lang['CHART_YXIS_SECONDARY_TITLE']; ?>',
                type: 'column',
                yAxis: 1,
                data: <?php echo json_encode($chartRainPos); // Get rain possibility and set to graph ?>,
				color: '#8ad6d7',
                tooltip: {
                    valueSuffix: ' %'
                }
    
            }, {
                name: '<?php echo $lang['CHART_YXIS_PRIMARY_TITLE']; ?>',
                type: 'spline',
                data: <?php echo json_encode($chartTemp); // Get temperature and set to graph ?>,
				color: '#EC5454',
				dataLabels: {
                    enabled: true,
					useHTML: true,
					format: '{y} <?php echo $tempsign; ?>',
                },
                tooltip: {
					useHTML: true,
                    valueSuffix: ' <?php echo $tempsign; ?>'
                }
            }]
        });
    });
	</script>
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
	<!-- Cookie -->
	<script type="text/javascript">
		function SetCookie(c_name,value,expiredays) {
			var exdate=new Date()
			exdate.setDate(exdate.getDate()+expiredays)
			document.cookie=c_name+ "=" +escape(value)+
			((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
			window.location.reload()
		}
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
			<form action="hourly.php" method="GET">
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
			<li><a href="index.php?location=<?php echo $_GET['location']; ?>" class="active"><?php echo $lang['MENU_CURRENTLY']; ?></a></li>
			<li><a href="hourly.php?location=<?php echo $_GET['location']; ?>"><?php echo $lang['MENU_HOYRLY']; ?></a></li>
			<li><a href="week.php?location=<?php echo $_GET['location']; ?>"><?php echo $lang['MENU_WEEK']; ?></a></li>
		</ul>
		<!-- End of Menu -->
		<div class="clear"></div>
		
		<?php
			echo "<h3 class='cityname'>" . $cityname . "</h3>"; // City and country name
			echo "<ul class='opt-menu'>";
				echo "<li class='opt'><a href'#'><img src='img/options.png' width='16' height='16' /></a>";
					echo "<ul>";
						echo "<li>" . $showtempformat . "</li>"; // Show temperatur format
						echo "<li>" . $defaultcity . "</li>";
					echo "</ul>";
				echo "</li>";
			echo "</ul>";
		?>
		
		<!-- Weather information -->
		<div class="info">
			<ul>
				<li class="info-tab" style="display: list-item;">
					<ul class="info-meta">
						<!-- Currently weather -->
						<li class="first">
							<?php GetTime($i=0,$forecast); ?>
							<div class="temperature">
								<div class="fl">
									<div class="sign-wrapper">
										<i class="icon-js"><?php echo $forecast->currently->icon; // Get icon name ?></i>
										<canvas id="icon0" width="64" height="64"></canvas>
									</div>
								</div>
								<div class="fr">
									<div class="gradus">
										<span class="celsius"><?php echo $tempsign; ?></span>
										<span class="number"><?php echo ceil($forecast->currently->temperature); // Show temperature ?></span>
									</div>
								</div>
							</div>
							<div class="discription"><?php echo $forecast->currently->summary; // Show summary weather ?></div>
							<div class="windchill"><?php echo $lang['WEATHER_CURRENTLY_APP_TEMP']; ?> <span class="point"><?php echo ceil($forecast->currently->apparentTemperature);  // Show temperature which will be felt ?></span><?php echo $tempsign; ?></div>
							<div class="other">
								<span class="humidity">
									<img src="img/icons/humidity.png" width="32" height="32" title="<?php echo $lang['WEATHER_IMG_TITLE_HUMIDITY']; ?>" class="icon" />
									<span class="point"><?php echo $forecast->currently->humidity * 100; // Show humidity in percent ?></span>%
								</span>								
								<span class="cloud">
									<img src="img/icons/cloudy.png" width="32" height="32" title="<?php echo $lang['WEATHER_IMG_TITLE_CLOUD_COVER']; ?>" class="icon" />
									<span class="point"><?php echo $forecast->currently->cloudCover * 100; // Show cloud cover in percent ?></span>%
								</span>
								<span class="wind">
									<?php WindPos($i=0,$forecast,$showby = "hourly",$lang); echo $windformat . "</span>"; // Show wind position ?>
								</span>
								<span class="rainpos">
									<img src="img/icons/posrain.png" width="32" height="32" title="<?php echo $lang['WEATHER_IMG_TITLE_RAIN_POS']; ?>" class="icon" />
									<span class="point"><?php echo $forecast->currently->precipProbability * 100 // Show rain possibility in percent ?>%</span>
								</span>

							</div>
						</li>
						<!-- End Currently weather -->
						
						<!-- Other day -->
						<li class="other">
							<div class="meta-wrapper">
								<?php echo "<span class='daydatetime'>" . $lang['WEATHER_TOMORROW'] . "<br>" . iconv('Windows-1252', 'UTF-8//TRANSLIT',strftime("%a %d.%m.%y", strtotime("+ 1 days"))) . "</span>"; ?>
								<span class="sign-wrapper popup">
									<i class="more-info">i</i>
									<i class="icon-js"><?php echo $forecast->hourly->data[1]->icon; // Get icon name ?></i>
									<canvas id="icon1" width="64" height="64"></canvas>
									<aside>
										<?php 
										// Popup
										echo "<span class='point popup-windpos'>";
											WindPos($i=1,$forecast,$showby = "daily",$lang); echo $windformat . "</span>"; // Show wind position 
										echo "</span>";
										echo "<span class='point'>";
											echo "<img src='img/icons/posrain.png' width='32' height='32' />";
											echo $forecast->hourly->data[1]->precipProbability * 100; // Show rain possibility in percent
										echo "%</span>";
										?>
									</aside>
								</span>
								<span class="title"><?php echo $forecast->daily->data[1]->summary; // Show summary weather ?></span>
								<span class="temperature">
									<span class="min">
										<?php echo ceil($forecast->daily->data[1]->temperatureMin); // Show minimum temperature ?>&#176;</span> / 
									<span class="max">
										<?php echo ceil($forecast->daily->data[1]->temperatureMax); // Show maximum temperature ?>&#176;
									</span>
								</span>
								<span class="humidity-cloud">
									<img src="img/icons/humidity.png" width="24" height="24" title="<?php echo $lang['WEATHER_IMG_TITLE_HUMIDITY']; ?>" class="icon" />
									<span class="point"><?php echo $forecast->daily->data[1]->humidity * 100; // Show humidity in percent ?></span>%&nbsp;&nbsp;&nbsp;
									<img src="img/icons/cloudy.png" width="24" height="24" title="<?php echo $lang['WEATHER_IMG_TITLE_CLOUD_COVER']; ?>" class="icon" />
									<span class="point"><?php echo $forecast->daily->data[1]->cloudCover * 100; // Show cloud cover in percent ?></span>%
								</span>
							</div>
						</li>

						<li class="other">
							<div class="meta-wrapper">
								<?php echo "<span class='daydatetime'>" . $lang['WEATHER_DAT'] . "<br>" . iconv('Windows-1252', 'UTF-8//TRANSLIT',strftime("%a %d.%m.%y", strtotime("+ 2 days"))) . "</span>"; // Show day after tomorrow ?>
								<span class="sign-wrapper popup">
									<i class="more-info">i</i>								
									<i class="icon-js"><?php echo $forecast->hourly->data[2]->icon; // Get icon name ?></i>
									<canvas id="icon2" width="64" height="64"></canvas>
									<aside>
										<?php 
										// Popup
										echo "<span class='point popup-windpos'>";
											Windpos($i=2,$forecast,$showby = "daily",$lang); echo $windformat . "</span>"; // Show wind position
										echo "</span>";
										echo "<span class='point'>";
											echo "<img src='img/icons/posrain.png' width='32' height='32' />";
											echo $forecast->hourly->data[2]->precipProbability * 100; // Show rain possibility in percent
										echo "%</span>";
										?>
									</aside>
								</span>
								<span class="title"><?php echo $forecast->daily->data[2]->summary;  // Show summary weather ?></span>
								<span class="temperature">
									<span class="min">
										<?php echo ceil($forecast->daily->data[2]->temperatureMin); // Show minimum temperature ?>&#176;</span> / 
									<span class="max">
										<?php echo ceil($forecast->daily->data[2]->temperatureMax); // Show maximum temperatur ?>&#176;
									</span>
								</span>
								<span class="humidity-cloud">
									<img src="img/icons/humidity.png" width="24" height="24" title="<?php echo $lang['WEATHER_IMG_TITLE_HUMIDITY']; ?>" class="icon" />
									<span class="point"><?php echo $forecast->daily->data[2]->humidity * 100; // Show humidity in percent ?></span>%&nbsp;&nbsp;&nbsp;
									<img src="img/icons/cloudy.png" width="24" height="24" title="<?php echo $lang['WEATHER_IMG_TITLE_CLOUD_COVER']; ?>" class="icon" />
									<span class="point"><?php echo $forecast->daily->data[2]->cloudCover * 100; // Show cloud cover in percent ?></span>%
								</span>
							</div>
						</li>

						<li class="other">
							<div class="meta-wrapper">
								<?php echo "<span class='daydatetime'>" . iconv('Windows-1252', 'UTF-8//TRANSLIT',strftime("%A <br> %d.%m.%y", strtotime("+ 3 days"))) . "</span>"; // Show day after tomorrow ?>
								<span class="sign-wrapper popup">
									<i class="more-info">i</i>								
									<i class="icon-js"><?php echo $forecast->hourly->data[3]->icon; // Get icon name ?></i>
									<canvas id="icon3" width="64" height="64"></canvas>
									<aside>
										<?php 
										// Popup
										echo "<span class='point popup-windpos'>";
											WindPos($i=3,$forecast,$showby = "daily",$lang); echo $windformat . "</span>"; // Show wind position
										echo "</span>";
										echo "<span class='point'>";
											echo "<img src='img/icons/posrain.png' width='32' height='32' />";
											echo $forecast->hourly->data[3]->precipProbability * 100; // Show rain possibility in percent
										echo "%</span>";
										?>
									</aside>
								</span>
								<span class="title"><?php echo $forecast->daily->data[3]->summary; // Show summary weather ?></span>
								<span class="temperature">
									<span class="min">
										<?php echo ceil($forecast->daily->data[3]->temperatureMin); // Show minimum temperatur ?>&#176;</span> / 
									<span class="max">
										<?php echo ceil($forecast->daily->data[3]->temperatureMax); // Show maximum temperatur ?>&#176;
									</span>
								</span>
								<span class="humidity-cloud">
									<img src="img/icons/humidity.png" width="24" height="24" title="<?php echo $lang['WEATHER_IMG_TITLE_HUMIDITY']; ?>" class="icon" />
									<span class="point"><?php echo $forecast->daily->data[3]->humidity * 100; // Show humidity in percent ?></span>%&nbsp;&nbsp;&nbsp;
									<img src="img/icons/cloudy.png" width="24" height="24" title="<?php echo $lang['WEATHER_IMG_TITLE_CLOUD_COVER']; ?>" class="icon" />
									<span class="point"><?php echo $forecast->daily->data[3]->cloudCover * 100; // Show cloud cover in percent ?></span>%
								</span>
							</div>
						</li>
						<!-- End of Other day -->
					</ul>
				</li>
			</ul>			
		</div>
		<!-- End of weather information -->
		<div class="clear"></div>
	
	<!-- Next 12 hours charts -->
	<div id="container" style="min-width: 310px; height: 400px; margin:60px auto 0 auto"></div>
	</div>
	<!-- End Content -->
	
	<div class="paper-line"></div>
	
	<!-- Add animated icons -->
	<script src="js/skycons/skycons.js"></script>
	<script src="js/addSkycons.js"></script>
</body>
</html>