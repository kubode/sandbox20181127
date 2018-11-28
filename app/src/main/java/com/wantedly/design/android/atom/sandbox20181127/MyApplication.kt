package com.wantedly.design.android.atom.sandbox20181127

import android.util.Log
import com.wantedly.design.android.atom.baseapp.ActivityScope
import com.wantedly.design.android.atom.baseapp.BaseApplication
import com.wantedly.design.android.atom.baseapp.BaseApplicationModule
import com.wantedly.design.android.atom.baseapp.Tracker
import com.wantedly.design.android.atom.feature1.Feature1FragmentModule
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        BaseApplicationModule::class,
        ActivityModule::class
    ]
)
interface MyApplicationComponent : AndroidInjector<MyApplication>

@Module
interface ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            MainActivityModule::class,
            TopFragmentModule::class,
            Feature1FragmentModule::class
        ]
    )
    fun contributeMainActivityInjector(): MainActivity
}

class MyApplication : BaseApplication() {

    @Inject
    lateinit var tracker: Tracker

    override fun onCreate() {
        super.onCreate()
        tracker.log("MyApplication.onCreate()")
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerMyApplicationComponent.builder()
            .baseApplicationModule(BaseApplicationModule(this, TrackerImpl()))
            .build()
    }
}

class TrackerImpl : Tracker {
    override fun log(action: String) {
        Log.v("TrackerImpl", action)
    }
}
