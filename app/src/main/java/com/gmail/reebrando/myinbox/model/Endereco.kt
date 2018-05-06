package com.gmail.reebrando.myinbox.model

import java.net.InetAddress
import java.util.*

/**
 * Created by Rodrigo on 05/05/2018.
 */
data class Endereco
(
    var id: Number,
    var logradouro: String,
    var bairro: String,
    var latitude: Double,
    var longitude: Double,
    var numeroMacAddress: InetAddress
)