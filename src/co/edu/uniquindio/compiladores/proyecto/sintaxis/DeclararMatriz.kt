package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class DeclararMatriz  (var tipoDato: Token, var filas: Token, var columnas: Token, var variable: Token?, var asignacion: Asignacion?): Sentencia(null) {
    override fun toString(): String {
        if(variable==null){
            return "DeclararMatriz(tipoDato=$tipoDato, filas=$filas, columnas=$columnas, variable=$variable, asignacion=$asignacion)"
        }
        return "DeclararMatriz(tipoDato=$tipoDato, filas=$filas, columnas=$columnas, asignacion=$asignacion)"
    }
    override fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem<String>("Declaración Matriz")

        raiz.children.add(TreeItem("Tipo Dato: ${tipoDato.lexema}"))
        raiz.children.add(TreeItem("Tamaño Filas: ${filas.lexema}"))
        raiz.children.add(TreeItem("Tamaño Columas: ${columnas.lexema}"))

        if(variable!=null){
            raiz.children.add(TreeItem("Tamaño: ${variable!!.lexema}"))
        }
        if(asignacion!= null){
            raiz.children.add(asignacion!!.getArbolVisual())
        }

        return raiz
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        if(asignacion != null){
            asignacion!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
            var tipoMatriz = asignacion!!.obtenerTipoAsignacion(tablaSimbolos, ambito)
            println(tipoMatriz)
            if("MATRIZ"+tipoDato.lexema != tipoMatriz){
                erroresSemanticos.add(Error("El tipo de dato de la matriz no coincide con el declarado.", tipoDato.fila, tipoDato.columna))
            }

            var arreglo1 = (asignacion!!.dato.dato as Matriz).arreglo1.listaDatos
            var arreglo2 = (asignacion!!.dato.dato as Matriz).arreglo2.listaDatos

            var f =filas.lexema.subSequence(1, filas.lexema.length)
            var c = columnas.lexema.subSequence(1, columnas.lexema.length)
            if(f != ""+arreglo1.size){
                erroresSemanticos.add(Error("El tamaño de las filas debe de ser ${filas.lexema}.", tipoDato.fila, tipoDato.columna))
            }
            if(c != ""+arreglo2.size){
                erroresSemanticos.add(Error("El tamaño de las columnas debe de ser ${columnas.lexema}.", tipoDato.fila, tipoDato.columna))
            }
        }
    }
}