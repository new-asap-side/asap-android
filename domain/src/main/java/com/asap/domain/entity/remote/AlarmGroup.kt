package com.asap.domain.entity.remote

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class AlarmGroup(
    @Json(name = "isPublic")
    val isPublic: Boolean,
    @Json(name = "thumbnailUrl")
    val thumbnailUrl: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "alarmDate")
    val alarmDates: List<String>,
    @Json(name = "alarmTime")
    val alarmTime: String,
    @Json(name = "totalNumber")
    val totalNumber: Int,
    @Json(name = "currentNumber")
    val currentNumber: Int,
) : Parcelable {
    constructor (parcel: Parcel) : this(
        isPublic = parcel.readInt() == 1,
        thumbnailUrl = parcel.readString() ?: "",
        title = parcel.readString() ?: "",
        alarmDates = parcel.createStringArrayList() ?: listOf(),
        alarmTime = parcel.readString() ?: "",
        totalNumber = parcel.readInt(),
        currentNumber = parcel.readInt(),
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(if (isPublic) 1 else 0)
            writeString(thumbnailUrl)
            writeString(title)
            writeStringList(alarmDates)
            writeString(alarmTime)
            writeInt(totalNumber)
            writeInt(currentNumber)
        }
    }

    companion object {
        fun dummy(): AlarmGroup {
            return AlarmGroup(
                isPublic = true,
                thumbnailUrl = "",
                title = "TEST Group",
                alarmDates = listOf("월", "화", "수"),
                alarmTime = "21:00",
                totalNumber = 8,
                currentNumber = 4,
            )
        }

        @JvmField
        val CREATOR = object : Parcelable.Creator<AlarmGroup> {
            override fun createFromParcel(parcel: Parcel): AlarmGroup {
                return AlarmGroup(parcel)
            }

            override fun newArray(size: Int): Array<AlarmGroup?> {
                return arrayOfNulls(size)
            }
        }
    }
}