package com.softdev.fmsb.creditApplication.infraestructure;

import com.softdev.fmsb.creditApplication.application.CreditApplicationService;
import com.softdev.fmsb.creditApplication.infraestructure.dto.CreditApplicationRequest;
import com.softdev.fmsb.creditApplication.infraestructure.dto.VerifyClientRequest;
import com.softdev.fmsb.creditApplication.infraestructure.dto.VerifyRegularClientResponse;
import com.softdev.fmsb.creditApplication.model.CreditApplication;
import com.softdev.fmsb.creditApplication.model.CreditApplicationStatus;
import com.softdev.fmsb.creditApplication.model.Reference;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;


@RestController
@RequestMapping("/api/v1/creditApplication")
@RequiredArgsConstructor
public class CreditApplicationController {

    private final CreditApplicationService creditApplicationService;
    private final String IDENTIFICATION_FILE = "identificacion";
    private final String PROOF_OF_INCOME_FILE = "comprobanteIngreso";
    private final String PROOF_OF_ADDRESS_FILE = "comprobanteDomicilio";

    @GetMapping("/")
    public ResponseEntity<?> getAllCreditsApplications() {
        try {
            List<CreditApplication> creditApplications = creditApplicationService.getAllCreditApplication();

            // Cargar los PDFs en cada CreditApplication
            for (CreditApplication creditApplication : creditApplications) {
                creditApplication.setIdentificationPdf(loadPdf(creditApplication.getIdentificationPdfPath()));
                creditApplication.setProofOfIncomePdf(loadPdf(creditApplication.getProofOfIncomePdfPath()));
                creditApplication.setProofOfAddressPdf(loadPdf(creditApplication.getProofOfAddressPdfPath()));
            }

            return ResponseEntity.ok(creditApplications);
        } catch (Exception e) {
            // TODO: Log exception
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    private byte[] loadPdf(String filePath) {
        if (filePath == null || filePath.equals("error")) {
            return null;
        }

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return null;
            }

            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            // TODO: Log exception
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCreditAplicationById(@PathVariable Integer id) {
        try {
            CreditApplication response = creditApplicationService.getCreditAplicationById(id);
            if (response != null) {
                return ResponseEntity.ok(response);
            } else{
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            //TODO: Log exception
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/regularClient")
    public ResponseEntity<?> getIsRegularClient(VerifyClientRequest verifyClientRequest) {
        try {
            VerifyRegularClientResponse verifyRegularClientResponse = creditApplicationService.verifyRegularClientResponse(verifyClientRequest);
            return ResponseEntity.ok(verifyRegularClientResponse);
        } catch (Exception e) {
            //TODO: Log exception
            e.printStackTrace();//TODO Borrar
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/applicate")
    public ResponseEntity<?> applicateForCredit(@RequestBody CreditApplicationRequest creditApplicationRequest) {

        CreditApplication creditApplication = convertCreditApplicationRequestToDto(creditApplicationRequest);

        try {
            creditApplicationService.createCreditApplication(creditApplication);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            //TODO: Log exception
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    private CreditApplication convertCreditApplicationRequestToDto (CreditApplicationRequest creditApplicationRequest){

        CreditApplication creditApplication = new CreditApplication();

        String clientRfc = creditApplicationRequest.getClientRfc();
        int creditTypeId = creditApplicationRequest.getSelectedCredit().getCreditTypeId();
        Date actualDate = new Date();

        List<Reference> references = new ArrayList<>();
        references.add(creditApplicationRequest.getFirstReference());
        references.add(creditApplicationRequest.getSecondReference());

        creditApplication.setDateOfApplication(actualDate);
        creditApplication.setStatus(CreditApplicationStatus.ON_HOLD_FOR_DICTAMEN);
        creditApplication.setReferences(references);
        creditApplication.setIdentificationPdfPath(savePdf(creditApplicationRequest.getIdentificationPdf(),IDENTIFICATION_FILE, clientRfc));
        creditApplication.setProofOfAddressPdfPath(savePdf(creditApplicationRequest.getProofOfAddressPdf(), PROOF_OF_ADDRESS_FILE, clientRfc));
        creditApplication.setProofOfIncomePdfPath(savePdf(creditApplicationRequest.getProofOfIncomePdf(), PROOF_OF_INCOME_FILE, clientRfc));

        try{
            creditApplication.setCreditApplicant(creditApplicationService.getClientByRfc(clientRfc));
            creditApplication.setSelectedCredit(creditApplicationService.getCreditTypeById(creditTypeId));
            return creditApplication;
        }
        catch (Exception e){
            //TODO: Log exception
            e.printStackTrace();
            return creditApplication;
        }
    }

    private String savePdf(byte[] bytesPDF, String fileName, String rfc) {
        try {
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateString = currentDate.format(formatter);

            String actualDirectory = System.getProperty("user.dir");
            String clientDirectory = actualDirectory + File.separator + rfc + File.separator + dateString;
            String fileDirectory = clientDirectory + File.separator + fileName + ".pdf";
            File fileClientDirectory = new File(clientDirectory);

            if (!fileClientDirectory.exists()) {
                fileClientDirectory.mkdirs();
            }

            FileOutputStream fileOutputStream = new FileOutputStream(fileDirectory);
            fileOutputStream.write(bytesPDF);
            fileOutputStream.close();

            System.out.println("PDF guardado exitosamente en: " + fileDirectory);

            return fileDirectory;
        } catch (IOException e) {
            System.out.println("Error al guardar el PDF: " + e.getMessage());
            e.printStackTrace();
            return "error";
        }
    }
}


