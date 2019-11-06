package com.codingblocks.todo

import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mArrayList: ArrayList<TaskModel> = ArrayList()
    lateinit var taskAdapter: TaskAdapter

    lateinit var mItemTouchHelper: ItemTouchHelper

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
        setContentView(R.layout.activity_main)

        recyclerViewTask.setHasFixedSize(true)
        recyclerViewTask.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(mArrayList)
        recyclerViewTask.adapter = taskAdapter

        initSwipe()

        db.taskDao().getTask(TASK_IS_NOT_FINISH).observe(this, Observer {
            if (it.isNotEmpty()){
                mArrayList = it as ArrayList<TaskModel>
                taskAdapter.setList(mArrayList)
            }else {
                mArrayList = it as ArrayList<TaskModel>
                taskAdapter.setList(mArrayList)
            }
            txtNoTask.isVisible = it.isEmpty()
        })

        fabAddTask.setOnClickListener {
            startActivity(Intent(this, NewTaskActivity::class.java))
        }
    }
    //Dont Explain this code
    private fun initSwipe() {

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                if (direction == ItemTouchHelper.LEFT) {
                    db.taskDao().deleleTask(taskAdapter.getItemId(position))
                    taskAdapter.notifyItemRemoved(position)
                } else {
                    db.taskDao().finishTask(taskAdapter.getItemId(position))
                    taskAdapter.notifyItemRemoved(position)
                }
            }

            override fun onChildDraw(canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    val itemView = viewHolder.itemView

                    val paint = Paint()
                    val iconBitmap: Bitmap

                    if (dX > 0) {

                        iconBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_check_white_png)

                        paint.color = Color.parseColor(getString(R.color.green))

                        canvas.drawRect(itemView.left.toFloat(), itemView.top.toFloat(),
                            itemView.left.toFloat() + dX, itemView.bottom.toFloat(), paint)

                        // Set the image icon for Right side swipe
                        canvas.drawBitmap(iconBitmap,
                            itemView.left.toFloat() + convertDpToPx(16),
                            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - iconBitmap.height.toFloat()) / 2,
                            paint)
                    } else {

                        iconBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_delete_white_png)

                        paint.color = Color.parseColor(getString(R.color.red))

                        canvas.drawRect(itemView.right.toFloat() + dX, itemView.top.toFloat(),
                            itemView.right.toFloat(), itemView.bottom.toFloat(), paint)

                        //Set the image icon for Left side swipe
                        canvas.drawBitmap(iconBitmap,
                            itemView.right.toFloat() - convertDpToPx(16) - iconBitmap.width,
                            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - iconBitmap.height.toFloat()) / 2,
                            paint)
                    }

                    val ALPHA_FULL: Float = 1.0f

                    // Fade out the view as it is swiped out of the parent's bounds
                    val alpha: Float = ALPHA_FULL - Math.abs(dX) / viewHolder.itemView.width.toFloat()
                    viewHolder.itemView.alpha = alpha
                    viewHolder.itemView.translationX = dX

                } else {
                    super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewTask)
    }


    private fun convertDpToPx(dp: Int): Int {
        return Math.round(dp * (resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }
}
