package org.example.springbootjpa.service;

import org.example.springbootjpa.entity.Category;
import org.example.springbootjpa.repository.CategoryRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryDao) {
        this.categoryRepository = categoryDao;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("categories")
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new IllegalArgumentException("Категория с ID " + id + " не найдена"));
    }

    @Override
    @Transactional
    public void saveCategory(Category category) {
        validateCategory(category);
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void updateCategory(Category category) {
        validateCategory(category);
        if (category.getId() == null) {
            throw new IllegalArgumentException("ID категории не может быть null");
        }
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID категории не может быть null");
        }
        categoryRepository.deleteById(Math.toIntExact(id));
    }

    private void validateCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Категория не может быть null");
        }
    }
}