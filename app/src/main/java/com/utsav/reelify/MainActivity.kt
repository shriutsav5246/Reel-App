package com.utsav.reelify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ReelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPagerReels)

        val reels = listOf(
            ReelVideo(R.raw.video1),
            ReelVideo(R.raw.video2),
            ReelVideo(R.raw.video3),
            ReelVideo(R.raw.video4),
            ReelVideo(R.raw.video5),
            ReelVideo(R.raw.video6),
            ReelVideo(R.raw.video7),
            ReelVideo(R.raw.video8),
            ReelVideo(R.raw.video9),
            ReelVideo(R.raw.video10),
            ReelVideo(R.raw.video11),
            ReelVideo(R.raw.video12),
            ReelVideo(R.raw.video13),
            ReelVideo(R.raw.video14),
            ReelVideo(R.raw.video15),
            ReelVideo(R.raw.video16),
            ReelVideo(R.raw.video17),
            ReelVideo(R.raw.video18),
            ReelVideo(R.raw.video19)
        )

        adapter = ReelAdapter(reels)

        viewPager.orientation =
            ViewPager2.ORIENTATION_VERTICAL

        viewPager.adapter = adapter

        viewPager.post {

            val recyclerView =
                viewPager.getChildAt(0) as RecyclerView

            adapter.playVideo(recyclerView, 0)
        }

        viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {

                    val recyclerView =
                        viewPager.getChildAt(0) as RecyclerView

                    adapter.playVideo(
                        recyclerView,
                        position
                    )
                }
            }
        )
    }

    override fun onPause() {
        super.onPause()
        adapter.releasePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.releasePlayer()
    }
}