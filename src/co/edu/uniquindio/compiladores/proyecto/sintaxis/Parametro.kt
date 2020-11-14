package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class Parametro (var nombre:Token, var tipoDato:Token){

    override fun toString(): String {
        return "Parametro(nombre=$nombre, tipoDato=$tipoDato)"
    }
}