package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class ExpresionLogica(var expresionRelacional: ExpresionRelacional, var expresionLogica: ExpresionLogica?, var operadorLogico:Token?){
}