package co.edu.uniquindio.compiladores.proyecto.lexico

class AnalizadorLexico(var codigoFuente:String) {

    var posicionActual=0

    var caracterActual  = codigoFuente[0]
    var listaTokens= ArrayList<Token>()
    var finCodigo= 0.toChar()
    var filaActual=0
    var columnaActual=0

    fun almacenarToken(lexema:String, categoria: Categoria, fila:Int, columna:Int) = listaTokens.add(Token(lexema, categoria, fila, columna))

    fun hacerBT(posicionInicial:Int,filaInicial:Int,columnaInicial:Int){
        posicionActual= posicionInicial
        filaActual=filaInicial
        columnaActual=columnaInicial

        caracterActual= codigoFuente[posicionActual]
    }

    fun analizar(){

        while (caracterActual != finCodigo){

            if (caracterActual == ' ' || caracterActual=='\t' || caracterActual=='\n'){
                obtenerSiguienteCaracter()
                continue
            }

            if(esClase()) continue

            if(esTipoCadena())continue
            if(esTipoEntero())continue
            if(esTipoLogico())continue
            if(esTipoReal())continue

            if(esConstante())continue

            if(esEntero()) continue
            if(esDecimal())continue
            if(esCadena()) continue
            if(esLogico()) continue
            if(esBloqueSentencia()) continue
            if(esAgrupacion()) continue
            if (esIdentificador())continue

            if(esConectorY()) continue
            if(esConectorO()) continue
            if(esNegacion()) continue

            if (esOperadorMatematico()) continue
            if (esCiclo()) continue
            if (esOperadorInicial()) continue
            if (esOperadorAsignacion()) continue

            almacenarToken(""+caracterActual, Categoria.DESCONOCIDO,filaActual,columnaActual)
            obtenerSiguienteCaracter()
        }
    }

    fun esBloqueSentencia():Boolean{
        if (caracterActual == '>'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual != '<' && caracterActual != finCodigo){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            if (caracterActual=='<'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.BLOQUE_SENTENCIA,filaInicial,columnaInicial)
                return true
            }
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }

    fun esAgrupacion():Boolean{
        if (caracterActual == '"'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual != '"' && caracterActual != finCodigo){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            if (caracterActual=='"'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.BLOQUE_AGRUPACION,filaInicial,columnaInicial)
                return true
            }
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }

    fun esCaracterEscape():String{
        if (caracterActual == '°'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            when(caracterActual){
                's' -> {
                    lexema += caracterActual
                    almacenarToken(lexema,Categoria.CARACTER_ESCAPE_SALTO_LINEA,filaInicial,columnaInicial)

                    return "\n"
                }
                '°' -> {
                    lexema += caracterActual
                    almacenarToken(lexema,Categoria.CARACTER_ESCAPE_SIGNO_GRADO,filaInicial,columnaInicial)

                    return "°"
                }
                '^' -> {
                    lexema += caracterActual
                    almacenarToken(lexema,Categoria.CARACTER_ESCAPE_SIGNO_INTERCALACION,filaInicial,columnaInicial)
                    return ""
                }
                't' -> {
                    lexema += caracterActual
                    almacenarToken(lexema,Categoria.CARACTER_ESCAPE_TABULACION,filaInicial,columnaInicial)

                    return "\t"
                }
                else -> {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)

                    return ""
                }
            }
        }
        return ""
    }

    fun esCadena():Boolean{
        if (caracterActual=='^'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual != '^' && caracterActual != finCodigo){
                var caracterEscape = esCaracterEscape()
                if (caracterEscape.isNotEmpty()){
                    lexema += caracterEscape
                }
                else {
                    lexema += caracterActual
                }
                    obtenerSiguienteCaracter()
            }
            if (caracterActual=='^'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.CADENA_CARACTER,filaInicial,columnaInicial)
                return true
            }
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }

    fun esLogico():Boolean{

        var lexema = ""
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual

        if (caracterActual==='s'){

            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual=='i'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }else{
                return false
            }
            if(caracterActual=='m'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }else{
                return false
            }
            if(caracterActual=='o'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }else{
                return false
            }
            if(caracterActual=='n'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.RESERVADA, filaInicial, columnaInicial)
                return true
            }else{
                return false
            }
        }else{
            if (caracterActual=='n'){

                lexema += caracterActual
                obtenerSiguienteCaracter()
                if(caracterActual=='e'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }else{
                    return false
                }
                if (caracterActual=='l'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }else{
                    return false
                }
                if(caracterActual=='s'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }else{
                    return false
                }
                if(caracterActual=='o'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }else{
                    return false
                }
                if(caracterActual=='n'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.RESERVADA, filaInicial, columnaInicial)
                    return true
                }else{
                    return false
                }
            }
        }
        return false
    }

    fun esEntero():Boolean{
        if(caracterActual == '@') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual.isDigit()) {

                lexema += caracterActual
                obtenerSiguienteCaracter()

                while (caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                almacenarToken(lexema, Categoria.ENTERO, filaInicial, columnaInicial)
                return true
            }
        }
        return false
    }

    fun esDecimal():Boolean{
        if (caracterActual== '$'){

            var lexema=""
            var filaInicial= filaActual
            var columnaInicial= columnaActual

            lexema+= caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual=='~'||caracterActual.isDigit()){


            if(caracterActual=='~'){
                lexema+= caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual.isDigit()){
                    lexema+= caracterActual
                    obtenerSiguienteCaracter()
                }
            }else{
                lexema+= caracterActual
                obtenerSiguienteCaracter()

                while(caracterActual.isDigit()){
                    lexema+= caracterActual
                    obtenerSiguienteCaracter()
                }
                if(caracterActual=='~') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }

            }
            while(caracterActual.isDigit()){
                lexema+= caracterActual
                obtenerSiguienteCaracter()
            }
            almacenarToken(lexema, Categoria.DECIMAL,filaInicial,columnaInicial)
            return true
        }
}
        return false
    }

    fun esTipoCadena():Boolean{
        if(caracterActual=='P'){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual=='A'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }else{
                return false
            }
            if (caracterActual=='L') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.RESERVADA,filaInicial,columnaInicial)
                return true
            }else{
                return false
            }
        }
        return false
    }

    fun esTipoEntero():Boolean{
        if(caracterActual=='E'){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual=='N'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }else{
                return false
            }
            if (caracterActual=='T') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.RESERVADA,filaInicial,columnaInicial)
                return true
            }else{
                return false
            }
        }
        return false
    }

    fun esTipoReal():Boolean{
        if(caracterActual=='R'){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual=='E'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }else{
                return false
            }
            if (caracterActual=='L') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.RESERVADA,filaInicial,columnaInicial)
                return true
            }else{
                return false
            }
        }
        return false
    }

    fun esTipoLogico():Boolean{
        if(caracterActual=='L'){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual=='O'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }else{
                return false
            }
            if (caracterActual=='G'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }else{
                return false
            }
            if (caracterActual=='I') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.RESERVADA,filaInicial,columnaInicial)
                return true
            }else{
                return false
            }
        }
        return false
    }

    fun esConstante():Boolean{
        if(caracterActual=='C'){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual=='O'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }else{
                return false
            }
            if (caracterActual=='N'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }else{
                return false
            }
            if (caracterActual=='S') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.RESERVADA,filaInicial,columnaInicial)
                return true
            }else{
                return false
            }
        }
        
        return false
    }
    fun esConectorY() :Boolean{
        if(caracterActual == '%')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
    
    fun esIdentificador():Boolean{

            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema,Categoria.OPERADOR_LOGICO,filaInicial,columnaInicial)
            return true
        }
        return false
    }
    fun esConectorO() :Boolean{
        if(caracterActual == '!')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema,Categoria.OPERADOR_LOGICO,filaInicial,columnaInicial)
            return true
        }
        return false
    }
    fun esNegacion() :Boolean{
        if(caracterActual == '~')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema,Categoria.OPERADOR_LOGICO,filaInicial,columnaInicial)
            return true
        }
        return false
    }
    fun esOperadorMatematico () :Boolean{
        if(caracterActual == '&')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual == '+' || caracterActual == '-' || caracterActual == '*' || caracterActual == '/' ||caracterActual == '%'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.OPERADOR_ARITMETICO, filaInicial,columnaInicial)
                return true
            }else{
            return false}
        }
        return false
    }
    fun esOperadorAsignacion() :Boolean{
        if(caracterActual == ':'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual == '~')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.OPERADOR_ASIGNACION,filaInicial,columnaInicial)
                return true
            } else{
                return false
            }
        }
        return false
    }
    fun esOperadorInicial () :Boolean{
        if(caracterActual == '-'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema,Categoria.OPERADOR_INICIAL,filaInicial,columnaInicial)
            return true
        }
        return false
    }
    fun esCiclo():Boolean{
        if(caracterActual=='W'){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual=='H'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }else{
                return false
            }
            if (caracterActual=='E'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }else{
                return false
            }
            if (caracterActual=='N') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.RESERVADA,filaInicial,columnaInicial)
                return true
            }else{
                return false
            }
        }

        return false
    }
        if(caracterActual.isLowerCase() && caracterActual.isLetter()){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual.isLowerCase()  && caracterActual.isLetter() ){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            almacenarToken(lexema,Categoria.IDENTIFICADOR,filaInicial,columnaInicial)
            return true
        }
        return false
    }


     fun esClase():Boolean{
         if(caracterActual=='-'){
             var lexema = ""
             var filaInicial = filaActual
             var columnaInicial = columnaActual
             var posicionInicial = posicionActual

             lexema += caracterActual
             obtenerSiguienteCaracter()
             if (caracterActual.isLetter()){
                 lexema += caracterActual
                 obtenerSiguienteCaracter()

                 while (caracterActual.isLetter()){
                     lexema += caracterActual
                     obtenerSiguienteCaracter()
                 }
                 if (caracterActual=='-'){
                     lexema += caracterActual
                     obtenerSiguienteCaracter()

                     almacenarToken(lexema,Categoria.IDENTIFICADOR,filaInicial,columnaInicial)
                     return true
                 }else{
                     return false
                 }
             }else{
                 return false
             }
         }
         return false
     }
    fun obtenerSiguienteCaracter(){
        if(posicionActual==codigoFuente.length-1){
            caracterActual= finCodigo
        }else{
            if(caracterActual=='\n'){
                filaActual++
                columnaActual=0
            }else{
                columnaActual++
            }

            posicionActual++
            caracterActual=codigoFuente[posicionActual]
        }
    }
}