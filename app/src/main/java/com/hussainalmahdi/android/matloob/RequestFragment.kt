package com.hussainalmahdi.android.matloob

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hussainalmahdi.android.zyara.R

class RequestFragment : Fragment() {
    private var token: String? = null

    private lateinit var button:FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token =  arguments?.getString("token")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_request, container, false)
        button=view.findViewById(R.id.floatingActionButton2)
        button.setOnClickListener {
            val fragment = NewRequestFragment()
            val args = Bundle().apply {
                putString("token",token)
            }
            fragment.arguments =args
            var fr = getFragmentManager()?.beginTransaction()
            fr?.replace(R.id.fragment_container,fragment )
            fr?.commit()
        }

        return view
    }
}