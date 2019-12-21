package com.felixfavour.mobotithe.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@Entity(tableName = "mobotithe_users")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    var uID: Int = 0,

    @ColumnInfo(name = "first_name")
    val firstName: String = "",

    @ColumnInfo(name = "last_name")
    val lastName: String = "",

    @ColumnInfo(name = "middle_name")
    val middleName: String="",

    @ColumnInfo(name = "date_of_birth")
    val dob: String="",

    @ColumnInfo(name = "username")
    val username: String="",

    @ColumnInfo(name = "email")
    val email: String="",

    @ColumnInfo(name = "photoUrl")
    val photoUrl: String?="",

    val incomeHistories: MutableList<IncomeHistory> = arrayListOf(),
    val incomes: MutableList<Income> = arrayListOf()
) {
}

@Parcelize
data class Income (
    val name: String = "",
    val interval: String = "",
    val usualBudget: Double = 0.0
) : Parcelable

@Parcelize
data class IncomeHistory(
    val transactionCreationDate: Date = Date(7248942848487),
    val amount: Double = 0.00,
    val incomeCategory: Income? = Income("jhdd", "jkhdjhd", 333.33)
) : Parcelable