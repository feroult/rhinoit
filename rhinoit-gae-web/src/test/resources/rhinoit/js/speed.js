(function() {
	
	var m = require('math');
	var m1 = require('math');
	
	if(m != m1) {
		throw 'should be the same ' + m + ', ' + m1;
	}
	
	exports.calc = function(a, b) {
		return m.divide(a, b);
	}
	
})();