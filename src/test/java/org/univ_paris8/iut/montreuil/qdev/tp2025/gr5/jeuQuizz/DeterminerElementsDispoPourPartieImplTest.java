package org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.entities.dto.ElementsDisponiblesDTO;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.entities.dto.JoueurDTO;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.entities.dto.QuestionnaireDTO;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.utils.exceptions.determinerElementsDispoExceptions.PasDeJoueurTrouve;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.utils.exceptions.determinerElementsDispoExceptions.PasDeQuestionnaireTrouve;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.utils.exceptions.fournirListeQuestionnaireExceptions.FournirQuestionnaireException;

class DeterminerElementsDispoPourPartieImplTest {

    /**
     * Test du scénario nominal.
     * - FournirListeQuestionnaires renvoie une liste non vide de QuestionnaireDTO.
     * - JoueurService renvoie une liste non vide de JoueurDTO.
     * On vérifie alors que ElementsDisponiblesDTO est bien constitué.
     */
    @Test
    void testDeterminerElementsDispoPourPartie_Success() throws Exception {
        // Données factices
        ArrayList<QuestionnaireDTO> questionnairesFake = new ArrayList<>(Arrays.asList(
                new QuestionnaireDTO("Q1"),
                new QuestionnaireDTO("Q2")
        ));
        ArrayList<JoueurDTO> joueursFake = new ArrayList<>(Arrays.asList(
                new JoueurDTO("Joueur1"),
                new JoueurDTO("Joueur2")
        ));

        // On "mock" la construction de FournirListeQuestionnaires
        try (MockedConstruction<FournirListeQuestionnaires> mockedFournir = 
                Mockito.mockConstruction(FournirListeQuestionnaires.class,
                    (mock, context) -> Mockito.when(mock.fournirListeQuestionnaires(Mockito.anyString()))
                                                  .thenReturn(questionnairesFake))) {

            // On "mock" la construction de JoueurService
            try (MockedConstruction<JoueurService> mockedJoueur = 
                    Mockito.mockConstruction(JoueurService.class,
                    (mock, context) -> Mockito.when(mock.getJoueurs())
                                                  .thenReturn(joueursFake))) {

                DeterminerElementsDispoPourPartieImpl service = new DeterminerElementsDispoPourPartieImpl();
                ElementsDisponiblesDTO result = service.determinerElementsDispoPourPartie();

                assertNotNull(result, "L'élément retourné ne doit pas être null");
                assertEquals(questionnairesFake, result.getListeQuestionnaires(), "La liste des questionnaires doit correspondre aux données factices");
                assertEquals(joueursFake, result.getListeJoueurs(), "La liste des joueurs doit correspondre aux données factices");
            }
        }
    }

    /**
     * Test lorsque la liste des questionnaires est vide.
     * On s'attend à l'exception PasDeQuestionnaireTrouve.
     */
    @Test
    void testDeterminerElementsDispoPourPartie_EmptyQuestionnaires() throws Exception {
        // Simuler une liste vide de questionnaires
        ArrayList<QuestionnaireDTO> emptyQuestionnaires = new ArrayList<>();

        try (MockedConstruction<FournirListeQuestionnaires> mockedFournir = 
                Mockito.mockConstruction(FournirListeQuestionnaires.class,
                    (mock, context) -> Mockito.when(mock.fournirListeQuestionnaires(Mockito.anyString()))
                                                  .thenReturn(emptyQuestionnaires))) {

            // Le mock sur JoueurService n'est pas utile ici mais doit être présent pour éviter la construction réelle.
            try (MockedConstruction<JoueurService> mockedJoueur = 
                    Mockito.mockConstruction(JoueurService.class,
                    (mock, context) -> Mockito.when(mock.getJoueurs())
                                                  .thenReturn(new ArrayList<>()))) {

                DeterminerElementsDispoPourPartieImpl service = new DeterminerElementsDispoPourPartieImpl();
                Exception exception = assertThrows(PasDeQuestionnaireTrouve.class,
                        () -> service.determinerElementsDispoPourPartie());
                assertEquals("Aucun questionnaire trouvé", exception.getMessage());
            }
        }
    }

    /**
     * Test lorsque la liste des joueurs est vide.
     * On s'attend à l'exception PasDeJoueurTrouve.
     */
    @Test
    void testDeterminerElementsDispoPourPartie_EmptyJoueurs() throws Exception {
        // Données factices pour les questionnaires
        ArrayList<QuestionnaireDTO> questionnairesFake = new ArrayList<>(Arrays.asList(
                new QuestionnaireDTO("Q1")
        ));
        // Simuler une liste vide de joueurs
        ArrayList<JoueurDTO> emptyJoueurs = new ArrayList<>();

        // On "mock" la construction de FournirListeQuestionnaires pour renvoyer une liste non vide
        try (MockedConstruction<FournirListeQuestionnaires> mockedFournir = 
                Mockito.mockConstruction(FournirListeQuestionnaires.class,
                    (mock, context) -> Mockito.when(mock.fournirListeQuestionnaires(Mockito.anyString()))
                                                  .thenReturn(questionnairesFake))) {

            // On "mock" la construction de JoueurService pour renvoyer une liste vide
            try (MockedConstruction<JoueurService> mockedJoueur = 
                    Mockito.mockConstruction(JoueurService.class,
                    (mock, context) -> Mockito.when(mock.getJoueurs())
                                                  .thenReturn(emptyJoueurs))) {

                DeterminerElementsDispoPourPartieImpl service = new DeterminerElementsDispoPourPartieImpl();
                Exception exception = assertThrows(PasDeJoueurTrouve.class,
                        () -> service.determinerElementsDispoPourPartie());
                assertEquals("Aucun joueur trouvé", exception.getMessage());
            }
        }
    }
}
