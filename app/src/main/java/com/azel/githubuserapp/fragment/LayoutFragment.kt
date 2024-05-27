package com.azel.githubuserapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.azel.githubuserapp.databinding.FragmentLayoutBinding
import com.azel.githubuserapp.ui.UsersAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LayoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LayoutFragment : Fragment() {

    private var _binding: FragmentLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LayoutFragmentViewModel
    private lateinit var adapter: UsersAdapter

    companion object {

        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"

        @JvmStatic
        fun newInstance(position: Int, username: String) =
            LayoutFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, position)
                    putString(ARG_USERNAME, username)
                }
            }
    }
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LayoutFragmentViewModel::class.java)
        adapter = UsersAdapter()

        binding.rvUser.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUser.adapter = adapter

        var position = requireArguments().getInt(ARG_POSITION)
        var username = requireArguments().getString(ARG_USERNAME)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }


        if (position == 1) {
            viewModel.fetchFollowing(username ?: "")
            viewModel.following.observe(viewLifecycleOwner) { following ->
                adapter.submitList(following)
            }
        } else {
            viewModel.fetchFollower(username ?: "")
            viewModel.followers.observe(viewLifecycleOwner) { followers ->
                adapter.submitList(followers)
            }
        }


        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}