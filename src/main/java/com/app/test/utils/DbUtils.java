package com.app.test.utils;

import com.app.test.entity.BaseEntity;
import org.reflections.Reflections;

import java.util.Set;
import javax.persistence.*;

/**
 * Created by swathy.iyer on 18/12/16.
 */

public class DbUtils {
    public static Set<Class<?>> getEntityClasses() {
        Reflections reflections = new Reflections(BaseEntity.class.getPackage().getName());
        return reflections.getTypesAnnotatedWith(Entity.class);
    }
}
