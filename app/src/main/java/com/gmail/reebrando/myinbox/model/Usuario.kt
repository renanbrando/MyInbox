package com.gmail.reebrando.myinbox.model

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by Rodrigo on 05/05/2018.
 */
@IgnoreExtraProperties
class Usuario {

    lateinit var id: String
    var nome: String? = null
    var eMail: String? = null
    var senha: String? = null

    constructor(){}

    constructor(id: String, nome: String?, email: String?, senha: String?){
        this.id = id
        this.nome = nome
        this.eMail = email
        this.senha = senha
    }

}
