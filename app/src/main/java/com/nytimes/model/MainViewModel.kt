package com.nytimes.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.nytimes.BuildConfig
import com.nytimes.interfaces.InternetListener
import com.nytimes.rest.ApiClient
import com.nytimes.rest.ApiInterface
import com.nytimes.util.Util
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel() : ViewModel() {
    private lateinit var contents: MutableLiveData<ArrayList<com.newyorktimes.model.Response.Result>>
    private val TAG = MainViewModel::class.java.simpleName
    lateinit var mlistener: InternetListener
    lateinit var mContext: Context
    fun getUsers(): LiveData<ArrayList<com.newyorktimes.model.Response.Result>> {
        if (!::contents.isInitialized) {
            contents = MutableLiveData()
            if (Util.isOnline(mContext)) {
                getContentList()
            } else {
                contents.value=null
                mlistener.isConnectionAvailable(false)
            }
        }
        return contents
    }


    fun setListener(listener: Any) {
        this.mlistener = listener as InternetListener
    }

    fun setContext(context: Any) {
        this.mContext = context as Context
    }

    fun getContentList() {

        val apiService = ApiClient.getClient(mlistener).create(ApiInterface::class.java)
        val call = apiService.getList(7, BuildConfig.NY_KEY)
        call.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                var resonse = response.body().toString()
                resonse = resonse.replace("media-metadata", "media_metadata")

                try {
                    val responseJSON = JSONObject(resonse)
                    val model_Response = Gson().fromJson(responseJSON.toString(), com.newyorktimes.model.Response::class.java)
                    contents.value = model_Response.results
                } catch (e: Exception) {
                    Log.e(TAG, "Exception $e")
                }

            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                contents.value = null
            }
        })


    }


}
