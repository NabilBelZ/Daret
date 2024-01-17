package org.gestion.daret.services.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.gestion.daret.dto.DaretDto;
import org.gestion.daret.models.Daret;
import org.gestion.daret.models.User;
import org.gestion.daret.repository.DaretRepository;
import org.gestion.daret.repository.UserRepository;
import org.gestion.daret.services.DaretService;
import org.gestion.daret.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.net.http.HttpRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DaretServiceImpl implements DaretService {

    private final DaretRepository daretRepository;

    @Autowired
    public DaretServiceImpl(DaretRepository daretRepository) {
        this.daretRepository = daretRepository;
    }

    @Override
    public String createDaret(DaretDto daretDto, RedirectAttributes redirectAttributes) {
        Daret daret = new Daret();
        daret.setId(daretDto.getId());
        daret.setNom(daretDto.getNom());
        daret.setMontant(daretDto.getMontant());
        //daret.setDateDemarrage(daretDto.getDateDemarrage());
        //daret.setDateFin(daretDto.getDateFin());
        //SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //Date dateDemarrage = dateTimeFormat.parse(daretDto.getDateDemarrage() + " 00:00:00");
            //Date dateFin = dateTimeFormat.parse(daretDto.getDateFin() + " 23:59:59");
            Date dateDemarrage = dateFormat.parse(daretDto.getDateDemarrage());
            Date dateFin = dateFormat.parse(daretDto.getDateFin());


            daret.setDateDemarrage(dateDemarrage);
            daret.setDateFin(dateFin);
        } catch (ParseException e) {

            e.printStackTrace();
            }
        daret.setPeriode(daretDto.getPeriode());
        daret.setPeriodeTour(daretDto.getPeriodeTour());
        daret.setMontantTotal(daretDto.getMontantTotal());
        String cleanedDescription = daretDto.getDescription().replaceAll("<p>", "").replaceAll("</p>", "");
        daret.setDescription(cleanedDescription);
        daret.setEtat(true);
        //daret.setNbParticipant(0);
      //  daret.setTourDeRole(new ArrayList<>());
       // daret.setParticipations(new ArrayList<>());
        daretRepository.save(daret);
        redirectAttributes.addFlashAttribute("msgSuccess", "Félicitations ! La Daret a été créée avec succès !");
        return "redirect:/tontines";
    }
    @Override
    public String listeDarets(Model model){
        List<Daret> darets = daretRepository.findAllByEtatIsTrueOrderByIdDesc();;
        model.addAttribute("darets", darets);
        return "tontines";
    }

    @Override
    public String readDaret(DaretDto DaretDto, Model model) {
        return null;
    }

    @Override
    public String updateDaret(DaretDto daretDto, RedirectAttributes redirectAttributes) {
            int id = daretDto.getId();
            Optional<Daret> optionalDaret = daretRepository.findById(id);
            if (optionalDaret.isPresent()) {
                Daret daret = optionalDaret.get();
                daret.setNom(daretDto.getNom());
                String cleanedDescription = daretDto.getDescription().replaceAll("<p>", "").replaceAll("</p>", "");
                daret.setDescription(cleanedDescription);
                daret.setMontant(daretDto.getMontant());
                daret.setMontantTotal(daretDto.getMontantTotal());
                daret.setPeriode(daretDto.getPeriode());
                daret.setPeriodeTour(daretDto.getPeriodeTour());
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date dateDemarrage = dateFormat.parse(daretDto.getDateDemarrage());
                    Date dateFin = dateFormat.parse(daretDto.getDateFin());
                    daret.setDateDemarrage(dateDemarrage);
                    daret.setDateFin(dateFin);
                } catch (ParseException e) {
                    redirectAttributes.addFlashAttribute("msgErreur", "Format de date invalide lors de la modification de la Daret.");
                    return "redirect:/tontines";
                }
                daret.setEtat(true);
                daretRepository.save(daret);
                redirectAttributes.addFlashAttribute("msgModif", "La Daret a été modifiée avec succès !");
                return "redirect:/tontines";
            }
        return "redirect:/tontines";
    }


    @Override
    public String deleteDaret(int id ,RedirectAttributes redirectAttributes) {

        Optional<Daret> optionalDaret = daretRepository.findById(id);
        optionalDaret.ifPresent(daret -> {
            daret.setEtat(false);
            daretRepository.save(daret);
        });
        redirectAttributes.addFlashAttribute("msgSupp", "La Daret a été supprimée avec succès !");
        return "redirect:/tontines";
    }

    @Override
    public String redirectionAjout(DaretDto daretDto, Model model, HttpSession session) {
        session.setAttribute("nom", daretDto.getNom());
        session.setAttribute("description", daretDto.getDescription());
        return "redirect:/2emeform";
    }
    @Override
    public String getInfo2emeForm(Model model, HttpSession session) {
        //Object nom = request.getAttribute("nom");
        //Object description = request.getAttribute("description");
        String nom = (String) session.getAttribute("nom");
        String description = (String) session.getAttribute("description");
        model.addAttribute("nom", nom);
        model.addAttribute("description", description);

        return "ajouterDaret";
    }

    @Override
    public DaretDto getDaretDetails(int id) {
        Optional<Daret> optionalDaret = daretRepository.findById(id);
        if (optionalDaret.isPresent()){
            Daret daret = optionalDaret.get();
            return convertToDto(daret);
        }

        return null;
    }

    private DaretDto convertToDto(Daret daret) {
        DaretDto daretDto = new DaretDto();
        daretDto.setId(daret.getId());
        daretDto.setNom(daret.getNom());
        daretDto.setMontant(daret.getMontant());


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (daret.getDateDemarrage() != null) {
            daretDto.setDateDemarrage(dateFormat.format(daret.getDateDemarrage()));
        }
        if (daret.getDateFin() != null) {
            daretDto.setDateFin(dateFormat.format(daret.getDateFin()));
        }

        daretDto.setPeriode(daret.getPeriode());
        daretDto.setPeriodeTour(daret.getPeriodeTour());
        daretDto.setMontantTotal(daret.getMontantTotal());
        daretDto.setDescription(daret.getDescription());
        daretDto.setNbParticipant(daretDto.getNbParticipant());
        daretDto.setEtat(daret.isEtat());

        return daretDto;
    }

}
