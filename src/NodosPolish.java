public class NodosPolish {
    String lexema;
    int token;
    NodosPolish sig = null;

    NodosPolish(String lexema, int token)
    {
        this.lexema = lexema;
        this.token = token;
    }
}