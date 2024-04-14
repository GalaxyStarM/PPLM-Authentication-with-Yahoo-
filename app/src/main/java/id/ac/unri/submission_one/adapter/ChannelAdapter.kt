package id.ac.unri.submission_one.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.unri.submission_one.data.Channel
import id.ac.unri.submission_one.ui.StreamingActivity
import id.ac.unri.submission_one.ui.StreamingActivity.Companion.EXTRA_VIDEO_URL
import id.ac.unri.submission_one.databinding.ItemChannelBinding

class ChannelAdapter (private val channelList: List<Channel>) : RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>(){

    inner class ChannelViewHolder(val binding: ItemChannelBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val binding = ItemChannelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChannelViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return channelList.size
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        val listChannel = channelList[position]
        holder.binding.apply {
            tvChannel.text = listChannel.nama

            root.setOnClickListener{
                Intent(root.context, StreamingActivity::class.java).also { intent ->
                    intent.putExtra(EXTRA_VIDEO_URL, listChannel.url)
                    root.context.startActivity(intent)
                }
            }
        }
    }
}