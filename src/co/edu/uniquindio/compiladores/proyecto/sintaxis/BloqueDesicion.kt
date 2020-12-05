package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class BloqueDesicion (var expresionLogica: ExpresionLogica?, var bloqueSentencia: ArrayList<Sentencia>, var bloqueSentencia2: ArrayList<Sentencia>?, var datoLogico: Dato?): Sentencia(null) {

    override fun toString(): String {
        return "BloqueDesicion(expresionLogica=$expresionLogica, bloqueSentencia=$bloqueSentencia, bloqueSentencia2=$bloqueSentencia2)"
    }
    override fun getArbolVisual(): TreeItem<String>{
        var raiz= TreeItem("Decision")

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

        if (bloqueSentencia2!=null){
            var raiz2= TreeItem("Sentencias Else")
            for(f1 in bloqueSentencia2!!){
                raiz2.children.add(f1.getArbolVisual())
            }
            raiz.children.add(raiz1)
        }
        return raiz

    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        for(s in bloqueSentencia){

            s.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, "$ambito $this")
        }
        if(bloqueSentencia2!=null){
            for (s in bloqueSentencia2!!){
                s.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, "$ambito $this")
            }
        }
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        if(expresionLogica != null){
            var fila = expresionLogica!!.expresionRelacional.operadorRelacional.fila
            var columna = expresionLogica!!.expresionRelacional.operadorRelacional.columna
            expresionLogica!!.analizarSemantica(tablaSimbolos, erroresSemanticos, "$ambito $this", fila, columna)
        }
        if(datoLogico != null){
            var fila: Int = 0
            var columna: Int = 0
            var tipo = datoLogico!!.obtenerTipo(tablaSimbolos, "$ambito $this")
            if(tipo != "LOGI"){
                erroresSemanticos.add(Error("El dato solo puede ser de tipo lógico (LOGI)", 0, 0))
            }
            datoLogico!!.analizarSemantica(tablaSimbolos, erroresSemanticos, "$ambito $this", fila, columna)
        }
        for (s in bloqueSentencia){
            s.analizarSemantica(tablaSimbolos,erroresSemanticos,"$ambito $this")
        }
        if(bloqueSentencia2!=null){
            for (s in bloqueSentencia2!!){
                s.analizarSemantica(tablaSimbolos, erroresSemanticos, "$ambito $this")
            }
        }
    }
}