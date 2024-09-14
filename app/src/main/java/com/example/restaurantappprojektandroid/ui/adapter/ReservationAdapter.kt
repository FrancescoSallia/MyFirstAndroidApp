package com.example.restaurantappprojektandroid.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantappprojektandroid.data.datasource.ReservationDatasource
import com.example.restaurantappprojektandroid.data.model.Reservation
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.databinding.ReservationItemBinding

class ReservationAdapter(
    val dataset: List<Reservation>,
    val viewModel: MainViewModel
) : RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {

    inner class ReservationViewHolder(val vb: ReservationItemBinding) :
        RecyclerView.ViewHolder(vb.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val vb: ReservationItemBinding =
            ReservationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationViewHolder(vb)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {

        val reservation = dataset[position]
        val reservationDatasoure = ReservationDatasource().loadRandomPictures()
        val datum = reservation.datum
        val reservationID = reservation.reservationId

        holder.vb.tvReservationId.text = "ResNr:${reservationID}"
        holder.vb.tvPersonanzahl.text = "Personen: ${reservation.gaeste.toString()}"
        holder.vb.tvWann.text =  "Wann: ${datum}"
        holder.vb.ivRandomImg.setImageResource(reservationDatasoure[position])

    }
}