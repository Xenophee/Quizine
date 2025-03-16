package com.dassonville.api.service;


import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.MismatchedIdException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.model.Theme;
import com.dassonville.api.repository.ThemeRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ThemeService {

    private static final Logger logger = LoggerFactory.getLogger(ThemeService.class);

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }


    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }


    public Theme findById(Long id) {
        return themeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Le thème avec l'ID {}, n'a pas été trouvé.", id);
                    return new NotFoundException("Le thème n'a pas été trouvé.");
                });
    }


    public Theme create(Theme themeToCreate) {
        themeToCreate.setName(StringUtils.capitalize(themeToCreate.getName()));

        if (themeRepository.existsByName(themeToCreate.getName())) {
            logger.warn("Un thème existe déjà avec le même nom : {}", themeToCreate.getName());
            throw new AlreadyExistException("Un thème existe déjà avec le même nom.");
        }

        return themeRepository.save(themeToCreate);
    }


    public Theme update(Long id, Theme themeToUpdate) {

        if (!id.equals(themeToUpdate.getId())) {
            logger.warn("L'ID de la requête ({}) ne correspond pas à l'ID du thème ({})", id, themeToUpdate.getId());
            throw new MismatchedIdException("L'ID de la requête ne correspond pas à l'ID du thème.");
        }

        Theme existingTheme = themeRepository.findById(id).orElseThrow(() -> {
            logger.warn("Le thème à modifier avec l'ID {}, n'a pas été trouvé.", id);
            return new NotFoundException("Le thème à modifier n'a pas été trouvé.");
        });

        themeToUpdate.setName(StringUtils.capitalize(themeToUpdate.getName()));

        if (!existingTheme.getName().equals(themeToUpdate.getName()) && themeRepository.existsByName(themeToUpdate.getName())) {
            logger.warn("Un thème existe déjà avec le même nom : {}", themeToUpdate.getName());
            throw new AlreadyExistException("Un thème existe déjà avec le même nom.");
        }

        return themeRepository.save(themeToUpdate);
    }


    public void delete(Long id) {

        if (!themeRepository.existsById(id)) {
            logger.warn("Le thème à supprimer avec l'ID {}, n'a pas été trouvé.", id);
            throw new NotFoundException("Le thème à supprimer n'a pas été trouvé.");
        }

        themeRepository.deleteById(id);
    }

}
