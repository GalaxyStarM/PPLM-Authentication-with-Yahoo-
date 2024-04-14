package id.ac.unri.submission_one.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.ac.unri.submission_one.ui.TvFragment

class SectionPagerAdaper(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = TvFragment()
        fragment.arguments = Bundle().apply {
            putInt(TvFragment.ARG_SECTION_NUMBER, position + 1)
        }
        return fragment
    }

}