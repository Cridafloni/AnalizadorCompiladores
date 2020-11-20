package co.edu.uniquindio.compiladores.proyecto.sintaxis

import javafx.scene.control.TreeItem

class VariableGlobal(var variableGlobal:DeclararVariable) {
    override fun toString(): String {
        return "VariableGlobal(variableGlobal=$variableGlobal)"
    }

    fun getArbolVisual(): TreeItem<String> {
        var raiz= TreeItem("Variable Global")
        raiz.children.add(variableGlobal.getArbolVisual())

        return raiz
    }
}