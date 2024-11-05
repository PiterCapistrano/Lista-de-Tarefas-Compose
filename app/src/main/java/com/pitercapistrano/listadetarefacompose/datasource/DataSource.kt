package com.pitercapistrano.listadetarefacompose.datasource

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.pitercapistrano.listadetarefacompose.model.Tarefa
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class DataSource @Inject constructor(){

    private val db = FirebaseFirestore.getInstance()

    private val _todastarefas = MutableStateFlow<MutableList<Tarefa>>(mutableListOf())

    private val todastarefas: StateFlow<MutableList<Tarefa>> = _todastarefas

    private val _nome = MutableStateFlow("")
    private val nome: StateFlow<String> = _nome

    fun salvarTarefa(tarefa: String, descricao: String, prioridade: Int, checkTarefa: Boolean) {

        val usuarioID = FirebaseAuth.getInstance().currentUser?.uid.toString()

        val tarefaMap = hashMapOf(
            "tarefa" to tarefa,
            "descricao" to descricao,
            "prioridade" to prioridade,
            "checkTarefa" to checkTarefa
        )

        db.collection("tarefas").document(usuarioID)
            .collection("tarefas_Usuario").document(tarefa)
            .set(tarefaMap).addOnCompleteListener {

        }.addOnFailureListener {
            Log.e("ErroDB", "Erro ao enviar dados para a Firestore!")
        }
    }

    fun recuperarTarefas(): Flow<MutableList<Tarefa>>{

        val usuarioID = FirebaseAuth.getInstance().currentUser?.uid.toString()

        val listaTarefas: MutableList<Tarefa> = mutableListOf()

        db.collection("tarefas").document(usuarioID)
            .collection("tarefas_Usuario").get().addOnCompleteListener{querySnapshot ->
            if (querySnapshot.isSuccessful){
                for (documento in querySnapshot.result){
                    val tarefa = documento.toObject(Tarefa::class.java)
                    listaTarefas.add(tarefa)
                }
                _todastarefas.value = listaTarefas
            }
        }
        return todastarefas
    }

    fun deletarTarefa(tarefa: String){

        val usuarioID = FirebaseAuth.getInstance().currentUser?.uid.toString()

        db.collection("tarefas").document(usuarioID)
            .collection("tarefas_Usuario").document(tarefa)
            .delete().addOnCompleteListener {

        }.addOnFailureListener {

        }
    }

    fun atualizarTarefa(tarefa: String, checkTarefa: Boolean){

        val usuarioID = FirebaseAuth.getInstance().currentUser?.uid.toString()

        db.collection("tarefas").document(usuarioID)
            .collection("tarefas_Usuario").document(tarefa)
            .update("checkTarefa", checkTarefa).addOnCompleteListener {

        }.addOnFailureListener {

        }
    }

    fun perfilUsuario(): Flow<String>{

        val usuarioID = FirebaseAuth.getInstance().currentUser?.uid.toString()

        db.collection("usuarios").document(usuarioID).get().addOnCompleteListener {documentSnapShot ->
            if (documentSnapShot.isSuccessful){
                val nome = documentSnapShot.result.getString("nome").toString()
                _nome.value = nome
            }
        }
        return nome
    }
}