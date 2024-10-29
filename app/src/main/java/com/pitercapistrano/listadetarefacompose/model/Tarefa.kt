package com.pitercapistrano.listadetarefacompose.model

data class Tarefa(
    val tarefa: String? = null,
    val descricao: String? = null,
    val prioridade: Int? = null,
    val checkTarefa: Boolean? = null
)
