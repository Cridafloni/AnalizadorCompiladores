package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import javafx.scene.control.TreeItem

class FuncionInvocada (var nombreFuncion: Token,var listaParametros:ArrayList<Parametro>): Sentencia(null) {
    override fun toString(): String {
        return "FuncionInvocada(nombreFuncion=$nombreFuncion, listaParametros=$listaParametros)"
    }

    override fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem<String>("Impresion")

        raiz.children.add(TreeItem("Nombre Función : ${nombreFuncion.lexema}"))

        var raiz1= TreeItem("Parametros")
        for (f in listaParametros){
            raiz1.children.add(f.getArbolVisual())
        }
        raiz.children.add(raiz1)

        return raiz
    }
}