package com.alexvoz.tawk_test_task.repository.network

import com.alexvoz.tawk_test_task.data.wrappers.Resource
import kotlinx.coroutines.flow.*

/**
 *  Generic networkBoundResource implementation
 *  Steps:
 *  1) Return data from local database is it exists with Resource.Loading
 *  2) Save download data in local database
 *  3) Return data from local database if it exists with Resource.Success or Error if request is failed
 *
 *  @param fromCache Flow<ResultType>
 *  @param requestCall ResponseType
 *  @param saveInCache Unit
 */
inline fun <ResponseType, ResultType> networkBoundResource(
    crossinline fromCache: () -> Flow<ResultType>,
    crossinline requestCall: suspend () -> ResponseType,
    crossinline saveInCache: suspend (ResponseType) -> Unit
) = flow {

    emit(Resource.Loading(fromCache().first()))

    try {
        saveInCache(requestCall())
        emit(Resource.Success(fromCache().first()))
    } catch (throwable: Throwable) {
        emit(Resource.Error(fromCache().first(),throwable))
    }
}
