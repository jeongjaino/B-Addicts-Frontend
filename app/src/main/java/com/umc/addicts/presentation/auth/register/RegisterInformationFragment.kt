package com.umc.addicts.presentation.auth.register

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.launch
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.umc.addicts.R
import com.umc.addicts.data.model.auth.Toxic
import com.umc.addicts.databinding.FragmentRegisterInformationBinding
import com.umc.addicts.presentation.utils.UiEvent
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.utils.io.makeShared
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class RegisterInformationFragment : Fragment() {

    private var _binding : FragmentRegisterInformationBinding?= null
    private val binding get() = requireNotNull(_binding) { "binding object is not initialized"}

    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var singlePhotoPickerLauncher : ActivityResultLauncher<Void?>

    private val args: RegisterInformationFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        singlePhotoPickerLauncher =
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
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_information, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
        observeData()
    }

    private fun initViews() {
        binding.imageView2.setOnClickListener {
            singlePhotoPickerLauncher.launch()
        }

        binding.allergyChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val toxicList : MutableList<Toxic> = mutableListOf()
            checkedIds.forEach{ id ->
                toxicList.add(
                    Toxic(
                        keyword = group.findViewById<Chip>(id).text.toString(),
                        degree = "관심"
                    )
                )
            }
            // 관심 위험 심각
            viewModel.setToxicList(toxicList)
        }

        binding.backButton.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun initData() {
        viewModel.setInfo(
            id = args.id,
            password = args.password,
            nickname= args.nickname
        )
    }

    private fun observeData() {
        viewModel.registerEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it) {
                    is UiEvent.Success -> {
                        navigateToLogin()
                    }
                    is UiEvent.Failure -> {
                        Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun getImageByteArray(imageUri: Uri) {
        val inputStream = requireActivity().contentResolver.openInputStream(imageUri)
        if (inputStream != null) {
            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes)

            // setImageUri, byteArray
            viewModel.setImageByteArray(bytes)
            setImageUri(imageUri)
        }
        else{
            Toast.makeText(requireContext(), "알 수 없는 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setImageUri(uri: Uri) {
        binding.imageView2.setImageURI(uri)
    }

    private fun navigateToLogin() {
        findNavController().navigate(
            RegisterInformationFragmentDirections.actionRegisterInformationFragmentToLoginFragment()
        )
    }

    private fun navigateToRegister() {
        findNavController().navigate(
            RegisterInformationFragmentDirections.actionRegisterInformationFragmentToRegisterFragment()
        )
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}