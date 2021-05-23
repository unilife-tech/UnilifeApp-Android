package unilife.com.unilife.profile

import android.os.Parcel
import android.os.Parcelable

class CompleteProfileEditTextModel() : Parcelable {

    var edittextvalue : String = ""
    var edittextpos : String = ""

    constructor(parcel: Parcel) : this() {
        edittextvalue = parcel.readString()
        edittextpos = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(edittextvalue)
        parcel.writeString(edittextpos)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CompleteProfileEditTextModel> {
        override fun createFromParcel(parcel: Parcel): CompleteProfileEditTextModel {
            return CompleteProfileEditTextModel(parcel)
        }

        override fun newArray(size: Int): Array<CompleteProfileEditTextModel?> {
            return arrayOfNulls(size)
        }
    }

}