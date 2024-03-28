package info.ahmadi.fontwriter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.presenter.PresenterLoginOneFragment
import info.ahmadi.fontwriter.view.ViewLoginOneFragment
import javax.inject.Inject

@AndroidEntryPoint
class LoginOneFragment : Fragment() , Controller{
    @Inject
    lateinit var view:ViewLoginOneFragment
    @Inject
    lateinit var presenter:PresenterLoginOneFragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return view.binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onCreate(this)
    }
    override fun changeFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment,fragment).commit()
    }

    override fun finishFromController() {
        requireActivity().finish()
    }
}