package org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.services.interfaces;

import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.entities.dto.ElementsDisponiblesDTO;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr5.jeuQuizz.utils.exceptions.determinerElementsDispoExceptions.DeterminerElementsDispoException;

public interface DeterminerElementsDispoPourPartieInterface {

    public ElementsDisponiblesDTO determinerElementsDispoPourPartie() throws DeterminerElementsDispoException;

}
