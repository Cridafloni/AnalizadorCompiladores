package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Categoria
import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem


open class Sentencia (var sentencia: Sentencia?) {

   open fun getArbolVisual(): TreeItem<String> {
        var raiz= TreeItem("Sentencia")
        raiz.children.add(sentencia!!.getArbolVisual())
        //println(sentencia)
        return raiz
    }


    override fun toString(): String {
        return "Sentencia(sentencia=$sentencia)"
    }

    open fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        if(sentencia is DeclararVariable){
            (sentencia as DeclararVariable).llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, ambito)
        }
        if(sentencia is BloqueCiclos){
            (sentencia as BloqueCiclos).llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, ambito)
        }
        if(sentencia is BloqueDesicion){
            (sentencia as BloqueDesicion).llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, ambito)
        }
    }

    open fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito:String ){

        if(sentencia is Asignacion){
            (sentencia as Asignacion).analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }
        if(sentencia is DeclararVariable){
            (sentencia as DeclararVariable).analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }
        if(sentencia is BloqueCiclos){
            (sentencia as BloqueCiclos).analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }
        if(sentencia is BloqueDesicion){
            (sentencia as BloqueDesicion).analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }
    }

}