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
            if(esEntero()) continue
            if(esDecimal())continue
            if(esCadena()) continue
            if(esLogico()) continue

            almacenarToken(""+caracterActual, Categoria.DESCONOCIDO,filaActual,columnaActual)
            obtenerSiguienteCaracter()
        }
    }

    fun esCadena():Boolean{
        if (caracterActual=='^'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual.isLetterOrDigit()||caracterActual == ' ' || caracterActual=='\t' || caracterActual=='\n'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            if (caracterActual=='^'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.CADENA_CARACTER,filaInicial,columnaInicial)
                return true
            }
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