package com.hci.TP3_HCI

class DataSourceException(
    var code: Int,
    message: String,
    var details: List<String>?
) : Exception(message)