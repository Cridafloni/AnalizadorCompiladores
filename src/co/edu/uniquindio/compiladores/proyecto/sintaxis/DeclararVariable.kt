package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class DeclararVariable (var constante: Token?, var tipoDato: Token, var variable: Token, var asignacion: Asignacion?, var declararArreglo: DeclararArreglo?) {
    override fun toString(): String {
        var cons = "constante=$constante, "
        if(constante==null){
            cons = ""
        }
        if(declararArreglo!=null){
            return "DeclararVariable("+cons+"declararArreglo=$declararArreglo)"
        }
        if(asignacion == null){
            return "DeclararVariable("+cons+"tipoDato=$tipoDato, variable=$variable)"
        }
        return "DeclararVariable("+cons+"tipoDato=$tipoDato, asignacion=$asignacion)"

    }
}