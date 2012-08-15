var console = require('console');
var fetcher = require('UrlFetcher');

testFetcher = function() {
	var resp = fetcher.send({
		async : false,
		type : 'POST',
		url : 'http://localhost:8580/t/echo',
		data : {
			msg : 'my test'
		}
	});
	return resp.contentText();
}

