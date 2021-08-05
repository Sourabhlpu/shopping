package com.example.shopping.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.shopping.R
import com.example.shopping.clientdetails.presentation.ClientDetailsFragment
import com.example.shopping.common.presentation.model.UIToolbar
import com.example.shopping.createclient.presentation.CreateClientFragment
import com.example.shopping.databinding.ActivityMainBinding
import com.example.shopping.displayclients.presentation.ClientsFragment
import com.example.shopping.products.presentation.ProductsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.clientsFragment,
                R.id.productsFragment
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavController()
        setupActionBar()
        setupBottomNav()
        setDestinationChangeListener()
    }

    private fun initNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setDestinationChangeListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.clientsFragment -> updateToolbar(ClientsFragment.uiToolbar)
                R.id.productsFragment -> updateToolbar(ProductsFragment.uiToolbar)
                R.id.clientDetailsFragment -> updateToolbar(ClientDetailsFragment.uiToolbar)
                R.id.createClientFragment -> updateToolbar(CreateClientFragment.uiToolbar)
            }
        }
    }

    private fun updateToolbar(toolbarModel: UIToolbar) {
        binding.bottomNavigation.isVisible = toolbarModel.isTopNav
        binding.toolbar.menu.findItem(R.id.create)?.isVisible = toolbarModel.showRightAction
        binding.statusSpinner.isVisible = toolbarModel.showSpinner
        supportActionBar?.setDisplayShowTitleEnabled(toolbarModel.showTitle)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun setupBottomNav() {
        binding.bottomNavigation.setupWithNavController(navController)
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        setupFilter()
    }

    private fun setupFilter() {
        ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.statusSpinner.adapter = adapter
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}