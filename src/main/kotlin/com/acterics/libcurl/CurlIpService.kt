package com.acterics.libcurl

import com.acterics.service.IpService
import com.acterics.service.IpServiceFactory

import com.acterics.model.Location

import com.acterics.cjson.LocationMapper

class CurlIpService: IpService {
    companion object {
        private const val ROOT = "http://ip-api.com"
    }

    override fun getLocation(): Location? {
        return try {
            fetch("$ROOT/json", LocationMapper::map)
        } catch(e: Exception) {
            println(e)
            null
        }
    } 
}

object CurlIpServiceFactory: IpServiceFactory {
    override fun create(): IpService = CurlIpService()
}