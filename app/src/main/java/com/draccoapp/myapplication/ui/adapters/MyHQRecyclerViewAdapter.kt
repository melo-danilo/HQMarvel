package com.draccoapp.myapplication.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.draccoapp.myapplication.api.models.Comic
import com.draccoapp.myapplication.databinding.FragmentItemBinding

interface HQItemListener{
    fun onItemSelected(position: Int)
}

class MyHQRecyclerViewAdapter(
    private val listener: HQItemListener
) : RecyclerView.Adapter<MyHQRecyclerViewAdapter.ViewHolder>() {

    private var values: List<Comic> = ArrayList()

    fun updateData(hqList: List<Comic>){
        values = hqList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bindItem(item)

        holder.view.setOnClickListener {
            listener.onItemSelected(position)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val view = binding.root

        fun bindItem(item: Comic){
            binding.hqItem = item
            binding.executePendingBindings()
        }
    }

}