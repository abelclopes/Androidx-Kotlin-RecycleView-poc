package eti.com.abellopes.ui.fragment.marvel

import android.os.Handler
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eti.com.abellopes.repository.ItemsRepository
import eti.com.abellopes.repository.model.Heroi
import eti.com.abellopes.ui.Adapter.ItemsAdapter
import eti.com.abellopes.ui.Event


data class Page(val positionStart: Int, val count: Int)

class MarvelViewModel : ViewModel() {

    private val repository = ItemsRepository()
    val list = mutableListOf<Heroi>()

    private val _items = MutableLiveData<List<Heroi>>().apply { value = list }
    private val _addItems = MutableLiveData<Event<Page>>()
    private val _removeItem = MutableLiveData<Event<Int>>()

    private val handler = Handler()

    private var loading = false

    private var delay = 0L

    val items: LiveData<List<Heroi>>
        get() = _items

    val addItems: LiveData<Event<Page>>
        get() = _addItems

    val removeItem: LiveData<Event<Int>>
        get() = _removeItem

    init {
        fetchItems()
    }

    fun fetchItems() {
        if (!loading) {
            loading = true

            showLoading()

            // Simulate delay

            handler.postDelayed({

                val position = list.size
                val newItems = repository.getItemsPage()
                list.addAll(newItems)

                addItems(position, newItems.size)
                removeLoading(position - 1)

                delay = if (delay == 0L) 3000 else 0
                loading = false
            }, delay)
        }
    }
    fun fetchMoreItems(customAdapter: ItemsAdapter, newText: String?) {
        if (!loading) {
            loading = true
            //customAdapter.update(mutableListOf())
            showLoading()

            // Simulate delay

            handler.postDelayed({

                val position = list.size
                val newItems = repository.getItemsPage()
                list.addAll(newItems)

                addItems(position, newItems.size)
                removeLoading(position - 1)
                customAdapter.update(list, newText!!.toLowerCase().trim())

                delay = if (delay == 0L) 3000 else 0
                loading = false
            }, delay)
        }
    }

    private fun showLoading() {
        list.add(
            Heroi(
                id = list.size + 1,
                name = "",
                description = "",
                picture = "",
                loading = "loading"
            )
        )
        addItems(list.size - 1, 1)
    }

    private fun removeLoading(position: Int) {
        list.removeAt(position)
        removeItems(position)
    }

    private fun addItems(positionStart: Int, count: Int) {
        _addItems.value = Event(Page(positionStart, count))
    }

    private fun removeItems(positionStart: Int) {
        _removeItem.value = Event(positionStart)
    }
}


abstract class DelayedOnQueryTextListener : SearchView.OnQueryTextListener {

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onQueryTextSubmit(s:String):Boolean {
        return false;
    }

    override fun onQueryTextChange(s: String): Boolean  {
        handler.removeCallbacks(runnable)
        runnable.let {
            onDelayerQueryTextChange(s)
        }
        handler.postDelayed(runnable, 400);
        return true
    }

    abstract fun onDelayerQueryTextChange(query: String)
}