package com.softdev.fmsb.politics.application;

import com.softdev.fmsb.politics.infraestructure.PoliticRepository;
import com.softdev.fmsb.politics.infraestructure.dto.PoliticRequest;
import com.softdev.fmsb.politics.model.Politic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PoliticsService {

    private final PoliticRepository politicRepository;

    public List<Politic> getPolitics() {
        return politicRepository.findAll();
    }

    public void register(PoliticRequest request) {
        Politic politic = new Politic().builder()
                .name(request.getName())
                .description(request.getDescription())
                .state(request.getState())
                .build();

        politicRepository.save(politic);
    }

    public void modify(PoliticRequest request) {

        Optional<Politic> politicInDB= politicRepository.findById(request.getPoliticId());
        politicInDB.get().setName(request.getName());
        politicInDB.get().setDescription(request.getDescription());
        politicInDB.get().setState(request.getState());
        politicRepository.save(politicInDB.get());
    }
}
