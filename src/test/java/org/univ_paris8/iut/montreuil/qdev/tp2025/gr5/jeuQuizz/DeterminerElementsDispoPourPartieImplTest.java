package org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.entities.dto.ElementsDisponiblesDTO;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.entities.dto.JoueurDTO;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.entities.dto.QuestionnaireDTO;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.services.impl.FournirListeQuestionnaires;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.services.impl.JoueurService;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.services.impl.PartieService;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.utils.enums.Langue;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.utils.exceptions.determinerElementsDispoExceptions.PasDeJoueurTrouve;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.utils.exceptions.determinerElementsDispoExceptions.PasDeQuestionnaireTrouve;
class DeterminerElementsDispoPourPartieImplTest {

    @Test
    void testDeterminerElementsDispoPourPartie_Success() throws Exception {
        ArrayList<QuestionnaireDTO> questionnairesFake = new ArrayList<>(Arrays.asList(
                new QuestionnaireDTO("Q1"),
                new QuestionnaireDTO("Q2")
        ));
        ArrayList<JoueurDTO> joueursFake = new ArrayList<>(Arrays.asList(
                new JoueurDTO("Joueur1", "Jérôme", 2004 , new ArrayList<String>(), Langue.fr),
                new JoueurDTO("Joueur2", "Martin", 1998, new ArrayList<String>(), Langue.fr)
        ));

        try (MockedConstruction<FournirListeQuestionnaires> mockedFournir =
                Mockito.mockConstruction(FournirListeQuestionnaires.class,
                    (mock, context) -> Mockito.when(mock.fournirListeQuestionnaires(Mockito.anyString()))
                                                  .thenReturn(questionnairesFake))) {

            try (MockedConstruction<JoueurService> mockedJoueur =
                    Mockito.mockConstruction(JoueurService.class,
                    (mock, context) -> Mockito.when(mock.getJoueurs())
                                                  .thenReturn(joueursFake))) {

                PartieService service = new PartieService();
                ElementsDisponiblesDTO result = service.determinerElementsDispoPourPartie();

                assertNotNull(result, "L'élément retourné ne doit pas être null");
                assertEquals(questionnairesFake, result.getListeQuestionnaires(), "La liste des questionnaires doit correspondre aux données factices");
                assertEquals(joueursFake, result.getListeJoueurs(), "La liste des joueurs doit correspondre aux données factices");
            }
        }
    }

    @Test
    void testDeterminerElementsDispoPourPartie_EmptyQuestionnaires() throws Exception {
        ArrayList<QuestionnaireDTO> emptyQuestionnaires = new ArrayList<>();

        try (MockedConstruction<FournirListeQuestionnaires> mockedFournir = 
                Mockito.mockConstruction(FournirListeQuestionnaires.class,
                    (mock, context) -> Mockito.when(mock.fournirListeQuestionnaires(Mockito.anyString()))
                                                  .thenReturn(emptyQuestionnaires))) {

            try (MockedConstruction<JoueurService> mockedJoueur = 
                    Mockito.mockConstruction(JoueurService.class,
                    (mock, context) -> Mockito.when(mock.getJoueurs())
                                                  .thenReturn(new ArrayList<>()))) {

                PartieService service = new PartieService();
                Exception exception = assertThrows(PasDeQuestionnaireTrouve.class,
                        () -> service.determinerElementsDispoPourPartie());
                assertEquals("Aucun questionnaire trouvé", exception.getMessage());
            }
        }
    }

    @Test
    void testDeterminerElementsDispoPourPartie_EmptyJoueurs() throws Exception {
        ArrayList<QuestionnaireDTO> questionnairesFake = new ArrayList<>(Arrays.asList(
                new QuestionnaireDTO("Q1")
        ));
        ArrayList<JoueurDTO> emptyJoueurs = new ArrayList<>();

        try (MockedConstruction<FournirListeQuestionnaires> mockedFournir = 
                Mockito.mockConstruction(FournirListeQuestionnaires.class,
                    (mock, context) -> Mockito.when(mock.fournirListeQuestionnaires(Mockito.anyString()))
                                                  .thenReturn(questionnairesFake))) {

            try (MockedConstruction<JoueurService> mockedJoueur = 
                    Mockito.mockConstruction(JoueurService.class,
                    (mock, context) -> Mockito.when(mock.getJoueurs())
                                                  .thenReturn(emptyJoueurs))) {

                PartieService service = new PartieService();
                Exception exception = assertThrows(PasDeJoueurTrouve.class,
                        () -> service.determinerElementsDispoPourPartie());
                assertEquals("Aucun joueur trouvé", exception.getMessage());
            }
        }
    }
}