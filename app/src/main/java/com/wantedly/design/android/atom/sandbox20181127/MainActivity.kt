package com.wantedly.design.android.atom.sandbox20181127

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
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
    fun bindNavControllerProvider(mainActivity: MainActivity): NavControllerProvider

    @ActivityScope
    @Binds
    fun bindFeature1Callback(callback: Feature1Callback): Feature1Fragment.Callback

    @ActivityScope
    @Binds
    fun bindTopCallback(callback: TopCallback): TopFragment.Callback
}

interface NavControllerProvider {
    val navController: NavController
}

class MainActivity : AppCompatActivity(), Injectable, HasSupportFragmentInjector,
    NavControllerProvider {

    @Inject
    lateinit var tracker: Tracker
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override val navController by lazy {
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
}

class Feature1Callback @Inject constructor(
    private val tracker: Tracker,
    private val navControllerProvider: NavControllerProvider
) : Feature1Fragment.Callback {
    override fun onFeature2Click() {
        tracker.log("onFeature2Click()")
        navControllerProvider.navController.navigate(NavGraphDirections.actionFeature2Fragment())
    }
}

class TopCallback @Inject constructor(
    private val tracker: Tracker,
    private val navControllerProvider: NavControllerProvider
) : TopFragment.Callback {
    override fun onFeature1Click() {
        tracker.log("onFeature1Click()")
        navControllerProvider.navController.navigate(
            NavGraphDirections.actionFeature1Fragment(
                112344
            )
        )
    }

    override fun onFeature2Click() {
        tracker.log("onFeature2Click()")
        navControllerProvider.navController.navigate(NavGraphDirections.actionFeature2Fragment())
    }
}
