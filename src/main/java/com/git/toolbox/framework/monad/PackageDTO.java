package com.git.toolbox.framework.monad;

/**
 * Created by poan on 2017/11/29.
 */
public class PackageDTO {

    private String name;

    private Integer id;

    private String identity;

    public PackageDTO(String name, Integer id, String identity) {
        this.name = name;
        this.id = id;
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    @Override
    public String toString() {
        return "PackageDTO{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", identity='" + identity + '\'' +
                '}';
    }
}
