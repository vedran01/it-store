package com.itstore.data.converter;

import com.itstore.core.data.converter.Converter;
import com.itstore.data.dto.FileDTO;
import io.minio.messages.Item;
import org.springframework.stereotype.Component;

@Component
public class MinioFileConverter implements Converter<FileDTO, Item> {

    @Override
    public FileDTO convert(Item source) {
        FileDTO dto = new FileDTO();
        dto.setEtag(source.etag());
        dto.setName(source.objectName());
        return dto;

    }
}
