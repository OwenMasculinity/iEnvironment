

//Array of weather types from the BBC.
	var weatherTypes = [
	["clear-day", Skycons.CLEAR_DAY],
	["clear-night", Skycons.CLEAR_NIGHT],
	["partly-cloudy-day", Skycons.PARTLY_CLOUDY_DAY],
	["partly-cloudy-night", Skycons.PARTLY_CLOUDY_NIGHT],
	["cloudy", Skycons.CLOUDY],
	["rain", Skycons.RAIN],
	["sleet", Skycons.SLEET],
	["thick cloud", Skycons.CLOUDY],
	["snow", Skycons.SNOW],
	["wind", Skycons.WIND],
	["fog", Skycons.FOG],	
	];
	  
	//Collect all the current
	var activeWeather = document.getElementsByClassName("icon-js");

	var skycons = new Skycons({"color": "white"});
	for (k = 0; k < activeWeather.length; k++) {
		
		//Locate only the forecast overview from the string (just before
		//the comma).
		currentWeather = activeWeather[k].textContent.toLowerCase();
		for  (j = 0; j < weatherTypes.length; j++) {
		//Match the currentWeather to an entry in the weather list and add
		//the correct skycons ID to the canvas.
		if (weatherTypes[j][0] === currentWeather) {
			//console.log("LOCKED WEATHER TYPE: " + weatherTypes[j][0] + " SKYCONS FLAG: " + weatherTypes[j][1]);
			//console.log('Targeting: icon' + k); 
			var skyconadder = weatherTypes[j][1];
			skycons.add(document.getElementById("icon" + k), skyconadder);
			 

		}
		
		}
	
	}
	skycons.play();
	console.log('**Javascript Running OK**');