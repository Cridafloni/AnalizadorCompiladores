package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Funcion(var nombreFuncion:Token, var tipoRetorno:Token?, var listaParametros:ArrayList<Parametro>, var listaSentencia:ArrayList<Sentencia> ) {

    override fun toString(): String {
        return "Funcion(nombreFuncion=$nombreFuncion, tipoRetorno=$tipoRetorno, listaParametros=$listaParametros, listaSentencia=$listaSentencia)"
    }

    fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem("Función")
        raiz.children.add(TreeItem("Nombre:${nombreFuncion.lexema}"))
        if(tipoRetorno!=null) {
            raiz.children.add(TreeItem("Tipo Retorno:${tipoRetorno!!.lexema}"))
        }
        var raiz1= TreeItem("Parametros")
        for (f in listaParametros){
            raiz1.children.add(f.getArbolVisual())

        }
        raiz.children.add(raiz1)

        var raiz2= TreeItem("Sentencias")
        for (f in listaSentencia){
            raiz2.children.add(f.getArbolVisual())

        }
        raiz.children.add(raiz2)
        return raiz
    }


    fun obtenerTiposParametros():ArrayList<String>{

        var lista = ArrayList<String>()

        for(p in listaParametros){
            lista.add(p.tipoDato.lexema)
        }
        return lista
    }
    fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        tablaSimbolos.guardarSimbloFuncion(nombreFuncion.lexema, tipoRetorno!!.lexema, obtenerTiposParametros(),ambito, nombreFuncion.fila, nombreFuncion.columna  )

        for(p in listaParametros){
            tablaSimbolos.guardarSimbloValor(p.nombre.lexema,p.tipoDato.lexema ,true, nombreFuncion.lexema, p.nombre.fila, p.nombre.columna)
        }

        for(s in listaSentencia){
            s.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, nombreFuncion.lexema)
        }
    }

    fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>){
        for(s in listaSentencia){
            s.analizarSemantica(tablaSimbolos, erroresSemanticos, nombreFuncion.lexema)
        }
    }
}