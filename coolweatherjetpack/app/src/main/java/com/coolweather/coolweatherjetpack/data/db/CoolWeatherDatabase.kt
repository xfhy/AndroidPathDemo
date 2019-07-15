package com.coolweather.coolweatherjetpack.data.db

/**
 * 单例
 * lazy init
 * 在第一次使用时才会加载
 */
object CoolWeatherDatabase {

    val placeDao by lazy { PlaceDao() }

    val weatherDao by lazy { WeatherDao() }

}