package com.example.teamproject_hometrainingassistant_app.ui.community

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamproject_hometrainingassistant_app.databinding.ItemCommunityBinding
import com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard.NoticeBoardActivity
import com.example.teamproject_hometrainingassistant_app.ui.dashboard.CalendarRoutineActivity

class CommunityAdapter(private val context: CommunityFragment) :
    RecyclerView.Adapter<CommunityAdapter.ViewHolder>() {

    var datas = mutableListOf<CommunityData>()
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding = ItemCommunityBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(private val binding: ItemCommunityBinding) : RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context

        fun bind(item: CommunityData){
            Glide.with(itemView).load(item.img).into(binding.communityImageView) // 이미지 연결에 용이한 Glide 라이브러리 사용
            binding.communityButton.text = item.text

            binding.communityButton.setOnClickListener {
                val intent = Intent(context, NoticeBoardActivity::class.java)
                intent.run { context.startActivity(this) }
            }
        }
    }
}