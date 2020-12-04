package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class DeclararVariable (var constante: Token?, var tipoDato: Token?, var variable: Token?, var asignacion: Asignacion?, var declararArreglo: DeclararArreglo?,
                        var declararMatriz: DeclararMatriz?) : Sentencia(null){

    override fun toString(): String {
        var cons = "constante=$constante, "
        if(constante==null){
            cons = ""
        }
        if(declararArreglo!=null){
            return "DeclararVariable("+cons+"declararArreglo=$declararArreglo)"
        }
        if(declararMatriz!=null){
            return "DeclararVariable("+cons+"declararMatriz=$declararMatriz)"
        }
        if(asignacion == null){
            return "DeclararVariable("+cons+"tipoDato=$tipoDato, variable=$variable)"
        }
        return "DeclararVariable("+cons+"tipoDato=$tipoDato, asignacion=$asignacion)"

    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Dato")

        if (constante != null) {
            raiz.children.add(TreeItem("Constante: ${constante!!.lexema}"))
        }
        if (tipoDato != null) {
            raiz.children.add(TreeItem("Tipo Dato: ${tipoDato!!.lexema}"))
        }
        if (variable != null) {
            raiz.children.add(TreeItem("Tipo Dato: ${variable!!.lexema}"))
        }
        if (asignacion != null) {
            raiz.children.add(asignacion!!.getArbolVisual())
        }
        if (declararArreglo != null) {
            raiz.children.add(declararArreglo!!.getArbolVisual())
        }
        if (declararMatriz != null) {
            raiz.children.add(declararMatriz!!.getArbolVisual())
        }
        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        var modificable:Boolean = false
        var tipo: String = tipoDato!!.lexema
        if(constante!=null){
            modificable = true
        }
        if (declararArreglo != null) {
            if(declararArreglo!!.asignacion == null){
                variable = declararArreglo!!.variable
            }else{
               asignacion = declararArreglo!!.asignacion
            }
            tipo = "ARREGLO"+declararArreglo!!.tipoDato
        }
        if (declararMatriz != null) {
            if(declararMatriz!!.asignacion == null){
                variable = declararMatriz!!.variable
            }else{
                asignacion = declararMatriz!!.asignacion
            }
            tipo = "MATRIZ"+declararMatriz!!.tipoDato
        }
        if(asignacion!=null){
            variable = asignacion!!.identificador
        }
        tablaSimbolos.guardarSimbloValor(variable!!.lexema, tipo, modificable, ambito, variable!!.fila, variable!!.columna)
        /**
         * Seria hacer lo de el arbol visual y llamando los diferentes declarar
         */
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        if (declararArreglo != null) {
            declararArreglo!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }
        if (declararMatriz != null) {
            if(declararMatriz!!.asignacion == null){
                variable = declararMatriz!!.variable
            }else{
                asignacion = declararMatriz!!.asignacion
            }
            tipoDato = declararMatriz!!.tipoDato
        }
        if(asignacion!=null){
            asignacion!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
            if(tipoDato!=null){
                var tipoAsignacion = asignacion!!.obtenerTipoAsignacion(tablaSimbolos, ambito)
                if(""+tipoDato!!.lexema != tipoAsignacion){
                    erroresSemanticos.add(Error("El tipo de dato de asignación no coincide con el tipo de dato del identificador", tipoDato!!.fila, tipoDato!!.columna))
                }
            }
        }
    }
}