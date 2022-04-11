package com.itstore.core.data.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

@JsonPropertyOrder({"data", "number", "size", "pages", "elements", "total"})
public class PageDTO<T> {

    private final List<T> data;
    private final int size;
    private final int number;
    private final int pages;
    private final int elements;
    private final long total;

    private PageDTO(Page<T> page) {
        this.data = page.getContent();
        this.size = page.getSize();
        this.number = page.getNumber();
        this.pages = page.getTotalPages();
        this.elements = page.getNumberOfElements();
        this.total = page.getTotalElements();
    }

    public List<T> getData() {
        return data;
    }

    public int getSize() {
        return size;
    }

    public int getNumber() {
        return number;
    }

    public int getPages() {
        return pages;
    }

    public int getElements() {
        return elements;
    }

    public long getTotal() {
        return total;
    }

    public static <T, E> PageDTO<T> of(Page<E> page, Function<E, T> mapper) {
        Page<T> map = page.map(mapper);
        return new PageDTO<>(map);
    }
}
