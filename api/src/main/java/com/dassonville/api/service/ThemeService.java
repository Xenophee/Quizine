package com.dassonville.api.service;


import com.dassonville.api.dto.ThemeDTO;
import com.dassonville.api.dto.ThemeUpsertDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.ThemeMapper;
import com.dassonville.api.model.Theme;
import com.dassonville.api.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.capitalize;


@Service
@RequiredArgsConstructor
public class ThemeService {

    private static final Logger logger = LoggerFactory.getLogger(ThemeService.class);

    private final ThemeRepository themeRepository;
    private final ThemeMapper themeMapper;


    public List<ThemeDTO> getAllThemes() {
        List<Theme> themes = themeRepository.findAll();
        return themes.stream().map(themeMapper::toDTO).collect(Collectors.toList());
    }

    public List<ThemeDTO> getAllActiveThemes() {
        List<Theme> themes = themeRepository.findByDisabledAtIsNull();
        return themes.stream().map(themeMapper::toDTO).collect(Collectors.toList());
    }


    public ThemeDTO findById(long id) {

        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Le thème avec l'ID {}, n'a pas été trouvé.", id);
                    return new NotFoundException("Le thème n'a pas été trouvé.");
                });

        return themeMapper.toDTO(theme);
    }


    public ThemeDTO create(ThemeUpsertDTO dto) {
        Theme themeToCreate = themeMapper.toModel(dto);

        themeToCreate.setName(capitalize(themeToCreate.getName()));

        if (themeRepository.existsByName(themeToCreate.getName())) {
            logger.warn("Un thème existe déjà avec le même nom : {}", themeToCreate.getName());
            throw new AlreadyExistException("Un thème existe déjà avec le même nom.");
        }

        Theme themeCreated = themeRepository.save(themeToCreate);

        return themeMapper.toDTO(themeCreated);
    }


    public ThemeDTO update(long id, ThemeUpsertDTO dto) {
        Theme existingTheme = themeRepository.findById(id).orElseThrow(() -> {
            logger.warn("Le thème à modifier avec l'ID {}, n'a pas été trouvé.", id);
            return new NotFoundException("Le thème à modifier n'a pas été trouvé.");
        });

        if (!existingTheme.getName().equals(dto.name()) && themeRepository.existsByName(dto.name())) {
            logger.warn("Un thème existe déjà avec le même nom : {}", dto.name());
            throw new AlreadyExistException("Un thème existe déjà avec le même nom.");
        }

        existingTheme.setName(capitalize(dto.name()));
        existingTheme.setDescription(dto.description());

        Theme themeUpdated = themeRepository.save(existingTheme);
        return themeMapper.toDTO(themeUpdated);
    }



    public void delete(long id) {

        if (!themeRepository.existsById(id)) {
            logger.warn("Le thème à supprimer avec l'ID {}, n'a pas été trouvé.", id);
            throw new NotFoundException("Le thème à supprimer n'a pas été trouvé.");
        }

        themeRepository.deleteById(id);
    }


    public void toggleDisable(long id) {
        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Le thème avec l'ID {}, n'a pas été trouvé.", id);
                    return new NotFoundException("Le thème n'a pas été trouvé.");
                });

        if (theme.getDisabledAt() == null) {
            theme.setDisabledAt(LocalDate.now()); // Désactiver
        } else {
            theme.setDisabledAt(null); // Réactiver
        }

        themeRepository.save(theme);
    }

}
