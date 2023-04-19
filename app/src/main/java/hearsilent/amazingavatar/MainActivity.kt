package hearsilent.amazingavatar

import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.appbar.AppBarLayout
import hearsilent.amazingavatar.adapter.DemoAdapter
import hearsilent.amazingavatar.callbacks.AvatarCallback
import hearsilent.amazingavatar.databinding.ActivityMainBinding
import hearsilent.amazingavatar.libs.AppBarStateChangeListener
import hearsilent.amazingavatar.libs.NetworkHelper
import hearsilent.amazingavatar.libs.Utils
import hearsilent.amazingavatar.models.AvatarModel
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setUpViews()
        fetchAvatar()
    }

    private fun setUpViews() {
        setUpToolbar()
        setUpRecyclerView()
        setUpAmazingAvatar()
    }

    private fun setUpRecyclerView() {
        mBinding.recyclerView.setHasFixedSize(true)
        mBinding.recyclerView.itemAnimator = DefaultItemAnimator()
        mBinding.recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mBinding.recyclerView.adapter = DemoAdapter()
    }

    private fun setUpToolbar() {
        mBinding.appBar.layoutParams.height = Utils.getDisplayMetrics(this).widthPixels * 9 / 16
        mBinding.appBar.requestLayout()
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setUpAmazingAvatar() {
        val typedArray = obtainStyledAttributes(
            android.R.style.TextAppearance_Material_Widget_Toolbar_Title,
            intArrayOf(android.R.attr.textSize)
        )
        val collapsedTextSize = typedArray.getDimension(0, mBinding.textViewTitle.textSize)
        typedArray.recycle()
        val expandTextSize = mBinding.textViewTitleShadow.textSize

        mBinding.appBar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {}

            override fun onOffsetChanged(state: State, offset: Float) {
                mBinding.motionHeader.progress = offset

                val newTextSize = expandTextSize + (collapsedTextSize - expandTextSize) * offset
                mBinding.textViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, newTextSize)
            }
        })
    }

    /**
     * Avatar from TinyFaces ([TinyFaces](https://github.com/maximedegreve/TinyFaces))
     */
    private fun fetchAvatar() {
        NetworkHelper.getAvatar(object : AvatarCallback() {
            override fun onSuccess(avatarModel: AvatarModel) {
                super.onSuccess(avatarModel)
                if (isFinishing) {
                    return
                }
                runOnUiThread {
                    mBinding.imageViewAvatar.load(avatarModel.url) { crossfade(true) }
                    val name = String.format(
                        Locale.getDefault(), "%s %s", avatarModel.firstName, avatarModel.lastName
                    )
                    mBinding.textViewTitleShadow.text = name
                    mBinding.textViewTitle.text = name
                }
            }
        })
    }
}