package com.template.demo.presentation.fragment.base

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.template.demo.MainActivity

abstract class BaseFragment(@LayoutRes private var layoutId: Int): Fragment(), MenuProvider {

    abstract val viewModel: BaseViewModel

    /**
     * Show or hide bottom menu.
     */
    open val isBottomMenuVisible: Boolean = true

    /**
     * Show or hide back button menu.
     */
    open val isBackMenuButtonVisible: Boolean = false

    /**
     * Resource for top menu.
     */
    open val configureCustomMenuId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Init menu it needed. */
        /* See example: https://stackoverflow.com/questions/71917856/sethasoptionsmenuboolean-unit-is-deprecated-deprecated-in-java */
        configureCustomMenuId?.let {
            val menuHost: MenuHost = requireActivity()
            menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        }

        /* Init base view configuration. */
        with(requireActivity() as MainActivity) {
            showOrHideBottomMenu(isBottomMenuVisible)
            showOrHideBackButtonVisible(isBackMenuButtonVisible)
        }

        /* Follow on event to show error message in snackbar. */
        viewModel.showMessage.observe(viewLifecycleOwner) {
            (requireActivity() as MainActivity).showErrorMessage(it)
        }

        /* Follow on event to show error view. */
        viewModel.showErrorView.observe(viewLifecycleOwner) {
            (requireActivity() as MainActivity).showErrorView(it)
        }

        /* Follow on event to show loading progress. */
        viewModel.isLoadingInProgress.observe(viewLifecycleOwner) {
            (requireActivity() as MainActivity).showOrHideProgress(it)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        val menuId = configureCustomMenuId ?: return
        menuInflater.inflate(menuId, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }
}