package com.acterics.service

import com.acterics.model.Location

interface IpService {
    fun getLocation(): Location?
}

interface IpServiceFactory {
    fun create(): IpService
}