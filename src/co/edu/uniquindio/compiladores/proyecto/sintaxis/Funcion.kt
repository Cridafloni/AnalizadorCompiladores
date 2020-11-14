package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import javafx.scene.control.TreeItem

class Funcion(var nombreFuncion:Token, var tipoRetorno:Token, var listaParametros:ArrayList<Parametro>, var listaSentencia:ArrayList<Sentencia> ) {

    override fun toString(): String {
        return "Funcion(nombreFuncion=$nombreFuncion, tipoRetorno=$tipoRetorno, listaParametros=$listaParametros, listaSentencia=$listaSentencia)"
    }

    fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem<String>("Función")
        raiz.children.add(TreeItem("Nombre:${nombreFuncion.lexema}"))
        raiz.children.add(TreeItem("Tipo Retorno:${tipoRetorno.lexema}"))


        return raiz
    }
}