package com.example.rappitest.utils

import android.content.ContextWrapper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class Bindings {

    companion object {
        fun getParentActivity(view: View): AppCompatActivity? {
            var context = view.context
            while (context is ContextWrapper) {
                if (context is AppCompatActivity) {
                    return context
                }
                context = context.baseContext
            }
            return null
        }
    }

    @BindingAdapter("mutableVisibility")
    fun mutableVisibility(view: View, visibility: LiveData<Boolean>) {
        val parentActivity: AppCompatActivity? = getParentActivity(view)
        if (parentActivity != null) {
            visibility.observe(
                parentActivity,
                Observer { value -> view.visibility = if (value) View.VISIBLE else View.GONE }
            )
        }
    }


}