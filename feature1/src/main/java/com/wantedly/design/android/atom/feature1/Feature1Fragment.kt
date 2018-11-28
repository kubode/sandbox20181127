package com.wantedly.design.android.atom.feature1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.wantedly.design.android.atom.baseapp.FragmentScope
import com.wantedly.design.android.atom.baseapp.Injectable
import com.wantedly.design.android.atom.baseapp.Tracker
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Inject

@Module
interface Feature1FragmentModule {
    @FragmentScope
    @ContributesAndroidInjector
    fun contributeFeature1FragmentInjector(): Feature1Fragment
}

class Feature1Fragment : Fragment(), Injectable {

    interface Callback {
        fun onFeature2Click()
    }

    @Inject
    lateinit var tracker: Tracker
    @Inject
    lateinit var callback: Feature1Fragment.Callback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feature1, container, false).apply {
            findViewById<TextView>(R.id.text).text =
                    Feature1FragmentArgs.fromBundle(arguments).arg1.toString()
            findViewById<Button>(R.id.button_feature2).setOnClickListener {
                tracker.log("feature2_click")
                callback.onFeature2Click()
            }
        }
    }
}
