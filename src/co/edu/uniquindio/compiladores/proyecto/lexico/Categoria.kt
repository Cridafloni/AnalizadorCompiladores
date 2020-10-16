package co.edu.uniquindio.compiladores.proyecto.lexico

enum class Categoria {
    ENTERO, DECIMAL, IDENTIFICADOR, OPERADOR_ARITMETICO, OPERADOR_LOGICO, OPERADOR_RELACIONAL,
    OPERADOR_INCREMENTAL, OPERADOR_DECREMENTAL, OPERADOR_ASIGNACION, AGRUPADOR, RESERVADA, BLOQUE_SENTENCIA, BLOQUE_AGRUPACION,
    COMENTARIO_LINEA, COMENTARIO_BLOQUE, CADENA_CARACTER, CARACTER, PUNTO, DOS_PUNTOS, SEPARADOR,DESCONOCIDO, CARACTER_ESCAPE_SALTO_LINEA,
    CARACTER_ESCAPE_TABULACION, CARACTER_ESCAPE_SIGNO_GRADO, CARACTER_ESCAPE_SIGNO_INTERCALACION,OPERADOR_INICIAL

}