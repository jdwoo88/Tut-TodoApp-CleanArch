package app.jwoo.myapplication.fragments.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.jwoo.myapplication.R
import app.jwoo.myapplication.data.models.ToDoData
import app.jwoo.myapplication.data.viewmodel.ToDoViewModel
import app.jwoo.myapplication.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        setHasOptionsMenu(true)

        view.update_txtTitle.setText(args.currentToDoItem.title)
        view.update_txtDescription.setText(args.currentToDoItem.description)
        view.update_spinnerPriorities.setSelection(mSharedViewModel.parsePriority(args.currentToDoItem.priority))
        view.update_spinnerPriorities.onItemSelectedListener = mSharedViewModel.listener

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_save) {
            updateItem()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val mTitle = update_txtTitle.text.toString()
        val mPriority = update_spinnerPriorities.selectedItem.toString()
        val mDescription = update_txtDescription.text.toString()

        if (mSharedViewModel.verifyDataFromUser(mTitle, mDescription)) {
            mToDoViewModel.updateData(
                ToDoData(
                    args.currentToDoItem.id,
                    mTitle,
                    mSharedViewModel.parsePriority(mPriority),
                    mDescription
                )
            )
            Toast.makeText(requireContext(), "Successfully Updated", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
                .show()
        }
    }
}