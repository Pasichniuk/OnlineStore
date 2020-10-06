package entitytest;

import entity.Category;

import org.junit.*;

import static org.junit.Assert.*;

public class CategoryTest {

    @Test
    public void testCategoryEntity() {
        Category category = new Category(1, "test", "тест");

        assertNotNull(category);

        category.setCategoryID(0);
        category.setName("name");
        category.setNameRU("название");

        assertEquals(category.getCategoryID(), 0);
        assertEquals(category.getName(), "name");
        assertEquals(category.getNameRU(), "название");
    }
}
