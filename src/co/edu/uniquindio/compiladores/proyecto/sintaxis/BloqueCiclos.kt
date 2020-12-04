package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class BloqueCiclos (var expresionLogica: ExpresionLogica?, var bloqueSentencia: ArrayList<Sentencia>, var datoLogico: Dato?): Sentencia(null) {

    override fun toString(): String {
        return "BloqueCiclos(expresionLogica=$expresionLogica, bloqueSentencia=$bloqueSentencia)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz= TreeItem("Ciclo")

        var condicion =  TreeItem("Condicion")
        if(expresionLogica!=null){
            condicion.children.add(expresionLogica!!.getArbolVisual())
        }
        if(datoLogico!=null){
            condicion.children.add(datoLogico!!.getArbolVisual())
        }

        raiz.children.add(condicion)

        var raiz1= TreeItem("Sentencias")
        for(f in bloqueSentencia){
            raiz1.children.add(f.getArbolVisual())
        }

        raiz.children.add(raiz1)

        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        for (s in bloqueSentencia){
            s.llenarTablaSimbolos(tablaSimbolos,erroresSemanticos, ambito)
        }
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        if(expresionLogica != null){
            var fila = expresionLogica!!.expresionRelacional.operadorRelacional.fila
            var columna = expresionLogica!!.expresionRelacional.operadorRelacional.columna
            expresionLogica!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, fila, columna)
        }
        if(datoLogico != null){
            var fila: Int = 0
            var columna: Int = 0
            var tipo = datoLogico!!.obtenerTipo(tablaSimbolos, ambito)
            if(tipo != "LOGI"){
                erroresSemanticos.add(Error("El dato solo puede ser de tipo lógico (LOGI)", 0, 0))
            }
            datoLogico!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, fila, columna)
        }
        for (s in bloqueSentencia){
            s.analizarSemantica(tablaSimbolos,erroresSemanticos, ambito)
        }
    }
}