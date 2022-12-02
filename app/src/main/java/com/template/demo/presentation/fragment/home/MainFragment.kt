package com.template.demo.presentation.fragment.home

import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import com.template.basecomponents.delegates.viewBinding
import com.template.demo.R
import com.template.demo.databinding.FmtMainBinding
import com.template.demo.presentation.fragment.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment(R.layout.fmt_main) {

    private val binding by viewBinding(FmtMainBinding::bind)
    override val isBottomMenuVisible = false
    override val configureCustomMenuId = R.menu.menu_settings
    override val viewModel: MainViewModel by viewModel()

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.menu_user_settings -> findNavController().navigate(MainFragmentDirections.actionNavigationHomeToSettingsFragment())
        }
        return false
    }
}