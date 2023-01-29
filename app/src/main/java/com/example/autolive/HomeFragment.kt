package com.example.autolive

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.autolive.databinding.FragmentHomeBinding
import com.example.autolive.fragment.ChatFragment
import com.example.autolive.fragment.NewsFeedFragment
import com.example.autolive.fragment.ProfileFragment
import com.example.autolive.utils.toast
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val numberOfColumn: Int =
            if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3

        /*myId = pref.getString("myId", "")
        gridLayoutManager = GridLayoutManager(requireContext(), numberOfColumn)
        mReference = FirebaseDatabase.getInstance().getReference("LiveUsers")
        blockUserRef = FirebaseDatabase.getInstance().getReference("BlockedUsers").child(myId)
        blockConversationRef = FirebaseDatabase.getInstance().getReference("ConversationBlocks").child(myId)
        usersList = ArrayList()*/

        //binding.recyclerVewStreamers.layoutManager = gridLayoutManager

        loadApis()

        binding.swipeToRefresh.setOnRefreshListener {
            binding.swipeToRefresh.isEnabled = false
            //loadApis()
        }

        binding.swipeToRefresh.viewTreeObserver.addOnScrollChangedListener {
            binding.swipeToRefresh.isEnabled = binding.recyclerVewStreamers.scrollY == 0
        }
    }

    private fun loadApis() {
        /*if (isNetworkAvailable()) {
            //loadLiveList()
            //loadNewLiveList()
            //loadBlockUserList()
        } else {
            requireContext().toast("No Internet connection")
        }*/
    }

    /*private fun loadLiveList() {
        callApi(getRestApis().streamingList, onApiSuccess = {

            //hide progress
            binding.swipeToRefresh.isRefreshing = false

            if (it.success) {
                allUserInfo.clear()
                allUserInfo = it.data

                val userStoryAdapter = UserStoryAdapter(requireContext(), it.data)

                binding.recyclerVewStreamers.apply {
                    layoutManager = gridLayoutManager
                    adapter = userStoryAdapter
                }

                binding.shimmerLayoutHome.visibility = View.GONE
            } else {
                //data failed to load
                binding.shimmerLayoutHome.visibility = View.GONE
                requireContext().toast(it.message)
            }
        }, onApiError = {
            //hide progress
            binding.swipeToRefresh.isRefreshing = false
            binding.shimmerLayoutHome.visibility = View.GONE

            requireContext().toast(it)
        }, onNetworkError = {
            //hide progress
            binding.swipeToRefresh.isRefreshing = false
            binding.shimmerLayoutHome.visibility = View.GONE

            requireContext().toast("No Internet Connection")
        })
    }

    private val liveUserEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            binding.shimmerLayoutHome.visibility = View.GONE
            usersList.clear()
            allUserInfo.clear()
            if (snapshot.exists()) {
                for (data in snapshot.children) {
                    val profileData = data.getValue(ProfileData::class.java)
                    if (blockedUserIdList.contains(profileData!!.id)) {
                        val position = blockedUserIdList.indexOf(profileData.id)
                        val blockItem = blockedUserList[position]
                        if (blockItem.blockDuration < System.currentTimeMillis()) {
                            usersList.add(profileData)
                        }
                    } else {
                        usersList.add(profileData)
                    }
                }

                allUserInfo = usersList.sortedBy { it.presentCoinBalance }.reversed()
                allUserInfo = usersList

                binding.emptyLayout.root.visibility = View.GONE

                val userStoryAdapter = UserStoryAdapter(requireContext(), allUserInfo)

                binding.recyclerVewStreamers.adapter = userStoryAdapter

            } else {
                val userStoryAdapter = UserStoryAdapter(requireContext(), usersList)

                binding.recyclerVewStreamers.apply {
                    layoutManager = gridLayoutManager
                    adapter = userStoryAdapter
                }

                binding.emptyLayout.root.visibility = View.VISIBLE
                binding.shimmerLayoutHome.visibility = View.GONE
            }
        }

        override fun onCancelled(error: DatabaseError) {
            requireContext().toast(error.message)
        }

    }

    private val blockUserEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            blockedUserList.clear()
            blockedUserIdList.clear()
            for (data in snapshot.children) {
                val blockItem = data.getValue(BlockItem::class.java)
                blockedUserList.add(blockItem)
                blockedUserIdList.add(blockItem!!.userId)
            }
        }

        override fun onCancelled(error: DatabaseError) {
            requireContext().toast(error.message)
        }
    }

    private val conversationBlockEvent = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            conversationBlockedList.clear()
            conversationBlockedIdList.clear()
            for (data in snapshot.children) {
                val blockItem = data.getValue(BlockItem::class.java)
                conversationBlockedList.add(blockItem)
                conversationBlockedIdList.add(blockItem!!.userId)
            }
        }

        override fun onCancelled(error: DatabaseError) {
            requireContext().toast(error.message)
        }
    }*/

    /*override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        loadApis()
    }*/

    override fun onResume() {
        super.onResume()

        /*blockConversationRef.addValueEventListener(conversationBlockEvent)
        blockUserRef.addValueEventListener(blockUserEventListener)
        mReference.addValueEventListener(liveUserEventListener)*/
    }

    override fun onDestroy() {
        super.onDestroy()

        /*blockConversationRef.removeEventListener(conversationBlockEvent)
        blockUserRef.removeEventListener(blockUserEventListener)
        mReference.removeEventListener(liveUserEventListener)*/
    }
}