package br.unisanta.desafio

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.unisanta.desafio.databinding.ActivityMainBinding
import br.unisanta.desafio.databinding.ActivityPacienteBinding
import com.google.firebase.firestore.ktx.firestore

class ActivityPaciente : AppCompatActivity() {
    private lateinit var binding: ActivityPacienteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPacienteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val db = com.google.firebase.ktx.Firebase.firestore

        binding.btnSalvarConsulta.setOnClickListener {
            val data = binding.editTextDate.text.toString()
            val hora = binding.editTextTime2.text.toString()
            val consulta = hashMapOf(
                "data" to data,
                "hora" to hora
            )
            db.collection("consultas")
                .add(consulta)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "Consulta salva com sucesso!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Erro ao salvar: ", e)
                    Toast.makeText(this, "Falha ao salvar o consulta.", Toast.LENGTH_SHORT).show()
                }
        }
    }
}