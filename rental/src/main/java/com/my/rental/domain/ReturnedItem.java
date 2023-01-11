package com.my.rental.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ReturnedItem.
 */
@Entity
@Table(name = "returned_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class ReturnedItem implements Serializable {

    private static final long serialVersionUID = 1L;

    // 반납아이템 일련 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 반납한 재고 도서 일랸번호 (도서 서비스에서 발행한 재고 도서 일련 번호)
    @Column(name = "book_id")
    private Long bookId;

    // 반납 일자
    @Column(name = "returned_date")
    private LocalDate returnedDate;

    // 반납 도서명
    @Column(name = "book_title")
    private String bookTitle;

//    @ManyToOne
//    @JsonIgnoreProperties("returnedItems")
//    private Rental rental;

    // 반납 아이템 생성 메서드
    public static ReturnedItem createReturnedItem(Long bookId, String bookTitle, LocalDate now) {
        ReturnedItem returnedItem = new ReturnedItem();
        returnedItem.setBookId(bookId);
        returnedItem.setBookTitle(bookTitle);
        returnedItem.setReturnedDate(now);
        return returnedItem;
    }

    public ReturnedItem bookId(Long bookId) {
        this.bookId = bookId;
        return this;

    }

    public ReturnedItem returnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
        return this;
    }

    public ReturnedItem bookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

//    public ReturnedItem rental(Rental rental) {
//        this.rental = rental;
//        return this;
//    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReturnedItem that = (ReturnedItem) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
