package com.xquare.v1servicegit.user.usecase

import com.xquare.v1servicegit.common.annotations.UseCase
import com.xquare.v1servicegit.user.exceptions.UserExceptions
import java.util.*

@UseCase
class GetUserIdUseCase {
    fun execute(userId: String?): UUID {
        if (userId == null) {
            throw UserExceptions.UnAuthorized()
        }
        return UUID.fromString(userId)
    }
}