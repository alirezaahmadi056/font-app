package info.ahmadi.fontwriter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.presenter.PresenterFreeAssetsFragment
import info.ahmadi.fontwriter.presenter.PresenterGoldAssetsFragment
import info.ahmadi.fontwriter.view.ViewFreeAssetsFragment
import info.ahmadi.fontwriter.view.ViewGoldAssetsFragment
import javax.inject.Inject

@AndroidEntryPoint
class FreeAssetsFragment : Fragment()  {
    @Inject
    lateinit var view: ViewFreeAssetsFragment
    @Inject
    lateinit var presenter: PresenterFreeAssetsFragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return view.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onCreate()
    }


}