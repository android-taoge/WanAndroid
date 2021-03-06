package com.example.jetpackpaging.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.jetpackpaging.R
import kotlinx.android.synthetic.main.fragment_project_list.*


/**
 * A simple [Fragment] subclass.
 * Use the [ProjectListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProjectListFragment : Fragment() {


    companion object {
        @JvmStatic
        fun newInstance() =
            ProjectListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_nav.setOnClickListener {
            //findNavController().navigate(R.id.action_projectListFragment_to_aboutActivity)
            //startActivity(Intent(requireActivity(), AboutActivity::class.java))
        }
    }


}