package hearsilent.amazingavatar.callbacks

import hearsilent.amazingavatar.models.AvatarModel

open class AvatarCallback {
    open fun onSuccess(avatarModel: AvatarModel) {}
    fun onFail() {}
}