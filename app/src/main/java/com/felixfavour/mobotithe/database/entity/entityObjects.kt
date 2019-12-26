package com.felixfavour.mobotithe.database.entity

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
    val photoUrl: String?="",

    val histories: MutableList<History> = arrayListOf(),
    val incomes: MutableList<Income> = arrayListOf()
) {
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
data class History(
    val transactionCreationDate: Date = Date(),
    val amount: Long = 0,
    val incomeCategory: Income? = Income("", "", 0),
    val expenseCategory: Expense? = Expense("", "", 0),
    val isIncome : Boolean = false,
    val isExpense: Boolean = false
) : Parcelable {
}