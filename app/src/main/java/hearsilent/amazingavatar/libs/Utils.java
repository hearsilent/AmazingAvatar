package hearsilent.amazingavatar.libs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.util.DisplayMetrics;

public class Utils {

	public static DisplayMetrics getDisplayMetrics(Context context) {
		Resources resources = context.getResources();
		return resources.getDisplayMetrics();
	}

	public static float convertDpToPixel(float dp, Context context) {
		return dp * (getDisplayMetrics(context).densityDpi / 160f);
	}

	public static int convertDpToPixelSize(float dp, Context context) {
		float pixels = convertDpToPixel(dp, context);
		final int res = (int) (pixels + 0.5f);
		if (res != 0) {
			return res;
		} else if (pixels == 0) {
			return 0;
		} else if (pixels > 0) {
			return 1;
		}
		return -1;
	}

	public static float getTextWidth(Paint paint, String text) {
		return paint.measureText(text);
	}

}
