package support;

public enum Color {
    ORANGE("Y61EngcCUSch7G"),
    BRICK("ccKK61gi4PnIKL"),
    YELLOW("FGbdNCziUilgoc"),
    GREEN("E60PMA8W2pE823"),
    LIGHT_GREEN("YWomjSyjZMZ6Tm"),
    PINK("K8dwyVpuMbtVle"),
    PURPLE("GziGRL0ZgWBUcv"),
    LIGHT_BLUE("vWkTM7d2SPHQQK"),
    BLUE("EVyyZCOtkE_OoN"),
    DARK_BLUE("OERnjPadUN5AwX");
    //Текущий цвет карточки обозначается
    private final String code;

     Color(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
