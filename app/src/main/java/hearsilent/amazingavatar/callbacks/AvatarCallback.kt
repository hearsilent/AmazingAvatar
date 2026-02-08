package hearsilent.amazingavatar.callbacks

import hearsilent.amazingavatar.models.AvatarModel

interface AvatarCallback {
    fun onSuccess(avatarModel: AvatarModel)
    fun onFail()
}