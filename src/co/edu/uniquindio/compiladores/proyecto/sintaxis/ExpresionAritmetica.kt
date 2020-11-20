package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class ExpresionAritmetica (var dato1: Any?, var operadorAritmetico: Token, var dato2: Any?){
    override fun toString(): String {
        return "ExpresionAritmetica(dato1=$dato1, operadorAritmetico=$operadorAritmetico, dato2=$dato2)"
    }
}