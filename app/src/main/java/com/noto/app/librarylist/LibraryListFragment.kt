package com.noto.app.librarylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.noto.app.R
import com.noto.app.databinding.LibraryListFragmentBinding
import com.noto.app.domain.model.Library
import com.noto.app.util.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class LibraryListFragment : Fragment() {

    private val viewModel by viewModel<LibraryListViewModel>()

    private val libraryItemClickListener = object : LibraryListAdapter.LibraryItemClickListener {
        override fun onClick(library: Library) = findNavController()
            .navigate(LibraryListFragmentDirections.actionLibraryListFragmentToLibraryFragment(library.id))

        override fun onLongClick(library: Library) = findNavController()
            .navigate(LibraryListFragmentDirections.actionLibraryListFragmentToLibraryDialogFragment(library.id))

        override fun countLibraryNotes(library: Library): Int = viewModel.countNotes(library.id)
    }

    private val adapter = LibraryListAdapter(libraryItemClickListener)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        LibraryListFragmentBinding.inflate(inflater, container, false).withBinding {
            setupListeners()
            setupState()
        }

    private fun LibraryListFragmentBinding.setupListeners() {
        fab.setOnClickListener {
            findNavController().navigate(LibraryListFragmentDirections.actionLibraryListFragmentToNewLibraryDialogFragment())
        }

        bab.setNavigationOnClickListener {
            findNavController().navigate(LibraryListFragmentDirections.actionLibraryListFragmentToLibraryListDialogFragment())
        }

        bab.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.layout_manager -> setupLayoutMangerMenuItem()
                R.id.theme -> setupThemeMenuItem()
                else -> false
            }
        }
    }

    private fun LibraryListFragmentBinding.setupState() {
        rv.adapter = adapter
        val layoutManagerMenuItem = bab.menu.findItem(R.id.layout_manager)
        val layoutItems = listOf(tvLibrariesCount, rv)

        viewModel.layoutManager
            .onEach { layoutManager -> setupLayoutManager(layoutManager, layoutManagerMenuItem) }
            .launchIn(lifecycleScope)

        viewModel.libraries
            .onEach { libraries -> setupLibraries(libraries, layoutItems) }
            .launchIn(lifecycleScope)
    }

    private fun LibraryListFragmentBinding.setupLayoutManager(layoutManager: LayoutManager, layoutManagerMenuItem: MenuItem) {
        when (layoutManager) {
            LayoutManager.Linear -> {
                layoutManagerMenuItem.icon = resources.drawableResource(R.drawable.ic_round_view_grid_24)
                rv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
            LayoutManager.Grid -> {
                layoutManagerMenuItem.icon = resources.drawableResource(R.drawable.ic_round_view_agenda_24)
                rv.layoutManager = GridLayoutManager(requireContext(), 2)
            }
        }
        rv.visibility = View.INVISIBLE
        rv.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.hide))

        rv.visibility = View.VISIBLE
        rv.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.show))
    }

    private fun LibraryListFragmentBinding.setupLibraries(libraries: List<Library>, layoutItems: List<View>) {
        if (libraries.isEmpty()) {
            layoutItems.forEach { it.visibility = View.GONE }
            tvPlaceHolder.visibility = View.VISIBLE
        } else {
            layoutItems.forEach { it.visibility = View.VISIBLE }
            tvPlaceHolder.visibility = View.GONE
            adapter.submitList(libraries)
            tvLibrariesCount.text = libraries.size.toCountText(
                resources.stringResource(R.string.library),
                resources.stringResource(R.string.libraries)
            )
        }
    }

    private fun LibraryListFragmentBinding.setupLayoutMangerMenuItem(): Boolean {
        when (viewModel.layoutManager.value) {
            LayoutManager.Linear -> {
                viewModel.updateLayoutManager(LayoutManager.Grid)
                root.snackbar(
                    getString(R.string.layout_is_grid_mode),
                    anchorView = fab
                )
            }
            LayoutManager.Grid -> {
                viewModel.updateLayoutManager(LayoutManager.Linear)
                root.snackbar(
                    getString(R.string.layout_is_list_mode),
                    anchorView = fab
                )
            }
        }
        return true
    }

    private fun LibraryListFragmentBinding.setupThemeMenuItem(): Boolean {
        findNavController().navigate(LibraryListFragmentDirections.actionLibraryListFragmentToThemeDialogFragment())
        return true
    }

}