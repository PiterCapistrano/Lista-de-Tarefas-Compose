package com.pitercapistrano.listadetarefacompose.repositorio

import com.pitercapistrano.listadetarefacompose.datasource.DataSource


class TarefasRepositorio() {

    private val dataSource = DataSource()
    fun salvarTarefa(tarefa: String, descricao: String, prioridade: Int){
        dataSource.salvarTarefa(tarefa, descricao, prioridade)
    }

}