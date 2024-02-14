package com.example.Penta.Controller;

import com.example.Penta.Entity.StudentTeacherRequestMap;
import com.example.Penta.Service.StudentTeacherMapService;
import com.example.Penta.dto.DeleteStuTeachMapRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class StudTeacherReqMapController {
    @Autowired
    private StudentTeacherMapService studentTeacherMapService;

    // student request to a teacher
    @PostMapping("/studTeacherReq/save")
    public ResponseEntity<?> saveRequest(@RequestBody StudentTeacherRequestMap map){
        String response = studentTeacherMapService.saveData(map);
        if(response.equals("Request Sent")){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        else if(response.equals("Already have an Advisor")){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }

    }

    // If any teacher reject
    @DeleteMapping("/studTeacherReq/delete/{teacher_user_id}")
    public ResponseEntity<?> deleteStudentTeacherMap(@PathVariable("teacher_user_id") UUID teacher_user_id,
                                                     @RequestBody DeleteStuTeachMapRequest request){
        String response = studentTeacherMapService.deleteStudentTeacherMapId(request.getStudent_user_id(),teacher_user_id);
        if(response.equals("Request Deleted")){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
