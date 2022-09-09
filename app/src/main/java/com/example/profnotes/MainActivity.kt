package com.example.profnotes

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.profnotes.core.gone
import com.example.profnotes.core.invisible
import com.example.profnotes.core.visible
import com.example.profnotes.databinding.ActivityMainBinding
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

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
            val bundle = Bundle()
            bundle.putString("MyArg", "addNewNote")
            findNavController(R.id.nav_host_fragment_activity_main).navigate(
                R.id.addNoteFragment,
                bundle
            )
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

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