package com.task.noteapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.task.noteapp.databinding.FragmentCreateOrEditBinding
import com.task.noteapp.util.FabStyle
import com.task.noteapp.viewmodel.MainActivityViewModel

class CreateOrEditFragment: Fragment() {

    private var binding: FragmentCreateOrEditBinding? = null

    private val activityModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateOrEditBinding.inflate(inflater, container, false)
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
        activityModel.setFabStyle(FabStyle.SAVE)
    }

    private fun initObservers() {

    }
}