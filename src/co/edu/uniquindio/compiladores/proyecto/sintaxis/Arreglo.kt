package co.edu.uniquindio.compiladores.proyecto.sintaxis

import javafx.scene.control.TreeItem

class Arreglo (var listaDatos: ArrayList<Dato>){
    override fun toString(): String {
        return "Arreglo(listaDatos=$listaDatos)"
    }

    fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem<String>("Datos")


        for (f in listaDatos){
            raiz.children.add(f.getArbolVisual())

        }


        return raiz
    }

}
