package com.example.edupulse.ui.home.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import com.example.edupulse.R
import com.example.edupulse.databinding.AlertdialogTasksBinding
import com.example.edupulse.databinding.FragmentTasksBinding
import com.example.edupulse.ui.home.tasks.adapter.TasksAdapter

class TasksFragment : Fragment() {
    private lateinit var binding:FragmentTasksBinding
    private lateinit var adapter: TasksAdapter
    private lateinit var list: ArrayList<Int>
    private lateinit var alertDialog:AlertDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentTasksBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLoad()
        initAlertDialog()
        initAdapter()
    }

    private fun initAlertDialog() {
        val alertDialogBuilder=AlertDialog.Builder(requireContext(),R.style.CustomAlertDialogStyle)
        val alertBinding=AlertdialogTasksBinding.inflate(layoutInflater)
        alertDialogBuilder.setView(alertBinding.root)
        alertBinding.btnNext.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog=alertDialogBuilder.create()
    }

    private fun OnClick(possition: Int) {
        val anim: Animation = AnimationUtils.loadAnimation(requireContext(),R.anim.btn_anim_image_button)
        binding.rvTasks.getChildAt(possition).startAnimation(anim)
    }

    private fun initLoad() {
        list= ArrayList()
        list.add(R.drawable.ic_star)
        list.add(R.drawable.ic_star)
        list.add(R.drawable.ic_star)
        list.add(R.drawable.ic_star)
        list.add(R.drawable.ic_star)
        list.add(R.drawable.ic_star)
        list.add(R.drawable.ic_star)
        list.add(R.drawable.ic_star)
        list.add(R.drawable.ic_star)
        list.add(R.drawable.ic_star)
        list.add(R.drawable.ic_star)
        list.add(R.drawable.ic_star)

    }

    private fun initAdapter() {
        adapter= TasksAdapter(list,this::OnClick)
        alertDialog.show()
        binding.rvTasks.adapter=adapter
    }

}