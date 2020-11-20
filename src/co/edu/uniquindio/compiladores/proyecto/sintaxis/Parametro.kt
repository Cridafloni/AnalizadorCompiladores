package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import javafx.scene.control.TreeItem

class Parametro (var nombre:Token, var tipoDato:Token){

    fun getArbolVisual(): TreeItem<String> {

        return TreeItem("${nombre.lexema} : ${tipoDato.lexema}")
    }



    override fun toString(): String {
        return "Parametro(nombre=$nombre, tipoDato=$tipoDato)"
    }
}