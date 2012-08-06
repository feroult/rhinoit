(function() {
	
	var m = require('Mymath');
	var m1 = require('Mymath');
	
	if(m != m1) {
		throw 'should be the same ' + m + ', ' + m1;
	}
	
	exports.calc = function(a, b) {
		return m.divide(a, b);
	}
	
})();