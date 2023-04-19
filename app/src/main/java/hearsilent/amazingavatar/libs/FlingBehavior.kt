package hearsilent.amazingavatar.libs

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout

class FlingBehavior : AppBarLayout.Behavior {
    private var isPositive = false

    constructor() {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onNestedFling(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout, target: View, velocityX: Float,
        velocityY: Float, consumed: Boolean
    ): Boolean {
        var newVelocityY = velocityY
        var newConsumed = consumed
        if (velocityY > 0 && !isPositive || velocityY < 0 && isPositive) {
            newVelocityY *= -1
        }
        if (target is RecyclerView && velocityY < 0) {
            val firstChild = target.getChildAt(0)
            val childAdapterPosition = target.getChildAdapterPosition(firstChild)
            newConsumed = childAdapterPosition > TOP_CHILD_FLING_THRESHOLD
        }
        return super.onNestedFling(
            coordinatorLayout,
            child,
            target,
            velocityX,
            newVelocityY,
            newConsumed
        )
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout, child: AppBarLayout,
        target: View, dx: Int, dy: Int, consumed: IntArray, type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        isPositive = dy > 0
    }

    companion object {
        private const val TOP_CHILD_FLING_THRESHOLD = 3
    }
}