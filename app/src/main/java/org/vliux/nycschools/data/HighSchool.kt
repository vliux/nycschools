package org.vliux.nycschools.data

import android.os.Parcel
import android.os.Parcelable

data class HighSchool(val name: String, val dbn: String) : Parcelable {
  constructor(parcel: Parcel) : this(parcel.readString()!!, parcel.readString()!!) {}

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(name)
    parcel.writeString(dbn)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<HighSchool> {
    override fun createFromParcel(parcel: Parcel): HighSchool {
      return HighSchool(parcel)
    }

    override fun newArray(size: Int): Array<HighSchool?> {
      return arrayOfNulls(size)
    }
  }
}
