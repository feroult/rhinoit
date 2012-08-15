package voidrepo.js;

import org.mozilla.javascript.ScriptableObject;

public class MymathJSModule extends ScriptableObject {

	private static final long serialVersionUID = -5258907437781600679L;

	@Override
	public String getClassName() {
		return "Mymath";
	}

	public double jsFunction_divide(double a, double b) {
		return a / b;
	}

}
