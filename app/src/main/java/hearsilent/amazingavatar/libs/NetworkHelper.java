package hearsilent.amazingavatar.libs;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import hearsilent.amazingavatar.callbacks.AvatarCallback;
import hearsilent.amazingavatar.models.AvatarModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NetworkHelper {

	private static OkHttpClient mClient;

	private static void init() {
		mClient = new OkHttpClient().newBuilder().followRedirects(false).followSslRedirects(false)
				.connectTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
				.readTimeout(30, TimeUnit.SECONDS).build();
	}

	private static OkHttpClient getClient() {
		if (mClient == null) {
			init();
		}
		return mClient;
	}

	private static final String AVATAR_URL = "https://tinyfac.es/api/users";

	public static void getAvatar(@NonNull final AvatarCallback callback) {
		OkHttpClient client = getClient();

		Request request = new Request.Builder().url(AVATAR_URL).get().build();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onFailure(@NonNull Call call, @NonNull IOException e) {
				callback.onFail();
			}

			@Override
			public void onResponse(@NonNull Call call, @NonNull Response response) {
				if (response.code() == 200) {
					try {
						ResponseBody responseBodyCopy = response.peekBody(Long.MAX_VALUE);
						String body = responseBodyCopy.string();
						JSONArray jsonArray = new JSONArray(body);
						JSONObject jsonObject = jsonArray.getJSONObject(0);
						AvatarModel model = new AvatarModel();
						model.url = jsonObject.getJSONArray("avatars").getJSONObject(0)
								.getString("url");
						model.firstName = jsonObject.getString("first_name");
						model.lastName = jsonObject.getString("last_name");
						callback.onSuccess(model);
					} catch (Exception ignore) {
						callback.onFail();
					}
				} else {
					callback.onFail();
				}
			}
		});
	}

}
