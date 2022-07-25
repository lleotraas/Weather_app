package fr.lleotraas.myapplication.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.lleotraas.myapplication.activity.WeatherActivity
import fr.lleotraas.myapplication.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        configureListener()
        return binding.root
    }

    private fun configureListener() {
        binding.fragmentMainBtn.setOnClickListener {
            val intent = Intent(requireContext(), WeatherActivity::class.java)
            startActivity(intent)
        }
    }
}