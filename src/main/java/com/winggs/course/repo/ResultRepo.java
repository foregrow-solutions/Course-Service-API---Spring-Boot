package com.winggs.course.repo;

import com.winggs.course.modal.entity.Result;
import com.winggs.course.modal.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepo extends JpaRepository<Result, Integer> {

    List<Result> findAllByUser(User user, Sort sort);
}
