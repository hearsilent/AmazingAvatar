package hearsilent.amazingavatar.base;

import android.app.Application;
import android.os.StrictMode;

import hearsilent.amazingavatar.BuildConfig;

public class BaseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		if (BuildConfig.DEBUG) {
			enableStrictMode();
		}
	}

	private void enableStrictMode() {
		StrictMode.setThreadPolicy(
				new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites()
						.detectNetwork().penaltyLog().build());
		StrictMode.setVmPolicy(
				new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog()
						.penaltyDeath().build());
	}

	public BaseApplication() {
		super();
	}
}