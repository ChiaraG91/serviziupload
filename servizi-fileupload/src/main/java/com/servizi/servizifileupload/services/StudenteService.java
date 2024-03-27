package com.servizi.servizifileupload.services;

import com.servizi.servizifileupload.dto.DownloadProfilePictureDTO;
import com.servizi.servizifileupload.entities.Studente;
import com.servizi.servizifileupload.repositories.StudenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class StudenteService {

    @Autowired
    private StudenteRepository studenteRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public Studente addStudent(Studente studente){
       studenteRepository.save(studente);
        return studente;
    }

    public List<Studente> getAllStudents() {
        List<Studente> studenteList= studenteRepository.findAll();
        return studenteList;
    }

    public Optional<Studente> getStudent(Long id) {
        Optional<Studente> studenteOptional = studenteRepository.findById(id);
        return studenteOptional;
    }

    public Optional<Studente> updateStudent(Long id,Studente studente){
        Optional<Studente> studenteDaAggiornare = studenteRepository.findById(id);
        if (studenteDaAggiornare.isPresent()){
            studenteDaAggiornare.get().setNome(studente.getNome());
            studenteRepository.save(studenteDaAggiornare.get());
        } else {
            return Optional.empty();
        }
        return studenteDaAggiornare;
    }

    public Optional<Studente> deleteStudentById(Long id){
        Optional<Studente> studenteDaCancellareOptional = studenteRepository.findById(id);
        if(studenteDaCancellareOptional.isPresent()){
            studenteRepository.delete(studenteDaCancellareOptional.get());
            return studenteDaCancellareOptional;
        }else{
            return Optional.empty();
        }

    }


    public Studente uploadProfilePicture(Long id, MultipartFile profilePicture) throws Exception {
        Optional<Studente> studenteOPT = studenteRepository.findById(id);
        if(!studenteOPT.isPresent()) throw new Exception("Student not found");
        String fileName = fileStorageService.upload(profilePicture);
        Studente studente = studenteOPT.get();
        studente.setProfilePicture(fileName);
        studenteRepository.save(studente);
        return studente;
    }

    public DownloadProfilePictureDTO downloadProfilePicture(Long id) throws Exception {
        Optional<Studente> studente = getStudent(id);
        if (!studente.isPresent()) {
            throw new Exception("Studente non trovato");
        }
        Studente studenteInstance = studente.get();
        DownloadProfilePictureDTO dto = new DownloadProfilePictureDTO();
        dto.setStudente(studenteInstance);
        if (studenteInstance.getProfilePicture() == null) return dto;

        byte[] profilePicture = fileStorageService.download(studenteInstance.getProfilePicture());
        dto.setProfileImage(profilePicture);
        return dto;

    }
}
