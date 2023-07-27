package com.draccoapp.myapplication.ui.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.navGraphViewModels
import com.draccoapp.myapplication.R
import com.draccoapp.myapplication.databinding.FragmentHqDetailsBinding
import com.draccoapp.myapplication.ui.viewmodels.HQViewModel


class HQDetailsFragment : Fragment() {

    private lateinit var binding: FragmentHqDetailsBinding
    private val viewModel by navGraphViewModels<HQViewModel>(R.id.nav_hq){ defaultViewModelProviderFactory }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_hq_details,
            container,
            false
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

}