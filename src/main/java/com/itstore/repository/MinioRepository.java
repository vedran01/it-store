package com.itstore.repository;

import com.itstore.data.dto.FileDTO;

import java.util.List;

public interface MinioRepository {

    List<FileDTO> findAll();

    String save(String name, byte[] content) throws Exception;

    byte[] downloadById(String id) throws Exception;

    void deleteById(String id) throws Exception;

}
