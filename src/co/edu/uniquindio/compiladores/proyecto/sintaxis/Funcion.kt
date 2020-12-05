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
        var retorno:String = "VOID"
        if(tipoRetorno!=null){
            retorno =  tipoRetorno!!.lexema
        }
        tablaSimbolos.guardarSimbloFuncion(nombreFuncion.lexema, retorno, obtenerTiposParametros(), ambito, nombreFuncion.fila, nombreFuncion.columna  )

        for(p in listaParametros){
            tablaSimbolos.guardarSimbloValor(p.nombre.lexema,p.tipoDato.lexema ,true, nombreFuncion.lexema, p.nombre.fila, p.nombre.columna)
        }

        for(s in listaSentencia){
            s.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, nombreFuncion.lexema)
        }
    }

    fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>){
        var retorna = tipoRetorno!=null
        var nSentencia: Int = 0
        for(s in listaSentencia){
            if(retorna){
                if(s.sentencia != null){
                    if(s.sentencia is Retorno){
                        var tipo = (s.sentencia as Retorno).dato.obtenerTipo(tablaSimbolos, nombreFuncion.lexema)
                        (s.sentencia as Retorno).analizarSemantica(tablaSimbolos, erroresSemanticos, nombreFuncion.lexema)
                        if(nSentencia != listaSentencia.size-1){
                            erroresSemanticos.add(Error("No pueden existir sentencias después del retorno", nombreFuncion.fila, nombreFuncion.columna))
                        }
                        if(tipo != tipoRetorno!!.lexema){
                            erroresSemanticos.add(Error("El valor retornado no coincide con el tipo de retorno", nombreFuncion.fila, nombreFuncion.columna))
                        }
                    }else{
                        if(nSentencia == listaSentencia.size-1){
                            erroresSemanticos.add(Error("Debe existir una sentencia de retorno para la función ${nombreFuncion.lexema}", nombreFuncion.fila, nombreFuncion.columna))
                        }
                    }
                }
            }else{
                if(s.sentencia is Retorno){
                    erroresSemanticos.add(Error("La función ${nombreFuncion.lexema} no tiene un tipo de retorno", nombreFuncion.fila, nombreFuncion.columna))
                }
            }
            s.analizarSemantica(tablaSimbolos, erroresSemanticos, nombreFuncion.lexema)
            nSentencia++
        }
    }
}