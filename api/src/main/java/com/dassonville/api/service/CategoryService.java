package com.dassonville.api.service;

import com.dassonville.api.dto.CategoryAdminDTO;
import com.dassonville.api.dto.CategoryUpsertDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.CategoryMapper;
import com.dassonville.api.model.Category;
import com.dassonville.api.repository.CategoryRepository;
import com.dassonville.api.repository.ThemeRepository;
import com.dassonville.api.util.TextUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private final ThemeRepository themeRepository;




    public CategoryAdminDTO findById(long id) {

        Category category = findCategoryById(id);

        return categoryMapper.toAdminDTO(category);
    }

    public CategoryAdminDTO create(long themeId, CategoryUpsertDTO dto) {

        if (!themeRepository.existsById(themeId)) {
            logger.warn("Le thème avec l'ID {}, n'a pas été trouvé.", themeId);
            throw new NotFoundException("Le thème n'a pas été trouvé.");
        }

        String normalizedNewText = TextUtils.normalizeText(dto.name());
        logger.debug("Nom normalisé : {}, depuis {}", normalizedNewText, dto.name());

        if (categoryRepository.existsByNameIgnoreCase(normalizedNewText)) {
            logger.warn("Une catégorie existe déjà avec le même nom : {}", normalizedNewText);
            throw new AlreadyExistException("Une catégorie existe déjà avec le même nom.");
        }

        Category categoryToCreate = categoryMapper.toModel(dto, themeId);

        Category categoryCreated = categoryRepository.save(categoryToCreate);

        return categoryMapper.toAdminDTO(categoryCreated);
    }


    public CategoryAdminDTO update(long id, CategoryUpsertDTO dto) {

        Category categoryToUpdate = findCategoryById(id);

        String normalizedNewText = TextUtils.normalizeText(dto.name());
        logger.debug("Nom normalisé : {}, depuis {}", normalizedNewText, dto.name());

        if (categoryRepository.existsByNameIgnoreCaseAndIdNot(normalizedNewText, id)) {
            logger.warn("Une catégorie existe déjà avec le même nom : {}", normalizedNewText);
            throw new AlreadyExistException("Une catégorie existe déjà avec le même nom.");
        }

        categoryMapper.updateModelFromDTO(dto, categoryToUpdate);

        Category categoryUpdated = categoryRepository.save(categoryToUpdate);

        return categoryMapper.toAdminDTO(categoryUpdated);
    }


    public void delete(long id) {

        if (!categoryRepository.existsById(id)) {
            logger.warn("La catégorie à supprimer avec l'ID {}, n'a pas été trouvée.", id);
            throw new NotFoundException("La catégorie à supprimer n'a pas été trouvée.");
        }

        categoryRepository.deleteById(id);
    }


    public void updateVisibility(long id, boolean visible) {

        Category category = findCategoryById(id);

        if (category.isVisible() == visible) return;

        category.setVisible(visible);

        categoryRepository.save(category);
    }


    private Category findCategoryById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("La catégorie avec l'ID {}, n'a pas été trouvée.", id);
                    return new NotFoundException("La catégorie n'a pas été trouvée.");
                });
    }
}
