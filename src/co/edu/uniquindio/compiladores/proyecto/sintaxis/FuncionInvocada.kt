package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class FuncionInvocada (var nombreFuncion: Token,var listaParametros:ArrayList<Parametro>) {
    override fun toString(): String {
        return "FuncionInvocada(nombreFuncion=$nombreFuncion, listaParametros=$listaParametros)"
    }
}