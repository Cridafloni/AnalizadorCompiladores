package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class UnidadDeCompilacion (var listaFunciones: ArrayList<Funcion>, var listaVariablesGlobales: ArrayList<VariableGlobal>) {


    fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem<String>("Unidad de Compilación")

        for (f in listaFunciones) {
            raiz.children.add(f.getArbolVisual())

        }
        for (f1 in listaVariablesGlobales) {
            raiz.children.add(f1.getArbolVisual())
        }

        return raiz
    }

    override fun toString(): String {
        return "UnidadDeCompilacion(listaFunciones=$listaFunciones, listaVariablesGlobales=$listaVariablesGlobales)"
    }

    fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>) {

        for (f in listaFunciones) {
            f.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, "Unidad de Compilación")
        }

        for (f in listaVariablesGlobales) {
            f.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos)
        }
    }

    fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>) {
        for (f in listaFunciones) {
            f.analizarSemantica(tablaSimbolos, erroresSemanticos)
        }

         for(f in listaVariablesGlobales){
            f.analizarSemantica(tablaSimbolos, erroresSemanticos)
        }
    }

}