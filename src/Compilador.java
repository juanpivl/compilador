public class Compilador {

    public static void main(String[] args) {
        Lexico lex = new Lexico();
        if(!lex.errorBool){
            System.out.println("Lexico Completado");

            Sintactico sintact = new Sintactico(Lexico.cabeza);
            if(!sintact.errorBool){
                System.out.println("Sintactico Completado");
            }

        }
    }

}
