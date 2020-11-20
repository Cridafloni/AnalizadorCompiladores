package co.edu.uniquindio.compiladores.proyecto.sintaxis

class BloqueCiclos (var expresionLogica: ExpresionLogica, var bloqueSentencia: ArrayList<Sentencia>) {
    override fun toString(): String {
        return "BloqueCiclos(expresionLogica=$expresionLogica, bloqueSentencia=$bloqueSentencia)"
    }
}