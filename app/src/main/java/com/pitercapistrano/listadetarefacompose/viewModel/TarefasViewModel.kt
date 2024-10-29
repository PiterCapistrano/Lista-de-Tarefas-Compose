package com.pitercapistrano.listadetarefacompose.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pitercapistrano.listadetarefacompose.model.Tarefa
import com.pitercapistrano.listadetarefacompose.repositorio.TarefasRepositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TarefasViewModel @Inject constructor(private val tarefasRepositorio: TarefasRepositorio): ViewModel() {

    private val _todastarefas = MutableStateFlow<MutableList<Tarefa>>(mutableListOf())

    private val todastarefas: StateFlow<MutableList<Tarefa>> = _todastarefas

    fun salvarTarefa(tarefa: String, descricao: String, prioridade: Int, checkTarefa: Boolean){
        viewModelScope.launch{
            tarefasRepositorio.salvarTarefa(tarefa, descricao, prioridade, checkTarefa)
        }
    }

    fun recuperarTarefas(): Flow<MutableList<Tarefa>>{
        viewModelScope.launch {
            tarefasRepositorio.recuperarTarefas().collect{
                _todastarefas.value = it
            }
        }
        return todastarefas
    }

    fun deletarTarefa(tarefa: String){
        viewModelScope.launch{
            tarefasRepositorio.deletarTarefa(tarefa)
        }
    }

    fun atualizarTarefa(tarefa: String, checkTarefa: Boolean){
        viewModelScope.launch {
            tarefasRepositorio.atualizarTarefa(tarefa, checkTarefa)
        }
    }
}