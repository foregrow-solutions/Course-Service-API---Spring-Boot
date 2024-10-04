package com.winggs.course.service.impl;

import com.winggs.course.modal.dto.AttendanceDto;
import com.winggs.course.modal.dto.SchoolFacilityDto;
import com.winggs.course.modal.dto.StudentDto;
import com.winggs.course.modal.entity.*;
import com.winggs.course.modal.enums.FacilityDocType;
import com.winggs.course.modal.enums.Role;
import com.winggs.course.modal.payload.StudentAssignedPayload;
import com.winggs.course.repo.*;
import com.winggs.course.service.FacilityService;
import com.winggs.course.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService {
    private final UserRepo userRepo;
    private final SchoolRepo schoolRepo;
    private final FacilityRepo facilityRepo;
    private final StudentRepo studentRepo;
    private final AttendanceRepo attendanceRepo;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public SchoolFacilityDto addFacility(String schoolId, SchoolFacilityDto schoolFacilityDto) {
        School school = schoolRepo.getReferenceById(schoolId);

        var savedUser = userRepo.findUserByEmail(schoolFacilityDto.getEmail()).orElseGet(() -> {
//            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz23456789!@#$%&?";
//            String rawPswd = RandomStringUtils.random(10, characters);
//            log.info("Raw Password : {}",rawPswd);
            User user = new User();
            user.setEmail(schoolFacilityDto.getEmail());
            user.setMobile(schoolFacilityDto.getMobile());
            user.setFirstName(schoolFacilityDto.getFirstName());
            user.setLastName(schoolFacilityDto.getLastName());
            user.setPassword(passwordEncoder.encode("password"));
            user.setSchool(school);
            user.setRole(Role.SCHOOL_FACILITY);
            return userRepo.save(user);
        });

        Facility schoolFacility = new Facility();
        schoolFacility.setSchool(school);
        schoolFacility.setUser(savedUser);
        schoolFacility.setExperience(schoolFacilityDto.getExp());
        schoolFacility.setLicenseNumber(schoolFacilityDto.getLicenseNumber());
        schoolFacility.setLastCompanyName(schoolFacilityDto.getLastCompanyName());
        schoolFacility.setGender(schoolFacilityDto.getGender());
        schoolFacility.setAddress(schoolFacilityDto.getAddress());
        schoolFacility.setCreatedDate(Instant.now());
        Facility facility = facilityRepo.save(schoolFacility);

        schoolRepo.findById(schoolId).map(school1 -> {
            school1.setUser(savedUser);
            return school1;
        });
        return SchoolFacilityDto.of(facility);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SchoolFacilityDto> get(int id) {
        return facilityRepo.findById(id).map(SchoolFacilityDto::of);
    }

    @Override
    @Transactional
    public Optional<SchoolFacilityDto> update(int id, SchoolFacilityDto schoolFacilityDto) {
        return facilityRepo.findById(id).map(facility -> {
            String s = facility.getUser().getId();
            Optional<User> userOptional = userRepo.findById(s).map(user -> {
                user.setFirstName(schoolFacilityDto.getFirstName());
                user.setLastName(schoolFacilityDto.getLastName());
                user.setEmail(schoolFacilityDto.getEmail());
                user.setMobile(schoolFacilityDto.getMobile());
                return user;
            });
            facility.setLicenseNumber(schoolFacilityDto.getLicenseNumber());
            facility.setExperience(schoolFacilityDto.getExp());
            facility.setGender(schoolFacilityDto.getGender());
            facility.setAddress(schoolFacilityDto.getAddress());

            return SchoolFacilityDto.of(facility);
        });
    }

    @Override
    @Transactional
    public String uploadDoc(int id, FacilityDocType type, MultipartFile file) throws IOException {
        String upload = uploadService.upload(file);
        facilityRepo.findById(id).map(facility -> {
            if (type == FacilityDocType.LICENSE) {
                facility.setLicenseUrl(upload);
            } else {
                facility.setImageUrl(upload);
            }
            return facility;
        });
        return upload;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SchoolFacilityDto> getAllFacility(String schoolId, Pageable pageable) {
        return facilityRepo.findAllBySchool(schoolRepo.getReferenceById(schoolId), pageable).map(SchoolFacilityDto::of);
    }

    @Override
    @Transactional
    public void assignedStudent(int facilityId, StudentAssignedPayload assignedPayload) {

//        Facility facility = facilityRepo.getReferenceById(facilityId);
//        assignedPayload.studentIds().forEach(s -> {
//            studentRepo.findById(s).map(student -> {
//                student.setFacility(facility);
//                return student;
//            });
//        });
//        AssignedStudents assignedStudents = new AssignedStudents();
//        assignedStudents.setSchoolFacility(facility);
//        if (!assignedPayload.studentIds().isEmpty()) {
//            assignedStudents.getStudents().clear();
//            var students = assignedPayload.studentIds().stream().map(studentRepo::getReferenceById).toList();
//            assignedStudents.getStudents().addAll(students);
//        } else {
//            assignedStudents.setStudents(null);
//        }
//        AssignedStudents save = assignedStudentsRepo.save(assignedStudents);

    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getAllStudent(int user) {
        return facilityRepo.findById(user).get().getStudents().stream().map(StudentDto::of).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> getCount(int id) {
        int total = facilityRepo.findById(id).get().getStudents().size();
        HashMap<Object, Object> map = new HashMap<>();
        map.put("total", total);
        return ResponseEntity.ok(map);
    }

    @Override
    public Facility getByUsername(String user) {
        return facilityRepo.findByUser(userRepo.getReferenceById(user));
    }

    @Override
    @Transactional
    public void assign(int facilityId, StudentAssignedPayload assignedPayload) {

        facilityRepo.findById(facilityId).map(facility -> {
            if (!assignedPayload.studentIds().isEmpty()) {
                facility.getStudents().clear();
                var student = assignedPayload.studentIds().stream().map(studentRepo::getReferenceById).toList();
                facility.getStudents().addAll(student);
            } else {
                facility.setStudents(null);
            }
            return facility;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<SchoolFacilityDto> getAllFacilityList(String schoolId) {
        return facilityRepo.findAllBySchool(schoolRepo.getReferenceById(schoolId)).stream().map(SchoolFacilityDto::of).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AttendanceDto markAttendance(String facilityId, AttendanceDto attendanceDto) {
        User reference = userRepo.getReferenceById(facilityId);
        Student student = studentRepo.getReferenceById(attendanceDto.getStudentId());
        Facility user = facilityRepo.findByUser(reference);
        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setFacility(user);
        attendance.setSchool(user.getSchool());
        attendance.setDate(attendanceDto.getDate());
        attendance.setIn(attendanceDto.getIn());
        attendance.setOut(attendanceDto.getOut());
        Attendance save = attendanceRepo.save(attendance);
        return AttendanceDto.of(save);
    }
}
