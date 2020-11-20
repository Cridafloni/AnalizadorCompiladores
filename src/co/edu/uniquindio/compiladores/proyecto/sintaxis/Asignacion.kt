package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import javafx.scene.control.TreeItem

class Asignacion (var identificador: Token, var dato: Dato): Sentencia(null) {
    override fun toString(): String {
        return "Asignacion(identificador=$identificador ,dato=$dato)"
    }
    override fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem<String>("Asignación")

        raiz.children.add(TreeItem("Identificador ${identificador.lexema}"))
        raiz.children.add(dato.getArbolVisual())
        return raiz
    }

}