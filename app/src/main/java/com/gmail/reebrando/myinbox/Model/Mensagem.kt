package com.gmail.reebrando.myinbox.Model

import java.time.Instant
import java.util.*

/**
 * Created by Rodrigo on 11/05/2018.
 */
class Mensagem {

    //lateinit var id: String
    var topico: String? = null
    var texto: String? = null
    var dataChegada: Date? = null;
    var quantidade: Number = 0;
    var retirada: Boolean = false;
    var dataRetirada: Date? = null;
    var observacao: String? = null;

    constructor(){}

    constructor(topico: String?, texto: String?, dataChegada: Date, quantidade: Number,
                retirada: Boolean, dataRetirada: Date?, observacao: String?)
    {
        this.topico = topico
        this.texto = texto
        this.dataChegada = dataChegada
        this.quantidade = quantidade
        this.retirada = retirada
        this.dataRetirada = dataRetirada
        this.observacao = observacao
    }
}