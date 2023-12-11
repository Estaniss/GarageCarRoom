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
import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.aluno.garagecarroom.R
import br.edu.ifsp.aluno.garagecarroom.adapter.CarAdapter
import br.edu.ifsp.aluno.garagecarroom.data.Car
import br.edu.ifsp.aluno.garagecarroom.databinding.FragmentCarListBinding
import br.edu.ifsp.aluno.garagecarroom.viewmodel.CarViewModel

class CarListFragment : Fragment() {
    private var _binding: FragmentCarListBinding? = null
    private val binding get() = _binding!!

    lateinit var carAdapter: CarAdapter
    lateinit var viewModel: CarViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCarListBinding.inflate(inflater, container, false)
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_carListFragment_to_addFragment) }
        configureRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
                val searchView = menu.findItem(R.id.action_search).actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        TODO("Not yet implemented")
                    }
                    override fun onQueryTextChange(p0: String?): Boolean {
                        carAdapter.filter.filter(p0)
                        return true
                    }
                })
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun configureRecyclerView()
    {
        viewModel = ViewModelProvider(this).get(CarViewModel::class.java)
        viewModel.allCars.observe(viewLifecycleOwner) { list ->
            list?.let {
                carAdapter.updateList(list as ArrayList<Car>)
            }
        }
        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        carAdapter = CarAdapter()
        recyclerView.adapter = carAdapter
        val listener = object : CarAdapter.CarListener {
            override fun onItemClick(pos: Int) {
                val c = carAdapter.carListFilterable[pos]
                val bundle = Bundle()
                bundle.putInt("idCar", c.id)
                findNavController().navigate(
                    R.id.action_carListFragment_to_addFragment,
                    bundle
                )
            }
        }
        carAdapter.setClickListener(listener)
    }

}