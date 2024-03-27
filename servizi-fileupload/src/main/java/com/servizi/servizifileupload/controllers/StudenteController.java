package com.servizi.servizifileupload.controllers;

import com.servizi.servizifileupload.dto.DownloadProfilePictureDTO;
import com.servizi.servizifileupload.entities.Studente;
import com.servizi.servizifileupload.repositories.StudenteRepository;
import com.servizi.servizifileupload.services.StudenteService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/studenti")
public class StudenteController {

    @Autowired
    private StudenteRepository studenteRepository;
    @Autowired
    private StudenteService studenteService;

    @PostMapping("/addstudent")
    public ResponseEntity<Studente> addStudent(@RequestBody Studente studente){
        studenteService.addStudent(studente);
        return ResponseEntity.ok().body(studente);
    }

    @GetMapping("/getlist")
    public ResponseEntity<List<Studente>> getAllStudent(){
        List<Studente> allStudents =   studenteService.getAllStudents();
        return ResponseEntity.ok().body(allStudents);
    }

    @GetMapping("/getstudent/{id}")
    public ResponseEntity<Optional<Studente>> getStudent(@PathVariable Long id){
        Optional<Studente> studenteOptional = studenteService.getStudent(id);
        return ResponseEntity.ok().body(studenteOptional);
    }

    @PutMapping("/updatestudent/{id}")
    public ResponseEntity<Studente> updateStudentById(@RequestBody Studente studente,@PathVariable Long id){
        Optional<Studente> studenteOptional = studenteService.updateStudent(id,studente);
        if(studenteOptional.isPresent()){
            return ResponseEntity.ok().body(studenteOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteid")
    public ResponseEntity<Optional<Studente>> deleteStudentById(@RequestParam Long id){
        Optional<Studente> studenteDaCancellare = studenteService.deleteStudentById(id);
        if(studenteDaCancellare.isPresent()){
            return ResponseEntity.ok().body(studenteDaCancellare);
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping("/{id}/profile")
    public Studente uploadProfileImage(@PathVariable Long id, @RequestParam MultipartFile profilePicture) throws Exception{
        return studenteService.uploadProfilePicture(id, profilePicture);
    }

    @GetMapping("/{id}/profile")
    public @ResponseBody byte[]  getProfileImage(@PathVariable Long id,HttpServletResponse response) throws Exception {
        DownloadProfilePictureDTO downloadProfilePictureDTO = studenteService.downloadProfilePicture(id);
        String fileName = Arrays.toString(downloadProfilePictureDTO.getStudente().getProfilePicture().getBytes());
        String extension = FilenameUtils.getExtension(fileName);
        switch(extension){
            case "gif":
                response.setContentType(MediaType.IMAGE_GIF_VALUE);
                break;
            case "jpeg":
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                break;
            case "png":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
        }
        response.setHeader("Content disposition","attachment; filename=\""+fileName+"\"");
        return downloadProfilePictureDTO.getProfileImage();

    }
}
