

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Stack;

public class Sintactico {
    Nodo p, e;
    boolean errorBool = false;
    public static NodoVariables cabezaVariables = null, V, R;
    NodosPolish cabezaPolish = null;
    NodosPolish V2,V3;

    public String Var;

    String lexema = "";
    int tipo;
    int asig;
    int column1;
    int column2;
    int Var1;
    int Var2;
    int opera1 = 0;
    int opera2 = 0;

    String op1;
    String op2;
    String Temporal;

    int c1 = 0;
    int c2 = 0;

    public String comprobacion = "";
    public String comprobacion2 = "";

    int asignacion[][] = {
            {1,1,0,0},
            {1,1,0,0},
            {0,0,1,0},
            {0,0,0,1}
    };

    int suma[][] = {
            {1,1,0,0},
            {1,1,0,0},
            {0,0,1,0},
            {0,0,0,0}
    };

    int rmd[][] = {
            {1,1,0,0},
            {1,1,0,0},
            {0,0,0,0},
            {0,0,0,0}
    };

    int igualDif[][] = {
            {1,0,0,0},
            {0,1,0,0},
            {0,0,1,0},
            {0,0,0,1}
    };


    int relacionales[][] = {
            {1,1,0,0},
            {1,1,0,0},
            {0,0,0,0},
            {0,0,0,0}
    };

    int andOr[][] = {
            {0,0,0,0},
            {0,0,0,0},
            {0,0,0,0},
            {0,0,0,1}
    };

    Stack <Integer> E = new <Integer> Stack();
    Stack <Integer> I = new <Integer> Stack();
    Stack <Integer> P = new <Integer> Stack();
    Stack <Integer> P3 = new <Integer> Stack();
    Stack <Integer> S = new <Integer> Stack();
    Stack <Integer> T = new <Integer> Stack();

    Stack <Integer> Y = new <Integer> Stack();
    Stack <Integer> C = new <Integer> Stack();

    Stack <Integer> E3 = new <Integer> Stack();
    Stack <Integer> I3 = new <Integer> Stack();

    Stack <String> L = new <String> Stack();
    Stack <String> L2 = new <String> Stack();
    Stack <String> P2 = new <String> Stack();

    Stack <String> Stack = new <String> Stack();

    String errores[][] = {
            {"Se espera la palabra reservada 'ALGORTIMO'" ,                                                     "504" },
            {"Se espera un identificador" ,                                                                     "505" },
            {"Se espera ( " ,                                                                                   "506" },
            {"Se espera )" ,                                                                                    "507" },
            {"Se espera la palabra reservada 'ES'" ,                                                            "508" },
            {"Se espera : " ,                                                                                   "509" },
            {"Se espera el tipo de variable: ENTERO, DECIMAL, CADENA, LOGICO" ,                                 "510" },
            {"Se espera el asignador := " ,                                                                     "511" },
            {"Se espera la palabra reservada 'FIN'",                                                            "512" },
            {"Se espera la palabra reservada 'INICIO' ",                                                        "513" },
            {"Se espera la palabra reservada Fin_mientras ",                                                       "514" },
            {"Se espera la palabra reservada THEN ",                                                            "515" },
            {"Se espera la palabra reservada FIN_SI ",                                                          "516" },
            {"Se espera la palabra reservada DO  ",                                                             "517" },
            {"Se espera una comprobacion  ",                                                                    "518" },
            {"No se reconoce el operador numerico",                                                             "523" }
    };
    String erroresSem[][] = {
            {"Variable ya se encuentra establecida como nombre de la clase.",                                   "519" },
            {"Variable ya declarada" ,                                                                          "520" },
            {"Variable sin declarar" ,                                                                          "521" },
            {"Incompatibilidad de tipos" ,                                                                      "522" }
    };

    protected ArrayList<String> VarList = new ArrayList();

    Sintactico(Nodo cabezaVariableseza){
        p = cabezaVariableseza;
        try{
            while (p!=null){

                if(p.token == 212){ //ALGORITMO
                    p = p.sig;
                    if(p.token == 100){ // id
                        Var = p.lexema;
                        p = p.sig;
                        if(p.token == 115){ // (
                            p = p.sig;
                            if(p.token == 116){ // )
                                p = p.sig;
                                if(p.token == 217){ // ES
                                    p = p.sig;
                                    decl_variable();
                                    if(p.token == 213){ // INICIO
                                        Acciones();
                                        imprimirNodos();
                                        GenerarASM();
                                        p = p.sig;
                                        if(p.token == 214){ // FIN
                                            break;
                                        }else{
                                            imprimirMensajeError(512);
                                            break;
                                        }
                                    }else{
                                        imprimirMensajeError(513);
                                        break;
                                    }
                                }else{
                                    imprimirMensajeError(508);
                                    break;
                                }
                            }else{
                                imprimirMensajeError(507);
                                break;
                            }
                        }else{
                            imprimirMensajeError(506);
                            break;
                        }
                    }else{
                        imprimirMensajeError(505);
                        break;
                    }
                }else{
                    imprimirMensajeError(504);
                    break;
                }
            }
        }catch (Exception e){
            System.out.println("Fin de archivo inesperado"+e);
            errorBool = true;
        }
    }


    private void imprimirMensajeError(int numError){
        errorBool = true;

        for(String[] errore : errores){
            if(numError == Integer.valueOf(errore[1])){
                System.out.println("El error encrado es: " + errore[0] + " error "+ errore[1]
                        + " en el renglon " +p.renglon+ "\n" );

            }
        }
    }

    private void imprimirMensajeErrorSem(int numError){
        errorBool = true;

        for(String[] errore : erroresSem){
            if(numError == Integer.valueOf(errore[1])){
                System.out.println("El error encrado es: " + errore[0] + " error "+ errore[1]
                        + " en el renglon " +p.renglon+ "\n" );

            }
        }
    }

    private void insertarNodo(){
        NodoVariables NodoV = new NodoVariables(lexema, tipo, asig);
        if(cabezaVariables == null){
            cabezaVariables =   NodoV;
            V = cabezaVariables;
        }else{
            V.sig = NodoV;
            V = NodoV;
        }
    }

    private void CompararVariables(){
        R = cabezaVariables;
        while (R!=null){
            if(p.lexema.equals(R.lexema)){
                imprimirMensajeErrorSem(520);
            }
            R = R.sig;
        }
    }

    private void validarExistencia(){
        R = cabezaVariables;
        boolean existe = false;
        while (R!=null){
            if(p.lexema.equals(R.lexema)){
                existe = true;
                break;
            }
            R = R.sig;
        }
        if(existe == false){
            imprimirMensajeErrorSem(521);
        }
    }

    private void decl_variable() {

        if(p.token == 100){ //ID
            if(p.lexema.equals(Var)){
                imprimirMensajeErrorSem(519);
            }
            CompararVariables();
            lexema = p.lexema;
            p = p.sig;
            if(p.token== 119){ //:
                nombre_tipo_simple();
                p= p.sig;
                if(p.token== 118){ //;
                    p = p.sig;
                    decl_variable();
                }
            }else{
                imprimirMensajeError(509);
            }
        }else{
            imprimirMensajeError(505);
        }
    }

    private void nombre_tipo_simple() {
        p = p.sig;
        if(p.token == 218 || p.token == 219 || p.token == 220 || p.token == 221){
            //Se valido correctamente
            tipo = p.token;
            insertarNodo();
            lexema = "";
        }else{
            imprimirMensajeError(510);

        }
    }

    private void Acciones() {
        p = p.sig;

        switch(p.token){

            case 100: // id
                validarExistencia();
                ConvertirInfijoPostfijo();
                GenerarASMRel();
                PolishSalida();
                p= p.sig;
                if(p.token == 120){ // :=
                    p = p.sig;
                    expresion_numerica();
                }else{
                    imprimirMensajeError(511);
                }
                break;
            case 215: // LEER
                PolishSalida();
                p= p.sig;
                nombre_objeto();
                if(p.token == 118){ //;
                    Acciones();
                }
                break;
            case 216: // ESCRIBIR
                PolishSalida();
                p= p.sig;
                nombre_objeto();
                if(p.token == 118){ //;
                    Acciones();
                }
                break;
            case 205: // SI
                comprobacion2 = "Si";
                p = p.sig;

                c1++;
                T.push(c1);

                ConvertirInfijoPostfijo();
                GenerarASMRel();

                PolishSalida();

                expresion_logica();

                insertarNodoPolish("BrF A"+(T.peek()),0);
                if(p.token==206){ // ENTONCES
                    Acciones();
                    if(p.token == 208){ // SINO
                        insertarNodoPolish("BrI B"+T.peek(),0);
                        comprobacion = "Sino";
                        Acciones();
                    }
                    if(p.token == 207){ // FIN_SI
                        p = p.sig;
                        comprobacion = "Fin_si";
                        insertarNodoPolish("B"+T.peek(),0);
                        if(T.peek()>1){
                            T.pop();
                        }
                    }else{
                        imprimirMensajeError(516);
                    }
                }else{
                    imprimirMensajeError(515);
                }
                if(p.token == 118){ //;
                    Acciones();
                }
                break;
            case 209: // MIENTRAS
                comprobacion2 = "Mientras";
                p = p.sig;

                c2++;
                Y.push(c2);

                ConvertirInfijoPostfijo();
                GenerarASMRel();

                PolishSalida();

                expresion_logica();
                insertarNodoPolish("BrF C"+(Y.peek()),0);
                if(p.token == 210){ //DO
                    Acciones();
                    insertarNodoPolish("BrI D"+(Y.peek()),0);
                    if(p.token == 211){ //Fin_mientras
                        comprobacion = "Fin_mientras";
                        if(p.sig.token != 214){
                            p =p.sig;
                        }else{
                            insertarNodoPolish("C"+Y.peek(),0);
                        }
                    }else{
                        imprimirMensajeError(514);
                    }
                }else{
                    imprimirMensajeError(517);
                }
                if(p.token == 118){ //;
                    Acciones();
                }
                break;
            default:
                break;

        }
        if(p.token == 118){ //;
            Acciones();

        }
    }

    private void nombre_objeto() {
        if(p.token==100){ // ID
            validarExistencia();
            p = p.sig;
            if(p.token == 117){// ,
                p = p.sig;
                nombre_objeto();
            }
        }
    }

    private void expresion_numerica() {


        switch(p.token){

            case 115: // (
                p = p.sig;
                expresion_numerica();
                if(p.token == 116 ) // )
                {
                    p=p.sig;
                    expresion_numerica1();
                }else{
                    imprimirMensajeError(507);
                }
                break;
            case 106: // -
                p = p.sig;
                expresion_numerica();
                expresion_numerica1();
                break;
            case 100: //ID
                validarExistencia();
                p = p.sig;
                expresion_numerica1();
                break;
            case 103: //ND
                p = p.sig;
                expresion_numerica1();
                break;
            case 102: //NE
                p = p.sig;
                expresion_numerica1();
                break;
            case 203: //TRUE
                p = p.sig;
                expresion_numerica1();
                break;
            case 204: //FALSE
                p = p.sig;
                expresion_numerica1();
                break;
            case 104: //CADENA
                p = p.sig;
                expresion_numerica1();
                break;
            default:
                break;

        }

    }

    private void expresion_numerica1() {
        switch (p.token) {
            case 105: // +
                p = p.sig;
                expresion_numerica();
                expresion_numerica1();
                break;
            case 106: // -
                p = p.sig;
                expresion_numerica();
                expresion_numerica1();
                break;
            case 107: // *
                p = p.sig;
                expresion_numerica();
                expresion_numerica1();
                break;
            case 108: // /
                p = p.sig;
                expresion_numerica();
                expresion_numerica1();
                break;
            default:
                break;

        }

    }

    private void expresion_logica() {
        switch(p.token){

            case 115: // (
                p = p.sig;
                expresion_logica();
                if(p.token == 116 ) // )
                {
                    p=p.sig;
                    expresion_logica1();
                }else{
                    imprimirMensajeError(507);
                }
                break;
            case 200:// NOT
                p = p.sig;
                expresion_logica();
                expresion_logica1();
                break;
            case 100: //ID
                if(p.sig.token == 109 | p.sig.token == 110 | p.sig.token == 111 |
                        p.sig.token == 112 | p.sig.token == 113  | p.sig.token == 114 ){
                    expresion_relacional();
                    expresion_logica1();
                }else{
                    p = p.sig;
                    expresion_logica1();
                }
                break;
            case 203: // TRUE
                p =p.sig;
                expresion_numerica1();
                break;
            case 204: //FALSE
                p =p.sig;
                expresion_numerica1();
                break;
            default:
                expresion_relacional();
                expresion_logica1();
                break;

        }
    }

    private void expresion_logica1() {
        switch (p.token) {
            case 201: // AND
                p = p.sig;
                expresion_logica();
                expresion_logica1();
                break;
            case 202: // OR
                p = p.sig;
                expresion_logica();
                expresion_logica1();
                break;
            default:
                break;

        }
    }

    private void expresion_relacional() {
        expresion_numerica();
        operador_relacional();

    }

    private void operador_relacional() {
        switch (p.token) {
            case 109: // >
                p = p.sig;
                expresion_numerica();
                break;
            case 110: // >=
                p = p.sig;
                expresion_numerica();
                break;
            case 111: // <
                p = p.sig;
                expresion_numerica();
                break;
            case 112: // <=
                p = p.sig;
                expresion_numerica();
                break;
            case 113: // =
                p = p.sig;
                expresion_numerica();
                break;
            case 114: // <>
                p = p.sig;
                expresion_numerica();
                break;
            default:
                break;

        }
    }
//----------------------------------------------------------------------

    public void ConvertirInfijoPostfijo(){

        System.out.println("\n");

        E = new <Integer> Stack();
        I = new <Integer> Stack();
        P = new <Integer> Stack();
        S = new <Integer> Stack();

        Nodo l;
        l = p;
        E.push(115);
        while( l != null){
            if(l.token == 100){
                String var = l.lexema;
                validarExistenciaC(var);
                if(Var2 == 218){
                    E.push(102);
                    l = l.sig;
                }else if(Var2 == 219){
                    E.push(103);
                    l = l.sig;
                }else if (Var2 == 220){
                    E.push(104);
                    l = l.sig;
                }else if (Var2 == 221){
                    E.push(203);
                    l = l.sig;
                }
            }
            if(l.token == 118 || l.token == 214 || l.token == 206 || l.token == 210
                    || l.token == 207 || l.token == 208 || l.token == 211 ){

                if(l.token == 206 || l.token == 210 || l.token == 211){
                    break;
                }else{
                    E.push(116);
                    break;
                }

            }else{
                if(l.token == 120){
                    E.push(l.token);
                    E.push(115);
                    l = l.sig;
                }else{
                    E.push(l.token);
                    l = l.sig;
                }
            }
        }
        E.push(116);

        int a = E.size();
        for(int u=0; u<a ; u++){
            I.push(E.pop());
        }

        try {
            while (!I.isEmpty()) {

                switch (pref(I.peek())){
                    case 1:
                        P.push(I.pop());
                        break;
                    case 3:
                    case 4:
                        if(pref(I.peek()) == 3) {
                            while(P.peek() != (115)) {
                                S.push(P.pop());
                                System.out.println(S.peek());
                            }
                            P.pop();
                            I.pop();
                        }else if(pref(I.peek()) == 4){
                            while(pref(P.peek()) >= pref(I.peek())) {
                                S.push(P.pop());
                                System.out.println(S.peek());
                            }
                            P.push(I.pop());
                        }
                        else{
                            P.push(I.pop());
                        }
                        break;
                    case 5:
                        while(pref(P.peek()) >= pref(I.peek())) {
                            S.push(P.pop());
                            System.out.println(S.peek());
                        }
                        P.push(I.pop());
                        break;
                    case 6:
                        if(pref(I.peek()) == 6 ){
                            while(pref(P.peek()) >= pref(I.peek())) {
                                S.push(P.pop());
                                System.out.println(S.peek());
                            }
                            P.push(I.pop());
                        }
                        break;
                    case 2:
                        P.push(I.pop());
                        break;
                    default:
                        S.push(I.pop());
                        System.out.println(S.peek());
                }
            }
            System.out.println("\n");

        }catch(Exception ex){
            System.out.println("Hubo un error en la conversion.");
            System.err.println(ex);
        }

    }

    private static int pref(int op) {
        int prf = 99;
        if (op == (107) || op == (108)) prf = 6;
        if (op == (105) || op == (106)) prf = 5;
        if (op >= (109) && op <= (114)) prf = 4;
        if (op == (116) ) prf = 3;
        if (op == (115)) prf = 2;
        if (op == (117)) prf = 7;
        if (op == (120) || op >= (200) && op <= (202) || op == 215 || op == 216 ) prf = 1;
        return prf;
    }

    private void ComparacionT(){
        if(C.peek() == 102){
            opera1= C.pop();
            column1 = 0;
        }else if(C.peek() == 103){
            opera1= C.pop();
            column1 = 1;
        }else if(C.peek() == 104){
            opera1= C.pop();
            column1 = 2;
        }else if(C.peek() == 203 || C.peek() == 204){
            opera1= C.pop();
            column1 = 3;
        }

        if(C.peek() == 102){
            opera2= C.pop();
            column2 = 0;
        }else if(C.peek() == 103){
            opera2= C.pop();
            column2 = 1;
        }else if(C.peek() == 104){
            opera2= C.pop();
            column2 = 2;
        }else if(C.peek() == 203 || C.peek() == 204){
            opera2= C.pop();
            column2 = 3;

        }

    }

    private void GenerarASMRel() {

        opera1 = 0;
        opera2 = 0;

        Stack <Integer> Z = new <Integer> Stack(); //Pila entrada inversa
        C = new <Integer> Stack(); //Pila entrada inversa
        Stack <Integer> G = new <Integer> Stack(); //Pila entrada



        while (!S.isEmpty()) {
            if(S.peek() > 104 && S.peek() < 115 || S.peek() == 120 ||S.peek() >= 200 && S.peek() <= 202 ){
                Z.push(S.pop());
            }else{
                C.push(S.pop());
            }
        }
        while(!Z.isEmpty()){

            if(Z.peek() > 108 && Z.peek() < 115){
                Z.pop();
                ComparacionT();
                if(relacionales[column1][column2] == 1){
                    if(column1 == column2){
                        G.push(203);
                    }else{
                        if(opera1 == 103){
                            G.push(203);
                        }else{
                            G.push(203);
                        }
                    }
                }else{
                    imprimirMensajeErrorSem(522);
                }
            }else if(Z.peek() == 113 || Z.peek() == 114 ){
                Z.pop();
                ComparacionT();
                if(igualDif[column1][column2] == 1){
                    C.push(opera1);
                }else{
                    imprimirMensajeErrorSem(522);
                }
            }else if(Z.peek() == 201 || Z.peek() == 202 ){
                Z.pop();

                if(G.peek() == 102){
                    opera1= G.pop();
                    column1 = 0;
                }else if(G.peek() == 103){
                    opera1= G.pop();
                    column1 = 1;
                }else if(G.peek() == 104){
                    opera1= G.pop();
                    column1 = 2;
                }else if(G.peek() == 203 || G.peek() == 204){
                    opera1= G.pop();
                    column1 = 3;
                }

                if(G.peek() == 102){
                    opera2= G.pop();
                    column2 = 0;
                }else if(G.peek() == 103){
                    opera2= G.pop();
                    column2 = 1;
                }else if(G.peek() == 104){
                    opera2= G.pop();
                    column2 = 2;
                }else if(G.peek() == 203 || G.peek() == 204){
                    opera2= G.pop();
                    column2 = 3;
                }

                if(opera1 == opera2 || opera1 == 103 && opera2 == 102 || opera1 == 102 && opera2 == 103){
                    column1 = 3;
                    column2 = 3;
                    if(andOr[column1][column2] == 1){
                        if(column1 == column2){
                            C.push(203);
                        }else{
                            if(opera1 == 103){
                                C.push(203);
                            }else{
                                C.push(203);
                            }
                        }
                    }
                }else{
                    imprimirMensajeErrorSem(522);

                }

            }else if(Z.peek() == 200){
                Z.pop();
                if(G.peek() == 102){
                    opera1= G.pop();
                    column1 = 0;
                }else if(G.peek() == 103){
                    opera1= G.pop();
                    column1 = 1;
                }else if(G.peek() == 104){
                    opera1= G.pop();
                    column1 = 2;
                }else if(G.peek() == 203 || G.peek() == 204){
                    opera1= G.pop();
                    column1 = 3;
                }

                if(G.isEmpty()){
                    column2 = 99;
                }else{
                    if(G.peek() == 102){
                        opera2= G.pop();
                        column2 = 0;
                    }else if(G.peek() == 103){
                        opera2= G.pop();
                        column2 = 1;
                    }else if(G.peek() == 104){
                        opera2= G.pop();
                        column2 = 2;
                    }else if(G.peek() == 203 || G.peek() == 204){
                        opera2= G.pop();
                        column2 = 3;
                    }
                }

                if(column1 == 3 && column2 == 99){
                    C.push(opera1);
                }else{
                    imprimirMensajeErrorSem(522);
                }

            }else if(Z.peek() == 105){
                Z.pop();
                ComparacionT();
                if (suma[column1][column2] == 1) {
                    if (column1 == column2) {
                        C.push(opera1);
                    } else {
                        if (opera1 == 103) {
                            C.push(opera1);
                        } else {
                            C.push(opera2);
                        }
                    }
                } else {
                    imprimirMensajeErrorSem(522);
                }



            }else if(Z.peek() == 120){
                Z.pop();
                ComparacionT();
                if(asignacion[column1][column2] == 1){
                    if(column1 == column2){
                        C.push(opera1);
                    }else{
                        if(opera1 == 103){
                            C.push(opera1);
                        }else{
                            C.push(opera2);
                        }
                    }
                }else{
                    imprimirMensajeErrorSem(522);
                }
            }else if(Z.peek() == 106 || Z.peek() == 107 || Z.peek() == 108){
                Z.pop();
                ComparacionT();
                if(rmd[column1][column2] == 1){
                    if(column1 == column2){
                        C.push(opera1);
                    }else{
                        if(opera1 == 103){
                            C.push(opera1);
                        }else{
                            C.push(opera2);
                        }
                    }
                }else{
                    imprimirMensajeErrorSem(522);
                }

            }
        }
    }

    private void validarExistenciaC(String var){
        R = cabezaVariables;
        boolean existe = false;
        while (R!=null){
            if(var.equals(R.lexema)){
                existe = true;
                Var2 = R.tipo;
            }
            R = R.sig;
        }
        if(existe == false){
            imprimirMensajeErrorSem(521);
        }
    }


    private void insertarNodoPolish(String lex,int token){
        NodosPolish NodoSS = new NodosPolish(lex,token);
        if(cabezaPolish == null){
            cabezaPolish = NodoSS;
            V2 = cabezaPolish;
        }else{
            V2.sig = NodoSS;
            V2= NodoSS;
        }
    }

    private void imprimirNodos(){
        V3 = cabezaPolish;
        while (V3!=null){
            if( V3.token == 0){
                System.out.println(" Apuntador: " +V3.lexema );
            }else{
                System.out.println(" Lexema: " +V3.lexema );
            }
            V3 = V3.sig;
        }
    }

    public void PolishSalida(){

        E3 = new <Integer> Stack();
        I3 = new <Integer> Stack();
        L = new <String> Stack();
        L2 = new <String> Stack();
        P3 = new <Integer> Stack();
        P2 = new <String> Stack();

        Nodo l;
        l = p;
        E3.push(115);
        L.push("(");
        int temp = l.token;
        while( l != null){
            if(l.token == 118 || l.token == 214 || l.token == 206 || l.token == 210
                    || l.token == 207 || l.token == 208 || l.token == 211 ){
                if(l.token == 206 || l.token == 210 || l.token == 211){
                    break;
                }else{
                    if(temp == 216 || temp == 215){

                    }else{
                        E3.push(116);
                        L.push(")");
                    }
                    break;
                }
            }else{
                if(l.token == 120){
                    E3.push(l.token);
                    L.push(l.lexema);
                    E3.push(115);
                    L.push("(");
                    l = l.sig;
                }else{
                    E3.push(l.token);
                    L.push(l.lexema);
                    l = l.sig;
                }
            }
        }
        E3.push(116);
        L.push(")");

        int a = E3.size();
        for(int u=0; u<a ; u++){
            I3.push(E3.pop());
            L2.push(L.pop());
        }

        try {
            while (!I3.isEmpty()) {
                switch (pref(I3.peek())){
                    case 1:
                        P3.push(I3.pop());
                        P2.push(L2.pop());
                        break;
                    case 3:

                    case 4:
                        if(pref(I3.peek()) == 3) {
                            while(P3.peek() != (115)) {
                                insertarNodoPolish(P2.pop(),P3.pop());
                            }
                            P3.pop();
                            I3.pop();
                            L2.pop();
                            P2.pop();
                        }else if(pref(I3.peek()) == 4){
                            while(pref(P3.peek()) >= pref(I3.peek())) {
                                insertarNodoPolish(P2.pop(),P3.pop());
                            }
                            P3.push(I3.pop());
                            P2.push(L2.pop());
                        }
                        else{
                            P3.push(I3.pop());
                            P2.push(L2.pop());
                        }
                        break;
                    case 5:
                        while(pref(P3.peek()) >= pref(I3.peek())) {
                            insertarNodoPolish(P2.pop(),P3.pop());
                        }
                        P3.push(I3.pop());
                        P2.push(L2.pop());
                        break;
                    case 6:
                        if(pref(I3.peek()) == 6 ){
                            while(pref(P3.peek()) >= pref(I3.peek())) {
                                insertarNodoPolish(P2.pop(),P3.pop());
                            }
                            P3.push(I3.pop());
                            P2.push(L2.pop());
                        }
                        break;
                    case 2:
                        P3.push(I3.pop());
                        P2.push(L2.pop());
                        break;
                    case 7:
                        I3.pop();
                        L2.pop();
                        break;
                    default:
                        if(comprobacion == "Sino"){

                            if(comprobacion2 == "Mientras"){
                                insertarNodoPolish("A"+T.peek(),0);
                                insertarNodoPolish("D"+Y.peek(),0);
                                insertarNodoPolish(L2.pop(),I3.pop());
                                comprobacion2= "";
                            }else{
                                insertarNodoPolish("A"+T.peek(),0);
                                insertarNodoPolish(L2.pop(),I3.pop());
                            }
                            comprobacion= "";

                        }else if(comprobacion == "Fin_mientras"){
                            insertarNodoPolish("C"+Y.peek(),0);
                            insertarNodoPolish(L2.pop(),I3.pop());
                            Y.pop();
                            comprobacion= "";
                        }else if(comprobacion == "Fin_si"){
                            if(comprobacion2 == "Si"){
                                T.pop();
                                insertarNodoPolish("B"+T.peek(),0);
                                insertarNodoPolish(L2.pop(),I3.pop());
                                T.push(c1);
                            }else if(comprobacion2 == "Mientras"){
                                insertarNodoPolish("B"+T.peek(),0);
                                insertarNodoPolish("D"+Y.peek(),0);
                                insertarNodoPolish(L2.pop(),I3.pop());
                                T.pop();
                            }else{
                                insertarNodoPolish("B"+T.peek(),0);
                                insertarNodoPolish(L2.pop(),I3.pop());
                                T.pop();
                            }
                            comprobacion= "";
                        }else if(comprobacion2 == "Mientras"){
                            insertarNodoPolish("D"+Y.peek(),0);
                            insertarNodoPolish(L2.pop(),I3.pop());
                            comprobacion2= "";
                        }else{
                            insertarNodoPolish(L2.pop(),I3.pop());
                        }
                }
            }
        }catch(Exception ex){
            System.out.println("Error al convertir a postfijo");
            System.err.println(ex);
        }
    }

    public void addToList(String cadena){
        VarList.add(cadena);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
    private void GenerarASM() {

        Stack = new <String> Stack(); //Operandos

        String ruta = "C:/masm614/asm/compi.ASM";

        String TraduccionASM = "\n\nINCLUDE MACROS.mac\n" +
                ".MODEL SMALL\n" +
                ".586\n"+
                ".STACK 100h\n" +
                ".DATA\n";

        TraduccionASM += "\n\t\t\tProvisional db ?";

        //Encontrar variables de tipo cadena (220)
        NodoVariables x;
        x = cabezaVariables;

        while(x!=null){
            if(x.tipo == 220){
                TraduccionASM += ""+x.lexema+" ;/temporal, 13, 10,'$'";
                addToList(x.lexema);
            }else{
                TraduccionASM += "\n\t\t\t"+x.lexema+" db"+" ?" ;
            }
            x = x.sig;
        }
        //

        TraduccionASM += "\n\t\t\t;/Declarar";
        TraduccionASM += "\n.CODE\n"+
                ".STARTUP\n"+
                "\t\t\tMOV     AX, @DATA\n" +
                "\t\t\tMOV     DS, AX\n" +
                "\t\t\tCALL  COMPI\n" +
                "\t\t\tMOV AX, 4C00H\n" +
                "\t\t\tINT 21H\n" +
                "COMPI  PROC\n";;

        //Proceso de traduccion a ASM
        V3 = cabezaPolish;
        int c = 0;
        while (V3 !=null){
            if( V3.token > 104 && V3.token < 115 || V3.token == 120
                    || V3.token >= 200 && V3.token <= 202 || V3.token == 215 || V3.token == 216 || V3.token == 0 ){
                String Opera = V3.lexema;
                if("+".equals(Opera)){

                    op1 = Stack.pop();
                    op2 =  Stack.pop();

                    TraduccionASM += "\t\t\tSUMAR "+op1+","+op2+", VarTemp"+c+"\n";

                    Temporal = "VarTemp"+c;
                    Stack.push(Temporal);
                    TraduccionASM = reemplazar(TraduccionASM,";/Declarar","VarTemp"+c+" db"+" ?"+"\n\t\t\t;/Declarar");

                }else if("-".equals(Opera)){

                    op2 = Stack.pop();
                    op1 =  Stack.pop();

                    TraduccionASM += "\t\t\tRESTA "+op1+","+op2+", VarTemp"+c+"\n";

                    Temporal = "VarTemp"+c;
                    Stack.push(Temporal);
                    TraduccionASM = reemplazar(TraduccionASM,";/Declarar","VarTemp"+c+" db"+" ?"+"\n\t\t\t;/Declarar");

                }else if("/".equals(Opera)){

                    op2 = Stack.pop();
                    op1 =  Stack.pop();

                    TraduccionASM += "\t\t\tDIVIDE "+op1+","+op2+", VarTemp"+c+"\n";

                    Temporal = "VarTemp"+c;
                    Stack.push(Temporal);
                    TraduccionASM = reemplazar(TraduccionASM,";/Declarar","VarTemp"+c+" db"+" ?"+"\n\t\t\t;/Declarar");

                }else if("*".equals(Opera)){

                    op2 = Stack.pop();
                    op1 =  Stack.pop();

                    TraduccionASM += "\t\t\tMULTI "+op1+","+op2+", VarTemp"+c+"\n";

                    Temporal = "VarTemp"+c;
                    Stack.push(Temporal);
                    TraduccionASM = reemplazar(TraduccionASM,";/Declarar","VarTemp"+c+" db"+" ?"+"\n\t\t\t;/Declarar");

                }else if(":=".equals(Opera)){

                    op2 = Stack.pop();
                    op1 =  Stack.pop();
                    boolean Flag = true;

                    for (int i = 0; i < VarList.size(); i++){
                        if(VarList.get(i).equals(op1)){
                            TraduccionASM = reemplazar(TraduccionASM,""+op1+" ;/temporal","\n\t\t\t"+op1+" db "+op2);
                            Flag = false;
                            break;
                        }
                    }

                    if(Flag){
                        TraduccionASM += "\t\t\tI_ASIGNAR "+op1+","+op2+"\n";
                    }

                }else if("=".equals(Opera)){

                    op2 = Stack.pop();
                    op1 =  Stack.pop();

                    TraduccionASM += "\t\t\tI_IGUAL "+op1+","+op2+", VarTemp"+c+"\n";

                    Temporal = "VarTemp"+c;
                    Stack.push(Temporal);
                    TraduccionASM = reemplazar(TraduccionASM,";/Declarar","VarTemp"+c+" db"+" ?"+"\n\t\t\t;/Declarar");

                }else if(">".equals(Opera)){

                    op2 = Stack.pop();
                    op1 =  Stack.pop();

                    TraduccionASM += "\t\t\tI_MAYOR "+op1+","+op2+", VarTemp"+c+"\n";

                    Temporal = "VarTemp"+c;
                    Stack.push(Temporal);
                    TraduccionASM = reemplazar(TraduccionASM,";/Declarar","VarTemp"+c+" db"+" ?"+"\n\t\t\t;/Declarar");

                }else if("<".equals(Opera)){

                    op2 = Stack.pop();
                    op1 =  Stack.pop();

                    TraduccionASM += "\t\t\tI_MENOR "+op1+","+op2+", VarTemp"+c+"\n";

                    Temporal = "VarTemp"+c;
                    Stack.push(Temporal);
                    TraduccionASM = reemplazar(TraduccionASM,";/Declarar","VarTemp"+c+" db"+" ?"+"\n\t\t\t;/Declarar");

                }else if(">=".equals(Opera)){

                    op2 = Stack.pop();
                    op1 =  Stack.pop();

                    TraduccionASM += "\t\t\tI_MAYORIGUAL "+op1+","+op2+", VarTemp"+c+"\n";

                    Temporal = "VarTemp"+c;
                    Stack.push(Temporal);
                    TraduccionASM = reemplazar(TraduccionASM,";/Declarar","VarTemp"+c+" db"+" ?"+"\n\t\t\t;/Declarar");

                }else if("<=".equals(Opera)){

                    op2 = Stack.pop();
                    op1 =  Stack.pop();

                    TraduccionASM += "\t\t\tI_MENORIGUAL "+op1+","+op2+", VarTemp"+c+"\n";

                    Temporal = "VarTemp"+c;
                    Stack.push(Temporal);
                    TraduccionASM = reemplazar(TraduccionASM,";/Declarar","VarTemp"+c+" db"+" ?"+"\n\t\t\t;/Declarar");

                }else if("<>".equals(Opera)){

                    op2 = Stack.pop();
                    op1 =  Stack.pop();

                    TraduccionASM += "\t\t\tI_DIFERENTES "+op1+","+op2+", VarTemp"+c+"\n";

                    Temporal = "VarTemp"+c;
                    Stack.push(Temporal);
                    TraduccionASM = reemplazar(TraduccionASM,";/Declarar","VarTemp"+c+" db"+" ?"+"\n\t\t\t;/Declarar");

                }else if("Leer".equals(Opera)){

                    op1 =  Stack.pop();

                    boolean Flag = false;
                    for (int i = 0; i < VarList.size(); i++){
                        if(VarList.get(i).equals(op1)){
                            Flag = true;
                            break;
                        }
                    }

                    if(Flag){
                        String R = ""+op1+" ;/temporal";
                        boolean Flag2 = TraduccionASM.indexOf(R)>=0;
                        if(Flag2){
                            TraduccionASM = reemplazar(TraduccionASM,R,"\n\t\t\t"+R);
                            TraduccionASM = reemplazar(TraduccionASM,";/temporal"," db 0");
                        }
                    }

                    TraduccionASM += "\t\t\tLEE "+op1+"\n";

                }else if("Escribir".equals(Opera)){

                    op1 = Stack.pop();

                    boolean Flag = false;

                    for (int i = 0; i < VarList.size(); i++){
                        if(VarList.get(i).equals(op1)){
                            Flag = true;
                            break;
                        }
                    }

                    if(Flag){
                        TraduccionASM += "\t\t\tLEELN "+op1+"\n";
                    }else{
                        TraduccionASM += "\t\t\tWRITE "+op1+"\n";
                    }

                }else if(Opera.startsWith("BrF")){

                    String Apuntador = Opera.substring(4,6);
                    int t = c;
                    TraduccionASM += "JF VarTemp"+(t-1)+","+Apuntador+"\n";

                }else if(Opera.startsWith("BrI")){

                    String Apuntador = Opera.substring(4,6);
                    TraduccionASM += "JMP "+Apuntador+"\n";

                }else if(Opera.startsWith("A") || Opera.startsWith("B")
                        || Opera.startsWith("C") || Opera.startsWith("D")){

                    String Apuntador = Opera.substring(0,2);
                    TraduccionASM += ""+Apuntador+":\n";
                }
                c++;
            }else{
                Stack.push(V3.lexema);
            }
            V3 = V3.sig;
        }

        TraduccionASM += "\t\t\tret\n" +
                "COMPI  ENDP\n"+
                ".EXIT\n"+
                "END";

        //Creacion del archivo
        try {
            File file = new File(ruta);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(TraduccionASM);
            bw.close();
            System.out.println("\n\nSe ha creado el archivo.");
        } catch (Exception e) {
            System.out.println("\n\nError al crear archivo.");
        }

    }

    public static String reemplazar(String cadena, String busqueda, String reemplazo) {
        return cadena.replaceAll(busqueda, reemplazo);
    }
}