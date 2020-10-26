package ru.job4j.shortcut.service;

import org.springframework.stereotype.Service;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.repository.SiteRepository;

@Service
public class SiteServiceImpl implements SiteService {

    private final SiteRepository siteRepository;

    public SiteServiceImpl(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    @Override
    public boolean save(Site site) {
        if (siteRepository.existsBySiteEquals(site.getSite())) {
            return false;
        }
        siteRepository.save(site);
        return true;
    }

    @Override
    public Site findByLogin(String login) {
        return siteRepository.findByLogin(login);
    }

    @Override
    public Site findWithAllFieldsByLogin(String login) {
        return siteRepository.findWithAllFieldsByLogin(login);
    }
}
