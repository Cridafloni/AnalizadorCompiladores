package co.edu.uniquindio.compiladores.proyecto.sintaxis
import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Impresion(var listaCadena:ArrayList<Token>): Sentencia(null)
{
    override fun toString(): String {
        return "Impresion(listaCadena=$listaCadena)"
    }

    override fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem<String>("Impresion")

        for(f in listaCadena){
            raiz.children.add(TreeItem("Componentes impresión: ${f.lexema}"))
        }
        return raiz
    }


}