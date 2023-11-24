package com.umc.addicts.presentation.auth.register

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.umc.addicts.R
import com.umc.addicts.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(){

    private var _binding : FragmentRegisterBinding?= null
    private val binding get() = requireNotNull(_binding) { "binding object is not initialized"}

    private val viewModel: RegisterViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        var singlePhotoPickerLauncher =
            registerForActivityResult(PickSinglePhotoContract()) { imageUri: Uri? ->
                if (imageUri != null) {
                    getImageByteArray(imageUri)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getImageByteArray(imageUri: Uri) {
        val inputStream = requireActivity().contentResolver.openInputStream(imageUri)
        if(inputStream != null) {
            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes)
            viewModel.setImageByteArray(bytes)
        }
        else{
            Toast.makeText(requireContext(), "알 수 없는 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}