package com.lifting.app.domain.use_case

import com.lifting.app.core.util.Resource
import com.lifting.app.data.remote.model.AntrenmanProgramlari
import com.lifting.app.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetProgramDataUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<Resource<AntrenmanProgramlari>> = flow {
        emit(Resource.Loading)
        try {
            val response = repository.getProgramData()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}