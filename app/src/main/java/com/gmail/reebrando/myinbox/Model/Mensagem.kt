package com.gmail.reebrando.myinbox.Model

/**
 * Created by Rodrigo on 11/05/2018.
 */
class Mensagem {

    //lateinit var id: String
    var topico: String? = null
    var texto: String? = null

    constructor(){}

    constructor(topico: String?, texto: String?){
        //this.id = id
        this.topico = topico
        this.texto = texto
    }
}