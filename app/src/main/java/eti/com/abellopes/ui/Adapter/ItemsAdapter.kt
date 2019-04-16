//@file:JvmName("ItemsAdapter")
package eti.com.abellopes.ui.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import eti.com.abellopes.databinding.ItemViewBinding
import androidx.recyclerview.widget.RecyclerView
import eti.com.abellopes.R
import eti.com.abellopes.repository.model.Heroi
import kotlinx.android.synthetic.main.loading_view.view.*
import java.util.*
import android.widget.TextView
import android.text.Spannable
import android.text.style.RelativeSizeSpan
import android.text.style.BackgroundColorSpan
import android.graphics.Typeface
import android.text.style.TextAppearanceSpan
import android.content.res.ColorStateList
import android.text.SpannableStringBuilder
import java.util.regex.Pattern
import android.graphics.Color
import android.widget.ImageView


class ItemsAdapter(private var onItemClicked: (Heroi) -> Unit) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    private var items: List<Heroi> = emptyList()
    private var itemsSearchList: List<Heroi>? = emptyList()
    lateinit var searchText: String

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
            holder.bind(items[position],searchText, onItemClicked)
        }
    }

    fun update(items: List<Heroi>, searchText: String? = "") {
        this.searchText = searchText!!
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
        var nameItemView: TextView
        var descriptionItemView: TextView
        var pictureItemView: ImageView
        init {
            nameItemView = binding.name
            descriptionItemView = binding.description
            pictureItemView = binding.picture
        }
        fun bind(item: Heroi, searchText: String?, onItemClicked: (Heroi)-> Unit) {
            setAdvancedDetailsHighlight(binding.name, item.name!!, searchText)
            binding.description.text = item.description
            binding.root.setOnClickListener {
                onItemClicked(item)
            }
        }
        private fun setAdvancedDetailsHighlight(textView: TextView, fullText: String, search: String?) {
            val searchText: String = search!!.replace("'", "")

            // highlight search text
            if (!searchText.isEmpty()) {

                val wordSpan = SpannableStringBuilder(fullText)
                val p = Pattern.compile(searchText, Pattern.CASE_INSENSITIVE)
                val m = p.matcher(fullText)
                while (m.find()) {

                    val wordStart = m.start()
                    val wordEnd = m.end()

                    // Now highlight based on the word boundaries
                    val redColor = ColorStateList(arrayOf(intArrayOf()), intArrayOf(Color.BLACK))//intArrayOf(-0x000000)
                    val highlightSpan = TextAppearanceSpan(null, Typeface.BOLD, -1, redColor, null)

                    wordSpan.setSpan(highlightSpan, wordStart, wordEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    wordSpan.setSpan(
                        BackgroundColorSpan(Color.WHITE),
                        wordStart,
                        wordEnd,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    wordSpan.setSpan(RelativeSizeSpan(1.25f), wordStart, wordEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                }

                textView.setText(wordSpan, TextView.BufferType.SPANNABLE)

            } else {
                textView.text = fullText
            }
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

            update(tempNameVersionList, text)


        } else {
            for (i in 0..tempNameVersionList.size - 1) {
                if (tempNameVersionList.get(i).name!!.toLowerCase(Locale.getDefault()).contains(text)) {
                    update(tempNameVersionList.filter { children -> children.name!!.toLowerCase(Locale.getDefault()).contains(text)},text)
                }
            }
        }
        //This is to notify that data change in Adapter and Reflect the changes.
        notifyDataSetChanged()
    }


}