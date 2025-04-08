package org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.services.impl;

import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.entities.dto.ElementsDisponiblesDTO;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.entities.dto.JoueurDTO;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.entities.dto.QuestionnaireDTO;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.utils.exceptions.determinerElementsDispoExceptions.DeterminerElementsDispoException;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.utils.exceptions.fournirListeQuestionnaireExceptions.FournirQuestionnaireException;

import java.util.ArrayList;

public class DeterminerElementsDispoPourPartie {

    public ElementsDisponiblesDTO determinerElementsDispoPourPartie() throws DeterminerElementsDispoException, FournirQuestionnaireException {
        FournirListeQuestionnaires fournirListeQuestionnaires = new FournirListeQuestionnaires();
        ArrayList<QuestionnaireDTO> questionnaires = fournirListeQuestionnaires.fournirListeQuestionnaires("");

        JoueurService joueurService = new JoueurService();
        ArrayList<JoueurDTO> joueurs = joueurService.getJoueurs();

        return new ElementsDisponiblesDTO(questionnaires, joueurs);
    }

}
