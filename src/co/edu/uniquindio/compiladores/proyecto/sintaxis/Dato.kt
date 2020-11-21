package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Categoria
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import javafx.scene.control.TreeItem

class Dato (var dato: Any) {
    override fun toString(): String {
        return "Dato(dato=$dato)"
    }

    fun getArbolVisual(): TreeItem<String>? {
        if (dato is Token){
            return  TreeItem("Dato: ${(dato as Token).lexema}")
        }
        else{
            if(dato is  ExpresionAritmetica){
                return  (dato as ExpresionAritmetica).getArbolVisual()
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
}