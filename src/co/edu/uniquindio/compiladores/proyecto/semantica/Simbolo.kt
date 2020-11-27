package co.edu.uniquindio.compiladores.proyecto.semantica


class Simbolo (){

    var nombre :String= ""
    var tipo:String= ""
    var modificable:Boolean= false
    var ambito:String?= ""
    var fila:Int= 0
    var columna:Int= 0
    var tiposParametros:ArrayList<String>?= null

    /**
     * Constructor para crear un simbolo de tipo valor
     */
    constructor(nombre:String, tipoDato:String, modificable:Boolean, ambito:String, fila:Int,columna:Int): this(){
        this.nombre=nombre
        this.tipo=tipoDato
        this.modificable=modificable
        this.ambito=ambito
        this.fila=fila
        this.columna=columna

    }

    /**
     * Constructor para crear simbolo de tipo metodo'
     */
    constructor(nombre:String, tipoRetorno:String, tiposParametros:ArrayList<String>, ambito:String):this(){
        this.nombre=nombre
        this.tipo=tipoRetorno
        this.tiposParametros=tiposParametros
        this.ambito=ambito

    }

    override fun toString(): String {

        return if(tiposParametros==null){
             "Simbolo(nombre='$nombre', tipo='$tipo', modificable=$modificable, ambito=$ambito, fila=$fila, columna=$columna"
        }else{
            "Simbolo(nombre='$nombre', tipo='$tipo', ambito=$ambito, tiposParametros=$tiposParametros"
        }
    }


}