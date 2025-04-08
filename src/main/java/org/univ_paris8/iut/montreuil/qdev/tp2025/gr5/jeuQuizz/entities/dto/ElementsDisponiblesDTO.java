package org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.entities.dto;

import java.util.ArrayList;

public class ElementsDisponiblesDTO {

    private ArrayList<QuestionnaireDTO> listeQuestionnaires;
    private ArrayList<JoueurDTO> listeJoueurs;

    public ElementsDisponiblesDTO(ArrayList<QuestionnaireDTO> listeQuestionnaires, ArrayList<JoueurDTO> listeJoueurs) {
        this.listeQuestionnaires = listeQuestionnaires;
        this.listeJoueurs = listeJoueurs;
    }

    public ArrayList<QuestionnaireDTO> getListeQuestionnaires() {
        return listeQuestionnaires;
    }

    public ArrayList<JoueurDTO> getListeJoueurs() {
        return listeJoueurs;
    }
}
