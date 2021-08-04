package com.example.shopping.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.shopping.R
import com.example.shopping.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private val appBarConfiguration by lazy {
        AppBarConfiguration(topLevelDestinationIds = setOf(R.id.clientsFragment, R.id.productsFragment))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBar()
        setupBottomNav()
        setDestinationChangeListener()
    }

    private fun setDestinationChangeListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.clientsFragment -> {
                    binding.toolbar.menu.findItem(R.id.add_client)?.isVisible = true
                    binding.statusSpinner.isVisible = true
                    supportActionBar?.setDisplayShowTitleEnabled(false)
                }
                R.id.productsFragment -> {
                    binding.toolbar.menu.findItem(R.id.add_client)?.isVisible = false
                    binding.statusSpinner.isVisible = false
                    supportActionBar?.setDisplayShowTitleEnabled(true)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
    }

    private fun setupBottomNav() {
        binding.bottomNavigation.setupWithNavController(navController)
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        setupFilter()
    }

    private fun setupFilter(){
        ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item)
            .also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.statusSpinner.adapter = adapter
        }
    }


}