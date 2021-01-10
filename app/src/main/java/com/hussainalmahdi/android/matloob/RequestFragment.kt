package com.hussainalmahdi.android.matloob

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hussainalmahdi.android.matloob.remotesource.MyRequests
import com.hussainalmahdi.android.matloob.remotesource.RemoteSource
import com.hussainalmahdi.android.matloob.remotesource.TimeLineItem
import com.hussainalmahdi.android.zyara.R

class RequestFragment : Fragment() {
    private var token: String? = null
    private lateinit var pendingRecyclerView: RecyclerView
    private lateinit var doneRequestslineRecyclerview: RecyclerView
    private lateinit var button:FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token =  arguments?.getString("token")
        RemoteSource().getMyRequests(token!!)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_request, container, false)
        button=view.findViewById(R.id.floatingActionButton2)
        pendingRecyclerView=view.findViewById(R.id.pending_requests)
        doneRequestslineRecyclerview = view.findViewById(R.id.previous_requests)
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pendingRecyclerView.layoutManager = LinearLayoutManager(activity)
        doneRequestslineRecyclerview.layoutManager = LinearLayoutManager(activity)

        RemoteSource().getMyRequests(token!!).observe(
            viewLifecycleOwner, Observer { requests->
                for (request in requests){
                    if (!request.closed){
                        pendingRecyclerView.adapter = MyRequestsAdapter(requests)
                    }
                    else  {
                        doneRequestslineRecyclerview.adapter = MyRequestsAdapter(requests)
                    }

                }
            }
        )
    }


    private inner class MyRequestsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val timeLineOwnerTextView: TextView =itemView.findViewById(R.id.timeline_owner)
        val timelineTitleTextView: TextView =itemView.findViewById(R.id.timeline_title)
        val timelineDateTextView: TextView =itemView.findViewById(R.id.timeline_date)
        val timelineHashtags: TextView =itemView.findViewById(R.id.timeline_hashtags)
        val logoImageView: ImageView =itemView.findViewById(R.id.request_imageView)

    }

    private inner class MyRequestsAdapter(var tileLineItems: List<MyRequests>)
        :  RecyclerView.Adapter<MyRequestsHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRequestsHolder {
            val view = layoutInflater.inflate(R.layout.item_request, parent, false)

            return MyRequestsHolder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: MyRequestsHolder, position: Int) {
            val tileLineItem =tileLineItems[position]

            holder.timelineHashtags.text =tileLineItem.hashtagsTitle.toString().replace("[", "").replace("]", "")
            holder.timeLineOwnerTextView.text =tileLineItem.userName
            holder.timelineTitleTextView.text =tileLineItem.title
            holder.timelineDateTextView.text =tileLineItem.date
            Glide.with(holder.logoImageView).load("https://matloob.herokuapp.com/user/logo/"+tileLineItem.owner).centerCrop().into(holder.logoImageView)

        }

        override fun getItemCount()=tileLineItems.size
    }


}