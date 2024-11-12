package br.unisanta.desafio

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.unisanta.desafio.adapter.ConsultaAdapter
import br.unisanta.desafio.databinding.ActivityMedicoBinding
import br.unisanta.desafio.model.Consulta
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActivityMedico : AppCompatActivity() {
    private lateinit var binding: ActivityMedicoBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var consultaAdapter: ConsultaAdapter
    private val consultaList = mutableListOf<Consulta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMedicoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recyclerView = binding.recycler
        recyclerView.layoutManager = LinearLayoutManager(this)

        consultaAdapter = ConsultaAdapter(consultaList)
        recyclerView.adapter = consultaAdapter

        val db = Firebase.firestore
        db.collection("consultas")
            .get()
            .addOnSuccessListener { result ->
                consultaList.clear()
                for (document in result) {
                    val consulta = document.toObject(Consulta::class.java)
                    consultaList.add(consulta)
                }
                Toast.makeText(this, "Consultas carregados com sucesso!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Log.w("ActivityListar", "Erro ao carregar Consultas.", exception)
                Toast.makeText(this, "Falha ao carregar Consultas.", Toast.LENGTH_SHORT).show()
            }
    }
}