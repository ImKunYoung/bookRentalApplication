package com.my.bookcatalog.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.my.bookcatalog.IntegrationTest;
import com.my.bookcatalog.domain.BookCatalog;
import com.my.bookcatalog.repository.BookCatalogRepository;
import com.my.bookcatalog.service.dto.BookCatalogDTO;
import com.my.bookcatalog.service.mapper.BookCatalogMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link BookCatalogResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BookCatalogResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_BOOK_ID = 1L;
    private static final Long UPDATED_BOOK_ID = 2L;

    private static final Integer DEFAULT_RENT_CNT = 1;
    private static final Integer UPDATED_RENT_CNT = 2;

    private static final String ENTITY_API_URL = "/api/book-catalogs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private BookCatalogRepository bookCatalogRepository;

    @Autowired
    private BookCatalogMapper bookCatalogMapper;

    @Autowired
    private MockMvc restBookCatalogMockMvc;

    private BookCatalog bookCatalog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookCatalog createEntity() {
        BookCatalog bookCatalog = new BookCatalog()
            .title(DEFAULT_TITLE)
            .author(DEFAULT_AUTHOR)
            .description(DEFAULT_DESCRIPTION)
            .bookId(DEFAULT_BOOK_ID)
            .rentCnt(DEFAULT_RENT_CNT);
        return bookCatalog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookCatalog createUpdatedEntity() {
        BookCatalog bookCatalog = new BookCatalog()
            .title(UPDATED_TITLE)
            .author(UPDATED_AUTHOR)
            .description(UPDATED_DESCRIPTION)
            .bookId(UPDATED_BOOK_ID)
            .rentCnt(UPDATED_RENT_CNT);
        return bookCatalog;
    }

    @BeforeEach
    public void initTest() {
        bookCatalogRepository.deleteAll();
        bookCatalog = createEntity();
    }

    @Test
    void createBookCatalog() throws Exception {
        int databaseSizeBeforeCreate = bookCatalogRepository.findAll().size();
        // Create the BookCatalog
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);
        restBookCatalogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookCatalogDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BookCatalog in the database
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeCreate + 1);
        BookCatalog testBookCatalog = bookCatalogList.get(bookCatalogList.size() - 1);
        assertThat(testBookCatalog.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBookCatalog.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testBookCatalog.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBookCatalog.getBookId()).isEqualTo(DEFAULT_BOOK_ID);
        assertThat(testBookCatalog.getRentCnt()).isEqualTo(DEFAULT_RENT_CNT);
    }

    @Test
    void createBookCatalogWithExistingId() throws Exception {
        // Create the BookCatalog with an existing ID
        bookCatalog.setId("existing_id");
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        int databaseSizeBeforeCreate = bookCatalogRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookCatalogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookCatalogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookCatalog in the database
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookCatalogRepository.findAll().size();
        // set the field null
        bookCatalog.setTitle(null);

        // Create the BookCatalog, which fails.
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        restBookCatalogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookCatalogDTO))
            )
            .andExpect(status().isBadRequest());

        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkAuthorIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookCatalogRepository.findAll().size();
        // set the field null
        bookCatalog.setAuthor(null);

        // Create the BookCatalog, which fails.
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        restBookCatalogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookCatalogDTO))
            )
            .andExpect(status().isBadRequest());

        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllBookCatalogs() throws Exception {
        // Initialize the database
        bookCatalogRepository.save(bookCatalog);

        // Get all the bookCatalogList
        restBookCatalogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookCatalog.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].bookId").value(hasItem(DEFAULT_BOOK_ID.intValue())))
            .andExpect(jsonPath("$.[*].rentCnt").value(hasItem(DEFAULT_RENT_CNT)));
    }

    @Test
    void getBookCatalog() throws Exception {
        // Initialize the database
        bookCatalogRepository.save(bookCatalog);

        // Get the bookCatalog
        restBookCatalogMockMvc
            .perform(get(ENTITY_API_URL_ID, bookCatalog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bookCatalog.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.bookId").value(DEFAULT_BOOK_ID.intValue()))
            .andExpect(jsonPath("$.rentCnt").value(DEFAULT_RENT_CNT));
    }

    @Test
    void getNonExistingBookCatalog() throws Exception {
        // Get the bookCatalog
        restBookCatalogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingBookCatalog() throws Exception {
        // Initialize the database
        bookCatalogRepository.save(bookCatalog);

        int databaseSizeBeforeUpdate = bookCatalogRepository.findAll().size();

        // Update the bookCatalog
        BookCatalog updatedBookCatalog = bookCatalogRepository.findById(bookCatalog.getId()).get();
        updatedBookCatalog
            .title(UPDATED_TITLE)
            .author(UPDATED_AUTHOR)
            .description(UPDATED_DESCRIPTION)
            .bookId(UPDATED_BOOK_ID)
            .rentCnt(UPDATED_RENT_CNT);
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(updatedBookCatalog);

        restBookCatalogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookCatalogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookCatalogDTO))
            )
            .andExpect(status().isOk());

        // Validate the BookCatalog in the database
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeUpdate);
        BookCatalog testBookCatalog = bookCatalogList.get(bookCatalogList.size() - 1);
        assertThat(testBookCatalog.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBookCatalog.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testBookCatalog.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBookCatalog.getBookId()).isEqualTo(UPDATED_BOOK_ID);
        assertThat(testBookCatalog.getRentCnt()).isEqualTo(UPDATED_RENT_CNT);
    }

    @Test
    void putNonExistingBookCatalog() throws Exception {
        int databaseSizeBeforeUpdate = bookCatalogRepository.findAll().size();
        bookCatalog.setId(UUID.randomUUID().toString());

        // Create the BookCatalog
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookCatalogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookCatalogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookCatalogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookCatalog in the database
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchBookCatalog() throws Exception {
        int databaseSizeBeforeUpdate = bookCatalogRepository.findAll().size();
        bookCatalog.setId(UUID.randomUUID().toString());

        // Create the BookCatalog
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookCatalogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookCatalogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookCatalog in the database
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamBookCatalog() throws Exception {
        int databaseSizeBeforeUpdate = bookCatalogRepository.findAll().size();
        bookCatalog.setId(UUID.randomUUID().toString());

        // Create the BookCatalog
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookCatalogMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookCatalogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BookCatalog in the database
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateBookCatalogWithPatch() throws Exception {
        // Initialize the database
        bookCatalogRepository.save(bookCatalog);

        int databaseSizeBeforeUpdate = bookCatalogRepository.findAll().size();

        // Update the bookCatalog using partial update
        BookCatalog partialUpdatedBookCatalog = new BookCatalog();
        partialUpdatedBookCatalog.setId(bookCatalog.getId());

        partialUpdatedBookCatalog.rentCnt(UPDATED_RENT_CNT);

        restBookCatalogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookCatalog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBookCatalog))
            )
            .andExpect(status().isOk());

        // Validate the BookCatalog in the database
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeUpdate);
        BookCatalog testBookCatalog = bookCatalogList.get(bookCatalogList.size() - 1);
        assertThat(testBookCatalog.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBookCatalog.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testBookCatalog.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBookCatalog.getBookId()).isEqualTo(DEFAULT_BOOK_ID);
        assertThat(testBookCatalog.getRentCnt()).isEqualTo(UPDATED_RENT_CNT);
    }

    @Test
    void fullUpdateBookCatalogWithPatch() throws Exception {
        // Initialize the database
        bookCatalogRepository.save(bookCatalog);

        int databaseSizeBeforeUpdate = bookCatalogRepository.findAll().size();

        // Update the bookCatalog using partial update
        BookCatalog partialUpdatedBookCatalog = new BookCatalog();
        partialUpdatedBookCatalog.setId(bookCatalog.getId());

        partialUpdatedBookCatalog
            .title(UPDATED_TITLE)
            .author(UPDATED_AUTHOR)
            .description(UPDATED_DESCRIPTION)
            .bookId(UPDATED_BOOK_ID)
            .rentCnt(UPDATED_RENT_CNT);

        restBookCatalogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookCatalog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBookCatalog))
            )
            .andExpect(status().isOk());

        // Validate the BookCatalog in the database
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeUpdate);
        BookCatalog testBookCatalog = bookCatalogList.get(bookCatalogList.size() - 1);
        assertThat(testBookCatalog.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBookCatalog.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testBookCatalog.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBookCatalog.getBookId()).isEqualTo(UPDATED_BOOK_ID);
        assertThat(testBookCatalog.getRentCnt()).isEqualTo(UPDATED_RENT_CNT);
    }

    @Test
    void patchNonExistingBookCatalog() throws Exception {
        int databaseSizeBeforeUpdate = bookCatalogRepository.findAll().size();
        bookCatalog.setId(UUID.randomUUID().toString());

        // Create the BookCatalog
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookCatalogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bookCatalogDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookCatalogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookCatalog in the database
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchBookCatalog() throws Exception {
        int databaseSizeBeforeUpdate = bookCatalogRepository.findAll().size();
        bookCatalog.setId(UUID.randomUUID().toString());

        // Create the BookCatalog
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookCatalogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookCatalogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookCatalog in the database
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamBookCatalog() throws Exception {
        int databaseSizeBeforeUpdate = bookCatalogRepository.findAll().size();
        bookCatalog.setId(UUID.randomUUID().toString());

        // Create the BookCatalog
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookCatalogMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bookCatalogDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BookCatalog in the database
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteBookCatalog() throws Exception {
        // Initialize the database
        bookCatalogRepository.save(bookCatalog);

        int databaseSizeBeforeDelete = bookCatalogRepository.findAll().size();

        // Delete the bookCatalog
        restBookCatalogMockMvc
            .perform(delete(ENTITY_API_URL_ID, bookCatalog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findAll();
        assertThat(bookCatalogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
