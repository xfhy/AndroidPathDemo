package com.coolweather.coolweatherjetpack.data.db

import com.coolweather.coolweatherjetpack.data.model.place.City
import com.coolweather.coolweatherjetpack.data.model.place.County
import com.coolweather.coolweatherjetpack.data.model.place.Province
import org.litepal.LitePal

/**
 * 地点Dao
 */
class PlaceDao {

    /**
     * 查询所有的省份
     */
    fun getProvinceList(): MutableList<Province> = LitePal.findAll(Province::class.java)

    /**
     * 查询provinceId对应的城市
     */
    fun getCityList(provinceId: Int): MutableList<City> = LitePal.where("provinceId = ?", provinceId.toString()).find(City::class.java)

    /**
     * 查询cityId对应的县区
     */
    fun getCountyList(cityId: Int): MutableList<County> = LitePal.where("cityId = ?", cityId.toString()).find(County::class.java)

    /**
     * 保存省份数据
     */
    fun saveProvinceList(provinceList: List<Province>?) {
        if (provinceList != null && provinceList.isNotEmpty()) {
            LitePal.saveAll(provinceList)
        }
    }

    /**
     * 保存城市数据
     */
    fun saveCityList(cityList: List<City>?) {
        if (cityList != null && cityList.isNotEmpty()) {
            LitePal.saveAll(cityList)
        }
    }

    /**
     * 保存县区数据
     */
    fun saveCountyList(countyList: List<County>?) {
        if (countyList != null && countyList.isNotEmpty()) {
            LitePal.saveAll(countyList)
        }
    }

}