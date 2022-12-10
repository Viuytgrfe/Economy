package me.vineer.economyapi.money;

public enum MoneyType {
    MONEY("money"),
    DONATE_MONEY("donate_money");

    private String name;

    MoneyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static MoneyType getEnum(String value) {
        for(MoneyType v : values())
            if(v.getName().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}
