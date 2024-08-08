package com.study.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO for pagination information.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaginationDto {
    private int totalItems; // 전체 항목 수
    private int itemsPerPage; // 한 페이지당 항목 수
    private int pagesPerSection; // 한 섹션당 페이지 수
    private int totalPage; // 총 페이지 수
    private int currentPage; // 현재 페이지
    private int currentSectionPageBegin; // 현재 섹션의 시작 페이지
    private int currentSectionPageEnd; // 현재 섹션의 끝 페이지
    private int currentSection; // 현재 섹션

    /**
     * Creates a PaginationDto from totalItems, itemsPerPage, pagesPerSection, and currentPage.
     *
     * @param totalItems Total number of items.
     * @param itemsPerPage Number of items per page.
     * @param pagesPerSection Number of pages per section.
     * @param currentPage Current page number.
     * @return PaginationDto containing pagination information.
     */
    public static PaginationDto createPaginationDto(int totalItems, int itemsPerPage,
            int pagesPerSection, int currentPage) {
        PaginationDto pagination = new PaginationDto();
        pagination.setTotalItems(totalItems);
        pagination.setItemsPerPage(itemsPerPage);
        pagination.setPagesPerSection(pagesPerSection);
        pagination.setCurrentPage(currentPage);

        int totalPage = calculateTotalPage(totalItems, itemsPerPage, pagination);

        int currentSection = calculateCurrentSection(pagesPerSection, currentPage);
        pagination.setCurrentSection(currentSection);

        int currentSectionPageBegin = calculateCurrentPageBegin(pagesPerSection, currentSection);
        pagination.setCurrentSectionPageBegin(currentSectionPageBegin);

        int currentSectionPageEnd =
                calculateCurrentPageEnd(pagesPerSection, totalPage, currentSection);
        pagination.setCurrentSectionPageEnd(currentSectionPageEnd);

        return pagination;
    }

    /**
     * Calculates the end page number for the current section.
     *
     * @param pagesPerSection Number of pages per section.
     * @param totalPage Total number of pages.
     * @param currentSection Current section number.
     * @return The end page number for the current section.
     */
    private static int calculateCurrentPageEnd(int pagesPerSection, int totalPage,
            int currentSection) {
        return Math.min(currentSection * pagesPerSection, totalPage);
    }

    /**
     * Calculates the start page number for the current section.
     *
     * @param pagesPerSection Number of pages per section.
     * @param currentSection Current section number.
     * @return The start page number for the current section.
     */
    private static int calculateCurrentPageBegin(int pagesPerSection, int currentSection) {
        return getCurrentSectionPageBegin2(pagesPerSection, currentSection);
    }

    /**
     * Helper method to calculate the start page number for the current section.
     *
     * @param pagesPerSection Number of pages per section.
     * @param currentSection Current section number.
     * @return The start page number for the current section.
     */
    private static int getCurrentSectionPageBegin2(int pagesPerSection, int currentSection) {
        return (currentSection - 1) * pagesPerSection + 1;
    }

    /**
     * Calculates the current section number.
     *
     * @param pagesPerSection Number of pages per section.
     * @param currentPage Current page number.
     * @return The current section number.
     */
    private static int calculateCurrentSection(int pagesPerSection, int currentPage) {
        return (int) Math.ceil((double) currentPage / pagesPerSection);
    }

    /**
     * Calculates the total number of pages.
     *
     * @param totalItems Total number of items.
     * @param itemsPerPage Number of items per page.
     * @param pagination The PaginationDto object to set the total pages.
     * @return The total number of pages.
     */
    private static int calculateTotalPage(int totalItems, int itemsPerPage,
            PaginationDto pagination) {
        int totalPage = calculateCurrentSection(itemsPerPage, totalItems);
        pagination.setTotalPage(totalPage);
        return totalPage;
    }
}
