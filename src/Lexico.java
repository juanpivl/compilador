

import java.io.RandomAccessFile;

public class Lexico {
    public static Nodo cabeza = null, p;
    int estado = 0, columna, valorMT, numRenglon = 1;
    int caracter = 0;
    String lexema = "";
    Boolean errorBool = false;

    String archivo = "C:\\Users\\juanp\\Desktop\\compiladooooor\\src\\codigo.txt";

    int matriz[][] = {
            // l         d        _       .        '       +         -       *       /         >        <       =       (       )      ,       ;        :      {       }        eb      rt      tab     nl      eof     oc
            //  0        1        2       3        4       5         6       7       8         9       10       11      12      13     14      15      16      17      18       19      20      21      22      23       24
            {      1,       2,      500,    500,      5,     105,      106,    107,    108,       6,      7,      113,    115,    116,    117,    118,    8,       9,     500,      0,     0,      0,      0,      0,     500},
            {      1,       1,       1,     100,     100,    100,      100,    100,    100,     100,      100,    100,    100,    100,    100,    100,    100,    100,    100,    100,    100,    100,    100,    100,    100},
            {    102,       2,      102,     3,      102,    102,      102,    102,    102,     102,      102,    102,    102,    102,    102,    102,    102,    102,    102,    102,    102,    102,    102,    102,    102},
            {    501,       4,      501,    501,     501,    501,      501,    501,    501,     501,      501,    501,    501,    501,    501,    501,    501,    501,    501,    501,    501,    501,    501,    501,    501},
            {    103,       4,      103,    103,     103,    103,      103,    103,    103,     103,      103,    103,    103,    103,    103,    103,    103,    103,    103,    103,    103,    103,    103,    103,    103},
            {      5,       5,       5,      5,      104,      5,        5,      5,      5,       5,        5,      5,      5,      5,      5,      5,      5,      5,      5,      5,     5,      5,    503,     503,      5},
            {    109,     109,      109,    109,     109,    109,      109,    109,    109,     109,      109,    110,    109,    109,    109,    109,    109,    109,    109,    109,    109,    109,    109,    109,    109},
            {    111,     111,      111,    111,     111,    111,      111,    111,    111,     111,      114,    112,    111,    111,    111,    111,    111,    111,    111,    111,    111,    111,    111,    111,    111},
            {    119,     119,      119,    119,     119,    119,      119,    119,    119,     119,      119,    120,    119,    119,    119,    119,    119,    119,    119,    119,    119,    119,    119,    119,    119},
            {      9,       9,       9,      9,       9,      9,        9,      9,      9,       9,        9,      9,      9,      9,      9,      9,      9,      9,      0,      9,     502,      9,      9,    502,     9}


    };

    String PalabraReservada[][]=
            {
                    {"Not" ,           "200" },
                    {"And" ,           "201" },
                    {"Or" ,            "202" },
                    {"True" ,          "203" },
                    {"False" ,         "204" },
                    {"Si" ,            "205" },
                    {"Entonces",           "206" },
                    {"Fin_si" ,        "207" },
                    {"Sino" ,          "208" },
                    {"Mientras" ,      "209" },
                    {"Hacer" ,         "210" },
                    {"Fin_mientras" ,  "211" },
                    {"Algoritmo" ,     "212" },
                    {"Inicio" ,        "213" },
                    {"Fin" ,           "214" },
                    {"Leer" ,          "215" },
                    {"Escribir" ,      "216" },
                    {"Es" ,            "217" },
                    {"Entero" ,        "218" },
                    {"Decimal" ,       "219" },
                    {"Cadena" ,        "220" },
                    {"Logico" ,        "221" }

            };



    String errores[][] =
            {
                    {"Carácter no valido en el sistema" ,        "500" },
                    {"Se espera un digito" ,                     "501" },
                    {"Se esperaba } para cerrar comentario" ,    "502" },
                    {"Se espera ' para cerrar cadena" ,          "503" }

            };

    RandomAccessFile file = null;

    public Lexico()
    {
        try
        {
            file = new RandomAccessFile(archivo,"r");

            while (caracter != -1)
            {
                caracter = file.read();

                if (Character.isLetter(((char)caracter))) {

                    columna = 0;

                }else if (Character.isDigit(((char)caracter))){

                    columna = 1;
                } else {
                    switch ((char) caracter){

                        case '_':
                            columna = 2;
                            break;
                        case '.':
                            columna = 3;
                            break;
                        case 39 :
                            columna = 4;
                            break;
                        case '+':
                            columna = 5;
                            break;
                        case '-':
                            columna = 6;
                            break;
                        case '*':
                            columna = 7;
                            break;
                        case '/':
                            columna = 8;
                            break;
                        case '>':
                            columna = 9;
                            break;
                        case '<':
                            columna = 10;
                            break;
                        case '=':
                            columna = 11;
                            break;
                        case '(':
                            columna = 12;
                            break;
                        case ')':
                            columna = 13;
                            break;
                        case ',':
                            columna = 14;
                            break;
                        case ';':
                            columna = 15;
                            break;
                        case ':':
                            columna = 16;
                            break;
                        case '{':
                            columna = 17;
                            break;
                        case '}':
                            columna = 18;
                            break;
                        case ' ':
                            columna = 19;
                            break;
                        case  10 :
                            columna = 22; //Nueva linea
                            numRenglon = numRenglon +1;
                            break;
                        case  9 :
                            columna = 21; //Tabulador
                            break;
                        case 13 :
                            columna = 20; //Retorno de carro
                            break;
                        default:
                            columna = 24;
                            break;
                    }
                    if (caracter == -1){
                        columna = 23; // eof

                    }
                }
                valorMT = matriz[estado][columna];

                if(valorMT < 100) {
                    estado = valorMT;

                    if(estado == 0){
                        lexema = "";
                    }else{
                        lexema = lexema+(char) caracter;
                    }
                }else if( valorMT >= 100 && valorMT < 500){
                    if(valorMT == 100){
                        validarSiEsPalabraReservada();
                    }

                    if(valorMT ==100 || valorMT == 102 || valorMT ==103 || valorMT == 109
                            || valorMT == 111 || valorMT == 119 || valorMT >= 200 ){
                        file.seek(file.getFilePointer()-1);

                    }else{
                        lexema = lexema + (char) caracter;
                    }

                    insertarNodo();
                    estado= 0;
                    lexema = "";
                }else{
                    imprimirMensajeError();
                    break;
                }


            }
            imprimirNodos();
        }catch (Exception e){System.out.println(e.getMessage()); }

    }


    private void imprimirNodos(){
        p = cabeza;
        while (p!=null){
            System.out.println(p.lexema + " " + p.token + " " + p.renglon);
            p = p.sig;
        }
    }

    private void imprimirMensajeError(){
        if(valorMT >= 500){
            for(String[] errore : errores){
                if(valorMT == Integer.valueOf(errore[1])){
                    System.out.println("El error encontrado es: " + errore[0] + " error "+ valorMT
                            + " caracter "+caracter+ " en el renglon " +numRenglon+ "\n");
                }
            }
            errorBool = true;
        }
    }

    private void validarSiEsPalabraReservada(){

        for (String[] palReservada : PalabraReservada){
            if(lexema.equals(palReservada[0])){
                valorMT = Integer.valueOf(palReservada[1]);
            }
        }
    }


    private void insertarNodo(){
        Nodo Nodo = new Nodo(lexema, valorMT, numRenglon);
        if(cabeza == null){
            cabeza =    Nodo;
            p = cabeza;
        }else{
            p.sig = Nodo;
            p = Nodo;
        }
    }
}
