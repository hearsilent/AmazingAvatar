package hearsilent.amazingavatar.libs;

import android.support.design.widget.AppBarLayout;

public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

	public enum State {
		EXPANDED,
		COLLAPSED,
		IDLE
	}

	private State mCurrentState = State.IDLE;

	@Override
	public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
		if (i == 0) {
			if (mCurrentState != State.EXPANDED) {
				onStateChanged(appBarLayout, State.EXPANDED);
			}
			mCurrentState = State.EXPANDED;
		} else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
			if (mCurrentState != State.COLLAPSED) {
				onStateChanged(appBarLayout, State.COLLAPSED);
			}
			mCurrentState = State.COLLAPSED;
		} else {
			if (mCurrentState != State.IDLE) {
				onStateChanged(appBarLayout, State.IDLE);
			}
			mCurrentState = State.IDLE;
		}
		onOffsetChanged(mCurrentState, Math.abs(i / (float) appBarLayout.getTotalScrollRange()));
	}

	public State getCurrentState() {
		return mCurrentState;
	}

	public abstract void onStateChanged(AppBarLayout appBarLayout, State state);

	public abstract void onOffsetChanged(State state, float offset);
}