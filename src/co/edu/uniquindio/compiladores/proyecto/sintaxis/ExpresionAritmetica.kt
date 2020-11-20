package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionAritmetica (var dato1: Any?, var operadorAritmetico: Token, var dato2: Any?){
    override fun toString(): String {
        return "ExpresionAritmetica(dato1=$dato1, operadorAritmetico=$operadorAritmetico, dato2=$dato2)"
    }

    fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem<String>("Expresión Aritmetica")

            raiz.children.add(TreeItem("Dato 1: ${dato1.toString()}"))

            raiz.children.add(TreeItem("Operador: ${operadorAritmetico.lexema}"))

            raiz.children.add(TreeItem("Dato 2: ${dato2.toString()}"))
        return raiz
    }
}