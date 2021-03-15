package com.renesis.smartly.framework.remote

import android.util.Log
import com.renesis.smartly.framework.Result
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                val body = response.body()
                Log.e("response", body.toString())
                return if (body != null)
                    Result.success(body)
                else
                    error(response.code(), response.message())
            } else {
                val body = response.errorBody()
                return if (body != null) {
                    val errorMessage = body.string()
                    val json = JSONObject(errorMessage)
                    if (json.getString("message") != "")
                        error(response.code(), json.getString("message"))
                    else
                        error(response.code(), response.message())
                } else {
                    error(response.code(), response.message())
                }
            }
        } catch (e: Exception) {
            Log.e("response-exception", e.message ?: e.toString())
            return error(e.hashCode(), e.message ?: e.toString())
        }
    }


    private fun <T> error(responseCode: Int, message: String): Result<T> {
        Log.e(
            "NETWORK_CALL",
            "Network call has failed for a following reason: $responseCode $message"
        )
        return Result.error(message)
    }

}