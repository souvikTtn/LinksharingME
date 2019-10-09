package enums

enum Seriousness {
    SERIOUS("Serious"), CASUAL("Casual"), VERY_SERIOUS("Very Serious")
    String value
    Seriousness(String value){
        this.value=value
    }
    static Seriousness stringToEnum(String value){
        return valueOf(value.toUpperCase())
    }
}