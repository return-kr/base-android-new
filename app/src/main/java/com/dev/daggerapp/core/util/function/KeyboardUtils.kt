package com.dev.daggerapp.core.util.function

import android.app.Activity
import android.content.Context
import android.os.IBinder
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import timber.log.Timber

object KeyboardUtils {
    private const val TAG: String = "KeyboardUtils -- "

    fun hideKeyboard(activity: Activity) {
        try {
            val view = activity.window.currentFocus ?: return
            val inputManager = activity
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, e.toString())
        }
    }

    fun closeKeyboard(c: Context, windowToken: IBinder?) {
        val mgr = c.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mgr.hideSoftInputFromWindow(windowToken, 0)
    }

    fun setupKeyboardDone(context: Context, editText: EditText?, cursorVisible: Boolean = true) {
        editText?.setOnEditorActionListener { _, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(editText, 0)
                editText.isCursorVisible = cursorVisible
            }
            false
        }
    }

    fun openKeyboardWhenFocus(context: Context, editable: EditText) {
        if (editable.requestFocus()) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editable, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}