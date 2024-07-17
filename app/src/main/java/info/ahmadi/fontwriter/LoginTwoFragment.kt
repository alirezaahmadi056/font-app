package info.ahmadi.fontwriter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.presenter.PresenterLoginTwoFragment
import info.ahmadi.fontwriter.view.ViewLoginTwoFragment
import javax.inject.Inject

@AndroidEntryPoint
class LoginTwoFragment : Fragment() {
    @Inject
    lateinit var view: ViewLoginTwoFragment

    @Inject
    lateinit var presenter: PresenterLoginTwoFragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return view.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment , LoginOneFragment()).commit()
            }

        })
        presenter.onCreate()
    }
}