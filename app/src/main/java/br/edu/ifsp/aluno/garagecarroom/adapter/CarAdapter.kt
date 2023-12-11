package br.edu.ifsp.aluno.garagecarroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.aluno.garagecarroom.data.Car
import br.edu.ifsp.aluno.garagecarroom.databinding.CarCellBinding
import android.widget.Filter

class CarAdapter():
    RecyclerView.Adapter<CarAdapter.CarViewHolder>() ,
    Filterable {
    var carList = ArrayList<Car>()
    var listener: CarListener?=null
    var carListFilterable = ArrayList<Car>()

    private lateinit var binding: CarCellBinding

    fun updateList(newList: ArrayList<Car> ){
        carList = newList
        carListFilterable = carList
        notifyDataSetChanged()
    }

    fun setClickListener(listener: CarListener)
    {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarViewHolder {
        binding = CarCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }
    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
         holder.carModelVH.text = carListFilterable[position].carModel
        holder.carYearVH.text = carListFilterable[position].carYear
    }
    override fun getItemCount(): Int {
        return carListFilterable.size
    }

    inner class CarViewHolder(view:CarCellBinding): RecyclerView.ViewHolder(view.root)
    {
        val carModelVH = view.modelTV
        val carYearVH = view.yearTV
        init {
            view.root.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }
    interface CarListener
    {
        fun onItemClick(pos: Int)
    }


    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                if (p0.toString().isEmpty())
                    carListFilterable = carList
                else
                {
                    val resultList = ArrayList<Car>()
                    for (row in carList)
                        if (row.carModel.lowercase().contains(p0.toString().lowercase()))
                            resultList.add(row)
                    carListFilterable = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = carListFilterable
                return filterResults
            }
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                carListFilterable = p1?.values as ArrayList<Car>
                notifyDataSetChanged()
            }
        }
    }

}