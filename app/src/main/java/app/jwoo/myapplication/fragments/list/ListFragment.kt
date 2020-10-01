package app.jwoo.myapplication.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import app.jwoo.myapplication.R
import app.jwoo.myapplication.data.viewmodel.ToDoViewModel
import app.jwoo.myapplication.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val recyclerView = view.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { dataList ->
            mSharedViewModel.checkIfDbEmpty(dataList)
            adapter.setData(dataList)
        })

        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer { isEmpty ->
            showEmptyDatabase(isEmpty)
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        // Set Menu
        setHasOptionsMenu(true)

        return view
    }

    private fun showEmptyDatabase(isEmpty: Boolean) {
        view?.no_data_image?.isVisible = isEmpty
        view?.no_data_textView?.isVisible = isEmpty
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_deletAll -> {
                deleteAllItem()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllItem() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mToDoViewModel.deleteAll()

            Toast.makeText(
                requireContext(),
                "All items have been deleted.",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Confirm Delete")
        builder.setMessage("Are you sure you want to delete all items?")
        builder.create().show()
    }
}