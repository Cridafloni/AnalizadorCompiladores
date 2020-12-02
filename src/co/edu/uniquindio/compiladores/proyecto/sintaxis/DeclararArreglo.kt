package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import javafx.scene.control.TreeItem

class DeclararArreglo (var tipoDato: Token,var tamanio:Token , var variable: Token?, var asignacion: Asignacion?) {
    override fun toString(): String {
        if(variable==null){
            return "DeclararArreglo(tipoDato=$tipoDato, tamanio=$tamanio, variable=$variable, asignacion=$asignacion)"
        }
        return "DeclararArreglo(tipoDato=$tipoDato, tamanio=$tamanio, asignacion=$asignacion)"
    }

    fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem<String>("Declaración Arreglo")

        raiz.children.add(TreeItem("Tipo Dato: ${tipoDato.lexema}"))
        raiz.children.add(TreeItem("Tamaño: ${tamanio.lexema}"))

        if(variable!=null){
          raiz.children.add(TreeItem("Tamaño: ${variable!!.lexema}"))
        }
        if(asignacion!= null){
            raiz.children.add(asignacion!!.getArbolVisual())
        }

        return raiz
    }


}