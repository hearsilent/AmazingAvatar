package hearsilent.amazingavatar.libs

import android.content.Context
import android.util.DisplayMetrics

object Utils {

    @JvmStatic
    fun getDisplayMetrics(context: Context): DisplayMetrics {
        val resources = context.resources
        return resources.displayMetrics
    }
}