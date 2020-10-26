package ru.job4j.shortcut.service;

import ru.job4j.shortcut.model.Site;

public interface SiteService {

    boolean save(Site site);

    Site findByLogin(String login);

    Site findWithAllFieldsByLogin(String login);
}
