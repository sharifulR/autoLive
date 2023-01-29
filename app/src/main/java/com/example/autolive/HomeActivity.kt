package com.example.autolive

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.autolive.databinding.ActivityHomeBinding
import com.example.autolive.fragment.ChatFragment
import com.example.autolive.fragment.NewsFeedFragment
import com.example.autolive.fragment.ProfileFragment
import com.example.autolive.utils.*
import com.example.autolive.utils.Constants.ALL_PERMISSIONS
import com.example.autolive.utils.Constants.REQUEST_CODE_ALL_PERMISSION
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationBarView
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.http.GET

class HomeActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private lateinit var binding: ActivityHomeBinding

    private var selectedFragment: Fragment? = null
    private val mHomeFragment = HomeFragment()
    private val mProfileFragment = ProfileFragment()
    private val mChatFragment = ChatFragment()
    private val mNewsFeedFragment = NewsFeedFragment()
    private lateinit var progressDialog: CustomProgressDialog
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportFragmentManager.findFragmentById(binding.fragmentPlaceholder.id) != null) {
            supportFragmentManager.beginTransaction()
                .remove(supportFragmentManager.findFragmentById(binding.fragmentPlaceholder.id)!!)
                .commit()
        }

        /*progressDialog = CustomProgressDialog(this)
        progressDialog.showLoadingBar("Please wait")
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE)
        editor = sharedPreferences.edit()*/


        loadFragment(mHomeFragment)

        binding.bottomNavigationView.setOnItemSelectedListener(this)
        binding.bottomNavigationView.setOnItemReselectedListener {}

        binding.ivSearch.setOnClickListener {
            toast("Under development")
        }


    }

    private fun loadFragment(aFragment: Fragment) {
        if (selectedFragment != null) {
            if (selectedFragment == aFragment) return
            hideFragment(selectedFragment!!)
        }
        if (aFragment.isAdded) {
            showFragment(aFragment)
        } else {
            addFragment(aFragment, binding.fragmentPlaceholder.id)
        }
        selectedFragment = aFragment
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                binding.ivSearch.visibility = View.GONE
                loadFragment(mHomeFragment)
                return true
            }
            R.id.nav_search -> {
                binding.ivSearch.visibility = View.GONE
                loadFragment(mNewsFeedFragment)
                return true
            }

            R.id.nav_live -> {
                if (!Tools.hasPermissions(this, ALL_PERMISSIONS)) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            requestPermissions(ALL_PERMISSIONS, REQUEST_CODE_ALL_PERMISSION)
                        }
                    }
                } else {
                    /*val intent = Intent(this, LiveBroadcastActivity::class.java)
                    if (!userInfo.getId().isNullOrEmpty()) {
                        intent.putExtra("id", userInfo.getId())
                    } else {
                        intent.putExtra("id", "random")
                    }
                    startActivity(intent)*/
                }
                return true
            }

            R.id.nav_chat -> {
                //binding.ivSearch.visibility = View.VISIBLE
                loadFragment(mChatFragment)
                //Toast.makeText(applicationContext,"Under Development",Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.nav_profile -> {
                binding.ivSearch.visibility = View.GONE
                loadFragment(mProfileFragment)
                return true
            }
            else -> {
                binding.ivSearch.visibility = View.GONE
                return false
            }
        }
        return false
    }


    override fun onBackPressed() {
        if (selectedFragment != mHomeFragment) {
            loadFragment(mHomeFragment)
            binding.bottomNavigationView.selectedItemId = R.id.nav_home
        } else {
            super.onBackPressed()
        }
    }
}