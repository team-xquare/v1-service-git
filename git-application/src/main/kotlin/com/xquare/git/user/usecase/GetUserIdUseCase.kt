package com.xquare.git.user.usecase

import com.xquare.git.annotations.UseCase
import com.xquare.git.user.exceptions.UserExceptions
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