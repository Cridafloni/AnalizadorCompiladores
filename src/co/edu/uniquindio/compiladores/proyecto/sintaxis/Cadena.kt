package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class Cadena(var cadena:Token) {
    override fun toString(): String {
        return "Cadena=$cadena"
    }
}