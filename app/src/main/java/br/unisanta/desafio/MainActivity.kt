package br.unisanta.desafio

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.unisanta.desafio.databinding.ActivityMainBinding
import br.unisanta.desafio.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = com.google.firebase.ktx.Firebase.firestore

        val spinner: Spinner = binding.spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.planets_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val senha = binding.edtSenha.text.toString()

            val docRef = db.collection("usuarios").document(email)
            docRef.get().addOnSuccessListener { documentSnapshot ->
                val user = documentSnapshot.toObject<User>()
                if (user != null) {
                    if(user.cargo.equals("Paciente")){
                        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this) {
                                task -> if(task.isSuccessful){
                                    Log.d("LOGIN", "SUCESSO")
                                    Toast.makeText(this, "sucesso", Toast.LENGTH_LONG).show()
                                    val intent = Intent(this, ActivityPaciente::class.java)
                                    startActivity(intent)
                                }else{
                                    Log.d("LOGIN", "FALHOU")
                                    Toast.makeText(this, "erro", Toast.LENGTH_LONG).show()
                                }
                        }
                    }else{
                        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this) {
                                task -> if(task.isSuccessful){
                            Log.d("LOGIN", "SUCESSO")
                            Toast.makeText(this, "sucesso", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, ActivityMedico::class.java)
                            startActivity(intent)
                        }else{
                            Log.d("LOGIN", "FALHOU")
                            Toast.makeText(this, "erro", Toast.LENGTH_LONG).show()
                        }
                        }
                    }
                }
            }



        }


        binding.btnCadastrar.setOnClickListener {
            val edtEmail = binding.edtEmail.text.toString()
            val edtSenha = binding.edtSenha.text.toString()
            val position = binding.spinner.selectedItemPosition
            val cargo = binding.spinner.getItemAtPosition(position).toString()

            auth.createUserWithEmailAndPassword(edtEmail, edtSenha)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        if(cargo.toString().equals("Paciente")){
                            val user = hashMapOf(
                                "email" to edtEmail,
                                "cargo" to cargo
                            )
                            db.collection("usuarios")
                                .document(edtEmail)
                                .set(user)
                                .addOnSuccessListener { documentReference ->
                                    Toast.makeText(this, "User salvo com sucesso!", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener { e ->
                                    Log.w(TAG, "Erro ao salvar: ", e)
                                    Toast.makeText(this, "Falha ao salvar o usuario.", Toast.LENGTH_SHORT).show()
                                }
                        }else{
                            val user = hashMapOf(
                                "email" to edtEmail,
                                "cargo" to cargo
                            )
                            db.collection("usuarios")
                                .document(edtEmail)
                                .set(user)
                                .addOnSuccessListener { documentReference ->
                                    Toast.makeText(this, "User salvo com sucesso!", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener { e ->
                                    Log.w(TAG, "Erro ao salvar: ", e)
                                    Toast.makeText(this, "Falha ao salvar o usuario.", Toast.LENGTH_SHORT).show()
                                }
                        }

                        Log.d("CADASTRADO", "createUserWithEmail:success")

                    } else {
                        Log.w("não cadastrado", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
    }
}
