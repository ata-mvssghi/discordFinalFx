package Request;

import java.io.Serial;
import java.io.Serializable;

public class Request implements Serializable {

    public enum Type {
        signIn,
        signUp,
    }

    public Request(Type type,String text) {
        this.type = type;
        this.text=text;
    }

    private Type type;
    private String text;
    public String getText(){
        return text;
    }

    public Type getType() {
        return type;
    }
}

