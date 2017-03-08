package hearsilent.amazingavatar.base;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import hearsilent.amazingavatar.BuildConfig;

public class BaseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		if (BuildConfig.DEBUG) {
			enableStrictMode();
		}

		initImageLoader(this);
	}

	private static void initImageLoader(Context context) {
		ImageLoaderConfiguration config =
				new ImageLoaderConfiguration.Builder(context).threadPoolSize(5).build();

		ImageLoader.getInstance().init(config);
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