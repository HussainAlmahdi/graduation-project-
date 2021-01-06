package com.hussainalmahdi.android.matloob

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.hussainalmahdi.android.zyara.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var becomeProviderButton: Button
    private lateinit var location:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        becomeProviderButton= view.findViewById(R.id.provider)
        location=view.findViewById(R.id.location)
        becomeProviderButton.setOnClickListener {
            var fr = getFragmentManager()?.beginTransaction()
       //     fr?.replace(R.id.fragment, ProviderDetailFragment())
            fr?.replace(R.id.fragment_container, ProviderDetailFragment())
            fr?.commit()
        }
        location.setOnClickListener{
            var fr = getFragmentManager()?.beginTransaction()
            //     fr?.replace(R.id.fragment, MapsFragment())
            fr?.replace(R.id.fragment_container, MapsFragment())
            fr?.commit()
        }


        return view
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}