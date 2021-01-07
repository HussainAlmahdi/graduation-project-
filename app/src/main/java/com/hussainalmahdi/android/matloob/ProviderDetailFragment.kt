package com.hussainalmahdi.android.matloob

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hussainalmahdi.android.matloob.remotesource.RemoteSource
import com.hussainalmahdi.android.zyara.R

class ProviderDetailFragment : Fragment() {
    private lateinit var searchEditText: AutoCompleteTextView
    private lateinit var addTasksRecyclerView: RecyclerView
    private var token:String? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token=  arguments?.getString("token")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_provider_detail, container, false)
        searchEditText =view.findViewById(R.id.autoCompleteEditText)
        addTasksRecyclerView=view.findViewById(R.id.add_tags_recycler_view)
        addTasksRecyclerView.layoutManager= LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (searchEditText.text.isNotEmpty()) {
                    RemoteSource().autocomplete(token!!,s.toString()).observe(
                        viewLifecycleOwner, { list ->
                            Log.d("list",list.toString())

                            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(),
                                android.R.layout.simple_list_item_1, list.map { it.hashtag })
                            searchEditText.setAdapter(adapter)
                            adapter.notifyDataSetChanged()
                        }
                    )
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })



        val numbers = mutableListOf<String>("one","two","three")
        addTasksRecyclerView.adapter =TagsAdapter(numbers)

    }


    private inner class TagHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tagTextView: TextView =itemView.findViewById(R.id.tag_content)
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