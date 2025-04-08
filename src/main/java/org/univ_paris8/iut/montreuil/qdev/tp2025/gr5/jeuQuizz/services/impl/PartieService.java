package org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.services.impl;

import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.entities.dto.ElementsDisponiblesDTO;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.entities.dto.JoueurDTO;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.entities.dto.QuestionnaireDTO;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.utils.exceptions.determinerElementsDispoExceptions.DeterminerElementsDispoException;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.utils.exceptions.determinerElementsDispoExceptions.PasDeJoueurTrouve;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.utils.exceptions.determinerElementsDispoExceptions.PasDeQuestionnaireTrouve;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.utils.exceptions.fournirListeQuestionnaireExceptions.FournirQuestionnaireException;

import java.util.ArrayList;

public class PartieService {

    public ElementsDisponiblesDTO determinerElementsDispoPourPartie() throws DeterminerElementsDispoException, FournirQuestionnaireException {
            FournirListeQuestionnaires fournirListeQuestionnaires = new FournirListeQuestionnaires();
            ArrayList<QuestionnaireDTO> questionnaires = fournirListeQuestionnaires.fournirListeQuestionnaires("");
            if (questionnaires ==  null || questionnaires.isEmpty()) {
                throw new PasDeQuestionnaireTrouve();
            }

            JoueurService joueurService = new JoueurService();
            ArrayList<JoueurDTO> joueurs = joueurService.getJoueurs();

            if (joueurs == null || joueurs.isEmpty()) {
                throw new PasDeJoueurTrouve();
            }

            return new ElementsDisponiblesDTO(questionnaires, joueurs);
    }

}
