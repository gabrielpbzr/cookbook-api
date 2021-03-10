package com.github.gabrielpbzr.cookbook.web;

import java.util.List;

/**
 * Response to use with endpoints which return long lists of records
 * @author gabriel
 */
public class PaginatedResponse {
    private long currentPage;
    private long totalPageCount;
    private List data;

    protected PaginatedResponse() {
    }

    /**
     * Create a paginated response to list small chunks of big datasets
     * @param currentPage current page index
     * @param totalPageCount total pages available
     * @param data data chunk
     */
    public PaginatedResponse(long currentPage, long totalPageCount, List data) {
        this.currentPage = currentPage;
        this.totalPageCount = totalPageCount;
        this.data = data;
    }

    /**
     * Current page index
     * @return 
     */
    public long getCurrentPage() {
        return currentPage;
    }

    /**
     * Get total of pages available
     * @return 
     */
    public long getTotalPageCount() {
        return totalPageCount;
    }

    /**
     * Return current data chunk
     * @return data chunk
     */
    public List getData() {
        return data;
    }
}
