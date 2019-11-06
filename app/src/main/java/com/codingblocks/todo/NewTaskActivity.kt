package com.codingblocks.todo

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_new_task.*
import java.text.SimpleDateFormat
import java.util.*

const val TASK_ID = "task_id"
const val TASK_TITLE = "task_title"
const val TASK_TASK = "task_task"

class NewTaskActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var myCalendar: Calendar

    lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    lateinit var timeSetListener: TimePickerDialog.OnTimeSetListener

    //Final variable to save in database
    private var finalDate = ""
    private var finalTime = ""
    private var finalTitle = ""
    private var finalTask = ""
    private var finalCategoryName = "Default"

    val db: AppDatabase by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "todo.db"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)

        setSupportActionBar(toolbarAddTask)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        
        saveBtn.setOnClickListener(this)
        dateEdt.setOnClickListener(this)
        timeEdt.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.dateEdt -> {
                dateAndTime()
                setDate()
            }
            R.id.timeEdt -> {
                dateAndTime()
                setTime()
            }
            R.id.saveBtn -> {
//                if (checkTask())
                    saveTask()
            }
        }
    }

    private fun checkTask(): Boolean {
        return false
    }

    private fun saveTask() {
        finalTitle = titleInpLay.editText?.text.toString().trim()
        finalTask = taskInpLay.editText?.text.toString().trim()

        if (finalTitle != "") {
            if (finalTask != "") {
                if (finalCategoryName != "") {
                    if (finalDate != "") {
                        if (finalTime != "") {
                            val id = db.taskDao().insertTask(
                                TaskModel(
                                    finalTitle,
                                    finalTask,
                                    finalCategoryName,
                                    finalDate,
                                    finalTime
                                )
                            )
                            myCalendar.set(Calendar.SECOND, 0)
                            setNotification(myCalendar, id) // Set notification
                        } else {

                        }
                    } else {

                    }
                } else {
                    //show toast
                }
            } else {
                //show toast
            }
        } else {
            //show toast
        }
    }

    private fun setNotification(myCalendar: Calendar, id: Long) {

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, AlarmReceiver::class.java)
        intent.putExtra(TASK_ID, id)
        intent.putExtra(TASK_TITLE, finalTitle)
        intent.putExtra(TASK_TASK, finalTask)

        val pendingIntent =
            PendingIntent.getBroadcast(this, 0/*taskRowId*/, intent, PendingIntent.FLAG_ONE_SHOT)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            myCalendar.timeInMillis,
            pendingIntent
        )

    }

    /**
     * current Date and Time initialize
     * */
    private fun dateAndTime() {

        myCalendar = Calendar.getInstance()

        dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabelDate()
            }

        timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            myCalendar.set(Calendar.MINUTE, minute)
            updateLabelTime()
        }

    }

    /**
     * @DatePickerDialog for selecting date
     * */
    private fun setDate() {

        val datePickerDialog = DatePickerDialog(
            this, dateSetListener, myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()

    }


    /**
     * @TimePickerDialog for selecting time
     * */
    private fun setTime() {
        val timePickerDialog = TimePickerDialog(
            this, timeSetListener, myCalendar.get(Calendar.HOUR_OF_DAY),
            myCalendar.get(Calendar.MINUTE), false
        )
        timePickerDialog.show()
    }

    /**
     * UI Update of time
     * */
    private fun updateLabelTime() {

        val myFormat = "HH:mm"  // HH:mm:ss
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        finalTime = sdf.format(myCalendar.time)


        val myFormat2 = "h:mm a"
        val sdf2 = SimpleDateFormat(myFormat2, Locale.US)
        timeEdt.setText(sdf2.format(myCalendar.time))
    }


    /**
     * UI Update of Date
     * */
    private fun updateLabelDate() {

        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        finalDate = sdf.format(myCalendar.time)


        val myFormat2 = "EEE, d MMM yyyy"
        val sdf2 = SimpleDateFormat(myFormat2, Locale.US)
        dateEdt.setText(sdf2.format(myCalendar.time))

        timeInptLay.visibility = View.VISIBLE
    }
}
