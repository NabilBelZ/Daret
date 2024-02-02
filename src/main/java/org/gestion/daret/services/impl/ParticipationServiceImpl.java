package org.gestion.daret.services.impl;

import jakarta.servlet.http.HttpSession;
import org.gestion.daret.dto.MesParticipationDto;
import org.gestion.daret.dto.ParticipationDto;
import org.gestion.daret.dto.UserDto;
import org.gestion.daret.models.Daret;
import org.gestion.daret.models.Participation;
import org.gestion.daret.models.User;
import org.gestion.daret.repository.DaretRepository;
import org.gestion.daret.repository.ParticipationRepository;
import org.gestion.daret.repository.UserRepository;
import org.gestion.daret.services.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParticipationServiceImpl implements ParticipationService {

    private final ParticipationRepository participationRepository;
    private final DaretRepository daretRepository;
    private final UserRepository userRepository;

    @Autowired
    public ParticipationServiceImpl(ParticipationRepository participationRepository, DaretRepository daretRepository, UserRepository userRepository) {
        this.participationRepository = participationRepository;
        this.daretRepository = daretRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveParticipation(Participation participation) {
        participationRepository.save(participation);
    }
    @Override
    public List<ParticipationDto> getAllParticipations() {
        List<Participation> participations = participationRepository.findAllByOrderByIdDesc();
        return convertToDtoList(participations);
    }

    @Override
    public List<ParticipationDto> getMembreDaret(int id) {
        List<Participation> participations = participationRepository.findAllByDaret_Id(id);
        return convertToDtoList(participations);
    }

    @Override
    public List<MesParticipationDto> getMesParticipations(int userId) {
        List<Participation> participations = participationRepository.findByUserIdAndEtatEquals(userId, 1);
        List<MesParticipationDto> mesParticipations = convertToMesParticipationDto(participations);
        return mesParticipations;
    }

    private List<MesParticipationDto> convertToMesParticipationDto(List<Participation> participations) {
        List<MesParticipationDto> mesParticipationDtos = new ArrayList<>();
        for (Participation participation : participations) {
            MesParticipationDto mesParticipationDto = new MesParticipationDto();
            mesParticipationDto.setNomDaret(participation.getDaret().getNom());
            mesParticipationDto.setMontantDaret(participation.getDaret().getMontant());
            mesParticipationDto.setMontantDaretTotal(participation.getDaret().getMontantTotal());
            mesParticipationDto.setDateDemarrage(participation.getDaret().getDateDemarrage());
            mesParticipationDto.setDateFin(participation.getDaret().getDateFin());
            mesParticipationDto.setPeriodeDaret(participation.getDaret().getPeriode());
            mesParticipationDto.setPeriodeTour(participation.getDaret().getPeriodeTour());
            mesParticipationDto.setNbParticipant(participation.getDaret().getNbParticipant());
            mesParticipationDto.setDescriptionDaret(participation.getDaret().getDescription());
            mesParticipationDto.setMontantParticipation(participation.getMontantParticipation());
            mesParticipationDto.setTour(participation.getTour());

            mesParticipationDtos.add(mesParticipationDto);
        }
        return mesParticipationDtos;
    }
@Override
    public List<ParticipationDto> convertToDtoList(List<Participation> participations) {
        List<ParticipationDto> participationDtoList = new ArrayList<>();
        for (Participation participation : participations) {
            ParticipationDto participationDto = new ParticipationDto();
            Daret daret = participation.getDaret();
            participationDto.setId(participation.getId());
            participationDto.setTour(participation.getTour());
            participationDto.setIdDaret(daret.getId());
            participationDto.setNomDaret(daret.getNom());
            participationDto.setMontantTotalDaret(daret.getMontantTotal());
            User user = participation.getUser();
            participationDto.setNomUser(user.getLastname());
            participationDto.setPrenomUser(user.getFirstname());
            participationDto.setMontantDaret(daret.getMontant());
            participationDto.setMontantParticipation(participation.getMontantParticipation());
            participationDto.setEtat(participation.getEtat());
            participationDtoList.add(participationDto);
        }
        return participationDtoList;
    }
    @Override
    public String refuserDemande(int id, int id_daret, RedirectAttributes redirectAttributes) {
        Optional<Participation> optionalParticipation = participationRepository.findById(id);
        optionalParticipation.ifPresent(participation -> {
            if (participation.getEtat() == 0 || participation.getEtat() == 1) {

                try {
                    Daret daret = daretRepository.findById(id_daret).orElseThrow(()-> new Exception("Daret not found!"));
                    if(participation.getEtat() == 1){
                        daret.setNbParticipant(daret.getNbParticipant() - 1);
                        daretRepository.save(daret);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                participation.setEtat(-1);
                participation.setTour(0);
                participationRepository.save(participation);
                redirectAttributes.addFlashAttribute("msgRefus", "La Demande a été refusée !");
            } else if (participation.getEtat() == -1) {
                redirectAttributes.addFlashAttribute("msgRefus", "La Demande a été déjà refusée avant !");
            }
        });
        return "redirect:/listDemandesParticipation";
    }

    @Override
    public String refuserDemande2(int id , int id_daret, RedirectAttributes redirectAttributes) {

        Optional<Participation> optionalParticipation = participationRepository.findById(id);
        optionalParticipation.ifPresent(participation -> {
            if (participation.getEtat() == 0 || participation.getEtat() == 1) {
                try {
                    Daret daret = daretRepository.findById(id_daret).orElseThrow(()-> new Exception("Daret not found!"));
                    if(participation.getEtat() == 1){
                        daret.setNbParticipant(daret.getNbParticipant() - 1);
                        daretRepository.save(daret);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                participation.setEtat(-1);
                participation.setTour(0);
                participationRepository.save(participation);
                redirectAttributes.addFlashAttribute("msgRefus", "La Demande a été refusée !");
            } else if (participation.getEtat() == -1) {
                redirectAttributes.addFlashAttribute("msgRefus", "La Demande a été déjà refusée avant !");
            }
        });
        redirectAttributes.addAttribute("id_daret", id_daret);
        return "redirect:/membreDaret/{id_daret}";
    }

    @Override
    public String accepterDemande(HttpSession session, int id, int id_daret, RedirectAttributes redirectAttributes){
        Optional<Participation> optionalParticipation = participationRepository.findById(id);
        User user = optionalParticipation.get().getUser();
        int userId = user.getId();
        optionalParticipation.ifPresent(participation -> {
            if (participation.getEtat() == 0 || participation.getEtat() == -1) {
                participation.setEtat(1);
                try {
                    Daret daret = daretRepository.findById(id_daret).orElseThrow(()-> new Exception("Daret not found!"));
                    daret.setNbParticipant(daret.getNbParticipant() + 1);
                    daretRepository.save(daret);

                    User user2 = userRepository.findById(userId).orElseThrow(()-> new Exception("user not found"));
                    double nvSolde = user2.getSolde() - daret.getMontantTotal();
                    user2.setSolde(nvSolde);

                    userRepository.save(user2);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                participationRepository.save(participation);
                redirectAttributes.addFlashAttribute("msgAccept", "La Demande a été acceptée !");
            } else if (participation.getEtat() == 1) {
                redirectAttributes.addFlashAttribute("msgAccept", "La Demande a été déjà acceptée avant !");
            }
        });

        return "redirect:/listDemandesParticipation";
    }

    @Override
    public String accepterDemande2(int id_participation, int id_daret, RedirectAttributes redirectAttributes){
        Optional<Participation> optionalParticipation = participationRepository.findById(id_participation);
        User user = optionalParticipation.get().getUser();
        int userId = user.getId();
        optionalParticipation.ifPresent(participation -> {
            if (participation.getEtat() == 0 || participation.getEtat() == -1) {
                participation.setEtat(1);
                try {
                    Daret daret = daretRepository.findById(id_daret).orElseThrow(()-> new Exception("Daret not found!"));
                    daret.setNbParticipant(daret.getNbParticipant() + 1);
                    daretRepository.save(daret);

                    User user2 = userRepository.findById(userId).orElseThrow(()-> new Exception("user not found"));
                    double nvSolde = user2.getSolde() - daret.getMontantTotal();
                    user2.setSolde(nvSolde);

                    userRepository.save(user2);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                participationRepository.save(participation);
                redirectAttributes.addFlashAttribute("msgAccept", "La Demande a été acceptée !");
            } else if (participation.getEtat() == 1) {
                redirectAttributes.addFlashAttribute("msgAccept", "La Demande a été déjà acceptée avant !");
            }
        });
        redirectAttributes.addAttribute("id_daret", id_daret);
        return "redirect:/membreDaret/{id_daret}";
    }

    @Override
    public String mettreEnAttenteDemande(int id, int id_daret, RedirectAttributes redirectAttributes) {
        Optional<Participation> optionalParticipation = participationRepository.findById(id);

        optionalParticipation.ifPresent(participation -> {
            if (participation.getEtat() == 1 || participation.getEtat() == -1) {
                try {
                    Daret daret = daretRepository.findById(id_daret).orElseThrow(()-> new Exception("Daret not found!"));
                    if(participation.getEtat() == 1){
                        daret.setNbParticipant(daret.getNbParticipant() - 1);
                        daretRepository.save(daret);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                participation.setEtat(0);
                participationRepository.save(participation);
                redirectAttributes.addFlashAttribute("msgAttente", "La demande a été mise en attente.");
            } else if (participation.getEtat() == 0) {
                redirectAttributes.addFlashAttribute("msgAttente2", "La demande est déjà en attente !");
            }
        });
        return "redirect:/listDemandesParticipation";
    }

    @Override
    public String mettreEnAttenteDemande2(int id, int id_daret, RedirectAttributes redirectAttributes) {
        Optional<Participation> optionalParticipation = participationRepository.findById(id);

        optionalParticipation.ifPresent(participation -> {
            if (participation.getEtat() == 1 || participation.getEtat() == -1) {
                try {
                    Daret daret = daretRepository.findById(id_daret).orElseThrow(()-> new Exception("Daret not found!"));
                    if(participation.getEtat() == 1){
                        daret.setNbParticipant(daret.getNbParticipant() - 1);
                        daretRepository.save(daret);
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                participation.setEtat(0);
                participationRepository.save(participation);
                redirectAttributes.addFlashAttribute("msgAttente", "La demande a été mise en attente.");
            } else if (participation.getEtat() == 0) {
                redirectAttributes.addFlashAttribute("msgAttente2", "La demande est déjà en attente !");
            }
        });
        redirectAttributes.addAttribute("id_daret", id_daret);
        return "redirect:/membreDaret/{id_daret}";
    }

    @Override
    public String supprimerDemande(int id, RedirectAttributes redirectAttributes){
        participationRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("msgSuppression","La demande a été supprimée");
        return "redirect:/listDemandesParticipation";
    }

    @Override
    public String supprimerDemande2(int id, int id_daret, RedirectAttributes redirectAttributes){
        participationRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("msgSuppression", "La demande a été supprimée");
        redirectAttributes.addAttribute("id_daret", id_daret);
        return "redirect:/membreDaret/{id_daret}";
    }



}