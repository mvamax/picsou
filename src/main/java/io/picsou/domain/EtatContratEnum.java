package io.picsou.domain;

public enum EtatContratEnum {

	EN_COURS(10), 
	EN_ATTENTE_PAIEMENT(20),
	TERMINE(30),
	ANNULE(90);

    private final long value;
    
    private EtatContratEnum(int value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
