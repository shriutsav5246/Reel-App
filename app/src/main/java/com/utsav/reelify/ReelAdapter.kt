package com.utsav.reelify

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView

class ReelAdapter(
    private val reels: List<ReelVideo>
) : RecyclerView.Adapter<ReelAdapter.ReelViewHolder>() {

    private var currentPlayer: ExoPlayer? = null
    private var currentPosition = -1

    inner class ReelViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val playerView: PlayerView =
            itemView.findViewById(R.id.playerView)

        val touchOverlay: View =
            itemView.findViewById(R.id.touchOverlay)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReelViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.reel_item, parent, false)

        return ReelViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ReelViewHolder,
        position: Int
    ) {
        holder.playerView.player = null
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    fun playVideo(
        recyclerView: RecyclerView,
        position: Int
    ) {

        if (position == currentPosition) return

        currentPlayer?.release()

        val holder =
            recyclerView.findViewHolderForAdapterPosition(position)
                    as? ReelViewHolder ?: return

        val reel = reels[position % reels.size]

        val uri = Uri.parse(
            "android.resource://${holder.itemView.context.packageName}/${reel.videoResId}"
        )

        currentPlayer =
            ExoPlayer.Builder(holder.itemView.context).build()

        holder.playerView.player = currentPlayer

        currentPlayer?.apply {

            setMediaItem(MediaItem.fromUri(uri))

            repeatMode = ExoPlayer.REPEAT_MODE_ONE

            prepare()

            playWhenReady = true
        }

        holder.touchOverlay.setOnClickListener {

            currentPlayer?.let { player ->

                if (player.isPlaying) {
                    player.pause()
                } else {
                    player.play()
                }
            }
        }

        currentPosition = position
    }

    fun releasePlayer() {
        currentPlayer?.release()
        currentPlayer = null
    }
}