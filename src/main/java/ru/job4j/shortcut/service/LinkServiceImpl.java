package ru.job4j.shortcut.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import ru.job4j.shortcut.model.Link;
import ru.job4j.shortcut.repository.LinkRepository;

import java.util.Optional;

@Service
public class LinkServiceImpl implements LinkService {

    private static final int CODE_LENGTH = 10;

    private final LinkRepository linkRepository;

    public LinkServiceImpl(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public Optional<Link> saveAndGenerateCode(Link link) {
        Optional<Link> foundLink = linkRepository.findByUrl(link.getUrl());
        if (foundLink.isPresent()) {
            return foundLink;
        }
        link.setCode(RandomStringUtils.random(CODE_LENGTH, true, true));
        return Optional.of(linkRepository.save(link));
    }

    @Override
    public Optional<Link> findByCode(String code) {
        return linkRepository.findByCode(code);

    }
}
