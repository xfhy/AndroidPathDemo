package com.coolweather.coolweatherjetpack.data.db

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.coolweather.coolweatherjetpack.CoolWeatherApplication
import com.coolweather.coolweatherjetpack.data.model.weather.Weather
import com.google.gson.Gson

class WeatherDao {

    /**
     * 将天气信息缓存到SP
     */
    fun cacheWeatherInfo(weather: Weather?) {
        if (weather == null) return
        //系统的API
        PreferenceManager.getDefaultSharedPreferences(CoolWeatherApplication.context).edit {
            val weatherInfo = Gson().toJson(weather)
            putString("weather", weatherInfo)
        }
    }

    /**
     * 从SP中将天气信息提取出来
     */
    fun getCachedWeatherInfo(): Weather? {
        val weatherInfo = PreferenceManager.getDefaultSharedPreferences(CoolWeatherApplication.context).getString("weather", null)
        if (weatherInfo != null) {
            return Gson().fromJson(weatherInfo, Weather::class.java)
        }
        return null
    }

    /**
     * 缓存图片地址到SP
     */
    fun cacheBingPic(bingPic: String?) {
        if (bingPic == null) return
        val defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(CoolWeatherApplication.context)
        defaultSharedPreferences.edit {
            putString("bing_pic", bingPic)
        }
    }

    /**
     * 从SP中取出缓存的图片地址
     */
    fun getCachedBingPic(): String? = PreferenceManager.getDefaultSharedPreferences(CoolWeatherApplication.context).getString("bing_pic", null)

    /**
     * SharedPreferences 扩展
     */
    private fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
        val editor = edit()
        action(editor)
        editor.apply()
    }

}