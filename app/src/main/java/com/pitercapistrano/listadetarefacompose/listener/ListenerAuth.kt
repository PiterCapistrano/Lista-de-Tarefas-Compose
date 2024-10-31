package com.pitercapistrano.listadetarefacompose.listener

interface ListenerAuth {

    fun onSucess(mensagem: String, tela: String)
    fun onFailure(erro: String)
    fun erroSenha(erroSenha: String)

}