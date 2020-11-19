package co.edu.uniquindio.compiladores.proyecto.sintaxis
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
class Impresion(var impresion:Token, var comillasAp:Token, var listaCadena:ArrayList<Cadena> , var comillasCi:Token, var operadorFinal: Token )
{

    override fun toString(): String {
        return "Impresion "
    }
}