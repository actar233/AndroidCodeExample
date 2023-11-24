package io.github.zhaofanzhe.example.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle


abstract class BaseActivity : Activity() {

    private var activityResult: ActivityResult? = null

    fun startActivityForResult(intent: Intent?, activityResult: ActivityResult) {
        startActivityForResult(intent, null, activityResult)
    }

    fun startActivityForResult(intent: Intent?, options: Bundle?, activityResult: ActivityResult) {
        this.activityResult = activityResult
        super.startActivityForResult(intent, 0, options)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        activityResult?.invoke(resultCode, data)
        activityResult = null
    }

}