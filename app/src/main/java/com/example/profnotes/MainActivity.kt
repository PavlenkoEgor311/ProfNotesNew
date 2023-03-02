package com.example.profnotes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.profnotes.core.service.NotificationLocalNote
import com.example.profnotes.data.local.Prefs
import com.example.profnotes.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var prefs: Prefs
    private lateinit var binding: ActivityMainBinding
    private val shapeBottomBar: ShapeAppearanceModel by lazy {
        val radius = resources.getDimension(R.dimen.default_corner_radius)
        ShapeAppearanceModel()
            .toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, radius)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //startService()

        Timber.plant(Timber.DebugTree())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val navView: BottomNavigationView = binding.navView
        navView.menu.getItem(1).isEnabled = false

        binding.fab.setColorFilter(R.color.black)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_notifications
            )
        )

        binding.fab.setOnClickListener {
            findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.addNoteFragment)
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (prefs.authUser) {
            //navController.navigate(R.id.action_loginFragment_to_navigation_home)
        }
    }

    private fun startService() {
        val intent = Intent(this, NotificationLocalNote::class.java)
        startService(intent)
    }

    private fun stopService() {
        val intent = Intent(this, NotificationLocalNote::class.java)
        stopService(intent)
    }


    fun showLoading() {

    }

    fun showBottomBar(value: Boolean) {
        binding.bottomappbar.isGone = !value
        binding.fab.isGone = !value
    }

    fun setRoundNavBottom() {
        val radius = resources.getDimension(R.dimen.default_corner_radius)
        val bottomBarBackground = binding.bottomappbar.background as MaterialShapeDrawable
        bottomBarBackground.shapeAppearanceModel = bottomBarBackground.shapeAppearanceModel
            .toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, radius)
            .build()
        val bat = binding.navView.background as MaterialShapeDrawable
        bat.shapeAppearanceModel = bottomBarBackground.shapeAppearanceModel
            .toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, radius)
            .build()
        //val bottomAppBar = binding.bottomappbar
        //val bottomBarBackground = bottomAppBar.background as MaterialShapeDrawable
        //bottomBarBackground.shapeAppearanceModel = null
        //bottomBarBackground.shapeAppearanceModel = shapeBottomBar
    }

    override fun onStart() {
        super.onStart()
        setRoundNavBottom()
    }
}