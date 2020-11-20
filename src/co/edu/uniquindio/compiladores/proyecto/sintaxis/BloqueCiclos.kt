package co.edu.uniquindio.compiladores.proyecto.sintaxis

class BloqueCiclos (var expresionLogica: ExpresionLogica, var bloqueSentencia: ArrayList<Sentencia>): Sentencia(null) {
    override fun toString(): String {
        return "BloqueCiclos(expresionLogica=$expresionLogica, bloqueSentencia=$bloqueSentencia)"
    }
}