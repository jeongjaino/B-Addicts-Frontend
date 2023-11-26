package com.umc.addicts.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.umc.addicts.R
import com.umc.addicts.databinding.FragmentSelfDiagnosisBinding

class SelfDiagnosisFragment: Fragment() {

    private var _binding : FragmentSelfDiagnosisBinding?= null
    private val binding get() = requireNotNull(_binding) { "binding object is not initialized"}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_self_diagnosis, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.backButton.setOnClickListener {
            navigateToProfile()
        }
    }

    private fun navigateToProfile() {
        findNavController().navigate(
            SelfDiagnosisFragmentDirections.actionSelfDiagnosisFragmentToProfileFragment()
        )
    }
}