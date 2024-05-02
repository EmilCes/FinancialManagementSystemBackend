package com.softdev.fmsb.worker.infraestructure;


import com.softdev.fmsb.worker.infraestructure.dto.ModifyRequest;
import com.softdev.fmsb.worker.infraestructure.dto.RegisterRequest;
import com.softdev.fmsb.worker.application.WorkerService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/worker")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

    @GetMapping("/")
    public ResponseEntity<?> getUser(String rfc){
        var response = workerService.getUser(rfc);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-users")
    public ResponseEntity<?> getAllUsers(){
        var response = workerService.getAllUsers();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        var response = workerService.register(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> delete(String rfc){
        boolean response = workerService.deleteUser(rfc);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/modify-user")
    public ResponseEntity<?> modify(@RequestBody ModifyRequest request){
        var response = workerService.updateUser(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/rfc-exist")
    public ResponseEntity<?> rfcExist(String rfc){
        try{
            boolean isRfcRegistered = workerService.verifyRfc(rfc);
            return ResponseEntity.ok(isRfcRegistered);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user-number-exist")
    public ResponseEntity<?> userNumberExist(String userNumber){
        try{
            boolean isUserNumberRegistered = workerService.verifyUserNumber(userNumber);
            return ResponseEntity.ok(isUserNumberRegistered);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }



}
