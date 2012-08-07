var console = require('console');

testRhinoit = function() {
	console.info('testRhinoit');
	return "ok";
}

testModules = function() {
	var speed = require('./speed');
	var ret = speed.calc(120, 60);
	console.info('testModules: %s :)', ret);
	console.info(ret);
	return ret;
}
