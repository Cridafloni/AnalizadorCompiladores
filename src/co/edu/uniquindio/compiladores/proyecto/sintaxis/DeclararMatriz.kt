package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import javafx.scene.control.TreeItem

class DeclararMatriz  (var tipoDato: Token, var filas: Token, var columnas: Token, var variable: Token?, var asignacion: Asignacion?) {
    override fun toString(): String {
        if(variable==null){
            return "DeclararMatriz(tipoDato=$tipoDato, filas=$filas, columnas=$columnas, variable=$variable, asignacion=$asignacion)"
        }
        return "DeclararMatriz(tipoDato=$tipoDato, filas=$filas, columnas=$columnas, asignacion=$asignacion)"
    }
    fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem<String>("Declaración Arreglo")

        raiz.children.add(TreeItem("Tipo Dato: ${tipoDato.lexema}"))
        raiz.children.add(TreeItem("Tamaño: ${filas.lexema}"))
        raiz.children.add(TreeItem("Tamaño: ${columnas.lexema}"))

        if(variable!=null){
            raiz.children.add(TreeItem("Tamaño: ${variable!!.lexema}"))
        }
        if(asignacion!= null){
            raiz.children.add(asignacion!!.getArbolVisual())
        }

        return raiz
    }
}