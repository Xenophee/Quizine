package com.dassonville.api.service;

import com.dassonville.api.dto.CategoryDTO;
import com.dassonville.api.dto.CategoryUpsertDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.CategoryMapper;
import com.dassonville.api.model.Category;
import com.dassonville.api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static org.springframework.util.StringUtils.capitalize;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    public CategoryDTO findById(long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("La catégorie avec l'ID {}, n'a pas été trouvée.", id);
                    return new NotFoundException("La catégorie n'a pas été trouvée.");
                });

        return categoryMapper.toDTO(category);
    }

    public CategoryDTO create(CategoryUpsertDTO dto) {
        Category categoryToCreate = categoryMapper.toModel(dto);

        categoryToCreate.setName(capitalize(categoryToCreate.getName()));

        if (categoryRepository.existsByName(categoryToCreate.getName())) {
            logger.warn("Une catégorie existe déjà avec le même nom : {}", categoryToCreate.getName());
            throw new AlreadyExistException("Une catégorie existe déjà avec le même nom.");
        }

        Category categoryCreated = categoryRepository.save(categoryToCreate);

        return categoryMapper.toDTO(categoryCreated);
    }


    public CategoryDTO update(long id, CategoryUpsertDTO dto) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> {
            logger.warn("La catégorie à modifier avec l'ID {}, n'a pas été trouvée.", id);
            return new NotFoundException("La catégorie à modifier n'a pas été trouvée.");
        });

        if (!existingCategory.getName().equals(dto.name()) && categoryRepository.existsByName(dto.name())) {
            logger.warn("Une catégorie existe déjà avec le même nom : {}", dto.name());
            throw new AlreadyExistException("Une catégorie existe déjà avec le même nom.");
        }

        existingCategory.setName(capitalize(dto.name()));
        existingCategory.setDescription(dto.description());

        Category categoryUpdated = categoryRepository.save(existingCategory);
        return categoryMapper.toDTO(categoryUpdated);
    }


    public void delete(long id) {

        if (!categoryRepository.existsById(id)) {
            logger.warn("La catégorie à supprimer avec l'ID {}, n'a pas été trouvée.", id);
            throw new NotFoundException("La catégorie à supprimer n'a pas été trouvée.");
        }

        categoryRepository.deleteById(id);
    }


    public void toggleDisable(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("La catégorie avec l'ID {}, n'a pas été trouvée.", id);
                    return new NotFoundException("La catégorie n'a pas été trouvée.");
                });

        if (category.getDisabledAt() == null) {
            category.setDisabledAt(LocalDate.now()); // Désactiver
        } else {
            category.setDisabledAt(null); // Réactiver
        }

        categoryRepository.save(category);
    }
}
