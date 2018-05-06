package com.gmail.reebrando.myinbox.model

import java.util.*

/**
 * Created by Rodrigo on 05/05/2018.
 */
data class Dispositivo
(
    var id: Number,
    var dataUltimaTrocaBateria: Date,
    var percentualBateria: Double,
    var idUsuario: Number,
    var idEndereco: Number
)