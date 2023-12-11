package br.edu.ifsp.aluno.garagecarroom.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider

import br.edu.ifsp.aluno.garagecarroom.data.Car

import br.edu.ifsp.aluno.garagecarroom.viewmodel.CarViewModel
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.aluno.garagecarroom.R
import br.edu.ifsp.aluno.garagecarroom.databinding.FragmentAddBinding


class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: CarViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CarViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.add_menu, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_salvarContato -> {
                        val carModel = binding.commonLayout.modelET.text.toString()
                        val carYear = binding.commonLayout.yearET.text.toString()
                        val carColor = binding.commonLayout.colorET.text.toString()
                        val car = Car(0,carModel, carYear, carColor)
                        viewModel.insert(car)
                        Snackbar.make(binding.root, "Novo carro inserido", Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}