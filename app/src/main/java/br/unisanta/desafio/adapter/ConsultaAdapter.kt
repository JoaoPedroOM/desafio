package br.unisanta.desafio.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.unisanta.desafio.R
import br.unisanta.desafio.model.Consulta

class ConsultaAdapter(private val consultas: MutableList<Consulta>) : RecyclerView.Adapter<ConsultaAdapter.ConsultaViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_produto, parent, false)
        return ConsultaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConsultaViewHolder, position: Int) {
        val consulta = consultas[position]
        holder.bind(consulta)
    }

    override fun getItemCount(): Int = consultas.size

    inner class ConsultaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val data: TextView = itemView.findViewById(R.id.textData)
        private val hora: TextView = itemView.findViewById(R.id.textHora)

        fun bind(consulta: Consulta) {
            data.text = consulta.data
            hora.text = consulta.hora
        }
    }

}