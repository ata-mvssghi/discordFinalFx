package Response;

import java.io.Serializable;

public class Response implements Serializable {
    public enum  Type{
        signIn,
        signup
    }
    public Type type;
    private String text;

    public Response(Type type, String text) {
        this.type = type;
        this.text = text;
    }

    public Type getType() {
        return type;
    }

    public String getText() {
        return text;
    }
}
