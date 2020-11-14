package co.edu.uniquindio.compiladores.proyecto.sintaxis

import javafx.scene.control.TreeItem

class UnidadDeCompilacion (var listaFunciones: ArrayList<Funcion>) {
    override fun toString(): String {
        return "UnidadDeCompilacion(listaFuncion=$listaFunciones)"
    }

    fun getArbolVisual():TreeItem<String>{

        var raiz= TreeItem<String>("Unidad de Compilación")

        for (f in listaFunciones){
            raiz.children.add(f.getArbolVisual())

        }

            return raiz
    }
}