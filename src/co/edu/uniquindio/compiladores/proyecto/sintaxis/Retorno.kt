package co.edu.uniquindio.compiladores.proyecto.sintaxis

import javafx.scene.control.TreeItem

class Retorno(var dato:Dato):Sentencia(null) {

    override fun toString(): String {
        return "Retorno(dato=$dato)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz= TreeItem("Retorno")
        raiz.children.add(dato.getArbolVisual())

        return raiz
    }
}