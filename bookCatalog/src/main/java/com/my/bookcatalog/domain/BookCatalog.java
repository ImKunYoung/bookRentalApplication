package com.my.bookcatalog.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A BookCatalog.
 */
@Document(collection = "book_catalog")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BookCatalog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("title")
    private String title;

    @NotNull
    @Field("author")
    private String author;

    @Field("description")
    private String description;

    @Field("book_id")
    private Long bookId;

    @Field("rent_cnt")
    private Integer rentCnt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public BookCatalog id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public BookCatalog title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public BookCatalog author(String author) {
        this.setAuthor(author);
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return this.description;
    }

    public BookCatalog description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBookId() {
        return this.bookId;
    }

    public BookCatalog bookId(Long bookId) {
        this.setBookId(bookId);
        return this;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getRentCnt() {
        return this.rentCnt;
    }

    public BookCatalog rentCnt(Integer rentCnt) {
        this.setRentCnt(rentCnt);
        return this;
    }

    public void setRentCnt(Integer rentCnt) {
        this.rentCnt = rentCnt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookCatalog)) {
            return false;
        }
        return id != null && id.equals(((BookCatalog) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookCatalog{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", author='" + getAuthor() + "'" +
            ", description='" + getDescription() + "'" +
            ", bookId=" + getBookId() +
            ", rentCnt=" + getRentCnt() +
            "}";
    }
}
