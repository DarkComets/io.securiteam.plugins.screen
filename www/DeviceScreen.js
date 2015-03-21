(function(window, undefined)
{
	'use strict';

	var DeviceScreen =
	{
		getWidth: function(successFunction, errorFunction)
		{
			cordova.exec(successFunction, errorFunction, 'DeviceScreen', 'getWidth', []);
		},

		getHeight: function(successFunction, errorFunction)
		{
			cordova.exec(successFunction, errorFunction, 'DeviceScreen', 'getHeight', []);
		},
		
		getScale: function(successFunction, errorFunction)
		{
			cordova.exec(successFunction, errorFunction, 'DeviceScreen', 'getScale', []);
		}
	};
	
	cordova.addConstructor(function() 
	{
		window.DeviceScreen = DeviceScreen;
		return window.DeviceScreen;
	});
	
})(window);
