package com.agh.emt.service.parameters;

import com.agh.emt.model.form.RecruitmentForm;
import com.agh.emt.model.form.RecruitmentFormRepository;
import com.agh.emt.model.parameters.Parameter;
import com.agh.emt.model.parameters.ParameterRepository;
import com.agh.emt.model.user.UserRepository;
import com.agh.emt.service.form.RecruitmentFormNotFoundException;
import com.agh.emt.service.one_drive.OneDriveService;
import com.agh.emt.utils.authentication.Role;
import com.agh.emt.utils.parameters.ParameterNames;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ParameterService {
    ParameterRepository parameterRepository;
    OneDriveService oneDriveService;
    UserRepository userRepository;
    RecruitmentFormRepository recruitmentFormRepository;

    public Parameter findParameter(String id) throws ParameterNotFoundException {
        return parameterRepository.findById(id).orElseThrow(() -> new ParameterNotFoundException("Nie znaleziono parametru: " + id));
    }

    public Parameter setParameter(Parameter parameter) {
        return parameterRepository.save(parameter);
    }

    public Parameter setCurrentEdition(String edition){
        return setParameter(new Parameter(ParameterNames.ERASMUS_EDITION, edition));
    }

    public String getCurrentEdition() throws ParameterNotFoundException {
        return findParameter(ParameterNames.ERASMUS_EDITION).getValue();
    }

    public List<String> createEdition(String edition) throws RecruitmentFormNotFoundException, ParameterNotFoundException {
        oneDriveService.createChild(ParameterNames.ERASMUS_EDITION,edition);
        return  getAllEditions();
    }


    private void deletAllFromEdition(String edition){
        userRepository.findAll().stream()
                .filter(user -> user.getRole() == Role.ROLE_STUDENT)
                .forEach(user -> {
                    user.getRecruitmentForms()
                            .removeIf(recruitmentForm -> recruitmentForm!=null &&
                                    recruitmentForm.getOneDriveFormPath()!=null &&
                                    recruitmentForm.getOneDriveFormPath().contains(edition));
                    user = userRepository.save(user);
                });

        recruitmentFormRepository.findAll().stream()
                .filter(recruitmentForm -> recruitmentForm!=null &&
                        recruitmentForm.getOneDriveFormPath()!=null &&
                        recruitmentForm.getOneDriveFormPath().contains(edition))
                .forEach(recruitmentForm -> {
                    recruitmentFormRepository.deleteById(recruitmentForm.getId());
                    try {
                        oneDriveService.deleteRecruitmentDocumentFromId(recruitmentForm.getOneDriveScanId());
                    } catch (RecruitmentFormNotFoundException ignored) {}
                });
    }

    public String deleteEdition(String edition) throws RecruitmentFormNotFoundException {
        oneDriveService.deleteRecruitmentObjFromPath(ParameterNames.ERASMUS_EDITION + "/" + edition);
        deletAllFromEdition(edition);
        return edition;
    }

    public List<String> getAllEditions() throws RecruitmentFormNotFoundException, ParameterNotFoundException {
        LinkedList<String> allEditions =  oneDriveService.getChildrenList(ParameterNames.ERASMUS_EDITION).stream()
                .filter(val-> !val.equals("results") && !val.equals("wzor"))
                .collect(Collectors.toCollection(LinkedList::new));
        allEditions.remove(getCurrentEdition());
        allEditions.addFirst(getCurrentEdition());
        return allEditions;
    }
}
