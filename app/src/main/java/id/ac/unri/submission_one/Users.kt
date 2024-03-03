package id.ac.unri.submission_one

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users(
    val uid: String,
    val username: String,
    val email: String,

): Parcelable
