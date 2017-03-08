package hearsilent.amazingavatar;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.Space;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import hearsilent.amazingavatar.callbacks.AvatarCallback;
import hearsilent.amazingavatar.libs.AppBarStateChangeListener;
import hearsilent.amazingavatar.libs.NetworkHelper;
import hearsilent.amazingavatar.libs.Utils;
import hearsilent.amazingavatar.models.AvatarModel;

import static hearsilent.amazingavatar.R.id.recyclerView;

public class MainActivity extends AppCompatActivity {

	private final static float EXPAND_AVATAR_SIZE_DP = 80f;
	private final static float COLLAPSED_AVATAR_SIZE_DP = 32f;

	private AppBarLayout mAppBarLayout;
	private CircleImageView mAvatarImageView;
	private TextView mToolbarTextView, mTitleTextView;
	private Space mSpace;
	private Toolbar mToolBar;

	private RecyclerView mRecyclerView;
	private AppBarStateChangeListener mAppBarStateChangeListener;

	private int[] mAvatarPoint = new int[2], mSpacePoint = new int[2], mToolbarTextPoint =
			new int[2], mTitleTextViewPoint = new int[2];
	private float mTitleTextSize;
	private int mTitleTextViewWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViews();
		setUpViews();
		fetchAvatar();
	}

	private void findViews() {
		mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
		mAvatarImageView = (CircleImageView) findViewById(R.id.imageView_avatar);
		mToolbarTextView = (TextView) findViewById(R.id.toolbar_title);
		mTitleTextView = (TextView) findViewById(R.id.textView_title);
		mSpace = (Space) findViewById(R.id.space);
		mToolBar = (Toolbar) findViewById(R.id.toolbar);
		mRecyclerView = (RecyclerView) findViewById(recyclerView);
	}

	private void setUpViews() {
		mTitleTextSize = mTitleTextView.getTextSize();
		setUpToolbar();
		setUpRecyclerView();
		setUpAmazingAvatar();
	}

	private void setUpRecyclerView() {
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView
				.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setAdapter(new Adapter());
	}

	private void setUpToolbar() {
		setSupportActionBar(mToolBar);
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayShowTitleEnabled(false);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	private void setUpAmazingAvatar() {
		mAppBarStateChangeListener = new AppBarStateChangeListener() {

			@Override
			public void onStateChanged(AppBarLayout appBarLayout,
			                           AppBarStateChangeListener.State state) {
			}

			@Override
			public void onOffsetChanged(AppBarStateChangeListener.State state, float offset) {
				translationView(offset);
			}
		};
		mAppBarLayout.addOnOffsetChangedListener(mAppBarStateChangeListener);
	}

	private void translationView(float offset) {
		float xOffset = -(mAvatarPoint[0] - mSpacePoint[0]) * offset;
		float yOffset = -(mAvatarPoint[1] - mSpacePoint[1]) * offset;
		float xTitleOffset = -(mTitleTextViewPoint[0] - mToolbarTextPoint[0]) * offset;
		float yTitleOffset = -(mTitleTextViewPoint[1] - mToolbarTextPoint[1]) * offset;
		int newSize = Utils.convertDpToPixelSize(
				EXPAND_AVATAR_SIZE_DP - (EXPAND_AVATAR_SIZE_DP - COLLAPSED_AVATAR_SIZE_DP) * offset,
				MainActivity
						.this);
		float newTextSize =
				mTitleTextSize - (mTitleTextSize - mToolbarTextView.getTextSize()) * offset;
		mAvatarImageView.getLayoutParams().width = newSize;
		mAvatarImageView.getLayoutParams().height = newSize;
		mAvatarImageView.setTranslationX(xOffset);
		mAvatarImageView.setTranslationY(yOffset);
		mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, newTextSize);
		mTitleTextView.setTranslationX(xTitleOffset);
		mTitleTextView.setTranslationY(yTitleOffset);
	}

	/**
	 * Avatar from TinyFaces (https://github.com/maximedegreve/TinyFaces)
	 */
	private void fetchAvatar() {
		NetworkHelper.getAvatar(new AvatarCallback() {

			@Override
			public void onSuccess(AvatarModel avatarModel) {
				super.onSuccess(avatarModel);
				if (isFinishing()) {
					return;
				}
				ImageLoader.getInstance().displayImage(avatarModel.url, mAvatarImageView);
				mTitleTextView.setText(
						String.format(Locale.getDefault(), "%s %s", avatarModel.firstName,
								avatarModel.lastName));
				mTitleTextView.post(new Runnable() {

					@Override
					public void run() {
						Rect bounds = new Rect();
						Paint textPaint = mTitleTextView.getPaint();
						textPaint.setTextSize(mTitleTextSize);
						textPaint.getTextBounds(mTitleTextView.getText().toString(), 0,
								mTitleTextView.getText().length(), bounds);
						mTitleTextViewPoint[0] -= (bounds.width() - mTitleTextViewWidth) / 2;
						mTitleTextViewWidth = bounds.width();
						translationView(mAppBarStateChangeListener.getCurrentOffset());
					}
				});
			}
		});
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		mAvatarImageView.getLocationOnScreen(mAvatarPoint);
		mSpace.getLocationOnScreen(mSpacePoint);
		mToolbarTextView.getLocationOnScreen(mToolbarTextPoint);
		mToolbarTextPoint[0] += Utils.convertDpToPixelSize(24, this);
		mTitleTextView.getLocationOnScreen(mTitleTextViewPoint);
		mTitleTextViewWidth = mTitleTextView.getWidth();
	}

	private class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

		private class DemoViewHolder extends RecyclerView.ViewHolder {

			private TextView textView;

			private DemoViewHolder(View view) {
				super(view);

				textView = (TextView) view.findViewById(R.id.textView);
			}
		}

		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			return new DemoViewHolder(LayoutInflater.from(parent.getContext())
					.inflate(R.layout.item_demo, parent, false));
		}

		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
			((DemoViewHolder) holder).textView
					.setText(String.format(Locale.getDefault(), "HearSilent %d", position));
		}

		@Override
		public int getItemCount() {
			return 100;
		}
	}
}
