package com.gmail.reebrando.myinbox.model

import java.util.*

/**
 * Created by Rodrigo on 05/05/2018.
 */
data class Caixa
(
    // Identificador daquela carta a ser retirada
    var dataChegada: Date,
    var quantidade: Number,
    var retirada: Boolean,
    var dataRetirada: Date,
    var observacao: String
)
