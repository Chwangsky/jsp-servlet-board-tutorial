package com.study.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaginationDtoTest {

    @Test
    public void testCreatePaginationDto() {
        // given
        int totalItems = 100;
        int itemsPerPage = 10;
        int pagesPerSection = 5;
        int currentPage = 3;

        // when
        PaginationDto pagination = PaginationDto.createPaginationDto(totalItems, itemsPerPage,
                pagesPerSection, currentPage);

        // then
        assertEquals(totalItems, pagination.getTotalItems());
        assertEquals(itemsPerPage, pagination.getItemsPerPage());
        assertEquals(pagesPerSection, pagination.getPagesPerSection());
        assertEquals(currentPage, pagination.getCurrentPage());
        assertEquals(10, pagination.getTotalPage());
        assertEquals(2, pagination.getTotalSection());
        assertEquals(1, pagination.getCurrentSectionPageBegin());
        assertEquals(5, pagination.getCurrentSectionPageEnd());
        assertEquals(1, pagination.getCurrentSection());
    }

    @Test
    public void testCreatePaginationDtoDifferentPage() {
        // given
        int totalItems = 123;
        int itemsPerPage = 10;
        int pagesPerSection = 5;
        int currentPage = 7;

        // when
        PaginationDto pagination = PaginationDto.createPaginationDto(totalItems, itemsPerPage,
                pagesPerSection, currentPage);

        // then
        assertEquals(totalItems, pagination.getTotalItems());
        assertEquals(itemsPerPage, pagination.getItemsPerPage());
        assertEquals(pagesPerSection, pagination.getPagesPerSection());
        assertEquals(currentPage, pagination.getCurrentPage());
        assertEquals(13, pagination.getTotalPage());
        assertEquals(3, pagination.getTotalSection());
        assertEquals(6, pagination.getCurrentSectionPageBegin());
        assertEquals(10, pagination.getCurrentSectionPageEnd());
        assertEquals(2, pagination.getCurrentSection());
    }

    @Test
    public void testEdgeCasePaginationDto() {
        // given
        int totalItems = 5;
        int itemsPerPage = 10;
        int pagesPerSection = 5;
        int currentPage = 1;

        // when
        PaginationDto pagination = PaginationDto.createPaginationDto(totalItems, itemsPerPage,
                pagesPerSection, currentPage);

        // then
        assertEquals(totalItems, pagination.getTotalItems());
        assertEquals(itemsPerPage, pagination.getItemsPerPage());
        assertEquals(pagesPerSection, pagination.getPagesPerSection());
        assertEquals(currentPage, pagination.getCurrentPage());
        assertEquals(1, pagination.getTotalPage());
        assertEquals(1, pagination.getTotalSection());
        assertEquals(1, pagination.getCurrentSectionPageBegin());
        assertEquals(1, pagination.getCurrentSectionPageEnd());
        assertEquals(1, pagination.getCurrentSection());
    }
}
