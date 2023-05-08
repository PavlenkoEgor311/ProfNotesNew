package com.example.profnotes

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.core.base.activity.BottomBarOperator
import com.example.profnotes.core.gone
import com.example.profnotes.core.service.NotificationLocalNote
import com.example.profnotes.core.show
import com.example.profnotes.data.local.Prefs
import com.example.profnotes.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), BottomBarOperator {
    @Inject
    lateinit var prefs: Prefs

    private val binding by viewBinding(ActivityMainBinding::bind)


    private val navHostFragment
        get() = (supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment_activity_main
        ) as NavHostFragment).navController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val navView: BottomNavigationView = binding.navView
        navView.menu.getItem(1).isEnabled = false

        binding.fab.setColorFilter(R.color.black)

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_notifications)
        )

        binding.fab.setOnClickListener {
            navHostFragment.navigate(R.id.addNoteFragment)
        }

        setupActionBarWithNavController(navHostFragment, appBarConfiguration)
        navView.setupWithNavController(navHostFragment)
        isAuthUser()
        stopService()
    }

    private fun startService() {
        val intent = Intent(this, NotificationLocalNote::class.java)
        startService(intent)
    }

    private fun stopService() {
        val intent = Intent(this, NotificationLocalNote::class.java)
        stopService(intent)
    }

    fun showBottomBar(value: Boolean) {
        with(binding) {
            bottomappbar.isGone = !value
            fab.isGone = !value
        }
    }

    override fun hideNavBar() {
        binding.fab.visibility = View.INVISIBLE
        binding.bottomappbar.visibility = View.INVISIBLE
    }

    override fun showNavBar() {
        binding.fab.visibility = View.VISIBLE
        binding.bottomappbar.visibility = View.VISIBLE
    }

    override fun hideCircularProgress() {
        with(binding) {
            progressBar.gone()
            navHostFragmentActivityMain.show()
        }
    }

    override fun showCircularProgress() {
        with(binding) {
            navHostFragmentActivityMain.gone()
            progressBar.show()
        }
    }

    private fun isAuthUser() {
        if (prefs.authUser) navHostFragment.navigate(R.id.navigation_home)
    }

}