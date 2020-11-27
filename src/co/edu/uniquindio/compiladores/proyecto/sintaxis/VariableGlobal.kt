package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
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

    fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>) {

    }
}