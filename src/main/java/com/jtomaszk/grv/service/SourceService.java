package com.jtomaszk.grv.service;

import com.jtomaszk.grv.domain.*;
import com.jtomaszk.grv.domain.enumeration.ColumnEnum;
import com.jtomaszk.grv.domain.enumeration.SourceStatusEnum;
import com.jtomaszk.grv.repository.GrvItemRepository;
import com.jtomaszk.grv.repository.LocationRepository;
import com.jtomaszk.grv.repository.SourceArchiveRepository;
import com.jtomaszk.grv.repository.SourceRepository;
import com.jtomaszk.grv.repository.search.SourceSearchRepository;
import com.jtomaszk.grv.service.dto.SourceDTO;
import com.jtomaszk.grv.service.mapper.SourceMapper;
import com.jtomaszk.grv.service.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Source.
 */
@Service
@Transactional
public class SourceService {

    private final Logger log = LoggerFactory.getLogger(SourceService.class);

    private final SourceRepository sourceRepository;

    private final SourceArchiveRepository sourceArchiveRepository;

    private final SourceMapper sourceMapper;

    private final SourceSearchRepository sourceSearchRepository;

    private final GrvItemRepository grvItemRepository;

    private final LocationRepository locationRepository;

    public SourceService(SourceRepository sourceRepository, SourceArchiveRepository sourceArchiveRepository, SourceMapper sourceMapper, SourceSearchRepository sourceSearchRepository, GrvItemRepository grvItemRepository, LocationRepository locationRepository) {
        this.sourceRepository = sourceRepository;
        this.sourceArchiveRepository = sourceArchiveRepository;
        this.sourceMapper = sourceMapper;
        this.sourceSearchRepository = sourceSearchRepository;
        this.grvItemRepository = grvItemRepository;
        this.locationRepository = locationRepository;
    }

    /**
     * Save a source.
     *
     * @param sourceDTO the entity to save
     * @return the persisted entity
     */
    public SourceDTO save(SourceDTO sourceDTO) {
        log.debug("Request to save Source : {}", sourceDTO);
        Source source = sourceMapper.toEntity(sourceDTO);
        source = sourceRepository.save(source);
        SourceDTO result = sourceMapper.toDto(source);
        sourceSearchRepository.save(source);
        return result;
    }

    /**
     * Get all the sources.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SourceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sources");
        return sourceRepository.findAll(pageable)
            .map(sourceMapper::toDto);
    }

    /**
     * Get one source by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public SourceDTO findOne(Long id) {
        log.debug("Request to get Source : {}", id);
        Source source = sourceRepository.findOne(id);
        return sourceMapper.toDto(source);
    }

    public SourceDTO run(Long id) {
        log.debug("Request to get Source : {}", id);
        Source source = sourceRepository.findOne(id);

        try {
            final String json = readUrl(source.getUrl());
            SourceArchive sourceArchive = new SourceArchive().createdDate(Instant.now())
                .source(source)
                .json(json);
            source.addArchives(sourceArchive);
            sourceArchiveRepository.save(sourceArchive);

            Map<String, Location> locationMap = new HashMap<>();
            List<Map<ColumnEnum, String>> values = JsonUtil.parse(source.getPattern(), json);

            List<GrvItem> items = values.stream()
                .map(i -> {
                    GrvItemPerson person = new GrvItemPerson()
                        .firstName(i.get(ColumnEnum.FIRST_NAME))
                        .lastName(i.get(ColumnEnum.LAST_NAME))
                        .anotherLastName(i.get(ColumnEnum.ANOTHER_LAST_NAME))
                        .startDateString(i.get(ColumnEnum.START_DATE))
                        .endDateString(i.get(ColumnEnum.END_DATE));
                    GrvItem item = new GrvItem().createdDate(Instant.now())
                        .person(person)
                        .source(source)
                        .sourceArchive(sourceArchive)
                        .docnr(i.get(ColumnEnum.DOC_NR))
                        .validToDateString(i.get(ColumnEnum.VALID_TO))
                        .location(locationMap.computeIfAbsent(
                            i.get(ColumnEnum.BOX_EXTERNAL_ID),
                            key -> new Location().createdDate(Instant.now())
                                .externalid(key)
                                .source(source)
                                .coords(i.get(ColumnEnum.COORDINATES))
                        ))
                        .info(i.get(ColumnEnum.INFO))
                        .externalid(i.get(ColumnEnum.EXTERNAL_ID));
                    person.item(item);

                    return item;
                }).collect(Collectors.toList());

            locationRepository.save(locationMap.values());
            grvItemRepository.save(items);

            source.setStatus(SourceStatusEnum.OK);
        } catch (Exception e) {
            source.setStatus(SourceStatusEnum.ERROR);
            source.info(e.getMessage());
            e.printStackTrace();
        }
        sourceRepository.save(source);
        return sourceMapper.toDto(source);
    }


    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * Delete the source by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Source : {}", id);
        sourceRepository.delete(id);
        sourceSearchRepository.delete(id);
    }

    /**
     * Search for the source corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SourceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Sources for query {}", query);
        Page<Source> result = sourceSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(sourceMapper::toDto);
    }
}
