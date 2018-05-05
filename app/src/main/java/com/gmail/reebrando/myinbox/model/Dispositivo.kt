package com.gmail.reebrando.myinbox.model

import java.net.InetAddress
import java.util.*

/**
 * Created by Rodrigo on 05/05/2018.
 */
data class Dispositivo
(
    var dataUltimaTrocaBateria: Date,
    var numeroMacAddress: InetAddress,
    var idUsuario: Number,
    var idEndereco: Number
)