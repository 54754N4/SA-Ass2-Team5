package rmit.team5.visiderm.Service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmit.team5.visiderm.DAO.Intereface.IPatientDAO;
import rmit.team5.visiderm.DTO.PatientDTO;
import rmit.team5.visiderm.Exception.PatientNotFoundException;
import rmit.team5.visiderm.Model.PatientInfo.Address;
import rmit.team5.visiderm.Model.PatientInfo.ContactInfo;
import rmit.team5.visiderm.Model.PatientInfo.NextToKin;
import rmit.team5.visiderm.Model.PatientInfo.Patient;
import rmit.team5.visiderm.Service.Interface.IPatientService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService implements IPatientService {

    private final IPatientDAO patientDAO;
    private static final int PAGE_LIMIT = 10;

    @Autowired
    public PatientService (IPatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }


    @Override
    public HashMap<String, Object> getListOfPatient(int page) {
        page = page -1; // the page request in JPA is started from 0 not 1
        Pageable pageRequest = PageRequest.of(page, PAGE_LIMIT);
        Page<Patient> queryResult = patientDAO.findAll(pageRequest);
        return constructPatientSumQuery(queryResult);
    }

    @Override
    public HashMap<String, Object> getPatientByName(String patientName, int page) {
        page = page -1; // the page request in JPA is started from 0 not 1
        Pageable pageRequest = PageRequest.of(page, PAGE_LIMIT);
        Page<Patient> queryResult = patientDAO.findAllPatientWithName(patientName, pageRequest);
        return constructPatientSumQuery(queryResult);
    }

    @Override
    public HashMap<String, Object> getPatientByID(long patientID) {
        Optional<Patient> patient = patientDAO.findById(patientID);
        if (!patient.isPresent()) throw new PatientNotFoundException("Patient with ID: " + patientID + " is not existed");
        else {
            return constructPatientSumInfo(patient.get());
        }
    }

    @Override
    public Patient getPatientDetail(long patientID) {
        Optional<Patient> patient = patientDAO.findById(patientID);
        if (!patient.isPresent()) throw new PatientNotFoundException("Patient with ID: " + patientID + " is not existed");
        else return patient.get();
    }


    @Override
    public boolean addNewPatient(PatientDTO patientDTO) {
        Patient newPatient = new Patient();
        getPatientInfoFromDTO(patientDTO, newPatient);

        ContactInfo contactInfo = getContactInfoFromDTO(patientDTO);
        contactInfo.setPatient(newPatient);
        newPatient.setContactInfo(contactInfo);

        NextToKin nextToKin = getNTKInfoFromDTO(patientDTO);
        nextToKin.setPatient(newPatient);
        newPatient.setNextToKin(nextToKin);

        Address address = getAddressInfoFromDTO(patientDTO);
        address.setPatient(newPatient);
        newPatient.setHomeAddress(address);

        patientDAO.save(newPatient);
        return true;
    }

    @Override
    public boolean updateNewPatient(PatientDTO patientDTO, long ID) {
        Optional<Patient> patientCheck = patientDAO.findById(ID);
        if (patientCheck.isPresent()) {
            Patient updatePatient = patientCheck.get();
            getPatientInfoFromDTO(patientDTO, updatePatient);
            Address address;
            ContactInfo contactInfo;
            NextToKin nextToKin;
            // check if the current home address is null or not. if yes create new one
            if (updatePatient.getHomeAddress() == null) address = new Address();
            else address = updatePatient.getHomeAddress();
            // check if the current contact info is null or not. if yes create new one
            if (updatePatient.getContactInfo() == null) contactInfo = new ContactInfo();
            else contactInfo = updatePatient.getContactInfo();
            // check if the current next to kin is null or not. if yes create new one
            if (updatePatient.getNextToKin() == null) nextToKin = new NextToKin();
            else nextToKin = updatePatient.getNextToKin();

            setNewAddressInfo(patientDTO, address);
            setNewContactInfo(patientDTO, contactInfo);
            setNewNTKInfo(patientDTO, nextToKin);
            address.setPatient(updatePatient);
            contactInfo.setPatient(updatePatient);
            nextToKin.setPatient(updatePatient);
            updatePatient.setHomeAddress(address);
            updatePatient.setContactInfo(contactInfo);
            updatePatient.setNextToKin(nextToKin);
            patientDAO.save(updatePatient);
            return true;
        }else return false;
    }

    // construct the data patient table
    private HashMap<String, Object> constructPatientSumQuery (Page<Patient> queryResult) {
        HashMap<String, Object> returnPatientList = new HashMap<>();
        // get only patientID, name, gender, dob, address, phone
        List<HashMap<String, Object>> patientResult = new ArrayList<>();
        for (Patient p : queryResult.getContent()) {
            HashMap<String, Object> singlePatient = constructPatientSumInfo(p);
            patientResult.add(singlePatient);
        }
        returnPatientList.put("totalPage", queryResult.getTotalPages());
        returnPatientList.put("currentPage", queryResult.getPageable().getPageNumber() + 1);
        returnPatientList.put("data", patientResult);
        return returnPatientList;
    }

    // construct patient's information for screen 1 display including:
    // id; name, dob, address, mobileNum and gender
    private HashMap<String, Object> constructPatientSumInfo (Patient p) {
        Address patientAddress = p.getHomeAddress();
        String addressStr = "";
        String mobilePhone = "";
        if (p.getContactInfo() == null || p.getContactInfo().getMobilePhone() == null) mobilePhone = "N/A";
        else mobilePhone = p.getContactInfo().getMobilePhone();
        if (patientAddress == null) addressStr = "N/A";
        else addressStr = patientAddress.toString();

        String name = p.getFirstName() + " " + p.getLastName();
        HashMap<String, Object> singlePatient = new HashMap<>();
        singlePatient.put("id", p.getPatientID());
        singlePatient.put("name", name);
        singlePatient.put("dob", p.getBirthDay());
        singlePatient.put("address", addressStr);
        singlePatient.put("mobileNum", mobilePhone);
        singlePatient.put("gender", p.getGender());
        return singlePatient;
    }

    // get the patient information from the DTO
    private void getPatientInfoFromDTO (PatientDTO patientDTO, Patient patient) {
        patient.setFirstName(patientDTO.getFirstName().trim());
        patient.setLastName(patientDTO.getLastName().trim());
        patient.setBirthDay(patientDTO.getBirthDay());
        patient.setEmail(patientDTO.getEmail().trim());
        patient.setOccupation(patientDTO.getOccupation().trim());
        patient.setMarried(patientDTO.isMarried());
        patient.setTitle(patientDTO.getTitle().trim());
        patient.setGender(patientDTO.getGender());
    }

    // get the address inforamtion from the DTO
    private Address getAddressInfoFromDTO (PatientDTO patientDTO) {
        Address address = new Address();
        address.setCountry(patientDTO.getCountry().trim());
        address.setStreetAddress(patientDTO.getStreetAddress().trim());
        address.setSuburd(patientDTO.getSuburd().trim());
        return address;
    }

    // get the contact inforamtion from the DTO
    private ContactInfo getContactInfoFromDTO (PatientDTO patientDTO) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setFaxNumber(patientDTO.getFaxNumber().trim());
        contactInfo.setHomePhone(patientDTO.getHomePhone().trim());
        contactInfo.setMobilePhone(patientDTO.getMobilePhone().trim());
        contactInfo.setOfficePhone(patientDTO.getOfficePhone().trim());
        return contactInfo;
    }

    // set the next to kin information from the DTO
    private NextToKin getNTKInfoFromDTO (PatientDTO patientDTO) {
        NextToKin nextToKin = new NextToKin();
        nextToKin.setContactNum(patientDTO.getNtkContactInfo().trim());
        nextToKin.setName(patientDTO.getNtkName().trim());
        return nextToKin;
    }

    // set the information from the DTO
    private void setNewAddressInfo (PatientDTO patientDTO, Address address) {
        address.setCountry(patientDTO.getCountry().trim());
        address.setStreetAddress(patientDTO.getStreetAddress().trim());
        address.setSuburd(patientDTO.getSuburd().trim());
    }

    // set the contact information from the DTO
    private void setNewContactInfo (PatientDTO patientDTO, ContactInfo contactInfo){
        contactInfo.setFaxNumber(patientDTO.getFaxNumber().trim());
        contactInfo.setHomePhone(patientDTO.getHomePhone().trim());
        contactInfo.setMobilePhone(patientDTO.getMobilePhone().trim());
        contactInfo.setOfficePhone(patientDTO.getOfficePhone().trim());
    }

    // set the next to kin information from the DTO
    private void setNewNTKInfo (PatientDTO patientDTO, NextToKin nextToKin){
        nextToKin.setContactNum(patientDTO.getNtkContactInfo().trim());
        nextToKin.setName(patientDTO.getNtkName().trim());
    }
}
