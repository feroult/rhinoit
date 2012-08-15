var console = require('console');
var speed = require('./speed');

testRhino = function() {
	console.info('testRhino');
	return "ok";
}

testModules = function() {
	var ret = speed.calc(120, 60);
	console.info('testModules: %s :)', ret);
	console.info(ret);
	return ret;
}
