package com.wantedly.design.android.atom.sandbox20181127

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.wantedly.design.android.atom.baseapp.ActivityScope
import com.wantedly.design.android.atom.baseapp.Injectable
import com.wantedly.design.android.atom.baseapp.Tracker
import com.wantedly.design.android.atom.feature1.Feature1Fragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

@Module
interface MainActivityModule {
    @ActivityScope
    @Binds
    fun bindFeature1Callback(mainActivity: MainActivity): Feature1Fragment.Callback

    @ActivityScope
    @Binds
    fun bindTopCallback(mainActivity: MainActivity): TopFragment.Callback
}

class MainActivity : AppCompatActivity(), Injectable, HasSupportFragmentInjector,
    TopFragment.Callback,
    Feature1Fragment.Callback {

    @Inject
    lateinit var tracker: Tracker
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
            .navController
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onFeature1Click() {
        tracker.log("onFeature1Click()")
        navController.navigate(NavGraphDirections.actionFeature1Fragment(12345))
    }

    override fun onFeature2Click() {
        tracker.log("onFeature2Click()")
        navController.navigate(NavGraphDirections.actionFeature2Fragment())
    }
}
