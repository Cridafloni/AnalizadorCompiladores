package co.edu.uniquindio.compiladores.proyecto.semantica

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.sintaxis.UnidadDeCompilacion

class AnalizadorSemantico (var unidadDeCompilacion: UnidadDeCompilacion) {

    var erroresSemanticos : ArrayList<Error> = ArrayList()
    var tablaSimbolos: TablaSimbolos = TablaSimbolos(erroresSemanticos)

    fun llenarTablaSimbolos(){
        unidadDeCompilacion.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos)
    }

    fun analizarSemantica(){
        unidadDeCompilacion.analizarSemantica(tablaSimbolos, erroresSemanticos)
    }
}