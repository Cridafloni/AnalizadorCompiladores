package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionRelacional (var componente1: Dato, var operadorRelacional: Token, var componente2: Dato): Dato(null) {
    override fun toString(): String {
        return "ExpresionRelacional(componente1=$componente1, operadorRelacional=$operadorRelacional, componente2=$componente2)"
    }

    override fun getArbolVisual(): TreeItem<String>? {
        var raiz= TreeItem("Expresión Relacional")

        raiz.children.add(componente1.getArbolVisual())

        raiz.children.add(TreeItem("Operador Relacional: ${operadorRelacional.lexema}"))

        raiz.children.add(componente2.getArbolVisual())

        return raiz

    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String, fila: Int, columna: Int) {
        componente1.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, fila, columna)
        componente2.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, fila, columna)
        var tipo1 = componente1.obtenerTipo(tablaSimbolos, ambito)
        var tipo2 = componente2.obtenerTipo(tablaSimbolos, ambito)
        if(operadorRelacional.lexema !=":~:~" && operadorRelacional.lexema !="¬:~"){
            if(tipo1 != "ENT" && tipo1 != "REL"){
                erroresSemanticos.add(Error("El operador relacional ${operadorRelacional.lexema} no puede utilizarse con datos de tipo $tipo1 solo puede ser usado con valores numéricos.", fila,  columna))
            }
            if(tipo2 != "ENT" && tipo2 != "REL"){
                erroresSemanticos.add(Error("El operador relacional ${operadorRelacional.lexema} no puede utilizarse con datos de tipo $tipo2 solo puede ser usado con valores numéricos.", fila,  columna))
            }
        }else{
            if(tipo1 != tipo2){
                erroresSemanticos.add(Error("No se puede comparar un dato de tipo $tipo1 con uno de tipo $tipo2", fila,  columna))
            }
        }
    }
}