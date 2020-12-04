package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Matriz (var arreglo1: Arreglo, var arreglo2: Arreglo):Dato(null) {
    override fun toString(): String {
        return "Matriz(arreglo1=$arreglo1, arreglo2=$arreglo2)"
    }

    override fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem<String>("Matriz")

        var raiz1= TreeItem("Arreglo 1 ")
        raiz1.children.add(arreglo1.getArbolVisual())
        raiz.children.addAll(raiz1)

        var raiz2= TreeItem("Arreglo 2 ")
        raiz2.children.add(arreglo2.getArbolVisual())
        raiz.children.add(raiz2)

        return raiz
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: String): String {
        return arreglo1.obtenerTipo(tablaSimbolos, ambito)
    }
}