package hearsilent.amazingavatar.extensions

import androidx.core.view.WindowInsetsCompat

val WindowInsetsCompat.statusHeight
    get() = this.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars()).top