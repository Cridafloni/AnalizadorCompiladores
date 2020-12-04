package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Asignacion (var identificador: Token, var dato: Dato): Sentencia(null) {
    override fun toString(): String {
        return "Asignacion(identificador=$identificador ,dato=$dato)"
    }
    override fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem<String>("Asignación")

        raiz.children.add(TreeItem("Identificador ${identificador.lexema}"))
        raiz.children.add(dato.getArbolVisual())
        return raiz
    }
    fun obtenerTipoAsignacion(tablaSimbolos: TablaSimbolos, ambito: String): String{
        return dato.obtenerTipo(tablaSimbolos, ambito)
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
       dato.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, identificador.fila, identificador.columna)
        var simbolo = tablaSimbolos.buscarSimboloValor(identificador.lexema, ambito)
        if(simbolo != null){
            var tipoDato = dato.obtenerTipo(tablaSimbolos, ambito)
            if(tipoDato != "DESCONOCIDO"){
                if(tipoDato != simbolo.tipo){
                    erroresSemanticos.add(Error("Los tipos de datos no coinciden", identificador.fila, identificador.columna))
                }
            }else{
                erroresSemanticos.add(Error("El dato no posee un valor registrado", identificador.fila, identificador.columna))
            }
        }else{
            erroresSemanticos.add(Error("La variable invocada no existe", identificador.fila, identificador.columna))
        }
    }
}