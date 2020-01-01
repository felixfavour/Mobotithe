package com.felixfavour.mobotithe.database.entity

import android.net.Uri
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

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
    val photoUrl: Uri?= Uri.EMPTY,

    @ColumnInfo(name = "weekly_budget")
    val weeklyBudget: Long?=0,

    val histories: MutableList<History> = arrayListOf(),
    val incomes: MutableList<Income> = arrayListOf(),
    var fullName: String = ""
) {
    init {
        fullName = "$firstName $lastName"
    }
}

@Parcelize
data class Income (
    val name: String = "",
    val interval: String = "",
    val usualBudget: Long = 0
) : Parcelable

@Parcelize
data class Expense(
    val name: String = "",
    val interval: String = "",
    val usualBudget: Long = 0
) : Parcelable

@Parcelize
data class Transaction(
    val income: Income? = null,
    val expense: Expense? = null,
    val getIsIncome: Boolean = (expense == null),
    val transactionName: String? = income?.name ?: expense?.name
) : Parcelable

@Parcelize
data class History(
    val transactionCreationDate: Date? = null,
    val amount: Long = 0,
    val income : Boolean = false,
    val transactionName: String? = null
) : Parcelable