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
import com.bumptech.glide.Glide
import com.hussainalmahdi.android.zyara.R


class ProfileFragment : Fragment() {
    private var name: String? = null
    private var phone:String?=null
    private var email: String? = null
    private var description:String? =null
    private var token:String? =null

    private lateinit var becomeProviderButton: Button
    private lateinit var profileName:TextView
    private lateinit var profilePhone:TextView
    private lateinit var profileDescription:TextView
    private lateinit var profileEmail:TextView
    private lateinit var profileImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name =  arguments?.getString("name")
        phone = arguments?.getString("phone")
        email =  arguments?.getString("email")
        description=  arguments?.getString("description")
        token=  arguments?.getString("token")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        profileName=view.findViewById(R.id.profile_name)
        profilePhone=view.findViewById(R.id.profile_phone)
        profileDescription=view.findViewById(R.id.profile_description)
        profileEmail=view.findViewById(R.id.profile_email)
        profileImageView =view.findViewById(R.id.profile_image)

        profileName.text =name
        profilePhone.text = phone.toString()
        profileDescription.text =email
        profileEmail.text =description
       // Glide.with(profileImageView).load().into(profileImageView)

        becomeProviderButton= view.findViewById(R.id.provider)
        becomeProviderButton.setOnClickListener {
            val fragment = ProviderDetailFragment()
            val args = Bundle().apply {
                putString("token", token)
            }
            fragment.arguments = args
            var fr = getFragmentManager()?.beginTransaction()
       //     fr?.replace(R.id.fragment, ProviderDetailFragment())
            fr?.replace(R.id.fragment_container,fragment )
            fr?.commit()
        }



        return view
    }

}