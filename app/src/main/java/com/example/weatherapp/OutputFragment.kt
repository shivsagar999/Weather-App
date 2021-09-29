package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp.databinding.FragmentOutputBinding


class OutputFragment : Fragment() {



    private lateinit var _binding: FragmentOutputBinding

    private val binding get() = _binding
    private var mTextDisplay: String = ""

    //private var tempText : String = "Your temp has not yet received"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        Log.d("Wagle", " You are inside OnCreateView of OutputFragment")
        _binding = FragmentOutputBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Wagle", " You are inside OnViewCreated of OutputFragment")

        binding.textView.text = mTextDisplay


    }



}