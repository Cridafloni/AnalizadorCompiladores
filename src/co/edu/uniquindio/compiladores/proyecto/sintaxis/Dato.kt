package co.edu.uniquindio.compiladores.proyecto.sintaxis

import javafx.scene.control.TreeItem

class Dato (var dato: Any) {
    override fun toString(): String {
        return "Dato(dato=$dato)"
    }

    fun getArbolVisual(): TreeItem<String>? {
        return  TreeItem("Dato: ${dato.toString()}")
    }
}