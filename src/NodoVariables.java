public class NodoVariables {
    String lexema;
    int tipo;
    int asig;
    NodoVariables sig = null;

    NodoVariables(String lexema, int tipo, int asig)
    {
        this.lexema = lexema;
        this.tipo = tipo;
        this.asig = asig;

    }
}