package com.hussainalmahdi.android.matloob

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hussainalmahdi.android.matloob.remotesource.Hashtags
import com.hussainalmahdi.android.matloob.remotesource.RemoteSource
import com.hussainalmahdi.android.matloob.remotesource.TimeLineItem
import com.hussainalmahdi.android.zyara.R


class TimeLineFragment : Fragment() {
    private var token:String? =null
    private lateinit var timelineRecyclerView: RecyclerView
    private lateinit var hashtagsTimelineRecyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            token =  arguments?.getString("token")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_time_line, container, false)
        timelineRecyclerView =view.findViewById(R.id.timeline_recyclerview)
        hashtagsTimelineRecyclerview =view.findViewById(R.id.hashtags_timeline_recyclerview)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timelineRecyclerView.layoutManager = LinearLayoutManager(activity)
        hashtagsTimelineRecyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,true)

        if (token !=null){




            RemoteSource().getHashtag(token!!).observe(
                viewLifecycleOwner, Observer { tags->
                    hashtagsTimelineRecyclerview.adapter = TagsAdapter(tags)
                })

            RemoteSource().getUserTimeLine(token!!).observe(
                viewLifecycleOwner, Observer { items->
                    timelineRecyclerView.adapter = TimeLineAdapter((items))
                })

        }

        Log.d("token",token.toString())






    }


    private inner class TimeLineHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener  {

        private lateinit var timeLineItem: TimeLineItem
        val timeLineOwnerTextView: TextView =itemView.findViewById(R.id.timeline_owner)
        val timelineTitleTextView: TextView =itemView.findViewById(R.id.timeline_title)
        val timelineDateTextView: TextView =itemView.findViewById(R.id.timeline_date)
        val timelineHashtags: TextView =itemView.findViewById(R.id.timeline_hashtags)
        val logoImageView: ImageView =itemView.findViewById(R.id.request_imageView)
        val timelineDescription: TextView =itemView.findViewById(R.id.timeline_description)


        init {
            itemView.setOnClickListener(this)
        }

        fun timeLineInit(timeLineItem: TimeLineItem){
            this.timeLineItem=timeLineItem
        }

        override fun onClick(v: View?) {
            val args = Bundle().apply {
                putString("description",timeLineItem.description)
                putString("userName",timeLineItem.userName)
                putString("owner",timeLineItem.owner)
            }

            val fragment = MakeOfferFragment()
            fragment.arguments = args
            val fm = activity?.supportFragmentManager
            fm?.beginTransaction()
                ?.replace(R.id.fragment_container, fragment)
                ?.addToBackStack(null)
                ?.commit()
        }


    }

    private inner class TimeLineAdapter(var tileLineItems: List<TimeLineItem>)
        :  RecyclerView.Adapter<TimeLineHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineHolder {
            val view = layoutInflater.inflate(R.layout.item_request, parent, false)

            return TimeLineHolder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: TimeLineHolder, position: Int) {
            val tileLineItem =tileLineItems[position]


            holder.timeLineInit(tileLineItem)
            holder.timelineHashtags.text =tileLineItem.hashtagsTitle.toString().replace("[", "").replace("]", "")
            holder.timeLineOwnerTextView.text =tileLineItem.userName
            holder.timelineTitleTextView.text =tileLineItem.title
            holder.timelineDateTextView.text =tileLineItem.date
            holder.timelineDescription.text =tileLineItem.description.take(70)+"..."
            Glide.with(holder.logoImageView).load("https://matloob.herokuapp.com/user/logo/"+tileLineItem.owner).centerCrop().into(holder.logoImageView)


        }

        override fun getItemCount()=tileLineItems.size
    }



    private inner class TagHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tagTextView: TextView =itemView.findViewById(R.id.tag_content)
    }

    private inner class TagsAdapter(var tags: List<Hashtags>)
        :  RecyclerView.Adapter<TagHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagHolder {
            val view = layoutInflater.inflate(R.layout.item_list_tags, parent, false)

            return TagHolder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: TagHolder, position: Int) {
            val tag =tags[position]


            holder.tagTextView.text =tag.hashtag

        }

        override fun getItemCount()=tags.size
    }



}