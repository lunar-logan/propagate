package org.propagate.common.domain;

import java.io.Serializable;
import java.util.Objects;

public class ID implements Serializable {
    private String key;

    private String env;

    public ID() {
    }

    public ID(String key, String env) {
        this.key = key;
        this.env = env;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ID id = (ID) o;
        return Objects.equals(key, id.key) &&
                Objects.equals(env, id.env);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, env);
    }

    @Override
    public String toString() {
        return "ID{" +
                "key='" + key + '\'' +
                ", env='" + env + '\'' +
                '}';
    }

    public static ID valueOf(String key, String env) {
        return new ID(key, env);
    }
}
