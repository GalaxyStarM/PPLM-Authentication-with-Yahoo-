package id.ac.unri.submission_one.ui

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.ac.unri.submission_one.adapter.ChannelAdapter
import id.ac.unri.submission_one.data.Channel
import id.ac.unri.submission_one.databinding.FragmentTvBinding

/**
 * A simple [Fragment] subclass.
 * Use the [TvFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TvFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var channelList: ArrayList<Channel>
    private lateinit var recyclerView: RecyclerView

    private var _binding: FragmentTvBinding? = null
    private val binding get() = _binding!!


    companion object{
        const val ARG_SECTION_NUMBER = "section_number"
        private const val TAG = "TvFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        channelList = ArrayList()
        recyclerView = binding.rvChannel
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val databaseRef = database.getReference("channel")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                channelList.clear()
                for (channelSnapshot in snapshot.children) {
                    val channel = channelSnapshot.getValue(Channel::class.java)
                    if (channel != null && channel.status == true) {
                        channelList.add(channel)
                    }
                }
                recyclerView.adapter = ChannelAdapter(channelList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Database operation cancelled: ${error.message}")
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clearLoginStatus() {
        val sharedPreferences = requireContext().getSharedPreferences("login_status", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("isLoggedIn") // Hapus status login
        editor.apply()
    }
}