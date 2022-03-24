package com.task.noteapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.task.noteapp.R
import com.task.noteapp.databinding.ActivityMainBinding
import com.task.noteapp.db.entities.Note
import com.task.noteapp.util.FabStyle
import com.task.noteapp.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.fab.setOnClickListener {
            when(viewModel.getFabStyle()) {
                FabStyle.CREATE -> {
                    viewModel.note = Note()
                    findNavController(R.id.nav_host_fragment)
                        .navigate(R.id.go_createOrEditFragment)
                }
                FabStyle.SAVE -> {
                    viewModel.saveOrUpdate()
                    findNavController(R.id.nav_host_fragment).popBackStack()
                }
            }
        }
    }

    private fun initObservers() {
        viewModel.fabStyle.observe(this) { style ->
            when(style) {
                FabStyle.CREATE -> binding.fab.setImageResource(R.drawable.ic_round_add)
                else -> binding.fab.setImageResource(R.drawable.ic_round_save)
            }
        }
    }
}