package com.example.randomfilmk

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var movies: Array<String>
    private lateinit var tvTitle: TextView

    private var remaining = mutableListOf<Int>()
    private var shown = NONE

    companion object {
        const val NONE = -1
        const val END = -2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movies = resources.getStringArray(R.array.movies)
        tvTitle = findViewById(R.id.title)

        if (savedInstanceState != null) {
            remaining = savedInstanceState.getIntArray("remaining")!!.toMutableList()
            shown = savedInstanceState.getInt("shown")
        } else {
            resetMovies()
        }
        render()
    }

    fun onNextClick(view: View) {
        shown = if (remaining.isEmpty()) {
            END
        } else {
            remaining.removeAt(remaining.indices.random())
        }
        render()
    }

    fun onClearClick(view: View) {
        resetMovies()
        render()
    }

    private fun resetMovies() {
        remaining = movies.indices.toMutableList()
        shown = NONE
    }

    private fun render() {
        tvTitle.text = when (shown) {
            NONE -> getString(R.string.press_button)
            END -> getString(R.string.no_more)
            else -> movies[shown]
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntArray("remaining", remaining.toIntArray())
        outState.putInt("shown", shown)
    }
}