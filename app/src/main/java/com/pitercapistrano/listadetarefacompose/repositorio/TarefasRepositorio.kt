package com.pitercapistrano.listadetarefacompose.repositorio

import com.pitercapistrano.listadetarefacompose.datasource.DataSource
import com.pitercapistrano.listadetarefacompose.model.Tarefa
import kotlinx.coroutines.flow.Flow


class TarefasRepositorio() {

    private val dataSource = DataSource()
    fun salvarTarefa(tarefa: String, descricao: String, prioridade: Int, checkTarefas: Boolean){
        dataSource.salvarTarefa(tarefa, descricao, prioridade, checkTarefas)
    }

    fun recuperarTarefas(): Flow<MutableList<Tarefa>>{
        return dataSource.recuperarTarefas()
    }

    fun deletarTarefa(tarefa: String){
        dataSource.deletarTarefa(tarefa)
    }

    fun atualizarTarefa(tarefa: String, checkTarefa: Boolean){
        dataSource.atualizarTarefa(tarefa, checkTarefa)
    }
}