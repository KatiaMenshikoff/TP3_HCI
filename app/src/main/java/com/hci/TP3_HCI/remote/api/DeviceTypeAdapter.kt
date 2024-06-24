package com.hci.TP3_HCI.api.remote.api

import com.hci.TP3_HCI.remote.model.RemoteDevice
import com.hci.TP3_HCI.remote.model.RemoteDeviceType
import com.hci.TP3_HCI.remote.model.devices.lamp.RemoteLamp
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import com.hci.TP3_HCI.remote.model.devices.ac.RemoteAC
import com.hci.TP3_HCI.remote.model.devices.speaker.RemoteSpeaker
import com.hci.TP3_HCI.remote.model.devices.sprinkler.RemoteSprinkler
import java.lang.reflect.Type

class DeviceTypeAdapter : JsonDeserializer<RemoteDevice<*>?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): RemoteDevice<*>? {
        val gson = Gson()
        val jsonDeviceObject = json.asJsonObject
        val jsonDeviceTypeObject = jsonDeviceObject["type"].asJsonObject
        val deviceTypeId = jsonDeviceTypeObject["id"].asString
        if (deviceTypeId == RemoteDeviceType.LAMP_DEVICE_TYPE_ID) {
            return gson.fromJson(jsonDeviceObject, object : TypeToken<RemoteLamp?>() {}.type)
        } else if (deviceTypeId == RemoteDeviceType.AC_DEVICE_TYPE_ID){
            return gson.fromJson(jsonDeviceObject, object : TypeToken <RemoteAC?>() {}.type)
        } else if (deviceTypeId == RemoteDeviceType.SPEAKER_DEVICE_TYPE_ID){
            return gson.fromJson(jsonDeviceObject, object : TypeToken<RemoteSpeaker?>() {}.type)
        } else if (deviceTypeId == RemoteDeviceType.SPRINKLER_DEVICE_TYPE_ID){
            return gson.fromJson(jsonDeviceObject, object : TypeToken<RemoteSprinkler?>() {}.type)
        } else {
            return null
        }
    }
}