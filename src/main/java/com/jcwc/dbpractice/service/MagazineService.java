package com.jcwc.dbpractice.service;

import com.jcwc.dbpractice.domain.repository.jpa.MagazineRepository;
import com.jcwc.dbpractice.dto.MagazineDTO;
import com.jcwc.dbpractice.entity.Magazine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MagazineService {

    private final MagazineRepository magazineRepository;

    public List<MagazineDTO> getAllMagazines() {
        return magazineRepository.findAll()
                .stream()
                .map(magazine -> new MagazineDTO(
                        magazine.getId(),
                        magazine.getTitle(),
                        magazine.getIsbn(),
                        magazine.getPublicationYear(),
                        magazine.getPrice()
                        ))
                .toList();
    }
}
