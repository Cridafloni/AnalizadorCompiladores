package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionLogica(var operadorNegacion:Token?,var expresionRelacional: ExpresionRelacional, var expresionLogica: ExpresionLogica?, var operadorLogico:Token?): Dato(null){
    override fun toString(): String {
        return "ExpresionLogica(operadorNegacion=$operadorNegacion, expresionRelacional=$expresionRelacional, expresionLogica=$expresionLogica, operadorLogico=$operadorLogico)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz= TreeItem("Expresion Logica")

        if(operadorNegacion!=null){
            raiz.children.add(TreeItem("Operador Negacion: ${operadorNegacion!!.lexema}"))
        }

        raiz.children.add(expresionRelacional.getArbolVisual())

        if(expresionLogica!=null){
            raiz.children.add(expresionLogica!!.getArbolVisual())
        }
        if(operadorLogico!=null){
            raiz.children.add(TreeItem("Operador Logico: ${operadorLogico!!.lexema}"))
        }
        return raiz
    }
     fun obtenerTipo(): String {
        return "LOGI"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String, fila: Int, columna: Int) {
        if(expresionRelacional != null){
            expresionRelacional.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, fila, columna)
        }
    }
}