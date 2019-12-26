package com.felixfavour.mobotithe.gui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.mobotithe.database.entity.Income
import com.felixfavour.mobotithe.database.entity.History
import com.felixfavour.mobotithe.util.TaskAssesor
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.joda.time.LocalDate
import kotlin.collections.HashMap

class HistoryViewModel : ViewModel() {

    companion object {
        const val USERS_COLLECTION = "users"
        const val INCOME_HISTORIES = "income_histories"
    }
    // LiveData of income histories
    private val _incomeHistories = MutableLiveData<List<History>>()
    val incomeHistories : LiveData<List<History>>
        get() = _incomeHistories

    // LiveData of Error Status
    private val _errorStatus = MutableLiveData<TaskAssesor>()
    val errorStatus : LiveData<TaskAssesor>
        get() = _errorStatus

    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser
    private val firestore = FirebaseFirestore.getInstance()
    private val histories = mutableListOf<History>()

    init {
        getListOfIncomeHistories()
    }

    fun getListOfIncomeHistories() {
        histories.clear()
        firestore.collection(USERS_COLLECTION).document(currentUser!!.uid)
            .get().addOnSuccessListener { documentSnapshot ->
                try {
                    val incomeHistories = documentSnapshot.get(INCOME_HISTORIES) as MutableList<HashMap<String, Any>>
                    /*
                    Since the data collected from the online Database are not in the Original class
                    models but HashMaps, I looped through the list of HashMaps and assigned each
                    value to the appropriate property in the class Model
                    */
                    for (incomeHistory in incomeHistories) {

                        /*
                            Creating a skeleton for the class [Transaction]
                            to simplify the collection of data from database
                        */
                        val dateTimestamp = incomeHistory["transactionCreationDate"] as Timestamp
                        val date = dateTimestamp.toDate()
                        val incomeDeferredAsHashMap = incomeHistory["incomeCategory"] as HashMap<String, Any>
                        val amountHistory = incomeHistory["amount"] as Long

                        val amountIncome = incomeDeferredAsHashMap["usualBudget"].toString().toDouble().toLong()

                        val income = Income(
                            incomeDeferredAsHashMap["name"] as String,
                            incomeDeferredAsHashMap["interval"] as String,
                            amountIncome
                        )

                        val history = History(
                            date,
                            amountHistory,
                            income
                        )

                        histories.add(history)
                    }
                    _incomeHistories.value = histories.sortedByDescending { incomeHistoryParam ->
                        incomeHistoryParam.transactionCreationDate
                    }
                } catch (ex: TypeCastException) {
                    _errorStatus.value = TaskAssesor.EMPTY_SNAPSHOT
                }
            }
    }

    fun filterHistoriesByToday() {
        val historiesByDay = mutableListOf<History>()
        val dateNow = LocalDate.now()

        for(incomeHistory in histories) {
            val dateThen = LocalDate.fromDateFields(incomeHistory.transactionCreationDate)
            if (dateNow.dayOfYear == dateThen.dayOfYear) {
                historiesByDay.add(incomeHistory)
            }
            _incomeHistories.value = historiesByDay.sortedByDescending { incomeHistoryParam ->
                incomeHistoryParam.transactionCreationDate
            }
        }
    }

    fun filterHistoriesByWeek() {
        val historiesByWeek = mutableListOf<History>()
        val dateNow = LocalDate.now()

        for(incomeHistory in histories) {
            val dateThen = LocalDate.fromDateFields(incomeHistory.transactionCreationDate)
            if (dateNow.weekOfWeekyear == dateThen.weekOfWeekyear) {
                historiesByWeek.add(incomeHistory)
            }
            _incomeHistories.value = historiesByWeek.sortedByDescending { incomeHistoryParam ->
                incomeHistoryParam.transactionCreationDate
            }
        }
    }

    fun filterHistoriesByMonth() {
        val historiesByMonth = mutableListOf<History>()
        val dateNow = LocalDate.now()

        for(incomeHistory in histories) {
            val dateThen = LocalDate.fromDateFields(incomeHistory.transactionCreationDate)
            if (dateNow.monthOfYear == dateThen.monthOfYear) {
                historiesByMonth.add(incomeHistory)
            }
            _incomeHistories.value = historiesByMonth.sortedByDescending { incomeHistoryParam ->
                incomeHistoryParam.transactionCreationDate
            }
        }
    }

}