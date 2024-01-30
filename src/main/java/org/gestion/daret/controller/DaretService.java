package org.gestion.daret.controller;

import org.gestion.daret.models.Daret;
import org.gestion.daret.repository.DaretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DaretService {

    // haaaaaaaaaad Service mazal mast3mltha f 7ta chi blassa
    // i'm just thinking here
    DaretRepository daretRepository;

    private LocalDate startDate = LocalDate.of(2024, 1, 1);

    @Scheduled(cron = "0 0 0 * * MON") // Exécute tous les lundis à minuit
    public void giveMoneyToNextPerson() {

        // chercher les tontines actives
        List<Daret> all_tontines = daretRepository.findAll();
        List<Daret> tontines = new ArrayList<>();
        Date dateActuelle = new Date();

        for(Daret tontine : all_tontines){
            if (dateActuelle.after(tontine.getDateDemarrage()) && dateActuelle.before(tontine.getDateFin())){
                tontines.add(tontine);
            }
        }


    }
}
