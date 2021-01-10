package com.hussainalmahdi.android.matloob

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hussainalmahdi.android.matloob.remotesource.DataBaseService
import com.hussainalmahdi.android.matloob.remotesource.Post
import com.hussainalmahdi.android.matloob.remotesource.RemoteSource

import com.hussainalmahdi.android.zyara.R



class NewRequestFragment : Fragment() {
    private var token: String? = null
    private lateinit var tagsRecyclerView:RecyclerView
    private lateinit var addTagsButton: TextView
    private lateinit var sendRequestButton: Button
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText



    private lateinit var tagsEditText: EditText



    override fun onCreate(savedInstanceState: Bundle?) {

        token =  arguments?.getString("token")

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val view =inflater.inflate(R.layout.fragment_new_request, container, false)
        tagsRecyclerView=view.findViewById(R.id.added_tags_recycler_view)
        addTagsButton = view.findViewById(R.id.add_tags_button)
        tagsEditText =view.findViewById(R.id.tags_edit_text)
        sendRequestButton=view.findViewById(R.id.send_Request)
        titleEditText=view.findViewById(R.id.request_title)
        descriptionEditText=view.findViewById(R.id.request_description)



        tagsRecyclerView.layoutManager= LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val numbers = mutableListOf<String>()

        addTagsButton.setOnClickListener {


            numbers+=tagsEditText.text.toString()

            TagsAdapter(numbers).notifyDataSetChanged()
            tagsRecyclerView.adapter =TagsAdapter(numbers)
            Log.d("list",numbers.toString())
            tagsEditText.text.clear()

        }
        tagsRecyclerView.adapter =TagsAdapter(numbers)



        sendRequestButton.setOnClickListener {
           val  request = Post(titleEditText.text.toString(),descriptionEditText.text.toString(),numbers)
            RemoteSource().addRequest(token!!,request)


        }

    }

    private inner class TagHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tagTextView:TextView =itemView.findViewById(R.id.tag_content)
    }

    private inner class TagsAdapter(var tags: List<String>)
        :  RecyclerView.Adapter<TagHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagHolder {
            val view = layoutInflater.inflate(R.layout.item_list_tags, parent, false)
            return TagHolder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: TagHolder, position: Int) {
            val tag =tags[position]


            holder.tagTextView.text =tag

        }

        override fun getItemCount()=tags.size
    }



}