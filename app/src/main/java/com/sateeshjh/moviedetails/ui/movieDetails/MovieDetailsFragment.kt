package com.sateeshjh.moviedetails.ui.movieDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sateeshjh.moviedetails.MovieViewModel
import com.sateeshjh.moviedetails.R
import com.sateeshjh.moviedetails.databinding.FragmentMovieDetailsBinding
import com.sateeshjh.moviedetails.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    lateinit var binding: FragmentMovieDetailsBinding
    val args: MovieDetailsFragmentArgs by navArgs()
    val viewModel: MovieViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.backPress.setOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.getMovieDetails(args.imdbId!!)
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            when (it.getContentIfNotHandled()?.status) {
                Status.LOADING -> {
                    binding.detailsProgress.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.detailsProgress.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.detailsProgress.visibility = View.GONE
                    binding.movieDetails = it.peekContent().data
                }
                else -> {
                    binding.detailsProgress.visibility = View.GONE
                }
            }
        }
    }
}