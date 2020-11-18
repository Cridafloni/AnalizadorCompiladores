package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class DeclararArreglo (var tipoDato: Token, var variable: Token, var arreglo: ArrayList<DatoArreglo>?) {
    override fun toString(): String {
        return "DeclararArreglo(tipoDato=$tipoDato, variable=$variable, arreglo=$arreglo)"
    }
}