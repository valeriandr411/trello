package support;

public enum Color {
    ORANGE("OoK0QIe8cQzHgi"),
    BRICK("Mb+bBXR-YIjjuV"),
    YELLOW("tcDk2-PZyhPsAv"),
    GREEN("kx4oqRlqCFjWrx"),
    LIGHT_GREEN("eXRlaBda222+jb"),
    PINK("IxsrVj140xH-KZ"),
    PURPLE("E-0yILhbxErEtq"),
    LIGHT_BLUE("_76EAKzRColnwhq"),
    BLUE("Nnu0sImk+bPR2w"),
    DARK_BLUE("FQ9quRvSj34qg3");
    //Текущий цвет карточки обозначается
    private String code;

     Color(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
