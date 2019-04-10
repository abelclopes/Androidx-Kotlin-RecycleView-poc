package eti.com.abellopes.ui.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import eti.com.abellopes.R
import eti.com.abellopes.repository.model.Heroi

class MarvelCustomAdapter(private val context: Context, private val dogsList: MutableList<Heroi>, private val listener: ItemClickListener) 
    : RecyclerView.Adapter<MarvelCustomAdapter.HeroiViewHolder>(), Filterable {
    private var itemsSearchList: List<Heroi>? = null
    inner class HeroiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameItemView: TextView
        var descriptionItemView: TextView
        var pictureItemView: ImageView
        init {
            nameItemView = view.findViewById(R.id.name)
            descriptionItemView = view.findViewById(R.id.description)
            pictureItemView = view.findViewById(R.id.picture)
            view.setOnClickListener {
                listener.onItemClicked(itemsSearchList!![adapterPosition])
            }
        }
    }
    init {
        this.itemsSearchList = dogsList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroiViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return HeroiViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: HeroiViewHolder, position: Int) {
        val dog = itemsSearchList!![position]
        holder.nameItemView.text = dog.name
        holder.descriptionItemView.text = dog.description
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(30))
        Glide.with(context)
            .load(dog.picture)
            .apply(requestOptions)
            .into(holder.pictureItemView)
    }
    override fun getItemCount(): Int {
        return itemsSearchList!!.size
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    itemsSearchList = dogsList
                } else {
                    val filteredList = ArrayList<Heroi>()
                    for (row in dogsList) {
                        if (row.name!!.toLowerCase().contains(charString.toLowerCase()) || row.description!!.contains(charSequence)) {
                            filteredList.add(row)
                        }
                    }
                    itemsSearchList = filteredList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = itemsSearchList
                return filterResults
            }
            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                itemsSearchList = filterResults.values as ArrayList<Heroi>
                notifyDataSetChanged()
            }
        }
    }
    interface ItemClickListener {
        fun onItemClicked(dog: Heroi)
    }
}