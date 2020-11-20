package co.edu.uniquindio.compiladores.proyecto.sintaxis

import javafx.scene.control.TreeItem

class Arreglo (var listaDatos: ArrayList<Dato>){
    override fun toString(): String {
        return "Arreglo(listaDatos=$listaDatos)"
    }

    fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem<String>("Datos")

        var raiz1= TreeItem("Datos")
        for (f in listaDatos){
            raiz1.children.add(f.getArbolVisual())

        }
        raiz.children.add(raiz1)

        return raiz
    }

}
