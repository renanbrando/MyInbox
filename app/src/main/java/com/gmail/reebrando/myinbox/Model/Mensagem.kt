package com.gmail.reebrando.myinbox.Model

import java.time.Instant
import java.util.*

/**
 * Created by Rodrigo on 11/05/2018.
 */
class Mensagem {

    companion object Factory {
        fun create(): Mensagem = Mensagem()
    }

    lateinit var id: String
    var novoCorreio: String? = null
//    var dataChegada: String? = null
//    var retirada: Boolean = false
//    var dataRetirada: String? = null
//
//    constructor(){}
//
//    constructor(topico: String?, texto: String?, dataChegada: String,
//                retirada: Boolean, dataRetirada: String?, observacao: String?)
//    {
//        this.topico = topico
//        this.texto = texto
//        this.dataChegada = dataChegada
//        this.retirada = retirada
//        this.dataRetirada = dataRetirada
//    }
}