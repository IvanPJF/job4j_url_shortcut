package ru.job4j.shortcut.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.shortcut.model.Site;

public interface SiteRepository extends CrudRepository<Site, Integer> {

    boolean existsBySiteEquals(String site);

    Site findByLogin(String login);

    @Query("select s from Site s join fetch s.links where s.login = :login")
    Site findWithAllFieldsByLogin(String login);
}
