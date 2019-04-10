//@file:JvmName("ItemsAdapter")
package eti.com.abellopes.ui.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import eti.com.abellopes.databinding.ItemViewBinding
import androidx.recyclerview.widget.RecyclerView
import eti.com.abellopes.R
import eti.com.abellopes.repository.model.Heroi
import kotlinx.android.synthetic.main.loading_view.view.*
import java.util.*

import kotlin.collections.ArrayList

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ViewHolder>(), Filterable {

    private var items: List<Heroi> = emptyList()
    private var itemsSearchList: List<Heroi>? = emptyList()

    private val loading = 0
    private val item = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == loading) {
            LoadingViewHolder(parent)
        } else {
            ItemViewHolder(parent)
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is ItemViewHolder && items.size > position) {
            holder.bind(items[position])
        }
    }

    fun update(items: List<Heroi>) {
        this.items = items
        notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        @BindingAdapter("items")
        fun RecyclerView.bindItems(items: List<Heroi>) {
            val adapter = adapter as ItemsAdapter
            adapter.update(items)
        }
    }

    override fun getItemViewType(position: Int) =
        if (items[position].loading == "loading") loading else item

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class ItemViewHolder(
        private val parent: ViewGroup,
        private val binding: ItemViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_view,
            parent,
            false
        )
    ) : ViewHolder(binding.root) {

        fun bind(item: Heroi) {
            binding.item = item
        }
    }

    class LoadingViewHolder(
        private val parent: ViewGroup,
        view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.loading_view,
            parent,
            false
        )
    ) :
        ViewHolder(view) {

        init {
            view.progressBar.post { view.progressBar }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    itemsSearchList = items
                } else {
                    val filteredList = ArrayList<Heroi>()
                    for (row in items) {
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

    private var tempNameVersionList: List<Heroi> = mutableListOf()

    fun filter(text: String?) {


        //Our Search text
        val text = text!!.toLowerCase()

        tempNameVersionList = items
        //Here We Clear Both ArrayList because We update according to Search query.
        itemsSearchList = mutableListOf()


        if (text.length == 0) {

            /*If Search query is Empty than we add all temp data into our main ArrayList

            We store Value in temp in Starting of Program.

            */

            update(tempNameVersionList)


        } else {
            for (i in 0..tempNameVersionList.size - 1) {
                if (tempNameVersionList.get(i).name!!.toLowerCase(Locale.getDefault()).contains(text)) {
                    update(tempNameVersionList.filter { children -> children.name!!.toLowerCase(Locale.getDefault()).contains(text)})
                }
            }
        }
        //This is to notify that data change in Adapter and Reflect the changes.
        notifyDataSetChanged()
    }
}