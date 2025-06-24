package com.dorian.liketoday.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dorian.liketoday.R
import com.dorian.liketoday.databinding.ActivityMainBinding
import com.dorian.liketoday.ui.challenge.ChallengeFragment
import com.dorian.liketoday.ui.exercise.ExerciseFragment
import com.dorian.liketoday.ui.home.HomeFragment
import com.dorian.liketoday.ui.mylogs.MyLogsFragment

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        openHomeFragment()
        supportActionBar?.hide()

        binding?.apply {
            bottomNavBar.setItemSelected(R.id.home)
            bottomNavBar.setOnItemSelectedListener {
                when (it) {
                    R.id.home -> {
                        openHomeFragment()
                    }

                    R.id.activity -> {
                        val activityFragment = ExerciseFragment.newInstance()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frag_container_nav, activityFragment)
                            .commit()
                    }

                    R.id.goal -> {
                        val goalFragment = ChallengeFragment.newInstance()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frag_container_nav, goalFragment)
                            .commit()
                    }

                    R.id.logs -> {
                        val logFragment = MyLogsFragment.newInstance()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frag_container_nav, logFragment)
                            .commit()
                    }
                }
            }
        }
    }

    private fun openHomeFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frag_container_nav, HomeFragment.newInstance())
        transaction.commit()
    }
}