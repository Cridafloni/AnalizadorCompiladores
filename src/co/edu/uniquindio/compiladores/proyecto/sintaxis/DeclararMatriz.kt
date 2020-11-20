package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class DeclararMatriz  (var tipoDato: Token, var filas: Token, var columnas: Token, var variable: Token?, var asignacion: Asignacion?) {
    override fun toString(): String {
        if(variable==null){
            return "DeclararMatriz(tipoDato=$tipoDato, filas=$filas, columnas=$columnas, variable=$variable, asignacion=$asignacion)"
        }
        return "DeclararMatriz(tipoDato=$tipoDato, filas=$filas, columnas=$columnas, asignacion=$asignacion)"
    }
}