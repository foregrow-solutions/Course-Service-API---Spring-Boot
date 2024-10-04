package com.winggs.course.service.impl;

import com.winggs.course.modal.common.ApplicationException;
import com.winggs.course.modal.common.ErrorCode;
import com.winggs.course.modal.dto.SchoolDto;
import com.winggs.course.modal.entity.Facility;
import com.winggs.course.modal.entity.School;
import com.winggs.course.modal.entity.User;
import com.winggs.course.modal.enums.Role;
import com.winggs.course.repo.FacilityRepo;
import com.winggs.course.repo.SchoolRepo;
import com.winggs.course.repo.StudentRepo;
import com.winggs.course.repo.UserRepo;
import com.winggs.course.service.SchoolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {
    private final UserRepo userRepo;
    private final SchoolRepo schoolRepo;
    private final StudentRepo studentRepo;
    private final FacilityRepo facilityRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public SchoolDto add(SchoolDto schoolDto) {
        userRepo.findUserByEmail(schoolDto.getEmail()).ifPresent(user -> {
            throw new ApplicationException(ErrorCode.BAD_REQUEST, "Given Email Already Exists");
        });
        User user = new User();
        user.setEmail(schoolDto.getEmail());
        user.setMobile(schoolDto.getMobile());
        user.setPassword(passwordEncoder.encode("password"));
        user.setRole(Role.SCHOOL_ADMIN);
        user.setCreatedDate(Instant.now());
        User admin = userRepo.save(user);

        School school = new School();
        school.setName(schoolDto.getName());
        school.setDunsNo(schoolDto.getDunsNo());
        school.setFedTaxNo(schoolDto.getFedTaxNo());
        school.setAddress(schoolDto.getAddress());
        school.setWebsite(schoolDto.getWebsite());
        school.setUser(admin);
        school.setFee(schoolDto.getFee());
        school.setCreatedDate(Instant.now());
        school.setModifiedDate(Instant.now());
        School save = schoolRepo.save(school);

        //Todo: Remove This approach

        Facility schoolFacility = new Facility();
        schoolFacility.setSchool(save);
        schoolFacility.setUser(admin);
        Facility facility = facilityRepo.save(schoolFacility);

        userRepo.findById(admin.getId()).map(user1 -> {
            user1.setSchool(save);
            return user1;
        });
        return SchoolDto.of(save);
    }

    @Override
    @Transactional
    public Optional<SchoolDto> update(String id, SchoolDto schoolDto) {
        return schoolRepo.findById(id).map(school -> {
            school.getUser().setEmail(schoolDto.getEmail());
            school.getUser().setMobile(schoolDto.getMobile());
            school.setName(schoolDto.getName());
            school.setDunsNo(schoolDto.getDunsNo());
            school.setFedTaxNo(schoolDto.getFedTaxNo());
            school.setFee(schoolDto.getFee());
            school.setAddress(schoolDto.getAddress());
            school.setWebsite(schoolDto.getWebsite());
            school.setModifiedDate(Instant.now());
            return SchoolDto.of(school);
        });
    }

    @Override
    public Optional<SchoolDto> get(String id) {
        return schoolRepo.findById(id).map(SchoolDto::of);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getCount(String id) {
        School school = schoolRepo.getReferenceById(id);
        long facility = facilityRepo.findAllBySchool(school).size();
        int students = studentRepo.findAllBySchool(school).size();
        AtomicReference<Double> totalFee = new AtomicReference<>((double) 0);
        studentRepo.findAllBySchool(school).forEach(student -> {
            if (student.getFee() != null) {
                totalFee.updateAndGet(v -> v + student.getFee());
            }
        });
        HashMap<String, String> map = new HashMap<>();
        map.put("students", String.valueOf(students));
        map.put("facility", String.valueOf(facility));
        map.put("fee", String.valueOf(totalFee));
        return ResponseEntity.ok(map);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAdminCount() {
        int schools = schoolRepo.findAll().size();
        int students = studentRepo.findAll().size();
        AtomicReference<Double> totalFee = new AtomicReference<>((double) 0);
        studentRepo.findAll().forEach(student -> {
            totalFee.updateAndGet(f -> f + student.getFee());
        });
        HashMap<String, String> map = new HashMap<>();
        map.put("students", String.valueOf(students));
        map.put("schools", String.valueOf(schools));
        map.put("fee", String.valueOf(totalFee));
        return ResponseEntity.ok(map);
    }


    @Override
    @Transactional(readOnly = true)
    public List<SchoolDto> getAll() {
        return schoolRepo.findAll().stream().map(SchoolDto::of).collect(Collectors.toList());
    }

}
