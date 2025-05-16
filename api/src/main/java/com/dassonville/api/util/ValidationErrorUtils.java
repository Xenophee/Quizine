package com.dassonville.api.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationErrorUtils {


    /**
     * Navigue dans une structure de type Map imbriquée (similaire à du JSON) et crée les éléments manquants si nécessaire.
     * <p>
     * Cette méthode est principalement utilisée pour construire dynamiquement des objets imbriqués
     * à partir de noms de champs potentiellement indexés (ex. : {@code answers[0].text}).
     * </p>
     *
     * <p>
     * Si le champ spécifié est un tableau (ex. {@code answers[2]}), elle garantit que la liste
     * contient au moins jusqu'à cet index et retourne la Map correspondante à cet index.
     * Si le champ est un simple objet (ex. {@code title}), elle crée une sous-map si elle n'existe pas encore.
     * </p>
     *
     * @param parent la Map racine ou intermédiaire dans laquelle naviguer.
     * @param key le nom du champ à accéder, pouvant être un champ simple (ex. {@code title})
     *            ou indexé (ex. {@code answers[0]}).
     * @return la Map correspondant au champ final, prête à recevoir des valeurs.
     *
     * @throws ClassCastException si la structure existante ne correspond pas aux attentes (par exemple,
     *         si un champ censé être une Map ou une List a été mal initialisé ailleurs).
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> navigateOrCreate(Map<String, Object> parent, String key) {
        if (key.matches(".+\\[\\d+]")) {
            String field = key.substring(0, key.indexOf('['));
            int index = Integer.parseInt(key.substring(key.indexOf('[') + 1, key.indexOf(']')));

            List<Map<String, Object>> list = (List<Map<String, Object>>) parent.computeIfAbsent(field, k -> new ArrayList<>());

            while (list.size() <= index) {
                list.add(new HashMap<>());
            }

            return list.get(index);
        }

        return (Map<String, Object>) parent.computeIfAbsent(key, k -> new HashMap<>());
    }
}
