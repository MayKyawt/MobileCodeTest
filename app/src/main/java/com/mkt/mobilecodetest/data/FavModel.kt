package com.mkt.mobilecodetest.data

import androidx.room.Entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind

@Entity(tableName = "favorite", primaryKeys = ["id"])
@Serializable
data class FavModel (
    @SerialName("id")
    var id:Int?=0,

    @SerialName("isFav")
    var isFav:Boolean?=false

)