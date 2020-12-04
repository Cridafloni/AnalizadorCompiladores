package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Categoria
import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import com.sun.javafx.geom.transform.CanTransformVec3d
import javafx.scene.control.TreeItem

class ExpresionAritmetica (var dato1: Dato, var operadorAritmetico: Token, var dato2:Dato): Dato(null){
    override fun toString(): String {
        return "ExpresionAritmetica(dato1=$dato1, operadorAritmetico=$operadorAritmetico, dato2=$dato2)"
    }

    override fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem<String>("Expresión Aritmetica")

            raiz.children.add(dato1!!.getArbolVisual())

            raiz.children.add(TreeItem("Operador: ${operadorAritmetico.lexema}"))

            raiz.children.add(dato2!!.getArbolVisual())

        return raiz
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: String): String {
        if(dato1 != null && dato2 != null){
            var tipoD1 = dato1!!.obtenerTipo(tablaSimbolos, ambito)
            var tipoD2 = dato2!!.obtenerTipo(tablaSimbolos, ambito)
                if(tipoD1 == ""+Categoria.DECIMAL || tipoD2 == ""+Categoria.DECIMAL){
                    return ""+Categoria.DECIMAL
                }else{
                    return ""+Categoria.ENTERO
                }
            }
        return "DESCONOCIDO"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        var tipoD1 = dato1!!.obtenerTipo(tablaSimbolos, ambito)
        var tipoD2 = dato2!!.obtenerTipo(tablaSimbolos, ambito)
        if(tipoD1 ==  ""+Categoria.CADENA_CARACTER || tipoD1 == ""+Categoria.LOGICO){
            erroresSemanticos.add(Error("El primer dato debe de ser un valor numérico", operadorAritmetico.fila, operadorAritmetico.columna-1))
        }
        if(tipoD2 ==  ""+Categoria.CADENA_CARACTER || tipoD2 == ""+Categoria.LOGICO){
            erroresSemanticos.add(Error("El segundo dato debe de ser un valor numérico", operadorAritmetico.fila, operadorAritmetico.columna+1))
        }
    }
}