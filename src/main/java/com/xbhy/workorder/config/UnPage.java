package com.xbhy.workorder.config;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.data.web.PageableDefault;

public class UnPage implements Pageable {

//    @Override
//    public boolean isPaged() {
//        return Boolean.FALSE;
//    }
//
//    @Override
//    public int getPageNumber() {
//        return super.;
//    }
//
//    @Override
//    public int getPageSize() {
//        return 0;
//    }
//
//    @Override
//    public long getOffset() {
//        return 0;
//    }
//
//    @Override
//    public Sort getSort() {
//        return null;
//    }
//
//    @Override
//    public Pageable next() {
//        return null;
//    }
//
//    @Override
//    public Pageable previousOrFirst() {
//        return null;
//    }
//
//    @Override
//    public Pageable first() {
//        return null;
//    }
//
//    @Override
//    public boolean hasPrevious() {
//        return false;
//    }
//    private Unpaged() {
//    }
    @Override
    public boolean isPaged() {
        return false;
    }
    @Override
    public Pageable previousOrFirst() {
        return this;
    }
    @Override
    public Pageable next() {
        return this;
    }
    @Override
    public boolean hasPrevious() {
        return false;
    }
    @Override
    public Sort getSort() {
        return Sort.unsorted();
    }
    @Override
    public int getPageSize() {
        throw new UnsupportedOperationException();
    }
    @Override
    public int getPageNumber() {
        throw new UnsupportedOperationException();
    }
    @Override
    public long getOffset() {
        throw new UnsupportedOperationException();
    }
    @Override
    public Pageable first() {
        return this;
    }
}
