package com.acterics.libcurl

import com.acterics.service.IpService
import com.acterics.service.IpServiceFactory

import com.acterics.model.Location

import com.acterics.cjson.LocationMapper

class CurlIpService: IpService {
    override fun getLocation(): Location? {
        return try {
            fetch(API.IP_API.location, LocationMapper::map)
        } catch(e: Exception) {
            println(e)
            null
        }
    } 
}

object CurlIpServiceFactory: IpServiceFactory {
    override fun create(): IpService = CurlIpService()
}