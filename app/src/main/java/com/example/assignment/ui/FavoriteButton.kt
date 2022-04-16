package com.example.assignment.ui

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import com.example.assignment.R
import com.example.assignment.state.State

class FavoriteButton(
    context: Context, attrs: AttributeSet
) : AppCompatImageButton(context, attrs) {
    init {
        setBackgroundResource(R.drawable.ic_favorite)
    }

    fun updateIconState(state: State) {
        when(state) {
            State.LIKE -> {
                setBackgroundResource(R.drawable.ic_like)
            }
            State.DISLIKE -> {
                setBackgroundResource(R.drawable.ic_favorite)
            }
        }
    }
}