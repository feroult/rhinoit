
testRhinoit = function() {
	return "ok";
}

testModules = function() {
	var speed = require('./speed');
	var ret = speed.calc(120, 60);
	return ret;
}
