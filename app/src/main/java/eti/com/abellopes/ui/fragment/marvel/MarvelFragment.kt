package eti.com.abellopes.ui.fragment.marvel


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import eti.com.abellopes.R
import androidx.recyclerview.widget.LinearLayoutManager
import eti.com.abellopes.databinding.FragmentMarvelBinding
import eti.com.abellopes.ui.Adapter.ItemsAdapter
import eti.com.abellopes.ui.subscribe
import kotlinx.android.synthetic.main.fragment_marvel.*






class MarvelFragment : Fragment() {

    companion object {
        fun newInstance() = MarvelFragment()
    }
    private lateinit var binding: FragmentMarvelBinding
    private lateinit var viewModel: MarvelViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_marvel, container, false)

        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MarvelViewModel::class.java)

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.hasFixedSize()
        var customAdapter = ItemsAdapter()
        recyclerView.adapter =  customAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))

        addItensRecycle()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                val text = newText
                /*Call filter Method Created in Custom Adapter
                    This Method Filter ListView According to Search Keyword
                 */

                customAdapter.filter(text)
                return false
            }
        })
        searchView.setOnCloseListener( object : SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                Log.d("TAG", "CLOSE")
                addItensRecycle()
                return false
            }
        })


        binding.viewModel = viewModel
    }

    private fun addItensRecycle() {
        recyclerView.marvel { viewModel.fetchItems() }

        viewModel.addItems.subscribe(this) {
            (recyclerView.adapter as ItemsAdapter).notifyItemRangeInserted(data.positionStart, data.count)
        }
        viewModel.removeItem.subscribe(this) {
            (recyclerView.adapter as ItemsAdapter).notifyItemRemoved(data)
        }

    }
}