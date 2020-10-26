package ru.job4j.shortcut.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "site")
public class Site extends BaseEntity {

    @Column(name = "site")
    private String site;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "site", fetch = FetchType.LAZY)
    private List<Link> links;

    public static Site of(String site) {
        Site result = new Site();
        result.setSite(site);
        return result;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Site site = (Site) o;
        return Objects.equals(getId(), site.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Site.class.getSimpleName() + "[", "]")
                .add("id='" + getId() + "'")
                .add("site='" + site + "'")
                .add("login='" + login + "'")
                .add("password='" + password + "'")
                .toString();
    }
}
