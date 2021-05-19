package dev.samuelmcmurray.e_wastemanagement

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.samuelmcmurray.e_wastemanagement.databinding.ActivityMainBinding
import dev.samuelmcmurray.e_wastemanagement.ui.home.HomeFragment
import dev.samuelmcmurray.e_wastemanagement.ui.profile.ProfileFragment
import dev.samuelmcmurray.e_wastemanagement.ui.shops.ShopsFragment
import dev.samuelmcmurray.e_wastemanagement.ui.upload.UploadFragment


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var mNavView: NavigationView
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var toggle: ActionBarDrawerToggle


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        toolbar = binding.appBarMain.toolbar
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        drawerLayout = binding.drawerLayout
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.nav_app_bar_open_drawer_description, R.string.nav_app_bar_navigate_up_description)
        val navigationView = binding.navView
        drawerLayout.addDrawerListener(toggle)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        Log.d(TAG, "onCreate: $navHostFragment")
        navController = navHostFragment.navController

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_nav)
        val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> {
                        toolbar.title = "Home"
                        val songsFragment = HomeFragment.newInstance()
                        openFragment(songsFragment)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.upload_fragment -> {
                        toolbar.title = "Upload"
                        val songsFragment = UploadFragment.newInstance()
                        openFragment(songsFragment)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.profileFragment -> {
                        toolbar.title = "Profile"
                        val songsFragment = ProfileFragment.newInstance()
                        openFragment(songsFragment)
                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            }
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        appBarConfiguration = AppBarConfiguration(
            setOf
                (R.id.home_fragment,
                R.id.shops_fragment,
                R.id.sign_out
            ), drawerLayout
        )
        navigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment || destination.id == R.id.userTypeFragment
                || destination.id == R.id.registerCompanyFragment ||
                    destination.id == R.id.forgotPasswordFragment ||
                destination.id == R.id.registerIndividualFragment) {
                toolbar.visibility = View.GONE
                bottomNavigation.visibility = View.GONE
            } else {
                toolbar.visibility = View.VISIBLE
                bottomNavigation.visibility = View.VISIBLE
            }

        }
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.addAuthStateListener{ firebaseAuth ->
            if (firebaseAuth.currentUser == null) {
                navController.navigate(R.id.loginFragment)
            } else {
                navController.navigate(R.id.homeFragment)
            }
        }

        FirebaseAuth.AuthStateListener { firebaseAuth ->
            firebaseAuth.addAuthStateListener{ currentUser ->
                if (currentUser == null) {
                    navController.navigate(R.id.loginFragment)
                } else {
                    navController.navigate(R.id.homeFragment)
                }
            }
        }

        FirebaseAuth.IdTokenListener { firebaseAuth ->
            firebaseAuth.addIdTokenListener(FirebaseAuth.IdTokenListener {
                auth ->
                if (auth.currentUser == null) {
                    navController.navigate(R.id.loginFragment)
                } else {
                    navController.navigate(R.id.homeFragment)
                }
            })
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            supportFragmentManager.popBackStack()
            super.onBackPressed()
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.burger_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.d(TAG, "onSupportNavigateUp: here")
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected: ${item}")

        when (item.itemId) {
            R.id.home_fragment -> {
                toolbar.title = "Home"
                val songsFragment = HomeFragment.newInstance()
                openFragment(songsFragment)
                return true
            }
            R.id.shops_fragment -> {
                toolbar.title = "Companies"
                val songsFragment = ShopsFragment.newInstance()
                openFragment(songsFragment)
                return true
            }
            R.id.sign_out -> {
                toolbar.title = "Login"
                popFullBackstack()
                Firebase.auth.signOut()
                return true
            }
            else ->
                if (toggle.onOptionsItemSelected(item)) {
                    return true
                } else {
                    return super.onOptionsItemSelected(item)
                }
        }
    }

    private fun popFullBackstack() {
        val backStackCount = supportFragmentManager.backStackEntryCount
        for (i in 0 until backStackCount) {
            val backStackId = supportFragmentManager.getBackStackEntryAt(i).id
            supportFragmentManager.popBackStack(
                backStackId,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
    }
}
