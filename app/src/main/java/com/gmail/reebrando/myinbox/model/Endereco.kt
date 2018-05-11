package com.gmail.reebrando.myinbox.Model

import java.net.InetAddress

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