package com.wantedly.design.android.atom.sandbox20181127

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.wantedly.design.android.atom.baseapp.FragmentScope
import com.wantedly.design.android.atom.baseapp.Injectable
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Inject

@Module
interface TopFragmentModule {
    @FragmentScope
    @ContributesAndroidInjector
    fun contributeTopFragmentInjector(): TopFragment
}

class TopFragment : Fragment(), Injectable {

    interface Callback {
        fun onFeature1Click()
        fun onFeature2Click()
    }

    @Inject
    lateinit var callback: Callback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top, container, false).apply {
            findViewById<Button>(R.id.button_feature1).setOnClickListener {
                callback.onFeature1Click()
            }
            findViewById<Button>(R.id.button_feature2).setOnClickListener {
                callback.onFeature2Click()
            }
        }
    }
}
