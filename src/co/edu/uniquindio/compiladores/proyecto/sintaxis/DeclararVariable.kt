package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class DeclararVariable (var constante: Token?, var tipoDato: Token, var variable: Token, var operadorAsignacion: Token?, var dato: Dato?, var operadorFinal: Token) {
    override fun toString(): String {
        return "DeclararVariable(constante=$constante, tipoDato=$tipoDato, variable=$variable, operadorAsignacion=$operadorAsignacion, dato=$dato, operadorFinal=$operadorFinal)"
    }
}