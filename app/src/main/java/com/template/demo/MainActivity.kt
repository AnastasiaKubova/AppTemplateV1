package com.template.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.template.demo.databinding.ActivityMainBinding
import com.template.demo.presentation.fragment.data.ErrorVO

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_home_fragment))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navFragment =  supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navFragment.navController
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        // Fix stack of fragments after navigation.
        binding.navView.setOnItemSelectedListener {
            navController.popBackStack()
            navController.navigate(it.itemId)
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    /**
     * Show or hide error view with specific error.
     */
    fun showErrorView(errorVO: ErrorVO?) {
        binding.error.root.isVisible = errorVO != null
        errorVO?.let { err ->
            with(binding.error) {
                viewErrorImage.setImageResource(err.res)
                viewErrorText.setText(err.messageError)
                viewErrorUpdateButton.setText(err.buttonText)
                viewErrorUpdateButton.setOnClickListener { err.action?.invoke() }
            }
        }
    }

    /**
     * Show error message.
     */
    fun showErrorMessage(errorMessage: Int) {
        Snackbar.make(binding.navView, getString(errorMessage), Snackbar.LENGTH_SHORT).show()
    }

    /**
     * Show or hide loading progress.
     */
    fun showOrHideProgress(isLoading: Boolean) {
        binding.loadingProgress.isVisible = isLoading
    }

    /**
     * Show or hide bottom menu.
     */
    fun showOrHideBottomMenu(isMenuShouldShow: Boolean) {
        binding.navView.isVisible = isMenuShouldShow
    }

    fun showOrHideBackButtonVisible(isVisible: Boolean) {
        supportActionBar?.setHomeButtonEnabled(isVisible)
        supportActionBar?.setDisplayHomeAsUpEnabled(isVisible)
        supportActionBar?.setDisplayShowHomeEnabled(isVisible)
    }
}