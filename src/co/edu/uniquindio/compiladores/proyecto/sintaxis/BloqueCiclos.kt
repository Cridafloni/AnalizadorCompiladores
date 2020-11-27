package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class BloqueCiclos (var expresionLogica: ExpresionLogica, var bloqueSentencia: ArrayList<Sentencia>): Sentencia(null) {

    override fun toString(): String {
        return "BloqueCiclos(expresionLogica=$expresionLogica, bloqueSentencia=$bloqueSentencia)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz= TreeItem("Ciclo")

        var condicion =  TreeItem("Condicion")
        condicion.children.add(expresionLogica.getArbolVisual())

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
}