package co.edu.uniquindio.compiladores.proyecto.sintaxis

import javafx.scene.control.TreeItem

class UnidadDeCompilacion (var listaFunciones: ArrayList<Funcion>, var listaVariablesGlobales: ArrayList<VariableGlobal>) {


    fun getArbolVisual():TreeItem<String>{

        var raiz= TreeItem<String>("Unidad de Compilación")

        for (f in listaFunciones){
            raiz.children.add(f.getArbolVisual())

        }
            return raiz
    }

    override fun toString(): String {
        return "UnidadDeCompilacion(listaFunciones=$listaFunciones, listaVariablesGlobales=$listaVariablesGlobales)"
    }
}