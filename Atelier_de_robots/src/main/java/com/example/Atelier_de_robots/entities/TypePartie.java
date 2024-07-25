package com.example.Atelier_de_robots.entities;

public enum TypePartie {
    BRAS("Bras", 2),
    JAMBE("Jambe", 2),
    TORSE("Torse", 1),
    BATTERIE("Batterie", 1),
    PUCE_ELECTRONIQUE("Puce_électronique", 1),
    RENFORCEMENT("Renforcement pour lever de charges lourdes", 1),
    MODULE_SOUDE("Module de soudure", 1),
    EQUIPEMENTS_SERRAGE("Equipements de serrage", 1),
    SCANNER_BIOMETRIQUE("Scanner biométrique", 1),
    DISTRIBUTEUR_MEDICAMENT("Distributeur de médicament", 1),
    MODULE_PERSONNALITE("Module de personnalité 'Infirmière'", 1);

    private final String label;
    private final int maxCount;

    TypePartie(String label, int maxCount) {
        this.label = label;
        this.maxCount = maxCount;
    }

    public String getLabel() {
        return label;
    }

    public int getMaxCount() {
        return maxCount;
    }
}
