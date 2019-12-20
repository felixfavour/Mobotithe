package com.felixfavour.mobotithe.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.collections.ArrayList

@Entity(tableName = "mobotithe_users")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    var uID: Int,

    @ColumnInfo(name = "first_name")
    val firstName: String,

    @ColumnInfo(name = "last_name")
    val lastName: String,

    @ColumnInfo(name = "middle_name")
    val middleName: String,

    @ColumnInfo(name = "date_of_birth")
    val dob: String,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "photoUrl")
    val photoUrl: String?,

    val incomeHistories: ArrayList<IncomeHistory?>,
    val incomes: ArrayList<Income?>
) {
    companion object {
        var userID = 0
    }
    init {
        userID++
        uID = userID
    }
}

data class Income(
    val name: String,
    val interval: String,
    val usualBudget: Double
)

data class IncomeHistory(
    val transactionCreationDate: String,
    val amount: Double,
    val incomeCategory: Income
)