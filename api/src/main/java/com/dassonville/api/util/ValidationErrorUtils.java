package com.dassonville.api.util;

import java.util.*;

public class ValidationErrorUtils {


    /**
     * Navigue ou crée une structure de type {@code Map<String, Object>} dans un arbre d'erreurs,
     * en fonction d'une clé fournie, qui peut représenter un champ simple ou un champ indexé.
     * <p>
     * Cette méthode est utilisée pour construire dynamiquement une structure d'erreurs
     * de validation hiérarchique, typiquement pour des DTO complexes avec des listes imbriquées.
     *
     * <p>Comportement :
     * <ul>
     *   <li>Si la clé est de type indexé (ex: {@code "answers[2]"}), elle garantit la présence d'une
     *       liste associée à {@code "answers"} dans {@code parent}, ainsi qu'une Map à l'index donné.</li>
     *   <li>Si la clé est simple (ex: {@code "title"}), elle garantit qu'une Map est présente pour cette clé.</li>
     * </ul>
     *
     * <p>Les structures manquantes (listes ou maps) sont créées au besoin.
     *
     * @param parent la structure parente dans laquelle naviguer ou insérer les éléments d'erreur
     * @param key la clé ciblée, soit un champ simple (ex: "title"), soit un champ indexé (ex: "answers[1]")
     * @return la {@code Map<String, Object>} prête à recevoir des messages d'erreur pour ce champ
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> navigateOrCreate(Map<String, Object> parent, String key) {
        // ── 1. Gestion des chemins avec index (ex: answers[0]) ──────────────
        if (key.matches(".+\\[\\d+]")) {
            String field = key.substring(0, key.indexOf('['));
            int index = Integer.parseInt(key.substring(key.indexOf('[') + 1, key.indexOf(']')));

            Object existing = parent.get(field);
            List<Map<String, Object>> list;

            if (existing instanceof List) {
                list = (List<Map<String, Object>>) existing;
            } else {
                list = new ArrayList<>();
                parent.put(field, list);
            }

            while (list.size() <= index) {
                list.add(new HashMap<>());
            }

            return list.get(index);
        }

        // ── 2. Gestion des champs simples (ex: "title") ─────────────────────
        Object existing = parent.get(key);

        if (existing instanceof Map) {
            return (Map<String, Object>) existing;
        }

        Map<String, Object> child = new HashMap<>();
        parent.put(key, child);
        return child;
    }

}
