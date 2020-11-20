package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class DeclararArreglo (var tipoDato: Token,var tamanio:Token , var variable: Token?, var asignacion: Asignacion?) {
    override fun toString(): String {
        if(variable==null){
            return "DeclararArreglo(tipoDato=$tipoDato, tamanio=$tamanio, variable=$variable, asignacion=$asignacion)"
        }
        return "DeclararArreglo(tipoDato=$tipoDato, tamanio=$tamanio, asignacion=$asignacion)"
    }
}