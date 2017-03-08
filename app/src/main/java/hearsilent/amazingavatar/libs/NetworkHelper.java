package hearsilent.amazingavatar.libs;

import android.support.annotation.NonNull;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import hearsilent.amazingavatar.callbacks.AvatarCallback;
import hearsilent.amazingavatar.models.AvatarModel;

public class NetworkHelper {

	private static AsyncHttpClient mClient = null;

	private static AsyncHttpClient init() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.addHeader("Connection", "Keep-Alive");
		client.setEnableRedirects(true, true, true);

		client.setTimeout(8 * 1000);
		return client;
	}

	private static AsyncHttpClient getClient() {
		if (mClient == null) {
			mClient = init();
		}
		return mClient;
	}

	private static final String AVATAR_URL = "https://tinyfac.es/api/users";

	public static void getAvatar(@NonNull final AvatarCallback callback) {
		AsyncHttpClient client = getClient();

		client.get(AVATAR_URL, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
				super.onSuccess(statusCode, headers, response);
				if (statusCode != 200) {
					callback.onFail();
				} else {
					try {
						JSONObject jsonObject = response.getJSONObject(0);
						AvatarModel model = new AvatarModel();
						model.url = jsonObject.getJSONArray("avatars").getJSONObject(0)
								.getString("url");
						model.firstName = jsonObject.getString("first_name");
						model.lastName = jsonObject.getString("last_name");
						callback.onSuccess(model);
					} catch (JSONException ignore) {
						callback.onFail();
					}
				}
			}
		});
	}

}
