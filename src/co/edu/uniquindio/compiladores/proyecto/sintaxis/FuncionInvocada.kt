package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.Simbolo
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class FuncionInvocada (var nombreFuncion: Token,var listaParametros:ArrayList<Dato>): Sentencia(null) {
    override fun toString(): String {
        return "FuncionInvocada(nombreFuncion=$nombreFuncion, listaParametros=$listaParametros)"
    }

    override fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem<String>("Invocación")

        raiz.children.add(TreeItem("Nombre Función : ${nombreFuncion.lexema}"))

        var raiz1= TreeItem("Parámetros")
        for (f in listaParametros){
            raiz1.children.add(f.getArbolVisual())
        }
        raiz.children.add(raiz1)

        return raiz
    }
    fun obtenerRetorno(tablaSimbolos: TablaSimbolos, ambito: String): String{
        var parametros:ArrayList<String> = ArrayList<String>()
        for(f in listaParametros){
            parametros.add(f.obtenerTipo(tablaSimbolos, ambito))
        }
        var retorno = tablaSimbolos.buscarSimboloFuncion(nombreFuncion.lexema, parametros)
        if(retorno != null){
            return retorno.tipo
        }else{
            return "VOID"
        }
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        var simboloFuncion = obtenerRetorno(tablaSimbolos, ambito)
        if(simboloFuncion == "VOID"){
            erroresSemanticos.add(Error("No existe una función ${nombreFuncion.lexema} con esa lista de parámetros", nombreFuncion.fila, nombreFuncion.columna))
        }
    }
}