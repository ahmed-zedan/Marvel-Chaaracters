package com.zedan.example.marvelcharacters.widget

import android.content.Context
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.zedan.example.marvelcharacters.R

class CollapseView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var expanded = true
    private val titleView: TextView
    private val expandIcon: ImageView
    private val container: View
    private var toggle: Transition? = null
    private val root: View
    private val title: String?

    init {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.CollapseView, 0, 0)
        title = arr.getString(R.styleable.CollapseView_title)
        arr.recycle()
        root = LayoutInflater.from(context)
            .inflate(R.layout.collapse_view, this, true)

        container = root.findViewById(R.id.collapse_view_root)

        titleView = root.findViewById<TextView>(R.id.collapse_view_title).apply {
            text = title
        }

        expandIcon = root.findViewById(R.id.collapse_view_icon)
        toggle = TransitionInflater.from(context)
            .inflateTransition(R.transition.info_card_toggle)
    }

    private var view : View? = null
    fun setChildView(expandView: View?) {
        view = expandView
        container.setOnClickListener {
            view?.let {
                expanded = !expanded
                toggle?.duration = if (!expanded) 300L else 200L
                TransitionManager.beginDelayedTransition(root.parent as ViewGroup, toggle)
                it.visibility = if (expanded) View.VISIBLE else View.GONE
                expandIcon.rotationX = if (!expanded) 180f else 0f
            }
        }
    }

    fun setTitle(title: String){
        titleView.text = title
    }

}