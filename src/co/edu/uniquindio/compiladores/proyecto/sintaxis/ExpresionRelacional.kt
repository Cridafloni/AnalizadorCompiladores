package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionRelacional (var componente1: Dato, var operadorRelacional: Token, var componente2: Dato) {
    override fun toString(): String {
        return "ExpresionRelacional(componente1=$componente1, operadorRelacional=$operadorRelacional, componente2=$componente2)"
    }

    fun getArbolVisual(): TreeItem<String>? {
        var raiz= TreeItem("Expresión Relacional")

        raiz.children.add(componente1.getArbolVisual())

        raiz.children.add(TreeItem("Operador Relacional: ${operadorRelacional.lexema}"))

        raiz.children.add(componente2.getArbolVisual())

        return raiz

    }
}