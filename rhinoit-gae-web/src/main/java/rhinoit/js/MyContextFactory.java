package rhinoit.js;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;

public class MyContextFactory extends ContextFactory {

	@Override
	protected boolean hasFeature(Context cx, int featureIndex) {
		// if (featureIndex == Context.FEATURE_DYNAMIC_SCOPE) {
		// return true;
		// }
		return super.hasFeature(cx, featureIndex);
	}

	public static void init() {
		ContextFactory.initGlobal(new MyContextFactory());
	}

	@Override
	protected void onContextCreated(Context cx) {
		cx.setGeneratingDebug(true);
		cx.setOptimizationLevel(-1);
		super.onContextCreated(cx);
	}

}
