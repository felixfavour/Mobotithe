package com.felixfavour.mobotithe.gui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
        const val TRANSACTION_HISTORIES = "transaction_histories"
    }
    // LiveData of income histories
    private val _histories = MutableLiveData<List<History>>()
    val histories : LiveData<List<History>>
        get() = _histories

    // LiveData of Error Status
    private val _errorStatus = MutableLiveData<TaskAssesor>()
    val errorStatus : LiveData<TaskAssesor>
        get() = _errorStatus

    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser
    private val firestore = FirebaseFirestore.getInstance()
    private val historiesObjectList = mutableListOf<History>()

    init {
        getListOfTransactionHistories()
    }

    fun getListOfTransactionHistories() {
        firestore.collection(USERS_COLLECTION).document(currentUser!!.uid)
            .get().addOnSuccessListener { documentSnapshot ->
                try {
                    val transactionHistoriesHashMaps = documentSnapshot.get(TRANSACTION_HISTORIES) as MutableList<HashMap<String, Any>>
                    /*
                    Since the data collected from the online Database are not in the Original class
                    models but HashMaps, I looped through the list of HashMaps and assigned each
                    value to the appropriate property in the class Model
                    */
                    for (transactionHistoryHashMap in transactionHistoriesHashMaps) {

                        // History object has four properties; transactionCreationDate, amount, income, transactionName
                        // The goal is to get all these four properties from the Hashmap and create an actual
                        // History object with them.

                        // FOR [amount]
                        val transactionAmount = transactionHistoryHashMap["amount"].toString().toLong()
                        // FOR [income]
                        val isTransactionIncome = transactionHistoryHashMap["income"] as Boolean
                        // FOR [transactionName]
                        val transactionName = transactionHistoryHashMap["transactionName"] as String
                        // FOR [transactionCreationDate]
                        val transactionCreationDate = (transactionHistoryHashMap["transactionCreationDate"] as Timestamp).toDate()

                        //Creating the History Object
                        val historyObject = History(
                            transactionCreationDate = transactionCreationDate,
                            transactionName = transactionName,
                            income = isTransactionIncome,
                            amount = transactionAmount
                        )

                        // Add newly instantiated History object to list of history objects
                        historiesObjectList.add(historyObject)
                    }
                    _histories.value = historiesObjectList.sortedByDescending { incomeHistoryParam ->
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

        for(incomeHistory in historiesObjectList) {
            val dateThen = LocalDate.fromDateFields(incomeHistory.transactionCreationDate)
            if (dateNow.dayOfYear == dateThen.dayOfYear) {
                historiesByDay.add(incomeHistory)
            }
            _histories.value = historiesByDay.sortedByDescending { incomeHistoryParam ->
                incomeHistoryParam.transactionCreationDate
            }
        }
    }

    fun filterHistoriesByWeek() {
        val historiesByWeek = mutableListOf<History>()
        val dateNow = LocalDate.now()

        for(incomeHistory in historiesObjectList) {
            val dateThen = LocalDate.fromDateFields(incomeHistory.transactionCreationDate)
            if (dateNow.weekOfWeekyear == dateThen.weekOfWeekyear) {
                historiesByWeek.add(incomeHistory)
            }
            _histories.value = historiesByWeek.sortedByDescending { incomeHistoryParam ->
                incomeHistoryParam.transactionCreationDate
            }
        }
    }

    fun filterHistoriesByMonth() {
        val historiesByMonth = mutableListOf<History>()
        val dateNow = LocalDate.now()

        for(incomeHistory in historiesObjectList) {
            val dateThen = LocalDate.fromDateFields(incomeHistory.transactionCreationDate)
            if (dateNow.monthOfYear == dateThen.monthOfYear) {
                historiesByMonth.add(incomeHistory)
            }
            _histories.value = historiesByMonth.sortedByDescending { incomeHistoryParam ->
                incomeHistoryParam.transactionCreationDate
            }
        }
    }

}