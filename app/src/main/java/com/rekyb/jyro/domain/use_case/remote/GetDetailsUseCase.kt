package com.rekyb.jyro.domain.use_case.remote

import android.content.Context
import com.rekyb.jyro.common.Resources
import com.rekyb.jyro.domain.model.UserDetailsModel
import com.rekyb.jyro.repository.UserRepositoryImpl
import com.rekyb.jyro.utils.ExceptionHandler
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetDetailsUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repositoryImpl: UserRepositoryImpl,
) {

    operator fun invoke(userName: String): Flow<Resources<UserDetailsModel>> {
        return flow {
            try {
                emit(Resources.Loading)
                emit(Resources.Success(repositoryImpl.getDetails(userName)))
            } catch (error: Exception) {
                emit(ExceptionHandler(context).handleError(error))
            }
        }
    }
}
