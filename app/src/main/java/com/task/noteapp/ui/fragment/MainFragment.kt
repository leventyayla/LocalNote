package com.task.noteapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialSharedAxis
import com.task.noteapp.R
import com.task.noteapp.adapter.NoteRecyclerViewAdapter
import com.task.noteapp.databinding.FragmentMainBinding
import com.task.noteapp.db.entities.Note
import com.task.noteapp.util.FabStyle
import com.task.noteapp.viewmodel.MainActivityViewModel

class MainFragment: Fragment() {

    private var binding: FragmentMainBinding? = null

    private val activityModel: MainActivityViewModel by activityViewModels()

    private val mAdapter: NoteRecyclerViewAdapter by lazy {
        NoteRecyclerViewAdapter(listener = {
            activityModel.note = it
            findNavController().navigate(R.id.go_createOrEditFragment)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding?.notes?.adapter = mAdapter
        activityModel.setFabStyle(FabStyle.CREATE)
    }

    private fun initObservers() {
        activityModel.getAllNotes().observe(viewLifecycleOwner) {
            mAdapter.setData(it)
            binding?.status?.apply {
                val transition = MaterialSharedAxis(MaterialSharedAxis.Z,true)
                TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
                visibility = if (it.isEmpty()) {
                    text = getString(R.string.empty_list)
                    View.VISIBLE
                } else View.GONE
            }
        }
    }
}