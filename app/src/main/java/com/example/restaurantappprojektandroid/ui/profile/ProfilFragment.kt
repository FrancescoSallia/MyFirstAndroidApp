package com.example.restaurantappprojektandroid.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import coil.load
import com.example.restaurantappprojektandroid.MainActivity
import com.example.restaurantappprojektandroid.ui.adapter.FavoriteAdapter
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restaurantappprojektandroid.ui.adapter.ReservationAdapter
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.FragmentAnonymUserProfilBinding
import com.example.restuarantprojektapp.databinding.FragmentProfilBinding

class ProfilFragment : Fragment() {
    private lateinit var vb: ViewBinding
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getDataUser()
        (requireActivity()as MainActivity).bottomNavigation.visibility = View.VISIBLE
        vb = if(viewModel.currentUser.value?.isAnonymous == true) {
            Log.e("PROFIL", "is true")
            FragmentAnonymUserProfilBinding.inflate(layoutInflater)
        } else {
            Log.e("PROFIL", "is false")

            FragmentProfilBinding.inflate(layoutInflater)
        }
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDataUser()

        viewModel.currentUser.observe(viewLifecycleOwner) {
            Log.e("PROFILE", it.toString())

            if (it?.isAnonymous == true) {
                (vb as? FragmentAnonymUserProfilBinding)?.btnAlsGastAbmelden?.setOnClickListener {
                    viewModel.logOut()
                }
            }else if (it != null) {
                viewModel.snapShotListenerForReservation()

                loggedUser()
            } else {
                (requireActivity() as MainActivity).bottomNavigation.visibility = View.INVISIBLE
                findNavController().popBackStack()
                findNavController().navigate(R.id.logInFragment)
            }
        }
    }

    private fun loggedUser() {
        val binding = vb
        if(binding is FragmentProfilBinding) {
            Log.d("TAG", "currentUser: ${viewModel.currentUser}")
            viewModel.likedMeals.observe(viewLifecycleOwner) {
                binding.rvFavorite.adapter = FavoriteAdapter(it.reversed(),viewModel)
            }
            // Diese funktion ist für das Profilbild zuständig falls ein profilbild vorhanden ist, funktioniert noch nicht!
            viewModel.userData.observe(viewLifecycleOwner) {

            binding.ivProfilPicProfil.load(it.profilePicture)
                binding.tvProfilNameTitle.text = it.vorname + " " + it.nachname
            }

            binding.btnAusloggen.setOnClickListener {
                viewModel.logOut()
            }

            binding.btnProfilSetting.setOnClickListener {
                findNavController().navigate(ProfilFragmentDirections.actionProfilFragmentToProiflSettingsFragment())
            }

            viewModel.reservationsList.observe(viewLifecycleOwner) {
                binding.rvReservationItem.adapter = ReservationAdapter(it.sortedBy { it.reservationId.reversed() }, viewModel,requireContext())
            }
            
            binding.tvMehrReservation.setOnClickListener {
                (requireActivity() as MainActivity).bottomNavigation.visibility = View.GONE
                findNavController().navigate(ProfilFragmentDirections.actionProfilFragmentToAllReservationListFragment())
            }
            binding.tvMehrFavorite.setOnClickListener {
                // TODO:  
            }
        }
    }
}