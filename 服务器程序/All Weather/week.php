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
	<!-- Slider JS & CSS -->
	<link rel="stylesheet" type="text/css" href="css/slick.css"/>	
	<script type="text/javascript" src="js/slick.js"></script>
	<script type="text/javascript" src="js/scripts.js"></script>
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
			<li><a href="index.php?location=<?php echo $_GET['location']; ?>"><?php echo $lang['MENU_CURRENTLY']; ?></a></li>
			<li><a href="hourly.php?location=<?php echo $_GET['location']; ?>"><?php echo $lang['MENU_HOYRLY']; ?></a></li>
			<li><a href="week.php?location=<?php echo $_GET['location']; ?>" class="active"><?php echo $lang['MENU_WEEK']; ?></a></li>
		</ul>
		<!-- End of Menu -->
		
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
		
		<div class="clear"></div>
		
		<!-- Weather information for next 7 days -->
		<div class="slider responsive">
		<?php 
		for($i=0;$i<7;$i++) {		
			echo "<div>";
				echo "<div class='info'>";
					echo "<ul>";
						echo "<li class='info-tab' style='display: list-item;'>";
							echo "<ul class='info-meta'>";						
								echo "<li class='other'>";
									echo "<div class='meta-wrapper'>";
										GetTimeWeek($i,$forecast); // Get time for next 7 days and visualize
										echo "<span class='sign-wrapper'>";
											echo "<i class='icon-js'>" . $forecast->hourly->data[$i]->icon ."</i>"; // Get icon name
											echo "<canvas id='icon".$i."' width='64' height='64'></canvas>"; // Show icon
										echo "</span>";
										echo "<span class='title title-week'>". $forecast->daily->data[$i]->summary . "</span>"; // Show summary weather
										echo "<span class='temperature'>";								
											echo "<span class='min'>";
												echo ceil($forecast->daily->data[$i]->temperatureMin) . "&#176;</span> / "; // Show minimum temperature								
											echo "<span class='max'>";
												echo ceil($forecast->daily->data[$i]->temperatureMax) . "&#176;</span>"; // Show maximum temperature
											echo "</span>";
										echo "</span>";
										echo "<span class='humidity-cloud'>";
											echo "<img src='img/icons/humidity.png' width='32' height='32' title='" . $lang['WEATHER_IMG_TITLE_HUMIDITY'] . "' class='icon' />";
											echo "<span class='point'>" . $forecast->daily->data[$i]->humidity * 100 ."</span>%&nbsp;&nbsp;&nbsp"; // Show humidity in percent
											echo "<img src='img/icons/cloudy.png' width='32' height='32' title='" . $lang['WEATHER_IMG_TITLE_CLOUD_COVER'] . "' class='icon' />";
											echo "<span class='point'> ". $forecast->daily->data[$i]->cloudCover * 100 ."</span>%"; // Show cloud cover in percent
										echo "</span>";
										echo "<span class='wind'>";
												WindPos($i,$forecast,$showby = "hourly",$lang); echo $windformat . "</span>"; // Show wind position
											echo "</span>";
										echo "<span class='rainpos'>";
											echo "<img src='img/icons/posrain.png' width='32' height='32' title='" . $lang['WEATHER_IMG_TITLE_RAIN_POS'] ."' />";
											echo "<span class='point'>". $forecast->hourly->data[$i]->precipProbability * 100 . " %</span>"; // Show rain possibility in percent
										echo "</span>";
								echo "</div>";
							echo "</li>";						
						echo "</ul>";
					echo "</li>";
				echo "</ul>";
			echo "</div>";
		echo "</div>";
		}
		?>
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