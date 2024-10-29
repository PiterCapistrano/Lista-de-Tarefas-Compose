package com.pitercapistrano.listadetarefacompose.repositorio

import com.pitercapistrano.listadetarefacompose.datasource.DataSource
import com.pitercapistrano.listadetarefacompose.model.Tarefa
import kotlinx.coroutines.flow.Flow


class TarefasRepositorio() {

    private val dataSource = DataSource()
    fun salvarTarefa(tarefa: String, descricao: String, prioridade: Int){
        dataSource.salvarTarefa(tarefa, descricao, prioridade)
    }

    fun recuperarTarefas(): Flow<MutableList<Tarefa>>{
        return dataSource.recuperarTarefas()
    }

}