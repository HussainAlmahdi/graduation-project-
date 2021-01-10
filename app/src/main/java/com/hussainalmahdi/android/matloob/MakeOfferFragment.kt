package com.hussainalmahdi.android.matloob

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.hussainalmahdi.android.zyara.R


class MakeOfferFragment : Fragment() {

    private lateinit var makeOfferButton:TextView
    private lateinit var orderText:TextView
    private lateinit var offerOwnerImage:ImageView
    private lateinit var offerName:TextView

    private var owner:String? =""
    private var userName:String? =""
    private var description:String? =""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        owner = arguments?.getString("owner")
        userName =arguments?.getString("userName")
        description=arguments?.getString("description")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_make_offer, container, false)
        makeOfferButton=view.findViewById(R.id.make_offer)
        orderText=view.findViewById(R.id.order)
        offerName =view.findViewById(R.id.offer_name)
        offerOwnerImage = view.findViewById(R.id.offer_owner_image)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("description",description)
        orderText.text = description
        offerName.text =userName
        Glide.with(offerOwnerImage).load("https://matloob.herokuapp.com/user/logo/"+owner).centerCrop().into(offerOwnerImage)



        makeOfferButton.setOnClickListener {
            // go to chat
        }


    }

}