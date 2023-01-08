package com.my.rental.domain;

import com.my.rental.domain.enumeration.RentalStatus;
import com.my.rental.web.rest.errors.RentalUnavailableException;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Rental.
 */
@Entity
@Table(name = "rental")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Rental implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "rental_status")
    private RentalStatus rentalStatus;

    // 연체료
    @Column(name = "late_free")
    private Long lateFee;

    // 대출 아이템
    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @ToString.Exclude
    private Set<RentedItem> rentedItems = new HashSet<>();

    // 연체 아이템
    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @ToString.Exclude
    private Set<OverdueItem> overdueItems = new HashSet<>();

    // 반납 아이템
    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @ToString.Exclude
    private Set<ReturnedItem> returnedItems = new HashSet<>();


    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rental id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Rental userId(Long userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public RentalStatus getRentalStatus() {
        return this.rentalStatus;
    }

    public Rental rentalStatus(RentalStatus rentalStatus) {
        this.setRentalStatus(rentalStatus);
        return this;
    }



    public Rental addRentedItem(RentedItem rentedItem) {
        this.rentedItems.add(rentedItem);
        rentedItem.setRental(this);
        return this;
    }

    // Rental 엔티티 생성, 대출 생성 매서드
    public static Rental createRental(Long userId) {
        Rental rental = new Rental();
        rental.setUserId(userId); // Rental에 사용자 일련번호 부여
        rental.setRentalStatus(RentalStatus.RENT_AVAILABLE); // 대출 가능하도록 상태 변경
        rental.setLateFee(0L); // 연체료 초기화
        return rental;
    }



    // 대출 가능 여부 체크
    public boolean checkRentalAvailable() throws Exception {

        if (this.rentalStatus.equals(RentalStatus.RENT_UNAVAILABE) || this.getLateFee()!=0L) {
            throw new RentalUnavailableException("연체 상태입니다. 연체료를 정산 후, 도서를 대출하세요.");
        }

        if (this.getRentedItems().size()>=5) {
            throw new RentalUnavailableException("대출 가능한 도서의 수는" + (5-this.getRentedItems().size()) + "권 입니다.");
        }

        return true;
    }



    // 대출 처리 메서드
    public Rental rentBook(Long bookid, String title) {
        this.addRentedItem(RentedItem.createdRentedItem(bookid, title, LocalDate.now()));
        return this;
    }


    // 반납 처리 메서드
    public Rental returnBook(Long bookId) {

        RentedItem rentedItem = this.rentedItems.stream().filter(item -> item.getBookId().equals(bookId)).findFirst().get();

        this.addReturnedItem(ReturnedItem.createReturnedItem(rentedItem.getBookId(), rentedItem.getBookTitle(), LocalDate.now()));

        this.removeRentedItem(rentedItem);

        return this;
    }

    /*TODO: - Check here*/
    private void removeRentedItem(RentedItem rentedItem) {
        this.rentedItems.remove(rentedItem);
        rentedItem.setRental(null);
    }

    /*TODO: - Check here*/
    private void addReturnedItem(ReturnedItem returnedItem) {
        this.returnedItems.add(returnedItem);
        returnedItem.setRental(this);
    }


    public void setRentalStatus(RentalStatus rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    // prettier-ignore
    @Override
    public String toString() {
        return "Rental{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", rentalStatus='" + getRentalStatus() + "'" +
            "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }

        Rental rental = (Rental) o;

        return id != null && Objects.equals(id, rental.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
