package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Categoria
import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem


open class Sentencia (var sentencia: Sentencia?) {

   open fun getArbolVisual(): TreeItem<String> {
        //println("Estuve aqui :3 ")
        var raiz= TreeItem("Sentencia")
        raiz.children.add(sentencia!!.getArbolVisual())
        //println(sentencia)
        return raiz
    }


    override fun toString(): String {
        return "Sentencia(sentencia=$sentencia)"
    }

    open fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {

    }

    open fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito:String ){

    }

}