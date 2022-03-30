package by.anegin.testapp.features.clients.presentation.edit

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

internal class EditClientPagerAdapter(
    private val fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val pages: List<EditClientPage>
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = pages.size

    override fun createFragment(position: Int): Fragment {
        val page = pages.getOrNull(position)
            ?: throw IllegalStateException("Invalid page number")
        val classLoader = page.fragmentClass.classLoader
            ?: throw IllegalStateException("Class loader is null")
        return fragmentManager.fragmentFactory
            .instantiate(classLoader, page.fragmentClass.name)
    }
}