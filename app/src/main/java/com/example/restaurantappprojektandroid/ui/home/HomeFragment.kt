package com.example.restaurantappprojektandroid.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.restaurantappprojektandroid.MainActivity
import com.example.restuarantprojektapp.databinding.FragmentHomeBinding
import com.google.android.material.carousel.CarouselSnapHelper


class HomeFragment : Fragment() {
  private  lateinit var vb:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentHomeBinding.inflate(inflater, container, false)

        //BottomNavigationBar einblenden auf Visible
        (requireActivity() as MainActivity).bottomNavigation.visibility = View.VISIBLE

        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vb.rvMittagsTisch.setHasFixedSize(true)
        CarouselSnapHelper().attachToRecyclerView(vb.rvMittagsTisch)

        vb.rvMittagsTisch.adapter = TODO()



    }
}