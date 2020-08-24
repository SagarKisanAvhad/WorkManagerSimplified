package com.sagar.workmanagersimplified

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = Data.Builder().putString(EXTRA_TASK_DESC, "data description").build()
        val constraint = Constraints.Builder().setRequiresCharging(true).build()

        val request = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInputData(data)
            .setConstraints(constraint)
            .build()

        btn_perform.setOnClickListener {
            WorkManager.getInstance(this).enqueue(request)
        }

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.id).observe(this, Observer {

            if (it.state == WorkInfo.State.SUCCEEDED) {
                val data = it.outputData
            }
            val status = it.state.name
            tv_status.append(status + "\n")
        })
    }

    companion object {
        const val EXTRA_TASK_DESC = "extra_task_desc"
        const val EXTRA_OUTPUT = "extra_output"
    }
}