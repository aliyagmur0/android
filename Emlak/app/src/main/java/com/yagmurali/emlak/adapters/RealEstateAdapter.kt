package com.yagmurali.emlak.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yagmurali.emlak.R
import com.yagmurali.emlak.activities.AddNewMenkulActivity
import com.yagmurali.emlak.activities.MainActivity
import com.yagmurali.emlak.models.RealEstateModel

open class RealEstateAdapter (
    private val context: Context,
    private var list: ArrayList<RealEstateModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener : OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_real_estate,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            holder.bindItems(list[position])
            holder.itemView.setOnClickListener{
                if (onClickListener != null) {
                    onClickListener!!.onClick(position,  list[position])
                }
            }
        }
    }

//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val model = list[position]
//
//        if (holder is MyViewHolder) {
//            holder.itemView.iv_place_image.setImageURI(Uri.parse(model.image))
//            holder.itemView.tvTitle.text = model.title
//            holder.itemView.tvDescription.text = model.description
//        }
//    }

    override fun getItemCount(): Int {
        return list.size
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bindItems(itemModel : RealEstateModel) {
            val image = itemView.findViewById(R.id.iv_place_image) as ImageView
            val description = itemView.findViewById(R.id.tvDescription) as TextView
            val title = itemView.findViewById(R.id.tvTitle) as TextView

            description.text= itemModel.description
            image.setImageURI(Uri.parse(itemModel.image))
            title.text = itemModel.title
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model : RealEstateModel)
    }

    fun notifyEditItem(activity: Activity, position: Int, requestCode: Int) {
        val intent = Intent(context, AddNewMenkulActivity::class.java)
        intent.putExtra(MainActivity.EXTRA_PLACE_DETAILS, list[position])
        activity.startActivityForResult(intent, requestCode)
        notifyItemChanged(position)
    }
}
