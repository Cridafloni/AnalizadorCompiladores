package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class Asignacion (var identificador: Token, var dato: Dato): Sentencia(null) {
    override fun toString(): String {
        return "Asignacion(identificador=$identificador ,dato=$dato)"
    }
}