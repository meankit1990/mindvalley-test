package com.mindvalley.test.views.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.mindvalley.test.R
import com.mindvalley.test.views.fragments.PinboardViewFragment
import com.mindvalley.test.views.fragments.TestingFragment
import com.mindvalley.test.databinding.ActivityPinBoardBinding
import kotlinx.android.synthetic.main.activity_pin_board.*
import kotlinx.android.synthetic.main.app_bar_pin_board.*

class PinBoardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var pinBoardBinding: ActivityPinBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pinBoardBinding = DataBindingUtil.setContentView(this, R.layout.activity_pin_board)

        setSupportActionBar(toolbar)
        // Init The Drawer
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        navigation_view.setNavigationItemSelectedListener(this)


        // Init Open
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.mainFrameLayout,
            PinboardViewFragment()
        ).commit()
        navigation_view.setCheckedItem(R.id.nav_gallery)
        title = navigation_view.menu.getItem(0).title
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.pin_board, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        var fragment: Fragment? = null
        var fragmentClass: Class<*>? = null
        if (id == R.id.nav_gallery) {
            fragmentClass = PinboardViewFragment::class.java
        } else if (id == R.id.nav_manage) {
            fragmentClass = TestingFragment::class.java
        }

        try {
            fragment = fragmentClass?.newInstance() as Fragment
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        val fragmentManager = supportFragmentManager
        fragment?.let { fragmentManager.beginTransaction().replace(R.id.mainFrameLayout, it)}?.commit()

        item.isCheckable = true

        title = item.title

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
