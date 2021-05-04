package dev.samuelmcmurray.e_wastemanagement


import android.os.Bundle    
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dev.samuelmcmurray.e_wastemanagement.ui.home.HomeFragment
import dev.samuelmcmurray.e_wastemanagement.ui.upload.UploadFragment

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var mNavView: NavigationView

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
        navController = navHostFragment.navController



    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

  
  public override fun onStart() {
        super.onStart()
        /*val currentUser = firebaseAuth.currentUser*/
        /*if(currentUser != null){
            // nav to home screen
        } else {
            // nav to login fragment
        }*/
  }
}