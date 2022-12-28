package com.my.rental.domain.enumeration;

/**
 * The RentalStatus enumeration.
 */
public enum RentalStatus {
    RENT_AVAILABLE(0, "대출가능", "대출가능상태"),
    RENT_UNAVAILABE(1, "대출불가", "대출불가상태");

    private Integer id;
    private String title;
    private String description;

    RentalStatus(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
