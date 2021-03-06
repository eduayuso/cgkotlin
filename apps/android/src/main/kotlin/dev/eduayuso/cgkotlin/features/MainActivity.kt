package dev.eduayuso.cgkotlin.features

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import dev.eduayuso.cgkotlin.R
import dev.eduayuso.cgkotlin.features.convexhull.ConvexHullActivity
import dev.eduayuso.cgkotlin.features.segmentintersection.SegmentIntersectionActivity

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onConvexHullButtonClick(view: View) {

        Intent(this, ConvexHullActivity::class.java).let {
            intent -> startActivity(intent)
        }
    }

    fun onSegmentIntersectionButtonClick(view: View) {

        Intent(this, SegmentIntersectionActivity::class.java).let {
                intent -> startActivity(intent)
        }
    }
}