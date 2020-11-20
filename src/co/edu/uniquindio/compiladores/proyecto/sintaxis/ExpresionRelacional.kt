package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionRelacional (var componente1: Any, var operadorRelacional: Token, var componente2: Any) {
    override fun toString(): String {
        return "ExpresionRelacional(componente1=$componente1, operadorRelacional=$operadorRelacional, componente2=$componente2)"
    }

    fun getArbolVisual(): TreeItem<String>? {
        return TreeItem("${componente1.toString()} : ${operadorRelacional.lexema} : ${componente2.toString()}")
    }
}