package ru.job4j.shortcut.service;

import ru.job4j.shortcut.model.Link;

import java.util.Optional;

public interface LinkService {

    Optional<Link> saveAndGenerateCode(Link link);

    Optional<Link> findByCode(String code);
}
