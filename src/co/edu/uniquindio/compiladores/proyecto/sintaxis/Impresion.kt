package co.edu.uniquindio.compiladores.proyecto.sintaxis
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
class Impresion(var listaCadena:ArrayList<Token>): Sentencia(null)
{
    override fun toString(): String {
        return "Impresion(listaCadena=$listaCadena)"
    }
}