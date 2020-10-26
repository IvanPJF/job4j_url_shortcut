package ru.job4j.shortcut.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "link")
public class Link extends BaseEntity {

    @Column(name = "url")
    private String url;

    @Column(name = "code")
    private String code;

    @Column(name = "total")
    private int total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id")
    private Site site;

    public static Link of(String url, int total) {
        Link result = new Link();
        result.setUrl(url);
        result.setTotal(total);
        return result;
    }

    public static Link of(String url) {
        return of(url, 0);
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Link link = (Link) o;
        return Objects.equals(getId(), link.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Link.class.getSimpleName() + "[", "]")
                .add("id='" + getId() + "'")
                .add("url='" + url + "'")
                .add("code='" + code + "'")
                .add("total='" + total + "'")
                .toString();
    }
}
