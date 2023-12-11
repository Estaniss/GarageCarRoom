package br.edu.ifsp.aluno.garagecarroom.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.aluno.garagecarroom.R
import br.edu.ifsp.aluno.garagecarroom.data.Car
import br.edu.ifsp.aluno.garagecarroom.databinding.FragmentDetalheBinding
import br.edu.ifsp.aluno.garagecarroom.viewmodel.CarViewModel
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.fragment.findNavController

class DetalheFragment : Fragment() {
    private var _binding: FragmentDetalheBinding? = null
    private val binding get() = _binding!!
    lateinit var car: Car

    lateinit var modelET: EditText
    lateinit var yearET: EditText
    lateinit var colorET: EditText

    lateinit var viewModel: CarViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CarViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetalheBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        modelET = binding.commonLayout.modelET
        yearET = binding.commonLayout.yearET
        colorET = binding.commonLayout.colorET

        val idCar = requireArguments().getInt("idCar")
        viewModel.getContactById(idCar)
        viewModel.car.observe(viewLifecycleOwner) { result ->
            result?.let {
                car = result
                modelET.setText(car.carModel)
                yearET.setText(car.carYear)
                colorET.setText(car.carColor)
            }
        }

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.detalhe_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_alterarContato -> {
                        car.carModel = modelET.text.toString()
                        car.carYear = yearET.text.toString()
                        car.carColor = colorET.text.toString()
                        viewModel.update(car)
                        Snackbar.make(binding.root, "Contato alterado", Snackbar.LENGTH_SHORT)
                            .show()
                        findNavController().popBackStack()
                        true
                    }

                    R.id.action_excluirContato -> {
                        viewModel.delete(car)
                        Snackbar.make(binding.root, "Contato apagado", Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
    }