package dev.samuelmcmurray.e_wastemanagement

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import dev.samuelmcmurray.e_wastemanagement.ui.home.HomeFragment
import dev.samuelmcmurray.e_wastemanagement.ui.upload.UploadFragment

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var mNavView: NavigationView
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
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
                }
                false
            }
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        Log.d(TAG, "onCreate: $navHostFragment")
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.layout.login_fragment || destination.id == R.layout.user_type_fragment
                || destination.id == R.layout.register_company_fragment ||
                    destination.id == R.layout.fragment_forgot_password ||
                destination.id == R.layout.register_individual_fragment) {
                supportActionBar?.hide()
                bottomNavigation.visibility = View.GONE
            } else {
                supportActionBar?.show()
                bottomNavigation.visibility = View.VISIBLE
            }
        }

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.addAuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser == null) {
                navController.navigate(R.id.loginFragment)
            } else {
                navController.navigate(R.id.homeFragment)
            }
        }

    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}