package com.draccoapp.myapplication.ui.fragments.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.draccoapp.myapplication.R
import com.draccoapp.myapplication.databinding.FragmentItemListBinding
import com.draccoapp.myapplication.ui.adapters.HQItemListener
import com.draccoapp.myapplication.ui.adapters.MyHQRecyclerViewAdapter
import com.draccoapp.myapplication.ui.viewmodels.HQViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * A fragment representing a list of Items.
 */
class HQFragment : Fragment(), HQItemListener {

    private lateinit var binding: FragmentItemListBinding
    private lateinit var adapter: MyHQRecyclerViewAdapter
    private val viewModel by navGraphViewModels<HQViewModel>(R.id.nav_hq){ defaultViewModelProviderFactory }

    private var permissionResultLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){
            when{
                it -> {} //Aceitou
                !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                    // nao aceitou mais de duas vezes
//                    val intent = Intent()
//                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//                    val uri = Uri.fromParts("package", requireActivity().packageName, null)
//                    intent.data = uri
//                    startActivity(intent)
                }
                else -> {}
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        when{
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Usuário aceitou
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                //Usuario negou uma vez a permissao, chance de exibir uma tela explicando o motivo da permissao
                Snackbar.make(
                    view,
                    "Precisamos da permissão de câmera",
                    Snackbar.LENGTH_INDEFINITE
                ).show()
            }
            else -> {
                //Pedir permissao
                permissionResultLauncher.launch(Manifest.permission.CAMERA)
            }
        }

        adapter = MyHQRecyclerViewAdapter(this)


        binding.list.apply {
            this.adapter = this@HQFragment.adapter
            this.layoutManager = GridLayoutManager(context, 2)
        }

        initObservers()

    }

    private fun initObservers(){
        viewModel.hqListLiveData.observe(viewLifecycleOwner) {
            it?.let {
                adapter.updateData(it)
            }
        }

        viewModel.navigationToDetailsLiveData.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let {
                val action = HQFragmentDirections.actionHQFragmentToHQDetailsFragment()
                findNavController().navigate(action)
            }
        }
    }

    override fun onItemSelected(position: Int) {
        viewModel.onHQSelected(position)
    }
}