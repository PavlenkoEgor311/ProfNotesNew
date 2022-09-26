package com.example.profnotes.core

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs
import kotlin.math.max

class CardRecommendationPageTransformer : ViewPager2.PageTransformer {

    private var fadeValue = 0.5f
    private var maxScale = 0.1f

    override fun transformPage(page: View, position: Float) {
        val absPos = abs(position)
        val scale = 1 + maxScale * (1 - absPos)

        page.apply {
            translationX = absPos * 100f
            scaleX = scale
            scaleY = scale
        }

        page.alpha = when {
            position < -1 -> {
                fadeValue
            }
            position <= 1 -> {
                max(fadeValue, 1 - absPos)
            }
            else -> {
                fadeValue
            }
        }
    }
}