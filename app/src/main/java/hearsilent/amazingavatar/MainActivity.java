package hearsilent.amazingavatar;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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

	private View mContainerView;

	private AppBarLayout mAppBarLayout;
	private CircleImageView mAvatarImageView;
	private TextView mToolbarTextView, mTitleTextView;
	private Space mSpace;
	private Toolbar mToolBar;

	private RecyclerView mRecyclerView;
	private AppBarStateChangeListener mAppBarStateChangeListener;

	private float[] mAvatarPoint = new float[2], mSpacePoint = new float[2], mToolbarTextPoint =
			new float[2], mTitleTextViewPoint = new float[2];
	private float mTitleTextSize;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViews();
		setUpViews();
		fetchAvatar();
	}

	private void findViews() {
		mContainerView = findViewById(R.id.view_container);
		mAppBarLayout = findViewById(R.id.app_bar);
		mAvatarImageView = findViewById(R.id.imageView_avatar);
		mToolbarTextView = findViewById(R.id.toolbar_title);
		mTitleTextView = findViewById(R.id.textView_title);
		mSpace = findViewById(R.id.space);
		mToolBar = findViewById(R.id.toolbar);
		mRecyclerView = findViewById(recyclerView);
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
		mAppBarLayout.getLayoutParams().height = Utils.getDisplayMetrics(this).widthPixels * 9 / 16;
		mAppBarLayout.requestLayout();

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
		float newAvatarSize = Utils.convertDpToPixel(
				EXPAND_AVATAR_SIZE_DP - (EXPAND_AVATAR_SIZE_DP - COLLAPSED_AVATAR_SIZE_DP) * offset,
				this);
		float expandAvatarSize = Utils.convertDpToPixel(EXPAND_AVATAR_SIZE_DP, this);
		float xAvatarOffset =
				(mSpacePoint[0] - mAvatarPoint[0] - (expandAvatarSize - newAvatarSize) / 2f) *
						offset;
		// If avatar center in vertical, just half `(expandAvatarSize - newAvatarSize)`
		float yAvatarOffset =
				(mSpacePoint[1] - mAvatarPoint[1] - (expandAvatarSize - newAvatarSize)) * offset;
		mAvatarImageView.getLayoutParams().width = Math.round(newAvatarSize);
		mAvatarImageView.getLayoutParams().height = Math.round(newAvatarSize);
		mAvatarImageView.setTranslationX(xAvatarOffset);
		mAvatarImageView.setTranslationY(yAvatarOffset);

		float newTextSize =
				mTitleTextSize - (mTitleTextSize - mToolbarTextView.getTextSize()) * offset;
		Paint paint = new Paint(mTitleTextView.getPaint());
		paint.setTextSize(newTextSize);
		float newTextWidth = Utils.getTextWidth(paint, mTitleTextView.getText().toString());
		paint.setTextSize(mTitleTextSize);
		float originTextWidth = Utils.getTextWidth(paint, mTitleTextView.getText().toString());
		// If rtl should move title view to end of view.
		boolean isRTL = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) ==
				View.LAYOUT_DIRECTION_RTL ||
				mContainerView.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
		float xTitleOffset = ((mToolbarTextPoint[0] + (isRTL ? mToolbarTextView.getWidth() : 0)) -
				(mTitleTextViewPoint[0] + (isRTL ? mTitleTextView.getWidth() : 0)) -
				(mToolbarTextView.getWidth() > newTextWidth ?
						(originTextWidth - newTextWidth) / 2f : 0)) * offset;
		float yTitleOffset = (mToolbarTextPoint[1] - mTitleTextViewPoint[1]) * offset;
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
			public void onSuccess(final AvatarModel avatarModel) {
				super.onSuccess(avatarModel);
				if (isFinishing()) {
					return;
				}
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Glide.with(MainActivity.this).load(avatarModel.url).into(mAvatarImageView);
						String name =
								String.format(Locale.getDefault(), "%s %s", avatarModel.firstName,
										avatarModel.lastName);
						mTitleTextView.setText(name);
						mTitleTextView.post(new Runnable() {

							@Override
							public void run() {
								resetPoints(true);
							}
						});
					}
				});
			}
		});
	}

	private void resetPoints(boolean isTextChanged) {
		final float offset = mAppBarStateChangeListener.getCurrentOffset();

		float newAvatarSize = Utils.convertDpToPixel(
				EXPAND_AVATAR_SIZE_DP - (EXPAND_AVATAR_SIZE_DP - COLLAPSED_AVATAR_SIZE_DP) * offset,
				this);
		float expandAvatarSize = Utils.convertDpToPixel(EXPAND_AVATAR_SIZE_DP, this);

		int[] avatarPoint = new int[2];
		mAvatarImageView.getLocationOnScreen(avatarPoint);
		mAvatarPoint[0] = avatarPoint[0] - mAvatarImageView.getTranslationX() -
				(expandAvatarSize - newAvatarSize) / 2f;
		// If avatar center in vertical, just half `(expandAvatarSize - newAvatarSize)`
		mAvatarPoint[1] = avatarPoint[1] - mAvatarImageView.getTranslationY() -
				(expandAvatarSize - newAvatarSize);

		int[] spacePoint = new int[2];
		mSpace.getLocationOnScreen(spacePoint);
		mSpacePoint[0] = spacePoint[0];
		mSpacePoint[1] = spacePoint[1];

		int[] toolbarTextPoint = new int[2];
		mToolbarTextView.getLocationOnScreen(toolbarTextPoint);
		mToolbarTextPoint[0] = toolbarTextPoint[0];
		mToolbarTextPoint[1] = toolbarTextPoint[1];

		Paint paint = new Paint(mTitleTextView.getPaint());
		float newTextWidth = Utils.getTextWidth(paint, mTitleTextView.getText().toString());
		paint.setTextSize(mTitleTextSize);
		float originTextWidth = Utils.getTextWidth(paint, mTitleTextView.getText().toString());
		int[] titleTextViewPoint = new int[2];
		mTitleTextView.getLocationOnScreen(titleTextViewPoint);
		mTitleTextViewPoint[0] = titleTextViewPoint[0] - mTitleTextView.getTranslationX() -
				(mToolbarTextView.getWidth() > newTextWidth ?
						(originTextWidth - newTextWidth) / 2f : 0);
		mTitleTextViewPoint[1] = titleTextViewPoint[1] - mTitleTextView.getTranslationY();

		if (isTextChanged) {
			new Handler().post(new Runnable() {

				@Override
				public void run() {
					translationView(offset);
				}
			});
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (!hasFocus) {
			return;
		}
		resetPoints(false);
	}

	private class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

		private class DemoViewHolder extends RecyclerView.ViewHolder {

			private TextView textView;

			private DemoViewHolder(View view) {
				super(view);

				textView = view.findViewById(R.id.textView);
			}
		}

		@NonNull
		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			return new DemoViewHolder(LayoutInflater.from(parent.getContext())
					.inflate(R.layout.item_demo, parent, false));
		}

		@Override
		public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
			((DemoViewHolder) holder).textView
					.setText(String.format(Locale.getDefault(), "HearSilent %d", position));
		}

		@Override
		public int getItemCount() {
			return 100;
		}
	}
}
