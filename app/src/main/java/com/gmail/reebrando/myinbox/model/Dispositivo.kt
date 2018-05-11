package com.gmail.reebrando.myinbox.Model

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