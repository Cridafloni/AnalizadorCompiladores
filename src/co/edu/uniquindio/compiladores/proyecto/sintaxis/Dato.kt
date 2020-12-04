package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Categoria
import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

open class Dato (var dato: Any?) {
    override fun toString(): String {
        return "Dato(dato=$dato)"
    }

    open fun getArbolVisual(): TreeItem<String>? {
        if (dato is Token){
            return  TreeItem("Dato: ${(dato as Token).lexema}")
        }
        else{
            if(dato is  ExpresionAritmetica){
                return  (dato as ExpresionAritmetica).getArbolVisual()
            }
            if(dato is  ExpresionLogica){
                return  (dato as ExpresionLogica).getArbolVisual()
            }
            if (dato is Arreglo){
                return  (dato as Arreglo).getArbolVisual()
            }
            if(dato is Matriz){
                return  (dato as Matriz).getArbolVisual()
            }
            if(dato is FuncionInvocada){
                return  (dato as FuncionInvocada).getArbolVisual()
            }else{
                var raiz= TreeItem("Lista Cadenas")
                var arreglo = dato  as ArrayList<Token>
                for ( f in arreglo){
                    if(f.categoria == Categoria.CADENA_CARACTER){
                        raiz.children.add(TreeItem("Cadena: ${f.lexema}"))
                    }else{
                        raiz.children.add(TreeItem("Variable: ${f.lexema}"))
                    }
                }
                return raiz
            }

        }
    }
    open fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: String):String{
        if (dato is Token){
            if((dato as Token).categoria == Categoria.IDENTIFICADOR){
                var variable = tablaSimbolos.buscarSimboloValor((dato as Token).lexema, ambito)
                if(variable != null){
                    return variable.tipo
                }
            }
            if((dato as Token).categoria == Categoria.ENTERO){
                return "ENT"
            }
            if((dato as Token).categoria==Categoria.CADENA_CARACTER) {
                return "PAL"
            }
            if((dato as Token).categoria==Categoria.DECIMAL) {
                return "REL"
            }
            if((dato as Token).categoria==Categoria.LOGICO) {
                return "LOGI"
            }
        } else{
            if(dato is  ExpresionAritmetica){
                return  ""+(dato as ExpresionAritmetica).obtenerTipo(tablaSimbolos, ambito)
            }
            if(dato is  ExpresionLogica){
                return  (dato as ExpresionLogica).obtenerTipo()
            }
            if (dato is Arreglo){
                return  (dato as Arreglo).obtenerTipo(tablaSimbolos, ambito)
            }
            if(dato is Matriz){
                return  (dato as Matriz).obtenerTipo(tablaSimbolos, ambito)
            }
            if(dato is FuncionInvocada){
                return  (dato as FuncionInvocada).obtenerRetorno(tablaSimbolos, ambito)

            }else{
                var arreglo = dato  as ArrayList<Token>

                if(arreglo.isNotEmpty()){
                    return ""+Categoria.CADENA_CARACTER
                }
            }

        }
        return "DESCONOCIDO"
    }
    open fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito:String ){
        if(dato is  ExpresionAritmetica){
            (dato as ExpresionAritmetica).analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }
        if(dato is  ExpresionLogica){
           (dato as ExpresionLogica).analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }
        if (dato is Arreglo){
            (dato as Arreglo).analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }
        if(dato is Matriz){
            (dato as Matriz).analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }
        if(dato is FuncionInvocada){
            (dato as FuncionInvocada).analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
            var retorno = (dato as FuncionInvocada).obtenerRetorno(tablaSimbolos, ambito)
            if(retorno == "VOID"){
                erroresSemanticos.add(Error("La función invocada no tiene un retorno.", (dato as FuncionInvocada).nombreFuncion.fila, (dato as FuncionInvocada).nombreFuncion.columna))
            }
        }

    }
}