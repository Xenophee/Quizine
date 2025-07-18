package com.dassonville.api.service;

import com.dassonville.api.dto.request.CategoryUpsertDTO;
import com.dassonville.api.dto.response.CategoryAdminDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.CategoryMapper;
import com.dassonville.api.model.Category;
import com.dassonville.api.projection.IdAndNameProjection;
import com.dassonville.api.repository.CategoryRepository;
import com.dassonville.api.repository.ThemeRepository;
import com.dassonville.api.util.TextUtils;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private final ThemeRepository themeRepository;


    /**
     * Récupère toutes les catégories associées à un thème donné et retourne leurs IDs et noms par ordre alphabétique.
     *
     * <p>Cette méthode vérifie si le thème existe, puis récupère les projections des IDs
     * et noms des catégories associées, triées par nom.</p>
     *
     * @param themeId l'identifiant du thème
     * @return une liste de {@link IdAndNameProjection} contenant les IDs et noms des catégories
     * @throws NotFoundException si le thème avec l'ID spécifié n'existe pas
     */
    public List<IdAndNameProjection> findAllByTheme(long themeId) {

        if (!themeRepository.existsById(themeId)) {
            log.warn("Le thème avec l'ID {}, n'a pas été trouvé.", themeId);
            throw new NotFoundException(ErrorCode.THEME_NOT_FOUND);
        }

        return categoryRepository.findAllByThemeIdOrderByName(themeId);
    }

    /**
     * Récupère les détails d'une catégorie par son identifiant.
     *
     * <p>Cette méthode recherche une catégorie par son ID et la mappe vers un
     * {@link CategoryAdminDTO} pour l'administration.</p>
     *
     * @param id l'identifiant de la catégorie
     * @return un {@link CategoryAdminDTO} contenant les détails de la catégorie
     * @throws NotFoundException si la catégorie avec l'ID spécifié n'existe pas
     */
    public CategoryAdminDTO findById(long id) {

        Category category = findCategoryById(id);

        return categoryMapper.toAdminDTO(category);
    }

    /**
     * Crée une nouvelle catégorie pour un thème donné.
     *
     * <p>Cette méthode vérifie si le thème existe, normalise le nom de la catégorie,
     * vérifie qu'aucune catégorie avec le même nom n'existe déjà, puis crée et sauvegarde
     * la nouvelle catégorie dans la base de données.</p>
     *
     * @param themeId l'identifiant du thème auquel la catégorie sera associée
     * @param dto     les informations de la catégorie à créer contenues dans un {@link CategoryUpsertDTO}
     * @return un {@link CategoryAdminDTO} contenant les détails de la catégorie créée
     * @throws NotFoundException     si le thème avec l'ID spécifié n'existe pas
     * @throws AlreadyExistException si une catégorie avec le même nom existe déjà
     */
    public CategoryAdminDTO create(long themeId, CategoryUpsertDTO dto) {

        if (!themeRepository.existsById(themeId)) {
            log.warn("Le thème avec l'ID {}, n'a pas été trouvé.", themeId);
            throw new NotFoundException(ErrorCode.THEME_NOT_FOUND);
        }

        String normalizedNewText = TextUtils.normalizeText(dto.name());
        log.debug("Nom normalisé : {}, depuis {}", normalizedNewText, dto.name());

        if (categoryRepository.existsByNameIgnoreCase(normalizedNewText)) {
            log.warn("Une catégorie existe déjà avec le même nom : {}", normalizedNewText);
            throw new AlreadyExistException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }

        Category categoryToCreate = categoryMapper.toModel(dto, themeId);

        Category categoryCreated = categoryRepository.save(categoryToCreate);

        return categoryMapper.toAdminDTO(categoryCreated);
    }


    /**
     * Met à jour une catégorie existante avec les nouvelles informations fournies dans le DTO.
     *
     * <p>Cette méthode recherche la catégorie par son ID, normalise le nouveau nom,
     * vérifie qu'aucune autre catégorie avec le même nom n'existe déjà, puis met à jour
     * et sauvegarde la catégorie dans la base de données.</p>
     *
     * @param id  l'identifiant de la catégorie à mettre à jour
     * @param dto les nouvelles informations de la catégorie contenues dans un {@link CategoryUpsertDTO}
     * @return un {@link CategoryAdminDTO} contenant les détails de la catégorie mise à jour
     * @throws NotFoundException     si la catégorie avec l'ID spécifié n'existe pas
     * @throws AlreadyExistException si une autre catégorie avec le même nom existe déjà
     */
    @Transactional
    public CategoryAdminDTO update(long id, CategoryUpsertDTO dto) {

        Category category = findCategoryById(id);

        String normalizedNewText = TextUtils.normalizeText(dto.name());
        log.debug("Nom normalisé : {}, depuis {}", normalizedNewText, dto.name());

        if (categoryRepository.existsByNameIgnoreCaseAndIdNot(normalizedNewText, id)) {
            log.warn("Une catégorie existe déjà avec le même nom : {}", normalizedNewText);
            throw new AlreadyExistException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }

        categoryMapper.updateModelFromDTO(dto, category);

        return categoryMapper.toAdminDTO(category);
    }


    /**
     * Supprime une catégorie par son identifiant.
     *
     * <p>Cette méthode vérifie si la catégorie existe, puis la supprime de la base de données.</p>
     *
     * @param id l'identifiant de la catégorie à supprimer
     * @throws NotFoundException si la catégorie avec l'ID spécifié n'existe pas
     */
    public void delete(long id) {

        if (!categoryRepository.existsById(id)) {
            log.warn("La catégorie à supprimer avec l'ID {}, n'a pas été trouvée.", id);
            throw new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        categoryRepository.deleteById(id);
    }


    /**
     * Met à jour la visibilité d'une catégorie.
     *
     * <p>Cette méthode active ou désactive une catégorie en fonction de l'état de visibilité fourni.
     * Si la catégorie est déjà dans l'état de visibilité souhaité, aucune action n'est effectuée.</p>
     *
     * @param id      l'identifiant de la catégorie à mettre à jour
     * @param visible l'état de visibilité souhaité ({@code true} pour activer, {@code false} pour désactiver)
     * @throws NotFoundException si la catégorie avec l'ID spécifié n'existe pas
     */
    @Transactional
    public void updateVisibility(long id, boolean visible) {

        Category category = findCategoryById(id);

        if (category.isVisible() == visible) return;

        category.setVisible(visible);
    }


    /**
     * Recherche une catégorie par son identifiant.
     *
     * <p>Cette méthode tente de trouver une catégorie par son identifiant.
     * Si la catégorie n'est pas trouvée, elle lève une exception.</p>
     *
     * @param id l'identifiant de la catégorie à rechercher
     * @return la {@link Category} trouvée
     * @throws NotFoundException si aucune catégorie ne correspond à l'identifiant fourni
     */
    private Category findCategoryById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("La catégorie avec l'ID {}, n'a pas été trouvée.", id);
                    return new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND);
                });
    }
}
