package co.edu.uniquindio.compiladores.proyecto.lexico

enum class Categoria {
    ENTERO, DECIMAL, IDENTIFICADOR, OPERADOR_ARITMETICO, OPERADOR_LOGICO, OPERADOR_RELACIONAL,
    OPERADOR_INCREMENTAL, OPERADOR_DECREMENTAL, OPERADOR_ASIGNACION, AGRUPADOR, RESERVADA, FIN_SENTENCIA,
    COMENTARIO_LINEA, COMENTARIO_BLOQUE, CADENA_CARACTER, CARACTER, PUNTO, DOS_PUNTOS, SEPARADOR,DESCONOCIDO
}