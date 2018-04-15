package com.library.app.commontests.category;

import com.library.app.category.model.Category;
import org.junit.Ignore;

import java.util.Arrays;
import java.util.List;

@Ignore
public class CategoryForTests {

    public static Category javaCategory() {

        return new Category("Java");
    }
    public static Category pythonCategory() {

        return new Category("Python");
    }
    public static Category clojureCategory() {

        return new Category("Clojure");
    }
    public static Category javaScriptCategory() {

        return new Category("JavaScript");
    }

    public static List<Category> allCategories() {
        return Arrays.asList(javaCategory(), pythonCategory(), clojureCategory(), javaScriptCategory());
    }
}
