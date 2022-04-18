package com.itstore.repository.impl;

import com.itstore.data.converter.MinioFileConverter;
import com.itstore.data.dto.FileDTO;
import com.itstore.repository.MinioRepository;
import io.minio.*;
import io.minio.messages.Item;
import io.minio.messages.Tags;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class MinioRepositoryImpl implements MinioRepository {

    private final MinioClient client;
    private final MinioFileConverter converter;
    private final String BUCKET = "it-store";

    @Override
    public List<FileDTO> findAll() {
        ListObjectsArgs args = ListObjectsArgs
                .builder()
                .bucket(BUCKET)
                .build();

        Iterable<Result<Item>> results = client.listObjects(args);

        return StreamSupport.stream(results.spliterator(), false)
                .map(r -> {
                    try {
                        return r.get();
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public String save(String name, byte[] content) throws Exception {

        File file = new File(name);
        FileCopyUtils.copy(content, file);

        String key = UUID.randomUUID().toString();

        UploadObjectArgs args = UploadObjectArgs.builder()
                .bucket(BUCKET)
                .object(key + "." + FileNameUtils.getExtension(file.getName()))
                .tags(Tags.newObjectTags(Map.of("originalFileName", file.getName(), "id", key)))
                .filename(file.getName())
                .build();

        ObjectWriteResponse response = client.uploadObject(args);

        return response.object();
    }

    @Override
    public byte[] downloadById(String id) throws Exception {
        GetObjectArgs args = GetObjectArgs
                    .builder()
                    .bucket(BUCKET)
                    .matchETag(id)
                    .build();
        GetObjectResponse object = client.getObject(args);

        return object.readAllBytes();
    }

    @Override
    public void deleteById(String id) throws Exception {

        GetObjectArgs args1 = GetObjectArgs
                .builder()
                .matchETag(id)
                .build();

        GetObjectResponse object = client.getObject(args1);

        RemoveObjectArgs args  = RemoveObjectArgs.builder()
                .bucket(BUCKET)
                .object(object.object())
                .build();
        client.removeObject(args);
    }
}
