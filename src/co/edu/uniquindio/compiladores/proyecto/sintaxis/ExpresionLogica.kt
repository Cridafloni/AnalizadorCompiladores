package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionLogica(var operadorNegacion:Token?,var expresionRelacional: ExpresionRelacional, var expresionLogica: ExpresionLogica?, var operadorLogico:Token?){
    override fun toString(): String {
        return "ExpresionLogica(operadorNegacion=$operadorNegacion, expresionRelacional=$expresionRelacional, expresionLogica=$expresionLogica, operadorLogico=$operadorLogico)"
    }

    fun getArbolVisual(): TreeItem<String> {
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

}