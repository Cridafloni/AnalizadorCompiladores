package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Categoria
import javafx.scene.control.TreeItem

class Sentencia (var sentencia: Any) {

    fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem<String>("Sentencia")
        raiz.children.add(TreeItem("Sentencia:${sentencia}"))

        return raiz
    }


    override fun toString(): String {
        return "Sentencia(sentencia=$sentencia)"
    }

}